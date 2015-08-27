package com.android.rtmp.rtmp.utils;

/*
 * Created by Bo on 22.08.2015.
 */

import java.nio.ByteBuffer;

/**
 * Buffer Utility class which reads/writes intergers to the input/output buffer
 *
 * @author The Red5 Project (red5@osflash.org)
 * @author Luke Hubbard, Codegent Ltd (luke@codegent.com)
 * @author Andy Shaules (bowljoman@hotmail.com)
 */
public class BufferUtils {

    //private static Logger log = LoggerFactory.getLogger(BufferUtils.class);

    /**
     * Writes a Medium Int to the output buffer
     *
     * @param out          Container to write to
     * @param value        Integer to write
     */
    public static void writeMediumInt(ByteBuffer out, int value) {
        byte[] bytes = new byte[3];
        bytes[0] = (byte) ((value >>> 16) & 0x000000FF);
        bytes[1] = (byte) ((value >>> 8) & 0x000000FF);
        bytes[2] = (byte) (value & 0x00FF);
        out.put(bytes);
    }

    /**
     * Reads an unsigned Medium Int from the in buffer
     *
     * @param in           Source
     * @return int         Integer value
     */
    public static int readUnsignedMediumInt(ByteBuffer in) {
        byte[] bytes = new byte[3];
        in.get(bytes);
        int val = 0;
        val += (bytes[0] & 0xFF) * 256 * 256;
        val += (bytes[1] & 0xFF) * 256;
        val += (bytes[2] & 0xFF);
        return val;
    }

    /**
     * Reads a Medium Int to the in buffer
     *
     * @param in           Source
     * @return int         Medium int
     */
    public static int readMediumInt(ByteBuffer in) {
        byte[] bytes = new byte[3];
        in.get(bytes);
        int val = 0;
        val += bytes[0] * 256 * 256;
        val += bytes[1] * 256;
        val += bytes[2];
        if (val < 0) {
            val += 256;
        }
        return val;
    }

    /**
     * Puts input buffer stream to output buffer
     * and returns number of bytes written
     * @param out                Output buffer
     * @param in                 Input buffer
     * @param numBytesMax        Number of bytes max
     * @return int               Number of bytes written
     */
    @SuppressWarnings("unused")
    public static int put(ByteBuffer out, ByteBuffer in, int numBytesMax) {
        //Log.e("Put - out buffer: {} in buffer: {} max bytes: {}", new Object[]{out, in, numBytesMax});
        int limit = in.limit();
        int capacity = in.capacity();
        int numBytesRead = (numBytesMax > in.remaining()) ? in.remaining() : numBytesMax;
        //Log.e("limit: {} capacity: {} bytes read: {}", new Object[]{limit, capacity, numBytesRead});
        // buffer.limit
        // The new limit value, must be non-negative and no larger than this buffer's capacity
        // http://java.sun.com/j2se/1.4.2/docs/api/java/nio/Buffer.html#limit(int);
        // This is causing decoding error by raising RuntimeException IllegalArgumentError in
        // RTMPProtocolDecoder.decode to ProtocolException.
        int thisLimit = (in.position() + numBytesRead <= in.capacity()) ? in.position() + numBytesRead : capacity;
        //somehow the "in" buffer becomes null here occasionally
        in.limit(thisLimit);
        // any implication to giving output buffer in with limit set to capacity?
        // Reduces numBytesRead, triggers continueDecode?
        out.put(in);
        in.limit(limit);
        return numBytesRead;
    }

}
