package com.android.rtmp.rtmp.interfaces;

/*
 * Created by Bo on 22.08.2015.
 */

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Stream data packet
 */
public interface IStreamData<T> {

    /**
     * Getter for property 'data'.
     *
     * @return Value for property 'data'
     */
    public ByteBuffer getData();

    /**
     * Creates a byte accurate copy.
     *
     * @return duplicate of the current data item
     * @throws IOException on error
     * @throws ClassNotFoundException on class not found
     */
    public IStreamData<T> duplicate() throws IOException, ClassNotFoundException;

}
