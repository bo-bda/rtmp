package com.android.rtmp.rtmp.models.messaging;

/*
 * Created by Bo on 22.08.2015.
 */

import com.android.rtmp.rtmp.interfaces.events.IRTMPEvent;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;

/**
 * RTMP packet. Consists of packet header, data and event context.
 */
public class Packet implements Externalizable {

    private static final long serialVersionUID = -6415050845346626950L;

    /**
     * Header
     */
    private Header header;

    /**
     * RTMP event
     */
    private IRTMPEvent message;

    /**
     * Packet data
     */
    private ByteBuffer data;

    public Packet() {
        data = null;
        header = null;
    }
    /**
     * Create packet with given header
     * @param header       Packet header
     */
    public Packet(Header header) {
        this.header = header;
        data = ByteBuffer.allocate(header.getSize());
        // Workaround for SN-19: BufferOverflowException
        // Size is checked in RTMPProtocolDecoder
        //TODO : check overflow buffer and IndexOutOfBoundsException
//        data.setAutoExpand(true);
    }

    /**
     * Create packet with given header and event context
     * @param header     RTMP header
     * @param event      RTMP message
     */
    public Packet(Header header, IRTMPEvent event) {
        this.header = header;
        this.message = event;
    }

    /**
     * Getter for header
     *
     * @return  Packet header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Setter for event context
     *
     * @param message  RTMP event context
     */
    public void setMessage(IRTMPEvent message) {
        this.message = message;
    }

    /**
     * Getter for event context
     *
     * @return RTMP event context
     */
    public IRTMPEvent getMessage() {
        return message;
    }

    /**
     * Setter for data
     *
     * @param data Packet data
     */
    public void setData(ByteBuffer data) {
        this.data = data;
    }

    /**
     * Getter for data
     *
     * @return Packet data
     */
    public ByteBuffer getData() {
        return data;
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        header = (Header) in.readObject();
        message = (IRTMPEvent) in.readObject();
        message.setHeader(header);
        message.setTimestamp(header.getTimer());
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(header);
        out.writeObject(message);
    }
}