package com.nzion.service.common.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.nzion.enums.EnumerationType;
import com.nzion.repository.EnumerationRepository;
import com.nzion.service.common.GenericHomeScreenSearchService;

/**
 * @author Sandeep Prusty
 * Nov 16, 2010
 */

@Service("practiceSpecificEnumerationHomeScreenSearchService")
public class PracticeSpecificEnumerationHomeScreenSearchService implements GenericHomeScreenSearchService {
	
	private EnumerationRepository enumerationRepository;
	
	private static final String[] PRACTICE_SPECIFIC_ENUMERATIONS;
	
	static{
		int i = 0;
		PRACTICE_SPECIFIC_ENUMERATIONS = new String[EnumerationType.getPracticeSpecificEnumTypes().size()];
		for(EnumerationType type : EnumerationType.getPracticeSpecificEnumTypes()){
			PRACTICE_SPECIFIC_ENUMERATIONS[i++] = type.name();
		}
	}
	
	@Resource(name="enumerationRepository")
	@Required
	public void setEnumerationRepository(EnumerationRepository enumerationRepository) {
	this.enumerationRepository = enumerationRepository;
	}

	@Override
	public List<?> search(String searchString, Class<?> entityClass,  String sortColumn, boolean desc, String... fields) {
	return enumerationRepository.search(searchString, PRACTICE_SPECIFIC_ENUMERATIONS);	}

	@Override
	public <T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord, String sortColumn, boolean desc) {
	return (List<T>)enumerationRepository.getAllIncludingInactivesPageWise(PRACTICE_SPECIFIC_ENUMERATIONS, firstRecord, pageSize);
	}

	@Override
	public Long getCountForAllIncludingInactives(Class<?> klass) {
	return enumerationRepository.getCountForAllIncludingInactives(PRACTICE_SPECIFIC_ENUMERATIONS);
	}
}