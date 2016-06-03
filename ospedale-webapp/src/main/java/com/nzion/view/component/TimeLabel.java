package com.nzion.view.component;

import java.util.Date;

import org.zkoss.zul.Label;

import com.nzion.util.UtilDateTime;


/**
 * @author Sandeep Prusty
 * Apr 30, 2011
 */
public class TimeLabel extends Label {

	private static final long serialVersionUID = 1L;
	
	public void setTime(Date time){
	setValue(UtilDateTime.format(time, UtilDateTime.AM_PM_FORMATTER));
	}
}
