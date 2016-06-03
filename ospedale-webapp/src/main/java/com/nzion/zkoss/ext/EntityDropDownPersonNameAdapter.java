package com.nzion.zkoss.ext;

import com.nzion.util.ViewUtil;
import com.nzion.zkoss.ext.EntityDropdownRenderer.LabelFormatter;

/**
 * @author Sandeep Prusty
 * Jul 28, 2010
 */
public class EntityDropDownPersonNameAdapter implements LabelFormatter {

	@Override
	public String formatLabel(Object object, EntityDropdownRenderer renderer) throws Exception {
	String name = ViewUtil.getFormattedName(object);
	return name;
	}
}
