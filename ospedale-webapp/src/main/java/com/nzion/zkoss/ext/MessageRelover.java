package com.nzion.zkoss.ext;

import java.io.IOException;
import java.util.Properties;

public class MessageRelover extends AbstractMessageReslover{
	
	private final Properties properties = new Properties();
	
	public void setBasename(String basename) {
	ClassLoader bundleClassLoader = Thread.currentThread().getContextClassLoader();
	try {
		properties.load(bundleClassLoader.getResourceAsStream(basename));
	} catch (IOException e) {
		throw new RuntimeException(e);
	}
	}

	@Override
	public String getMessage(String propertyName){
	return properties.getProperty(propertyName);
	}

	@Override
	protected String resolveViewName(String viewName) {
	return properties.getProperty(viewName);
	}

}
