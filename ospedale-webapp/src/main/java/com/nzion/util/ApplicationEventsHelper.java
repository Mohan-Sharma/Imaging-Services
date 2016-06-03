package com.nzion.util;

import com.nzion.domain.Patient;

public class ApplicationEventsHelper {
	
	public static String buildEventLogForPatientSearch(String[] fields,Patient patient){
	StringBuilder stringBuilder = new StringBuilder("searched for patient on");
	for(String field : fields){
		Object value = UtilReflection.getNestedFieldValue(patient, field);
		if(value == null) continue;
		stringBuilder.append(UtilDisplay.camelcaseToUiForCopositeFiedl(field)).append("=").append(value.toString()).append(Constants.BLANK);
	}
	stringBuilder.append(" fields");
	return stringBuilder.toString();
	}
}
