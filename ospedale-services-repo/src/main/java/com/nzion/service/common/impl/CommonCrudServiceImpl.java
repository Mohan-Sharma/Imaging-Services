package com.nzion.service.common.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Folder;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.Practice;
import com.nzion.domain.Salutable;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.BaseEntity;
import com.nzion.domain.base.EventLog;
import com.nzion.domain.screen.NamingDisplayConfig;
import com.nzion.enums.EventType;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.GenericHomeScreenService;
import com.nzion.util.Constants;
import com.nzion.util.DomainBeansUtil;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * May 13, 2010
 */

public class CommonCrudServiceImpl implements CommonCrudService, GenericHomeScreenService {

	private CommonCrudRepository commonCrudRepository;

	@Override
	public <T> T getByAccountNumber(Class<T> klass, String accountNumber) {
	return getByAccountNumber(klass, DomainBeansUtil.getPropertyNameForAccountNumber(klass), accountNumber);
	}

	@Override
	public <T> T getByPractice(Class<T> klass) {
	T t = commonCrudRepository.findUniqueByEquality(klass, new String[] {}, new Object[] {});
	if (t == null) try {
		t = klass.newInstance();
	} catch (Exception e) {
		throw new RuntimeException(e);
	}
	return t;
	}

	private <T> T getByAccountNumber(Class<T> klass, String columnName, String accountNumber) {
	if (UtilValidator.isEmpty(columnName)) columnName = "accountNumber";
	return commonCrudRepository.getByAccountNumber(klass, columnName, accountNumber);
	}

	@Override
	public <T> T getByUniqueValue(Class<T> klass, String columnName, Object columnValue) {
	return commonCrudRepository.getByUniqueValue(klass, columnName, columnValue);
	}

	public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
	this.commonCrudRepository = commonCrudRepository;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T> T save(T t) {
	commonCrudRepository.save(t);
	return t;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(Collection<?> collection) {
	commonCrudRepository.save(collection);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void delete(Collection<?> collection) {
	for (Object object : collection) {
		commonCrudRepository.remove(object);
	}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T> T delete(T t) {
	return commonCrudRepository.remove(t);
	}

	@Override
	public <T> T getById(Class<T> klass, Serializable id) {
	return commonCrudRepository.findByPrimaryKey(klass, id);
	}

	@Override
	public <T> List<T> getAll(Class<T> klass) {
	return commonCrudRepository.getAll(klass);
	}

	@Override
	public <T> List<T> searchByExample(T t) {
	return commonCrudRepository.searchByExample(t);
	}

	@Override
	public List<Object> search(String searchString, Class<?> entityClass, String searchColumn, boolean desc,String... fields) {
	return commonCrudRepository.search(searchString, entityClass, searchColumn, desc, fields);
	}

	@Override
	public <T> List<T> getLatestUpdateds(Class<T> klass, int count) {
	return commonCrudRepository.getLatestUpdateds(klass, count);
	}

	@Override
	public void evict(Object entity) {
	commonCrudRepository.evict(entity);
	}

	@Override
	public void evict(Collection<?> entities) {
	for (Object entity : entities) {
		evict(entity);
	}
	}

	@Override
	public <T> void refreshEntity(Collection<T> entities) {
	for (T entity : entities) {
		refreshEntity(entity);
	}
	}

	@Override
	public <T> T refreshEntity(T entity) {
	commonCrudRepository.refreshEntity(entity);
	return entity;
	}

	@Override
	public Long findCount(String className) {
	return commonCrudRepository.findCount(className);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public <T> T merge(T entity) {
	T o = commonCrudRepository.merge(entity);
	return o;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void activate(Collection<?> collection) {
	if (UtilValidator.isEmpty(collection)) return;
	for (Object object : collection){
		((BaseEntity) object).activate();
		if (object instanceof Person) {
			Person deactivatedUser = ((Person) object);
			UserLogin userLogin = commonCrudRepository.getDeactivatedUserLogin(deactivatedUser);
			if (userLogin != null && !userLogin.isActive()) {
				userLogin.activate();
				userLogin.getPerson().setActive(Boolean.TRUE);
				commonCrudRepository.save(userLogin);
				continue;
			}
		}
		commonCrudRepository.save(object);
	}	
	}
	

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deActivate(Collection<?> collection, String reason) {
	if (UtilValidator.isEmpty(collection)) return;
	Person p = new Person();
	p.setId(Infrastructure.getUserLogin().getPerson().getId());
	for (Object object : collection) {
		((BaseEntity) object).deActivate(reason, p);
		if (object instanceof Person) {
			Person deactivatedUser = ((Person) object);
			UserLogin userLogin = commonCrudRepository.getByUniqueValue(UserLogin.class, "person", deactivatedUser);
			if (userLogin != null) {
				userLogin.deActivate(reason, p);
				userLogin.getPerson().setActive(Boolean.FALSE);
				commonCrudRepository.save(userLogin);
				continue;
			}
		}
		commonCrudRepository.save(object);
	}
	}

	@Override
	public <T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord,String defaultSortColumn, boolean defaultSortDesc) {
	return commonCrudRepository.getAllIncludingInactivesPageWise(klass, pageSize, firstRecord, defaultSortColumn,defaultSortDesc);
	}

	@Override
	public Long getCountForAllIncludingInactives(Class<?> klass) {
	return commonCrudRepository.getCountForAllIncludingInactives(klass);
	}

	public Folder getRootFolder(Patient patient) {
	return commonCrudRepository.getRootFolder(patient);
	}


	@Override
	public String getFormattedName(Salutable salutable) {
	return getFormattedName(salutable,null );
	}

	@Override
	public String getFormattedName(Salutable salutable, Practice practice) {
	if (salutable == null) return Constants.BLANK;
	NamingDisplayConfig displayConfig = getAll(NamingDisplayConfig.class).get(0);
	return displayConfig.buildName(salutable);
	}

	@Override
	public List<EventLog> searchEventLogs(EventType eventType, Date fromDate, Date thruDate, UserLogin createdBy,Patient patient, Date fromTime, Date thruTime) {
	return commonCrudRepository.searchEventLogs(eventType, fromDate, thruDate, createdBy, patient, fromTime, thruTime);
	}

	@Override
	public <T> List<T> findByEquality(Class<T> klass, String[] fields, Object[] values) {
	return commonCrudRepository.findByEquality(klass, fields, values);
	}

	@Override
	public <T> T findUniqueByEquality(Class<T> klass, String[] fields, Object[] values) {
	return commonCrudRepository.findUniqueByEquality(klass, fields, values);
	}
}