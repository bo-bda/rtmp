package com.android.rtmp.rtmp.interfaces;

/*
 * Created by Bo on 22.08.2015.
 */
/*
 * Class for AMF and RTMP marker values constants
 */
public interface Constants {

    enum Encoding {
        AMF0,
        AMF3
    }

    /**
     * Data originated from a file.
     */
    byte SOURCE_TYPE_VOD = 0x0;

    /**
     * Data originated from a live encoder or stream.
     */
    byte SOURCE_TYPE_LIVE = 0x01;

    /**
     * Medium integer max value
     */
    int MEDIUM_INT_MAX = 16777215;

    /**
     * RTMP chunk size constant
     */
    byte TYPE_CHUNK_SIZE = 0x01;

    /**
     * Abort message
     */
    byte TYPE_ABORT = 0x02;

    /**
     * Acknowledgment. Send every x bytes read by both sides.
     */
    byte TYPE_BYTES_READ = 0x03;

    /**
     * Ping is a stream control message, it has sub-types
     */
    byte TYPE_PING = 0x04;

    /**
     * Server (downstream) bandwidth marker
     */
    byte TYPE_SERVER_BANDWIDTH = 0x05;

    /**
     * Client (upstream) bandwidth marker
     */
    byte TYPE_CLIENT_BANDWIDTH = 0x06;

    // Unknown: 0x07

    /**
     * Audio data marker
     */
    byte TYPE_AUDIO_DATA = 0x08;

    /**
     * Video data marker
     */
    byte TYPE_VIDEO_DATA = 0x09;

    // Unknown: 0x0A ...  0x0E

    /**
     * AMF3 stream send
     */
    byte TYPE_FLEX_STREAM_SEND = 0x0F;

    /**
     * AMF3 shared object
     */
    byte TYPE_FLEX_SHARED_OBJECT = 0x10;

    /**
     * AMF3 message
     */
    byte TYPE_FLEX_MESSAGE = 0x11;

    /**
     * Notification is invocation without response
     */
    byte TYPE_NOTIFY = 0x12;

    /**
     * Stream metadata
     */
    byte TYPE_STREAM_METADATA = 0x12;

    /**
     * Shared Object marker
     */
    byte TYPE_SHARED_OBJECT = 0x13;

    /**
     * Invoke operation (remoting call but also used for streaming) marker
     */
    byte TYPE_INVOKE = 0x14;

    /**
     * Aggregate data marker
     */
    byte TYPE_AGGREGATE = 0x16;

    /**
     * New header marker
     */
    byte HEADER_NEW = 0x00;

    /**
     * Same source marker
     */
    byte HEADER_SAME_SOURCE = 0x01;

    /**
     * Timer change marker
     */
    byte HEADER_TIMER_CHANGE = 0x02;

    /**
     * There's more to encode
     */
    byte HEADER_CONTINUE = 0x03;

    /**
     * Size of initial handshake between client and server
     */
    int HANDSHAKE_SIZE = 1536;

    /**
     * Size of initial handshake between server and client
     */
    int HANDSHAKE_SIZE_SERVER = (Constants.HANDSHAKE_SIZE * 2) + 1;

    /**
     * Client Shared Object data update
     */
    byte SO_CLIENT_UPDATE_DATA = 0x04; //update data

    /**
     * Client Shared Object attribute update
     */
    byte SO_CLIENT_UPDATE_ATTRIBUTE = 0x05; //5: update attribute

    /**
     * Send SO message flag
     */
    byte SO_CLIENT_SEND_MESSAGE = 0x06; // 6: send message

    /**
     * Shared Object status marker
     */
    byte SO_CLIENT_STATUS = 0x07; // 7: status (usually returned with error messages)

    /**
     * Clear event for Shared Object
     */
    byte SO_CLIENT_CLEAR_DATA = 0x08; // 8: clear data

    /**
     * Delete data for Shared Object
     */
    byte SO_CLIENT_DELETE_DATA = 0x09; // 9: delete data

    /**
     * Initial SO data flag
     */
    byte SO_CLIENT_INITIAL_DATA = 0x0B; // 11: initial data

    /**
     * Shared Object connection
     */
    byte SO_CONNECT = 0x01;

    /**
     * Shared Object disconnection
     */
    byte SO_DISCONNECT = 0x02;

    /**
     * Set Shared Object attribute flag
     */
    byte SO_SET_ATTRIBUTE = 0x03;

    /**
     * Send message flag
     */
    byte SO_SEND_MESSAGE = 0x06;

    /**
     * Shared Object attribute deletion flag
     */
    byte SO_DELETE_ATTRIBUTE = 0x0A;

}
