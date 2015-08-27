package com.android.rtmp.rtmp.utils;

import com.android.rtmp.rtmp.interfaces.Constants;
import com.android.rtmp.rtmp.interfaces.events.IEventDecoder;
import com.android.rtmp.rtmp.models.events.ChunkSize;
import com.android.rtmp.rtmp.models.events.Invoke;
import com.android.rtmp.rtmp.models.events.Notify;
import com.android.rtmp.rtmp.models.events.Unknown;
import com.android.rtmp.rtmp.protocol.RTMP;

import java.nio.ByteBuffer;

/*
 * Created by Bo on 22.08.2015.
 */
public class RTMPDecoder implements Constants, IEventDecoder {

    @Override
    public Unknown decodeUnknown(byte dataType, ByteBuffer in) {
        return null;
    }

    @Override
    public ChunkSize decodeChunkSize(ByteBuffer in) {
        return null;
    }

    @Override
    public Notify decodeNotify(Encoding encoding, ByteBuffer in) {
        return null;
    }

    @Override
    public Invoke decodeInvoke(Encoding encoding, ByteBuffer in) {
        return null;
    }

    /**
     * Decodes handshake message.
     *
     * @param rtmp RTMP protocol state
     * @param in   IoBuffer
     * @return IoBuffer
     */
    public ByteBuffer decodeHandshake(RTMP rtmp, ByteBuffer in) {
        RTMPLog.e(this, "decodeHandshake - rtmp: " + rtmp + " buffer: " + in);
        final int remaining = in.remaining();

        if (rtmp.getState() == RTMP.STATE_CONNECT) {
            if (remaining < HANDSHAKE_SIZE + 1) {
                RTMPLog.e("Handshake init too small, buffering. remaining: {}", remaining);
                rtmp.bufferDecoding(HANDSHAKE_SIZE + 1);
            } else {
                final ByteBuffer hs = ByteBuffer.allocate(HANDSHAKE_SIZE);
                in.get(); // skip the header byte
                BufferUtils.put(hs, in, HANDSHAKE_SIZE);
                hs.flip();
                rtmp.setState(RTMP.STATE_HANDSHAKE);
                return hs;
            }
        } else if (rtmp.getState() == RTMP.STATE_HANDSHAKE) {
            RTMPLog.e(this, "Handshake reply");
            if (remaining < HANDSHAKE_SIZE) {
                RTMPLog.e("Handshake reply too small, buffering. remaining: {}", remaining);
                rtmp.bufferDecoding(HANDSHAKE_SIZE);
            } else {
                in.position(HANDSHAKE_SIZE);
                rtmp.setState(RTMP.STATE_CONNECTED);
                rtmp.continueDecoding();
            }
        }
        return null;
    }
}
