package com.nzion.view.component;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.nzion.util.UtilDateTime;

public class TimeConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
	return null;
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
	Date d = (Date)val;
	if(val==null)return "";
	return UtilDateTime.format(d, UtilDateTime.AM_PM_FORMATTER);
	}

}
