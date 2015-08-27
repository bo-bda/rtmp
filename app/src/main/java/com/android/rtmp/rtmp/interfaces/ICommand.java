package com.android.rtmp.rtmp.interfaces;

/*
 * Created by Bo on 22.08.2015.
 */

import com.android.rtmp.rtmp.interfaces.service.IServiceCall;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * Represents a "command" sent to or received from an end-point.
 *
 * @author Paul Gregoire (mondain@gmail.com)
 */
public interface ICommand {

    int getTransactionId();

    IServiceCall getCall();

    Map<String, Object> getConnectionParams();

    ByteBuffer getData();

}
