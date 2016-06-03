package com.nzion.view.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class BooleanToStringConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object arg0, Component arg1) {
	if(arg0 == null) return null;
	return "Yes".equals(arg0) ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public Object coerceToUi(Object arg0, Component arg1) {
	if(arg0 == null) return null;
	return arg0.equals(Boolean.TRUE) ? "Yes" : "No" ;
	}
}
