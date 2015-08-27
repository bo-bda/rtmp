package com.android.rtmp.rtmp.interfaces.service;

/*
 * Created by Bo on 22.08.2015.
 */
/**
 * Callback that will be executed when the result of a pending service call
 * has been received.
 *
 * @author The Red5 Project
 * @author Joachim Bauch (jojo@struktur.de)
 *
 */
public interface IPendingServiceCallback {

    /**
     * Triggered when results are recieved
     *
     * @param call Call object this callback is applied to
     */
    public void resultReceived(IPendingServiceCall call);

}
