package com.android.rtmp.rtmp.interfaces.events;

import com.android.rtmp.rtmp.models.messaging.Header;

/*
 * Created by Bo on 22.08.2015.
 */
public interface IRTMPEvent extends IEvent {

    /**
     * Getter for data type
     *
     * @return Data type
     */
    byte getDataType();

    /**
     * Setter for source
     *
     * @param source Source
     */
    void setSource(IEventListener source);

    /**
     * Getter for header
     *
     * @return RTMP packet header
     */
    Header getHeader();

    /**
     * Setter for header
     *
     * @param header RTMP packet header
     */
    void setHeader(Header header);

    /**
     * Getter for timestamp
     *
     * @return Event timestamp
     */
    int getTimestamp();

    /**
     * Setter for timestamp
     *
     * @param timestamp New event timestamp
     */
    void setTimestamp(int timestamp);

    /**
     * Getter for source type
     *
     * @return Source type
     */
    byte getSourceType();

    /**
     * Setter for source type
     *
     * @param sourceType
     */
    void setSourceType(byte sourceType);

    /**
     * Retain event
     */
    void retain();

    /**
     * Hook to free buffers allocated by the event.
     */
    void release();

}
