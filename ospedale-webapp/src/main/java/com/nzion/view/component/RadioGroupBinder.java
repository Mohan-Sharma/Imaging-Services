package com.nzion.view.component;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;

public class RadioGroupBinder implements TypeConverter {

	public Object coerceToBean(Object val, Component comp) {
	if(((Radiogroup) comp).getSelectedItem() == null)
		return null;
	return ((Radiogroup) comp).getSelectedItem().getValue();
	}

	public Object coerceToUi(Object val, Component comp) {
	List<Radio> radioBtns = ((Radiogroup) comp).getItems();
	for (Radio radio : radioBtns) {
		if(val == null)
			continue;
		if (radio.getValue().equals(val.toString())) {
			int index = radioBtns.indexOf(radio);
			((Radiogroup)comp).setSelectedIndex(index);
			return index;
		}
	}
	return null;
	}
}