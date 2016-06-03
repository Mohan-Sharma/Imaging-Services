package com.nzion.view.component;

import java.text.DateFormat;
import java.util.TimeZone;

import org.zkoss.zul.Timebox;

/**
 * @author Sandeep Prusty
 * May 23, 2011
 */
public class TimezoneIndependantTimebox extends Timebox {
	
	@Override
	protected DateFormat getDateFormat(String fmt) {
	DateFormat formatter = super.getDateFormat(fmt);
	formatter.setTimeZone(TimeZone.getDefault());
	return formatter;
	}

	private static final long serialVersionUID = 1L;
}
