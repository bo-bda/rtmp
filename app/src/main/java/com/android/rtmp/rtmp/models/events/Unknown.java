package com.android.rtmp.rtmp.models.events;

/*
 * Created by Bo on 22.08.2015.
 */

import com.android.rtmp.rtmp.utils.HexDump;
import com.android.rtmp.rtmp.utils.SerializeUtils;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;

/**
 * Unknown event
 */
public class Unknown extends BaseEvent {
    private static final long serialVersionUID = -1352770037962252975L;
    /**
     * Event data
     */
    protected ByteBuffer data;
    /**
     * Type of data
     */
    protected byte dataType;

    public Unknown() {}
    /**
     * Create new unknown event with given data and data type
     * @param dataType             Data type
     * @param data                 Event data
     */
    public Unknown(byte dataType, ByteBuffer data) {
        super(Type.SYSTEM);
        this.dataType = dataType;
        this.data = data;
    }

    /** {@inheritDoc} */
    @Override
    public byte getDataType() {
        return dataType;
    }

    /**
     * Getter for data
     *
     * @return  Data
     */
    public ByteBuffer getData() {
        return data;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        final ByteBuffer buf = getData();
        StringBuffer sb = new StringBuffer();
        sb.append("Size: ");
        sb.append(buf.remaining());
        sb.append(" Data:\n\n");
        sb.append(HexDump.formatHexDump(new String(buf.array())));
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    protected void releaseInternal() {
        if (data != null) {
            data.clear();
            data = null;
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        dataType = in.readByte();
        byte[] byteBuf = (byte[]) in.readObject();
        if (byteBuf != null) {
            data = ByteBuffer.allocate(0);
            SerializeUtils.ByteArrayToByteBuffer(byteBuf, data);
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeByte(dataType);
        if (data != null) {
            out.writeObject(SerializeUtils.ByteBufferToByteArray(data));
        } else {
            out.writeObject(null);
        }
    }
}
