package com.android.rtmp.rtmp.interfaces.events;

/*
 * Created by Bo on 22.08.2015.
 */

import com.android.rtmp.rtmp.interfaces.Constants;
import com.android.rtmp.rtmp.models.events.ChunkSize;
import com.android.rtmp.rtmp.models.events.Invoke;
import com.android.rtmp.rtmp.models.events.Notify;
import com.android.rtmp.rtmp.models.events.Unknown;

import java.nio.ByteBuffer;

/**
 * Event decoder decodes event objects from incoming byte buffer.
 */
public interface IEventDecoder {
    //TODO: impements other data

    /**
     * Decodes event of Unknown type.
     *
     * @param dataType               Data type
     * @param in                     Byte buffer to decode
     * @return                       Unknown event
     */
    Unknown decodeUnknown(byte dataType, ByteBuffer in);

    /**
     * Decodes chunk size event.
     *
     * @param in                     Byte buffer to decode
     * @return                       ChunkSize event
     */
     ChunkSize decodeChunkSize(ByteBuffer in);

//    /**
//     * Decodes shared object message event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       ISharedObjectMessage event
//     */
//     ISharedObjectMessage decodeSharedObject(ByteBuffer in);
//
//    /**
//     * Decodes shared object message event from AMF3 encoding.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       ISharedObjectMessage event
//     */
//     ISharedObjectMessage decodeFlexSharedObject(ByteBuffer in);

    /**
     * Decode a Notify.
     *
     * @param encoding encoding
     * @param in input buffer
     * @return decoded notify result
     */
     Notify decodeNotify(Constants.Encoding encoding, ByteBuffer in);

    /**
     * Decodes invocation event.
     *
     * @param encoding encoding
     * @param in                     Byte buffer to decode
     * @return                       Invoke event
     */
     Invoke decodeInvoke(Constants.Encoding encoding, ByteBuffer in);

//    /**
//     * Decodes ping event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       Ping event
//     */
//     Ping decodePing(ByteBuffer in);
//
//    /**
//     * Decodes BytesRead event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       BytesRead event
//     */
//     BytesRead decodeBytesRead(ByteBuffer in);
//
//    /**
//     * Decodes the aggregated data.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       Aggregate event
//     */
//     Aggregate decodeAggregate(ByteBuffer in);
//
//    /**
//     * Decodes audio data event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       AudioData event
//     */
//     AudioData decodeAudioData(ByteBuffer in);
//
//    /**
//     * Decodes video data event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       VideoData event
//     */
//     VideoData decodeVideoData(ByteBuffer in);
//
//    /**
//     * Decodes Flex message event.
//     *
//     * @param in                     Byte buffer to decode
//     * @return                       FlexMessage event
//     */
//     FlexMessage decodeFlexMessage(ByteBuffer in);

}
