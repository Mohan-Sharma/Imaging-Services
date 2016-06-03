package com.nzion.service.common;

import java.util.List;
import java.util.Map;

/**
 * @author Sandeep Prusty
 * Aug 28, 2010
 */
public interface SearchService {

	List<?> search(String searchString, Class<?> entityClass, String ...fields);

	List<?> search(Map<String, Object> searchFieldAndValue,  Class<?> entityClass);
}
