package com.nzion.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nzion.domain.Party.PartyType;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;

// TODO: Auto-generated Javadoc
/**
 * The Class UserLoginService.
 * 
 * @author Sandeep Prusty Apr 6, 2010
 */
public interface UserLoginService extends UserDetailsService {

	UserLogin createUserLogin(UserLogin userLogin);

    UserLogin createUserLoginForPatient(UserLogin userLogin);
	
	UserLogin lockUserLogin();
	
	boolean unLockUserLogin(String password);

	UserLogin getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException;

	UserLogin save(UserLogin userLogin);

	Set<UserLogin> getRelevantUserLogins();

	List<? extends Person> relevantPersonLookup(Map<String, Object> searchData);
	
	boolean sendPassword(String secretAnswer,String userName);
	
	void changePassword(UserLogin userLogin,String newPassword);
	
	void resetFailedLoginCount(UserLogin userLogin);
	
    boolean isSecretQuestionConfigured(UserLogin login);
    
    List<Person> getImpersonatedPersonFor(Person logedInPerson);

	UserLogin getUserLogin(Person person);
	
	List<UserLogin> getEmergencyLogins();
	
	List<? extends Person> relevantEmployeeLookup(Map<String, Object> searchData);
	
	boolean hasPatientHavingUserLogin(Patient patient);
	
	public void resetPasswordForSelectedUsers(List<UserLogin> userLogins);
	
	List<UserLogin> getUserLoginsFor(Collection<PartyType> partyTypes);

	public List<LabDepartment> getLabDepartments();
	
	public List<Laboratories> getLaboratories();
}