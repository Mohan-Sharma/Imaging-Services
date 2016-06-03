package com.nzion.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;

/**
 * @author Sandeep Prusty
 * Apr 13, 2010
 */
public interface BaseRepository {
	
	<T> void save(T t);

	<T> T findByPrimaryKey(Class<T> klass, Serializable id, LockMode lockMode);

	<T> T findByPrimaryKey(Class<T> klass, Serializable id);

    <T> T remove(Serializable pk, Class<?> klass);

    <T> T remove(T t);

    void remove(Collection<?> collection);

    void save(Collection<?> collection);
    
	<T> void save(T ...ts);

    <T> List<T> getAll(Class<T> klass);

    <T> List<T> getAll(Class<T> klass,boolean onlyEnabled);

    <T> List<T> simulateExampleSearch(String[] searchFields, Object exampleObject);

    <T> T merge(T entity);

    void evict(Object entity);
    
    void refresh(Object entity);
}