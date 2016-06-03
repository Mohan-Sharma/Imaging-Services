package com.nzion.zkoss.ext;

import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.nzion.domain.Enumeration;

public class EnumerationRenderer implements ListitemRenderer {

	public void render(Listitem item, Object data,int index) throws Exception {
	if (data instanceof Enumeration) {
		Enumeration enumeration = (Enumeration) data;
		String desc = enumeration.getDescription();
		item.setLabel(desc==null?enumeration.getEnumCode():desc);
		item.setValue(enumeration);
	}
	}

}
