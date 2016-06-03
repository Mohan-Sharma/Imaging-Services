package com.nzion.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.nzion.domain.Party.PartyType;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserLoginRepository.
 * 
 * @author Sandeep Prusty Apr 6, 2010
 */
public interface UserLoginRepository extends BaseRepository{

	UserLogin loadUserByUsername(String username);

	List<? extends Person> relevantPersonLookup(Map<String, Object> searchData);
	
	void updateLockedFlag(UserLogin login);
	
	void updatePassword(UserLogin login);

	void resetFailedLoginCount(UserLogin userLogin);
	
	List<Person> getImpersonatedPersonFor(Person logedInPerson);

	UserLogin getUserLoginForPerson(Person person);

	List<UserLogin> getEmergencyLogins();
	
	List<? extends Person> relevantEmployeeLookup(Map<String, Object> searchData);
	
	Long noOfUserLoginFor(Patient patient);

	Long getCountForEmergenyAcessUserLogins();

	<T> List<T> getAllEmergenyAcessUserLoginsPageWise(int pageSize, int firstRecord);

	List<?> searchEmergenyAccessUserLogins(String searchString, String[] fields);
	
	List<UserLogin> getUserLoginsFor(Collection<PartyType> partyTypes);
	
	List<LabDepartment> getLabDepartments();
	
	List<Laboratories> getLaboratories();

}
