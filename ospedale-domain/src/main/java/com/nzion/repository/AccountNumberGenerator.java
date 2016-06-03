package com.nzion.repository;

import java.io.Serializable;


/**
 * @author Sandeep Prusty
 * Apr 14, 2010
 */
public interface AccountNumberGenerator extends Serializable  {
	
	void generateAndSetAccountNumber(Object accountNumberObject, String accountNumberField);
}
