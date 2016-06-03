package com.nzion.repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.nzion.domain.Employee;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Location;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.Speciality;

/**
 * @author Sandeep Prusty
 * Apr 16, 2010
 */
public interface ProviderRepository extends BaseRepository {
	
	Provider getProvider(String accountNumber);
	

	
	List<Provider> getAllProviders();
	

	
	
	List<Referral> searchReferral(String firstName,String lastName,String speciality);
	
	List<Provider> getAllProvidersForLocation(Location location);
	
	
	
	List<Provider> doLookUp(String accountNumber, String firstName, String lastName, Speciality speciality,Location location);

	List<Provider> searchProvider(String searchField,String value,Set<Location> locations);


	
	List<Speciality> searchSpecialiesBy(String code,String description);
	
	List<Provider> getProvidersForSpeciality(Speciality speciality);
	
	List<Provider> searchProviderBy(String firstName, String lastName, Enumeration gender, Collection<Speciality> speciality,Location location);
	
	List<Employee> searchEmployeeBy(String firstName, String lastName, Enumeration gender,Location location);
	
	List<Referral> searchReferralBy(String firstName, String lastName, Enumeration gender, Collection<Speciality> speciality);

}