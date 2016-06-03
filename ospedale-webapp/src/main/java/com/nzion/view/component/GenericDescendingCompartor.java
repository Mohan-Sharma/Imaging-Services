package com.nzion.view.component;

import java.util.Comparator;

import com.nzion.util.UtilReflection;

public class GenericDescendingCompartor implements Comparator<Object> {

	private String fieldName;

	public GenericDescendingCompartor(String fieldName) {
	this.fieldName = fieldName;
	}

	public int compare(Object o1, Object o2) {
	Object field1 = UtilReflection.getNestedFieldValue(o1, fieldName);
	Object field2 = UtilReflection.getNestedFieldValue(o2, fieldName);
	String field1String = field1 == null ? "" : field1.toString();
	String field2String = field2 == null ? "" : field2.toString();
	return String.CASE_INSENSITIVE_ORDER.compare(field2String, field1String);
	}

}
