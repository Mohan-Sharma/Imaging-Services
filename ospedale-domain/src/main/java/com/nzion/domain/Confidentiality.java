package com.nzion.domain;

import java.util.Set;


/**
 * @author Sandeep Prusty
 * Dec 1, 2010
 */
public interface Confidentiality {
	
	boolean isConfidential();
	
	void setConfidential(boolean confidential);
	
	Set<Person> getAuthorizedPersons();
	
	Set<Person> getUnAuthorizedPersons();
}
