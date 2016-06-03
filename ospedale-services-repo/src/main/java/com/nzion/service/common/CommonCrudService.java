package com.nzion.service.common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.nzion.domain.Folder;
import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.Salutable;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.EventLog;
import com.nzion.enums.EventType;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */
public interface CommonCrudService {
	
	<T> T save(T t);

	<T> T delete(T t);

	<T> T getByAccountNumber(Class<T> klass, String accountNumber);
	
	<T> T getByPractice(Class<T> klass);

	<T> T getByUniqueValue(Class<T> klass, String columnName, Object columnValue);

	<T> T getById(Class<T> klass, Serializable id);

	<T> List<T> getAll(Class<T> klass);

	<T> List<T> searchByExample(T t);

	void save(Collection<?> collection);

	void delete(Collection<?> collection);

	void evict(Object entity);

	void evict(Collection<?> entities);

	<T> void refreshEntity(Collection<T> entities);

	<T> T refreshEntity(T entity);

	Long findCount(String className);

	<T> T merge(T entity);
	
	Folder getRootFolder(Patient patient);
	
	
	String getFormattedName(Salutable salutable);
	
	String getFormattedName(Salutable salutable,Practice practice);
	
	List<EventLog> searchEventLogs(EventType eventType,Date fromDate,Date thruDate,UserLogin createdBy,Patient patient,Date fromTime,Date thruTime);
	
	void activate(Collection<?> collection);
	
	void deActivate(Collection<?> collection, String reason);
	
	<T> List<T> findByEquality(Class<T> klass, String[] fields, Object[] values);

	<T> T findUniqueByEquality(Class<T> klass, String[] fields, Object[] values);
	
}