package com.nzion.zkoss.ext;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class EnumDropdownRenderer implements ListitemRenderer, ComboitemRenderer {
	
	public void render(Listitem item, Object data,int index) throws Exception {
	item.setValue(data);
	if(data != null){
		String label = data.toString();
		item.setLabel(label);
	}
	}

	public void render(Comboitem item, Object data,int index) throws Exception {
	item.setValue(data);
	if(data != null){
		String label = data.toString();
		item.setLabel(label);
	}
	}
}