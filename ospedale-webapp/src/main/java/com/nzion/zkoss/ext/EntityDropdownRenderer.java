package com.nzion.zkoss.ext;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.nzion.util.Constants;
import com.nzion.util.UtilReflection;

/**
 * @author Sandeep Prusty
 * Apr 24, 2010
 */
public class EntityDropdownRenderer implements ListitemRenderer, ComboitemRenderer {
	
	private String label;
	
	private final LabelFormatter labelFormatter;
	
	public EntityDropdownRenderer() {
	labelFormatter = DEFAULT_FORMATTER;
	}
	
	public EntityDropdownRenderer(String label) {
	this();
	this.label = label;
	}

	public EntityDropdownRenderer(LabelFormatter labelFormatter) {
	this.labelFormatter = labelFormatter;
	}
		
	public void render(Listitem item, Object data,int index) throws Exception {
	item.setSclass("dropdownClass");
	item.setValue(data);
	if(data != null){
		item.setLabel(labelFormatter.formatLabel(data, this));
	}
	}

	public void render(Comboitem item, Object data,int index) throws Exception {
	item.setValue(data);
	if(data != null){
		item.setLabel(labelFormatter.formatLabel(data, this));
	}
	}

	public static interface LabelFormatter {
		String formatLabel(Object object, EntityDropdownRenderer renderer) throws Exception;
	}
	
	private static final LabelFormatter DEFAULT_FORMATTER = new LabelFormatter() {
		@Override
		public String formatLabel(Object object, EntityDropdownRenderer renderer) throws Exception{
		if(renderer.label == null)
			return object.toString();
		Object value = UtilReflection.getNestedFieldValue(object, renderer.label);
		if(value != null)
			return value.toString();
		return Constants.BLANK;
		}
	};

	public static final EntityDropdownRenderer DEFAULT_INSTANCE = new EntityDropdownRenderer(DEFAULT_FORMATTER);
}