package com.nzion.repository;

import java.util.List;

import com.nzion.domain.Enumeration;

/**
 * @author Sandeep Prusty
 * Apr 13, 2010
 */
public interface EnumerationRepository extends BaseRepository {
	
	List<Enumeration> getRelevantEnumerationsByType(String enumType);

	List<Enumeration> getGeneralEnumerationsByType(String enumType);

	List<Enumeration> getPracticeSpecificEnumerationsByType(String enumType);
	
	Enumeration updateEnumeration(Long enumId, String enumCode, String description);
	
	Enumeration findByEnumCode(String enumCode);

	boolean deleteAllEnumerationByType(String enumType);
	
	Enumeration findByEnumCodeAndEnumType(String enumCode,String enumType);
	
	List<Enumeration> getAllIncludingInactivesPageWise(String[] enumerationType, int firstRecord, int pageSize);

	Long getCountForAllIncludingInactives(String[] enumerationTypes);

	List<Enumeration> getLatestUpdateds(int count, String[] enumerationType);

	List<Enumeration> search(String searchString, String[] enumerationType);
	
	List<Enumeration> getRelevantEnumerationOrderedByCode(String enumType);

}
