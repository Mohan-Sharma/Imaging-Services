package com.nzion.zkoss.ext;

import com.nzion.util.Infrastructure;

public class Meesage {

	public static AbstractMessageReslover messageReslover = Infrastructure.getSpringBean("ecosmosMessageResolver");
	
	public String message(String propertyName){
	return messageReslover.getMessage(propertyName);
	}
}
