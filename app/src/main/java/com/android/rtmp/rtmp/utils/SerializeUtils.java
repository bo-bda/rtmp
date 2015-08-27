package com.android.rtmp.rtmp.utils;

/*
 * Created by Bo on 22.08.2015.
 */

import java.nio.ByteBuffer;

/**
 * The utility class provides conversion methods to ease the use of
 * byte arrays, Mina IoBuffers, and NIO ByteBuffers.
 *
 * @author Paul Gregoire (mondain@gmail.com)
 */
public class SerializeUtils {

    public static byte[] ByteBufferToByteArray(ByteBuffer buf) {
        byte[] byteBuf = new byte[buf.limit()];
        int pos = buf.position();
        buf.rewind();
        buf.get(byteBuf);
        buf.position(pos);
        return byteBuf;
    }

    public static byte[] NioByteBufferToByteArray(ByteBuffer buf) {
        byte[] byteBuf = new byte[buf.limit()];
        int pos = buf.position();
        buf.position(0);
        buf.get(byteBuf);
        buf.position(pos);
        return byteBuf;
    }

    public static void ByteArrayToByteBuffer(byte[] byteBuf, ByteBuffer buf) {
        buf.put(byteBuf);
        buf.flip();
    }

    public static void ByteArrayToNioByteBuffer(byte[] byteBuf, ByteBuffer buf) {
        buf.put(byteBuf);
        buf.flip();
    }

}
