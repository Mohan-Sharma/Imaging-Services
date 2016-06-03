package com.nzion.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Enumeration;
import com.nzion.repository.EnumerationRepository;
import com.nzion.service.common.EnumerationService;
import com.nzion.util.Infrastructure;

/**
 * @author Sandeep Prusty
 * Apr 13, 2010
 */
@Transactional(readOnly=false)
@Service("enumerationService")
public class EnumerationServiceImpl implements EnumerationService {
	
	private EnumerationRepository repository;
	
	@Resource(name="enumerationRepository")
	@Required
	public void setRepository(EnumerationRepository repository) {
	this.repository = repository;
	}

	public Enumeration addEnumeration(String enumType, String enumCode, String description) {
	Enumeration enumeration = new Enumeration(enumType, enumCode, description);
	return addEnumeration(enumeration);
	}

	public Enumeration disableEnumeration(String enumCode) {
	Enumeration enumeration = repository.findByEnumCode(enumCode);
	enumeration.activate();
	repository.save(enumeration);
	return enumeration;
	}

	public Enumeration enableEnumeration(String enumCode) {
	Enumeration enumeration = repository.findByEnumCode(enumCode);
	enumeration.deActivate(null,null);
	repository.save(enumeration);
	return enumeration;
	}

	public List<Enumeration> getGeneralEnumerationsByType(String enumType) {
	return repository.getGeneralEnumerationsByType(enumType);
	}

	public List<Enumeration> getPracticeSpecificEnumerationsByType(String enumType) {
	return repository.getPracticeSpecificEnumerationsByType(enumType);
	}

	public List<Enumeration> getRelevantEnumerationsByType(String enumType) {
	return repository.getRelevantEnumerationsByType(enumType);
	}

	public Enumeration addEnumeration(Enumeration enumeration) {
	repository.save(enumeration);
	return enumeration;
	}

	public Enumeration deleteEnumeration(Long id) {
	return repository.remove(id, Enumeration.class);
	}

	public void deleteAllEnumerationByType(String enumType) {
	
	}

	public void deleteEnumeration(Enumeration enumeration) {
	repository.remove(enumeration);
	}

	public Enumeration getByEnumCode(String enumCode) {
	return repository.findByEnumCode(enumCode);
	}

	public Enumeration findByEnumCodeAndEnumType(String enumCode,String enumType){
	return repository.findByEnumCodeAndEnumType(enumCode, enumType);
	}

	@Override
	public List<Enumeration> getRelevantEnumerationOrderedByCode(String enumType) {
	return repository.getRelevantEnumerationOrderedByCode(enumType);
	}
}