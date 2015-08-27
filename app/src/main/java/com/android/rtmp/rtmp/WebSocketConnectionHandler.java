package com.android.rtmp.rtmp;

/**
 * Created by Admin on 22.08.2015.
 */
public class WebSocketConnectionHandler implements WebSocket.ConnectionHandler {

    /**
     * Fired when the WebSockets connection has been established.
     * After this happened, messages may be sent.
     */
    public void onOpen() {
    }

    /**
     * Fired when the WebSockets connection has deceased (or could
     * not established in the first place).
     *
     * @param code       Close code.
     * @param reason     Close reason (human-readable).
     */
    public void onClose(int code, String reason) {
    }

    /**
     * Fired when a text message has been received (and text
     * messages are not set to be received raw).
     *
     * @param payload    Text message payload or null (empty payload).
     */
    public void onTextMessage(String payload) {
    }

    /**
     * Fired when a text message has been received (and text
     * messages are set to be received raw).
     *
     * @param payload    Text message payload as raw UTF-8 or null (empty payload).
     */
    public void onRawTextMessage(byte[] payload) {
    }

    /**
     * Fired when a binary message has been received.
     *
     * @param payload    Binar message payload or null (empty payload).
     */
    public void onBinaryMessage(byte[] payload) {
    }

}
