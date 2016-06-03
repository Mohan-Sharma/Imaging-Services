package com.nzion.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Employee;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Location;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.Speciality;
import com.nzion.domain.screen.ScheduleCustomView;
import com.nzion.repository.ProviderRepository;
import com.nzion.service.ProviderService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;
import com.nzion.view.ProviderValueObject;

/**
 * @author Sandeep Prusty
 * Apr 16, 2010
 */

@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

	private ProviderRepository repository;

	
	@Resource(name = "providerRepository")
	@Required
	public void setRepository(ProviderRepository repository) {
	this.repository = repository;
	}

	public Provider getProvider(String accountNumber) {
	return repository.getProvider(accountNumber);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(Provider provider) {
	if(!provider.isProviderAssistant())
		provider.setSchedulable(true);
	repository.save(provider);
	}


	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(ProviderValueObject providerVO) {
	Provider provider = providerVO.getProvider();
	if(!provider.isProviderAssistant())
		provider.setSchedulable(true);
	/*if (provider.getId() == null) {
		EMRProviderInfo emrProvierInfo = new EMRProviderInfo();
		emrProvierInfo.setProvider(provider);
		save(emrProvierInfo);
	}*/
	save(provider);
	}

	public Provider getProvider(Long providerId) {
	return repository.findByPrimaryKey(Provider.class, providerId);
	}

	
	@Override
	public List<Provider> getAllProviders() {
	return repository.getAllProviders();
	}

	@Override
	public List<Referral> searchReferral(String firstName, String lastName,String speciality) {
	if(firstName.isEmpty() && lastName.isEmpty() && speciality.isEmpty())
		return new ArrayList<Referral>();
	return repository.searchReferral(firstName, lastName,speciality);
	}

	@Override
	public ScheduleCustomView getDefaultProvidersForLoggedInUser() {
	//return scheduleRepository.getDefaultProvidersForLoggedInUser();
		return null;
	}
	
	@Override
	public List<Provider> searchProviderBy(String firstName, String lastName, Speciality speciality ,Location location){
	return repository.doLookUp(null ,firstName, lastName, speciality,location);
	}

	@Override
	public List<Provider> getAllProvidersForLocation(Location location) {
	return repository.getAllProvidersForLocation(location);
	}

	
	
	public List<Provider> searchProvider(String searchField,String value){
		return repository.searchProvider(searchField, value, Infrastructure.getUserLogin().getLocations());
	}

	

	
	
	public List<Speciality> searchSpecialitiesBy(String code,String description){
	if(UtilValidator.isEmpty(code) && UtilValidator.isEmpty(description))
		return Collections.emptyList();
	return repository.searchSpecialiesBy(code, description);
	}
	
	public List<Provider> getProvidersForSpeciality(Speciality speciality){
	return repository.getProvidersForSpeciality(speciality);	
	}
	@Override
	public List<Provider> searchProviderBy(String firstName, String lastName,
			Enumeration gender, Collection<Speciality> speciality,Location location) {
		return repository.searchProviderBy(firstName, lastName, gender, speciality,location);	
	}

	@Override
	public List<Employee> searchEmployeeBy(String firstName, String lastName,
			Enumeration gender, Location location) {
		return repository.searchEmployeeBy(firstName, lastName, gender,location);
	}
	
	@Override
	public List<Referral> searchReferralBy(String firstName, String lastName, Enumeration gender, Collection<Speciality> speciality){
		return repository.searchReferralBy(firstName, lastName, gender, speciality);	
	}
}