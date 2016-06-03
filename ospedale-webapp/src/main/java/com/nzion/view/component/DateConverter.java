package com.nzion.view.component;

import java.util.Date;

import org.zkoss.zkplus.databind.TypeConverter;

import com.nzion.util.UtilDateTime;

public class DateConverter implements TypeConverter {


	public Object coerceToBean(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
	return null;
	}

	public Object coerceToUi(java.lang.Object val, final org.zkoss.zk.ui.Component comp) {
		Date d = (Date)val;
		if(val==null)return "";
		return UtilDateTime.format(d);
	}

}
