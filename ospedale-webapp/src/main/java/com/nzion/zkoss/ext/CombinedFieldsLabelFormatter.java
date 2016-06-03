/**
 * @author shwetha
 * Oct 28, 2010 
 */
/**
 * @author shwetha
 * Oct 28, 2010 
 */
package com.nzion.zkoss.ext;

import com.nzion.util.UtilReflection;
import com.nzion.zkoss.ext.EntityDropdownRenderer.LabelFormatter;

/**
 * @author shwetha
 * Oct 28, 2010
 */
public class CombinedFieldsLabelFormatter implements LabelFormatter{

	private String[] fields;
	
	public CombinedFieldsLabelFormatter(String ...args) {
	fields = args;
	}
	
	@Override
	public String formatLabel(Object object, EntityDropdownRenderer renderer) throws Exception {
	StringBuilder label = new StringBuilder();
	for(String field : fields)
		label.append((String) UtilReflection.getGetterMethod(field, object.getClass()).invoke(object, (Object[])null)).append(" ");
	return label.toString();
	}
}
