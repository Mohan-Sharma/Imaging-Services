package com.nzion.service.common;

import java.util.List;

/**
 * @author Sandeep Prusty
 * Aug 28, 2010
 */
public interface GenericHomeScreenSearchService {
	
	<T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord, String searchColumn, boolean desc);

	Long getCountForAllIncludingInactives(Class<?> klass);

	List<?> search(String searchString, Class<?> entityClass, String searchColumn, boolean desc, String ...fields);

//	List<?> search(Map<String, Object> searchFieldAndValue,  Class<?> entityClass);
}
