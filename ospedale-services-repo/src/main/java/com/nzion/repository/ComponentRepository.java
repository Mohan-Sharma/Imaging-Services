/**
 * @author shwetha
 * Dec 1, 2010 
 */
package com.nzion.repository;

import java.util.List;
import java.util.Map;


public interface ComponentRepository extends BaseRepository {
	
	<T> List<T> searchEntities(String searchString,Class<T> klass,String[] searchColumns);

	<T> List<T> findByParamsMap(Class<T> klass,Map<String, Object> searchParams);
	
	<T> List<T> getMasterDataStartingWith(Class<T> klass,String value,String attribute);
	
}
