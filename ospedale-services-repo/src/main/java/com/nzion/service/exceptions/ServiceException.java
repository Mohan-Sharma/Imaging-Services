/*
 * header file
 */
package com.nzion.service.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class EcosmosBaseRuntimeException.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */
public class ServiceException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ecosmos base runtime exception.
     */
    public ServiceException() {
	super();
    }

    /**
     * Instantiates a new ecosmos base runtime exception.
     * 
     * @param message
     *            the message
     */
    public ServiceException(String message) {
	super(message);
    }

    /**
     * Instantiates a new ecosmos base runtime exception.
     * 
     * @param message
     *            the message
     * @param t
     *            the t
     */
    public ServiceException(String message, Throwable t) {
	super(message, t);
    }

    /**
     * Instantiates a new ecosmos base runtime exception.
     * 
     * @param t
     *            the t
     */
    public ServiceException(Throwable t) {
	super(t);
    }
}
