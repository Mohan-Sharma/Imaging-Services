package com.nzion.zkoss.ext;

public abstract class AbstractMessageReslover {

	abstract protected String resolveViewName(String viewName);
	
	public String getMessage(String propertyName){
	return resolveViewName(propertyName);
	}
}
