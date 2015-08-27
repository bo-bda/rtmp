package com.android.rtmp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.android.rtmp.rtmp.WebSocket;
import com.android.rtmp.rtmp.WebSocketConnection;
import com.android.rtmp.rtmp.WebSocketConnectionHandler;
import com.android.rtmp.rtmp.models.events.Invoke;
import com.android.rtmp.rtmp.models.service.PendingCall;
import com.android.rtmp.rtmp.utils.RTMPLog;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final WebSocket mConnection = new WebSocketConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RTMPLog.setIsDebug(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
//                    final String host = "rtmp://ntv.cdnvideo.ru";
                    final String host = "rtmp://fms.dev.shindigevents.com:1935/shindigCommunicationAPINew";
                    final int port = 1935;
                    final HashMap<String, Object> params = new HashMap<>();
                    params.put("app", "shindigCommunicationAPINew");
                    params.put("objectEncoding", 0);
                    params.put("fpad", Boolean.FALSE);
                    params.put("flashVer", "FMLE/3.0: CocoaRTMP framework 4.0.b2738e4 - Professional [2015-04-20] (compatible; FMSc/1.0)");
                    params.put("audioCodecs", 3191);
                    params.put("videoFunction", 1);
                    params.put("capabilities", 15);
                    params.put("videoCodecs", 252);

                    mConnection.connect(host, new WebSocketConnectionHandler() {
                        @Override
                        public void onOpen() {
                            RTMPLog.e(this, "onOpen");
                            PendingCall pendingCall = new PendingCall("connect");
                            pendingCall.setArguments(null);
                            Invoke invoke = new Invoke(pendingCall);
                            invoke.setConnectionParams(params);
                            mConnection.sendRawTextMessage(invoke.toString().getBytes());
                        }

                        @Override
                        public void onClose(int code, String reason) {
                            super.onClose(code, reason);
                        }

                        @Override
                        public void onTextMessage(String payload) {
                            super.onTextMessage(payload);
                        }

                        @Override
                        public void onRawTextMessage(byte[] payload) {
                            mConnection.sendRawTextMessage(payload);
                        }

                        @Override
                        public void onBinaryMessage(byte[] payload) {
                            super.onBinaryMessage(payload);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
