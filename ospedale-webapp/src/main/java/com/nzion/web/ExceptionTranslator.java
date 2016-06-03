package com.nzion.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.hibernate.JDBCException;

/**
 * @author Sandeep Prusty
 * May 27, 2011
 */
public class ExceptionTranslator {
	
	public static String translateException(Throwable throwable){
//	if(throwable.getCause() instanceof JDBCException)
//		return translateException((ConstraintViolationException)throwable);
//	if(throwable instanceof DataAccessException)
//		return translateException((JDBCException)throwable.getCause());
	String message = throwable.getMessage();
	while(message == null && throwable != null){
		throwable = throwable.getCause();
		message = throwable.getMessage();
        throwable.printStackTrace();
	}
	return message;
	}

	private static final Properties ERROR_MESSAGES;
	
	public static final String CONSTRAINT = "CONSTRAINT.";
	
	static {
	ERROR_MESSAGES = new Properties();
	try {
		ERROR_MESSAGES.load(new InputStreamReader( Thread.currentThread().getContextClassLoader().getResourceAsStream("error_translation.properties")));
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
	}
	
	public static String translateException(JDBCException hibernateConstraintViolationException){
	return hibernateConstraintViolationException.getSQLException().getMessage();
	
//	return ERROR_MESSAGES.getProperty(CONSTRAINT+hibernateConstraintViolationException.getConstraintName());
	}
}