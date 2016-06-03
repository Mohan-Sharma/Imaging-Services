package com.nzion.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 12/24/12
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionException extends Exception {

    public TransactionException(){}

    public TransactionException(String message){
        super(message);
    }

}
