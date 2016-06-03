package com.nzion.view.component;

import org.zkoss.zul.Label;

import com.nzion.util.ViewUtil;

/**
 * @author Sandeep Prusty
 * Jun 25, 2010
 */
public class NameLabel extends Label {
	
	public NameLabel() {}
	
	public NameLabel(Object object) {
	setObject(object);
	}

	public void setObject(Object object) {
	if(object == null)
		return;
	setValue(ViewUtil.getFormattedName(object));
	}

	private static final long serialVersionUID = 1L;
}
