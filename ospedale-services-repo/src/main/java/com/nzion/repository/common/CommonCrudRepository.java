package com.nzion.repository.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nzion.domain.Folder;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.EventLog;
import com.nzion.enums.EventType;
import com.nzion.repository.BaseRepository;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */
public interface CommonCrudRepository extends BaseRepository{
	
	<T> T getByAccountNumber(Class<T> klass, String columnName, String accountNumber);
	
	<T> T getByUniqueValue(Class<T> klass,String columnName,Object columnValue);
	
	<T> List<T> searchByExample(T t);
	
	<T> List<T> findByQuery(String query);
	
	<T> List<T> findByQuery(String query,int start,int maxResult);

	Long findCount(String entityName);
	
	<T> List<T> findByEquality(Class<T> klass, String[] fields, Object[] values);

	<T> T findUniqueByEquality(Class<T> klass, String[] fields, Object[] values);
	
	<T> T findByNullValue(Class<T> klass, String field);
	
	<T> List<T> search(String searchString, Class<?> entityClass, String searchColumn, boolean desc, String... fields);
	
	<T> List<T> search(String searchString, Class<?> entityClass, boolean onlyStartWith, int start, int pageSize,String searchColumn, boolean desc, String... fields);

	<T> List<T> getLatestUpdateds(Class<T> klass, int count);
	
	<T>  void refreshEntity(T entity); 
	
    <T> List<T> getAllIncludingInactives(Class<T> klass, String... lazyLoadAssocPaths);
    
	<T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord, String defaultSortColumn, boolean defaultSortDesc);

	Long getCountForAllIncludingInactives(Class<?> klass);
	
	List<EventLog> searchEventLogs(EventType eventType,Date fromDate,Date thruDate,UserLogin createdBy,Patient patient,Date fromTime,Date thruTime);

	List getObjects(Class className,List<String> distinctTargetIds);
	

	Folder getRootFolder(Patient patient);

	
	UserLogin getDeactivatedUserLogin(Person deactivatedUser);

    <T> T load(Class<T> klass,Long id);
}