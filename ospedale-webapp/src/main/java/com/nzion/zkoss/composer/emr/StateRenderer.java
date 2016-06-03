package com.nzion.zkoss.composer.emr;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import com.nzion.domain.Enumeration;

public class StateRenderer implements ComboitemRenderer {

	@Override
	public void render(Comboitem item, Object o,int index) throws Exception {
	if (o == null) return;
	Enumeration enumeration = (Enumeration) o;
	item.setLabel(enumeration.getEnumCode());
	item.setValue(enumeration);
	item.setDescription(enumeration.getDescription());
	}
	}
