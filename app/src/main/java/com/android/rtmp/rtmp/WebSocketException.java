package com.android.rtmp.rtmp;

/**
 * Created by Admin on 22.08.2015.
 */
public class WebSocketException extends Exception {

    private static final long serialVersionUID = 1L;

    public WebSocketException(String message) {
        super(message);
    }

    public WebSocketException(String message, Throwable t) {
        super(message, t);
    }
}
