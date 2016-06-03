package com.nzion.service.common;

import java.util.List;

import com.nzion.domain.Enumeration;

/**
 * @author Sandeep Prusty
 * Apr 13, 2010
 */

public interface EnumerationService{
	
	List<Enumeration> getRelevantEnumerationsByType(String enumType);

	List<Enumeration> getGeneralEnumerationsByType(String enumType);

	List<Enumeration> getPracticeSpecificEnumerationsByType(String enumType);
	
	Enumeration addEnumeration(String enumType, String enumCode, String description);
	
	Enumeration addEnumeration(Enumeration enumeration);

	Enumeration deleteEnumeration(Long id);

	Enumeration disableEnumeration(String enumCode);
	
	Enumeration enableEnumeration(String enumCode);
	
	void deleteEnumeration(Enumeration enumeration);
	
	void deleteAllEnumerationByType(String enumType);
	
	Enumeration getByEnumCode(String enumCode);
	
	Enumeration findByEnumCodeAndEnumType(String enumCode,String enumType);
	
	List<Enumeration> getRelevantEnumerationOrderedByCode(String enumType);

}