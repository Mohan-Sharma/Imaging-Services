package com.nzion.service;

import java.util.Collection;
import java.util.List;

import com.nzion.domain.Employee;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Location;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.Speciality;
import com.nzion.domain.screen.ScheduleCustomView;
import com.nzion.view.ProviderValueObject;

public interface ProviderService {

	void save(Provider provider);


	void save(ProviderValueObject providerVO);

	Provider getProvider(String accountNumber);
	
	Provider getProvider(Long providerId);

	
	
	List<Provider> getAllProviders();
	
	List<Referral> searchReferral(String firstName,String lastName,String speciality);
	
	ScheduleCustomView getDefaultProvidersForLoggedInUser();
	
	List<Provider> searchProviderBy(String firstName, String lastName, Speciality speciality ,Location location);
	
	List<Provider> getAllProvidersForLocation(Location location);
	
	
	
	List<Provider> searchProvider(String searchField,String value);
	


	
	List<Speciality> searchSpecialitiesBy(String code,String description);
	
	List<Provider> getProvidersForSpeciality(Speciality speciality);
	
	List<Provider> searchProviderBy(String firstName, String lastName, Enumeration gender, Collection<Speciality> speciality,Location location);
	
	List<Employee> searchEmployeeBy(String firstName, String lastName, Enumeration gender,Location location);
	
	List<Referral> searchReferralBy(String firstName, String lastName, Enumeration gender, Collection<Speciality> speciality);
}