package com.nzion.repository;

import java.util.List;


public interface VaccineRepository {

	<T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord,	String... lazyLoadAssocPaths);

	Long getCountForAllIncludingInactives(Class<?> klass);

	<T> List<T> search(String searchString, Class<?> entityClass, String... fields);


	
	
}
