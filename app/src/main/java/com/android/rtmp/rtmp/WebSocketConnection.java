package com.android.rtmp.rtmp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import com.android.rtmp.rtmp.utils.RTMPLog;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Admin on 22.08.2015.
 */
public class WebSocketConnection implements WebSocket {

    private static final String TAG = WebSocketConnection.class.getName();

    protected static Handler mMasterHandler;

    protected WebSocketReader mReader;
    protected WebSocketWriter mWriter;
    protected HandlerThread mWriterThread;

    protected SocketChannel mTransportChannel;

    private URI mWsUri;
    private String mWsScheme;
    private String mWsHost;
    private int mWsPort;
    private String mWsPath;
    private String mWsQuery;
    private String[] mWsSubprotocols;
    private HashMap mWsHeaders;

    private static WebSocket.ConnectionHandler mWsHandler;

    protected WebSocketOptions mOptions;

    private boolean mActive;
    private boolean mPrevConnected;

    /**
     * Asynchronous socket connector.
     */
    private class WebSocketConnector extends Thread {

        public void run() {
            Thread.currentThread().setName("WebSocketConnector");

			/*
             * connect TCP socket
			 */
            try {
                mTransportChannel = SocketChannel.open();

                // the following will block until connection was established or
                // an error occurred!
                mTransportChannel.socket().connect(
                        new InetSocketAddress(mWsHost, mWsPort), mOptions.getSocketConnectTimeout());

                // before doing any data transfer on the socket, set socket
                // options
                mTransportChannel.socket().setSoTimeout(mOptions.getSocketReceiveTimeout());
                mTransportChannel.socket().setTcpNoDelay(mOptions.getTcpNoDelay());

            } catch (IOException e) {
                onClose(WebSocketConnectionHandler.CLOSE_CANNOT_CONNECT, e.getMessage());
                return;
            }

            if (mTransportChannel.isConnected()) {

                try {

                    // create & start WebSocket reader
                    createReader();

                    // create & start WebSocket writer
                    createWriter();

                    // start rtmp handshake

                    WebSocketMessage.RawTextMessage rawTextMessage = new WebSocketMessage.RawTextMessage(generateClientRequest1());
                    mWriter.forward(rawTextMessage);

                    mPrevConnected = true;

                } catch (Exception e) {
                    onClose(WebSocketConnectionHandler.CLOSE_INTERNAL_ERROR, e.getMessage());
                }
            } else {
                onClose(WebSocketConnectionHandler.CLOSE_CANNOT_CONNECT,                        "Could not connect to WebSocket server");
            }
        }

    }

    public WebSocketConnection() {
        RTMPLog.e(TAG, "created");

        // create WebSocket master handler
        createHandler();

        // set initial values
        mActive = false;
        mPrevConnected = false;
    }


    public void sendTextMessage(String payload) {
        mWriter.forward(new WebSocketMessage.TextMessage(payload));
    }


    public void sendRawTextMessage(byte[] payload) {
        mWriter.forward(new WebSocketMessage.RawTextMessage(payload));
    }


    public void sendBinaryMessage(byte[] payload) {
        mWriter.forward(new WebSocketMessage.BinaryMessage(payload));
    }


    public boolean isConnected() {
        return mTransportChannel != null && mTransportChannel.isConnected();
    }


    private void failConnection(int code, String reason) {

        RTMPLog.e(TAG, "fail connection [code = " + code + ", reason = " + reason);

        if (mReader != null) {
            mReader.quit();
            try {
                mReader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //mReader = null;
        } else {
            RTMPLog.e(TAG, "mReader already NULL");
        }

        if (mWriter != null) {
            //mWriterThread.getLooper().quit();
            mWriter.forward(new WebSocketMessage.Quit());
            try {
                mWriterThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //mWriterThread = null;
        } else {
            RTMPLog.e(TAG, "mWriter already NULL");
        }

        if (mTransportChannel != null) {
            try {
                mTransportChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //mTransportChannel = null;
        } else {
            RTMPLog.e(TAG, "mTransportChannel already NULL");
        }

        onClose(code, reason);

        RTMPLog.e(TAG, "worker threads stopped");
    }


    public void connect(String wsUri, WebSocket.ConnectionHandler wsHandler) throws WebSocketException {
        connect(wsUri, null, wsHandler, new WebSocketOptions(), null);
    }


    public void connect(String wsUri, WebSocket.ConnectionHandler wsHandler, WebSocketOptions options) throws WebSocketException {
        connect(wsUri, null, wsHandler, options, null);
    }


    public void connect(String wsUri, String[] wsSubprotocols, WebSocket.ConnectionHandler wsHandler, WebSocketOptions options, HashMap headers) throws WebSocketException {

        // don't connect if already connected .. user needs to disconnect first
        //
        if (mTransportChannel != null && mTransportChannel.isConnected()) {
            throw new WebSocketException("already connected");
        }

        // parse WebSockets URI
        //
        try {
            mWsUri = new URI(wsUri);

//            if (!mWsUri.getScheme().equals("ws") && !mWsUri.getScheme().equals("wss")) {
//                throw new WebSocketException("unsupported scheme for WebSockets URI");
//            }
//
//            if (mWsUri.getScheme().equals("wss")) {
//                throw new WebSocketException("secure WebSockets not implemented");
//            }

            mWsScheme = mWsUri.getScheme();

            if (mWsUri.getPort() == -1) {
                if (mWsScheme.equals("ws")) {
                    mWsPort = 80;
                } else {
                    mWsPort = 443;
                }
            } else {
                mWsPort = mWsUri.getPort();
            }

            if (mWsUri.getHost() == null) {
                throw new WebSocketException("no host specified in WebSockets URI");
            } else {
                mWsHost = mWsUri.getHost();
            }

            if (mWsUri.getRawPath() == null || mWsUri.getRawPath().equals("")) {
                mWsPath = "/";
            } else {
                mWsPath = mWsUri.getRawPath();
            }

            if (mWsUri.getRawQuery() == null || mWsUri.getRawQuery().equals("")) {
                mWsQuery = null;
            } else {
                mWsQuery = mWsUri.getRawQuery();
            }

        } catch (URISyntaxException e) {

            throw new WebSocketException("invalid WebSockets URI");
        }

        mWsSubprotocols = wsSubprotocols;
        mWsHeaders = headers;
        mWsHandler = wsHandler;

        // make copy of options!
        mOptions = new WebSocketOptions(options);

        // set connection active
        mActive = true;

        // use asynch connector on short-lived background thread
        new WebSocketConnector().start();
    }


    public void disconnect() {
        if (mWriter != null) {
            mWriter.forward(new WebSocketMessage.Close(1000));
        } else {
            RTMPLog.e(TAG, "could not send Close .. writer already NULL");
        }
        if (mReader != null) {
            mReader.quit();
        } else {
            RTMPLog.e(TAG, "could not send Close .. reader already NULL");
        }
        mActive = false;
        mPrevConnected = false;
    }

    /**
     * Reconnect to the server with the latest options
     *
     * @return true if reconnection performed
     */
    public boolean reconnect() {
        if (!isConnected() && (mWsUri != null)) {
            new WebSocketConnector().start();
            return true;
        }
        return false;
    }

    /**
     * Perform reconnection
     *
     * @return true if reconnection was scheduled
     */
    protected boolean scheduleReconnect() {
        /**
         * Reconnect only if:
         *  - connection active (connected but not disconnected)
         *  - has previous success connections
         *  - reconnect interval is set
         */
        int interval = mOptions.getReconnectInterval();
        boolean need = mActive && mPrevConnected && (interval > 0);
        if (need) {
            RTMPLog.e(TAG, "Reconnection scheduled");
            mMasterHandler.postDelayed(new Runnable() {

                public void run() {
                    RTMPLog.e(TAG, "Reconnecting...");
                    reconnect();
                }
            }, interval);
        }
        return need;
    }

    /**
     * Common close handler
     *
     * @param code   Close code.
     * @param reason Close reason (human-readable).
     */
    private void onClose(int code, String reason) {
        boolean reconnecting = false;

        if ((code == ConnectionHandler.CLOSE_CANNOT_CONNECT) ||
                (code == ConnectionHandler.CLOSE_CONNECTION_LOST)) {
            reconnecting = scheduleReconnect();
        }


        if (mWsHandler != null) {
            try {
                if (reconnecting) {
                    mWsHandler.onClose(ConnectionHandler.CLOSE_RECONNECT, reason);
                } else {
                    mWsHandler.onClose(code, reason);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //mWsHandler = null;
        } else {
            RTMPLog.e(TAG, "mWsHandler already NULL");
        }
    }


    /**
     * Create master message handler.
     */
    protected void createHandler() {

        mMasterHandler = new Handler(Looper.getMainLooper()) {

            public void handleMessage(Message msg) {

                if (msg.obj instanceof WebSocketMessage.TextMessage) {

                    WebSocketMessage.TextMessage textMessage = (WebSocketMessage.TextMessage) msg.obj;

                    if (mWsHandler != null) {
                        mWsHandler.onTextMessage(textMessage.mPayload);
                    } else {

                        RTMPLog.e(TAG, "could not call onTextMessage() .. handler already NULL");
                    }

                } else if (msg.obj instanceof WebSocketMessage.RawTextMessage) {

                    WebSocketMessage.RawTextMessage rawTextMessage = (WebSocketMessage.RawTextMessage) msg.obj;

                    if (mWsHandler != null) {
                        mWsHandler.onRawTextMessage(rawTextMessage.mPayload);
                    } else {

                        RTMPLog.e(TAG, "could not call onRawTextMessage() .. handler already NULL");
                    }

                } else if (msg.obj instanceof WebSocketMessage.BinaryMessage) {

                    WebSocketMessage.BinaryMessage binaryMessage = (WebSocketMessage.BinaryMessage) msg.obj;

                    if (mWsHandler != null) {
                        mWsHandler.onBinaryMessage(binaryMessage.mPayload);
                    } else {

                        RTMPLog.e(TAG, "could not call onBinaryMessage() .. handler already NULL");
                    }

                } else if (msg.obj instanceof WebSocketMessage.Ping) {

                    WebSocketMessage.Ping ping = (WebSocketMessage.Ping) msg.obj;
                    RTMPLog.e(TAG, "WebSockets Ping received");

                    // reply with Pong
                    WebSocketMessage.Pong pong = new WebSocketMessage.Pong();
                    pong.mPayload = ping.mPayload;
                    mWriter.forward(pong);

                } else if (msg.obj instanceof WebSocketMessage.Pong) {

                    @SuppressWarnings("unused")
                    WebSocketMessage.Pong pong = (WebSocketMessage.Pong) msg.obj;

                    RTMPLog.e(TAG, "WebSockets Pong received");

                } else if (msg.obj instanceof WebSocketMessage.Close) {

                    WebSocketMessage.Close close = (WebSocketMessage.Close) msg.obj;


                    RTMPLog.e(TAG, "WebSockets Close received (" + close.mCode + " - " + close.mReason + ")");

                    final int tavendoCloseCode = (close.mCode == 1000) ? ConnectionHandler.CLOSE_NORMAL : ConnectionHandler.CLOSE_CONNECTION_LOST;

                    if (mActive) {
                        mWriter.forward(new WebSocketMessage.Close(1000));
                    } else {
                        // we've initiated disconnect, so ready to close the channel
                        try {
                            mTransportChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    onClose(tavendoCloseCode, close.mReason);

                } else if (msg.obj instanceof WebSocketMessage.ServerHandshake) {

                    WebSocketMessage.ServerHandshake serverHandshake = (WebSocketMessage.ServerHandshake) msg.obj;

                    RTMPLog.e(TAG, "opening handshake received");

                    if (serverHandshake.mSuccess) {
                        if (mWsHandler != null) {
                            mWsHandler.onOpen();
                        } else {

                            RTMPLog.e(TAG, "could not call onOpen() .. handler already NULL");
                        }
                    }

                } else if (msg.obj instanceof WebSocketMessage.ConnectionLost) {

                    @SuppressWarnings("unused")
                    WebSocketMessage.ConnectionLost connnectionLost = (WebSocketMessage.ConnectionLost) msg.obj;
                    failConnection(WebSocketConnectionHandler.CLOSE_CONNECTION_LOST, "WebSockets connection lost");

                } else if (msg.obj instanceof WebSocketMessage.ProtocolViolation) {

                    @SuppressWarnings("unused")
                    WebSocketMessage.ProtocolViolation protocolViolation = (WebSocketMessage.ProtocolViolation) msg.obj;
                    failConnection(WebSocketConnectionHandler.CLOSE_PROTOCOL_ERROR, "WebSockets protocol violation");

                } else if (msg.obj instanceof WebSocketMessage.Error) {

                    WebSocketMessage.Error error = (WebSocketMessage.Error) msg.obj;
                    failConnection(WebSocketConnectionHandler.CLOSE_INTERNAL_ERROR, "WebSockets internal error (" + error.mException.toString() + ")");

                } else if (msg.obj instanceof WebSocketMessage.ServerError) {

                    WebSocketMessage.ServerError error = (WebSocketMessage.ServerError) msg.obj;
                    failConnection(WebSocketConnectionHandler.CLOSE_SERVER_ERROR, "Server error " + error.mStatusCode + " (" + error.mStatusMessage + ")");

                } else {

                    processAppMessage(msg.obj);

                }
            }
        };
    }


    protected void processAppMessage(Object message) {
    }


    /**
     * Create WebSockets background writer.
     */
    protected void createWriter() {

        mWriterThread = new HandlerThread("WebSocketWriter");
        mWriterThread.start();
        mWriter = new WebSocketWriter(mWriterThread.getLooper(), mMasterHandler, mTransportChannel, mOptions);

        RTMPLog.e(TAG, "WS writer created and started");
    }


    /**
     * Create WebSockets background reader.
     */
    protected void createReader() {

        mReader = new WebSocketReader(mMasterHandler, mTransportChannel, mOptions, "WebSocketReader");
        mReader.start();

        RTMPLog.e(TAG, "WS reader created and started");
    }

    public byte[] generateClientRequest1() {
        ByteBuffer buffer = ByteBuffer.allocate(1536 + 1);
        byte[] handshakeBytes = new byte[1536];
        handshakeBytes[4] = 0;
        handshakeBytes[5] = 0;
        handshakeBytes[6] = 0;
        handshakeBytes[7] = 0;
        // flash player version 9.0.124.2
        handshakeBytes[0] = 85;
        handshakeBytes[1] = (byte) 196;
        handshakeBytes[2] = (byte) 132;
        handshakeBytes[3] = 102;
        byte[] rndBytes = new byte[1536 - 8];
        Random random = new Random();
        random.nextBytes(rndBytes);
        System.arraycopy(rndBytes, 0, handshakeBytes, 8, (1536 - 8));
        buffer.put((byte) (0x03));
        buffer.put(handshakeBytes);
        buffer.flip();
        return buffer.array();
    }
}
