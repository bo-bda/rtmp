package com.android.rtmp.rtmp.interfaces.service;

/*
 * Created by Bo on 23.08.2015.
 */
/**
 * Container for a Service Call 
 */
public interface IServiceCall {

    /**
     * Whether call was successful or not
     *
     * @return	<code>true</code> on success, <code>false</code> otherwise
     */
     boolean isSuccess();

    /**
     * Returns service method name 
     *
     * @return	Service method name as string
     */
     String getServiceMethodName();

    /**
     * Returns service name
     *
     * @return	Service name
     */
     String getServiceName();

    /**
     * Returns array of service method arguments
     *
     * @return	array of service method arguments
     */
     Object[] getArguments();

    /**
     * Get service call status
     *
     * @return	service call status
     */
     byte getStatus();

    /**
     * Get service call exception
     *
     * @return	service call exception
     */
     Exception getException();

    /**
     * Sets status
     *
     * @param status Status as byte
     */
     void setStatus(byte status);

    /**
     * Sets exception
     *
     * @param exception Call exception
     */
     void setException(Exception exception);

}
