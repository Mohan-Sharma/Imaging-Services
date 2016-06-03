package com.nzion.view.component;

import java.util.Comparator;
import java.util.Map;

public class StringAscendingComparator implements Comparator<Object> {

	@Override
	public int compare(Object object1, Object object2) {
	String stringObj1 = null;
	String stringObj2 = null;
	if (object1 instanceof Map.Entry<?, ?>) {
		Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object1;
		stringObj1 = entry.getKey().toString();
	} else {
		stringObj1 = object1 != null ? object1.toString() : "";
	}
	if (object2 instanceof Map.Entry<?, ?>) {
		Map.Entry<?, ?> entry = (Map.Entry<?, ?>) object1;
		stringObj2 = entry.getKey().toString();
	} else {
		stringObj2 = object2 != null ? object2.toString() : "";
	}

	return String.CASE_INSENSITIVE_ORDER.compare(stringObj1, stringObj2);
	}

}
