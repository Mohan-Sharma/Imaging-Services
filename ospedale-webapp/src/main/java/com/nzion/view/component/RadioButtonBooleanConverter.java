package com.nzion.view.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class RadioButtonBooleanConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
	return  val.equals(1) ? true : false;
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
	return (Boolean)val ? 1 : 0;
	}
}
