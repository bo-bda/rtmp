package com.android.rtmp.rtmp.models.events;

/*
 * Created by Bo on 22.08.2015.
 */

import com.android.rtmp.rtmp.interfaces.service.IPendingServiceCall;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Remote invocation event
 */
public class Invoke extends Notify {

    private static final long serialVersionUID = -769677790148010729L;

    /** Constructs a new Invoke. */
    public Invoke() {
        super();
        dataType = TYPE_INVOKE;
    }

    /**
     * Create new invocation event with given data
     * @param data        Event data
     */
    public Invoke(ByteBuffer data) {
        super(data);
        dataType = TYPE_INVOKE;
    }

    /**
     * Create new invocation event with given pending service call
     * @param call         Pending call
     */
    public Invoke(IPendingServiceCall call) {
        super(call);
        dataType = TYPE_INVOKE;
    }

    /**
     * Setter for transaction id
     *
     * @param transactionId the transactionId to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /** {@inheritDoc} */
    @Override
    public IPendingServiceCall getCall() {
        return (IPendingServiceCall) call;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("Invoke #%d: %s", transactionId, (call != null ? call.toString() : "null"));
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Invoke)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Duplicate this Invoke message to future injection. Serialize to memory and deserialize, safe way.
     *
     * @return duplicated Invoke event
     */
    @Override
    public Invoke duplicate() throws IOException, ClassNotFoundException {
        Invoke result = new Invoke();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        writeExternal(oos);
        oos.close();

        byte[] buf = baos.toByteArray();
        baos.close();

        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        ObjectInputStream ois = new ObjectInputStream(bais);

        result.readExternal(ois);
        ois.close();
        bais.close();

        return result;
    }

}