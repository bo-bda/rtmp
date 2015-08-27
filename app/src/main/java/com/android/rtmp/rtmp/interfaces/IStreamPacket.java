package com.android.rtmp.rtmp.interfaces;

/*
 * Created by Bo on 22.08.2015.
 */

import java.nio.ByteBuffer;

/**
 * Packet containing stream data.
 *
 * @author The Red5 Project
 * @author Joachim Bauch (jojo@struktur.de)
 */
public interface IStreamPacket {

    /**
     * Type of this packet. This is one of the <pre>TYPE_</pre> constants.
     *
     * @return the type
     */
    public byte getDataType();

    /**
     * Timestamp of this packet.
     *
     * @return the timestamp in milliseconds
     */
    public int getTimestamp();

    /**
     * Packet contents.
     *
     * @return the contents
     */
    public ByteBuffer getData();

}
