package com.nzion.util;

import java.util.List;

import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zkplus.databind.BindingListModelList;

import com.nzion.service.common.EnumerationService;

public class EnumerationUtil {

	public static BindingListModel getEnum(String enumType) {
	EnumerationService service = (EnumerationService) org.zkoss.zkplus.spring.SpringUtil.getBean("enumerationService");
	List<?> l;
	if("HCFA_PLACE_OF_SERVICE".equalsIgnoreCase(enumType))
		l = service.getRelevantEnumerationOrderedByCode(enumType);
	else
		l = service.getRelevantEnumerationsByType(enumType);
	l.add(0, null);
	BindingListModel enumList = new BindingListModelList(l, false);
	return enumList;
	}
}
