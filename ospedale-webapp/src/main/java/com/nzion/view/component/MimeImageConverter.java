package com.nzion.view.component;

import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;

public class MimeImageConverter implements TypeConverter {
	public Object coerceToBean(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
	return null;
	}

	public Object coerceToUi(java.lang.Object val, final org.zkoss.zk.ui.Component comp) {
	final Listcell lc = ((Listcell) comp);
	String mimeType = (String) val;
	if (mimeType == null) return null;
	String mime = "";
	Image img = new Image("/images/mimeicons/file-" + mime + ".png");
	img.setWidth("15px");
	img.setHeight("15px");
	img.setParent(lc);
	return null;
	}
}
