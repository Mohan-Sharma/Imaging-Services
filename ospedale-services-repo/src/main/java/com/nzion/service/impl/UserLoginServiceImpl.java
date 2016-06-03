package com.nzion.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

import javax.annotation.Resource;

import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.Party.PartyType;
import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.repository.UserLoginRepository;
import com.nzion.service.UserLoginService;
import com.nzion.service.common.GenericHomeScreenSearchService;
import com.nzion.service.common.MailService;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService, GenericHomeScreenSearchService {
	private UserLoginRepository userLoginRepository;
	private MailService mailService;

	@Autowired
	EncryptionService encryptionService;

	static boolean PORTAL_AUTHENTICATION = false;
	static {
		Properties properties = new Properties();
		try {
			String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
			properties.load(UserLoginServiceImpl.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
			PORTAL_AUTHENTICATION = "true".equals(properties.get("PORTAL_AUTHENTICATION"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Resource(name = "mailService")
	@Required
	public void setMailService(MailService mailService) {
	this.mailService = mailService;
	}

	/*public UserLoginUserDetailsAdapter loadUserByUsername(String username) throws UsernameNotFoundException,DataAccessException {
	UserLogin login = userLoginRepository.loadUserByUsername(username);
	if (login == null) throw new UsernameNotFoundException(username + " Not taken yet");
	return new UserLoginUserDetailsAdapter(login);
	}*/

	public UserLoginUserDetailsAdapter loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		UserLogin login = null;
		if (PORTAL_AUTHENTICATION) {
			Map<String, Object> user = AfyaPortalServiceConsumer.getUserLoginByUserName(username);

			if (user == null) {
				throw new UsernameNotFoundException("Invalid User or security token");
			}
			String tenantId = (String) user.get("tenantId");
			//String tenantId = "afya_lab_db";
			if (tenantId != null) {
				TenantIdHolder.setTenantId(tenantId);
			}
			try {
				login = userLoginRepository.loadUserByUsername((String) user.get("userName"));
				if (login != null) {
					login.setPassword(user.get("password").toString());
				} else {
					System.out.println("Error :: Local database user not found ....Tenant " + tenantId + ", Token : " + username + ",  User " + (String) user.get("userName"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else  {
			login = userLoginRepository.loadUserByUsername(username);
			System.out.println("Hurre trying localdb authentication");

		}
		if (login == null) {
			throw new UsernameNotFoundException(username + " Not taken yet");
		}
		return new UserLoginUserDetailsAdapter(login);
	}

	public UserLogin getUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
	UserLogin login = userLoginRepository.loadUserByUsername(username);
	if (login == null) throw new UsernameNotFoundException(username + " Not taken yet");
	return login;
	}

	@Resource(name = "userLoginRepository")
	@Required
	public void setUserLoginRepository(UserLoginRepository userLoginRepository) {
	this.userLoginRepository = userLoginRepository;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserLogin createUserLogin(UserLogin userLogin) {
	userLogin.setPassword(userLogin.getUsername());
	userLogin.setRequirePasswordChange(true);
	userLogin.setPwdChangedTime(new Timestamp(new Date().getTime()));
	save(userLogin);	
	if(UtilValidator.isNotEmpty(userLogin.getPerson().getContacts().getEmail()))
		mailService.sendMail(userLogin);	
	
	return userLogin;
	}

	public Set<UserLogin> getRelevantUserLogins() {
	return new HashSet<UserLogin>(userLoginRepository.getAll(UserLogin.class));
	}

	public List<? extends Person> relevantPersonLookup(Map<String, Object> searchData) {
	return userLoginRepository.relevantPersonLookup(searchData);
	}

	public List<? extends Person> relevantEmployeeLookup(Map<String, Object> searchData) {
	return userLoginRepository.relevantEmployeeLookup(searchData);
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserLogin save(UserLogin userLogin) {
	userLoginRepository.save(userLogin);
	return userLogin;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public UserLogin lockUserLogin() {
	UserLogin userLogin = Infrastructure.getUserLogin();
	userLogin.setLocked(true);
	userLoginRepository.updateLockedFlag(userLogin);
	return userLogin;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public boolean unLockUserLogin(String password) {
	UserLogin userLogin = Infrastructure.getUserLogin();
	if(!(userLogin.getPassword().equals(password)))
		return false;
	userLogin.setLocked(false);
	userLoginRepository.updateLockedFlag(userLogin);
	return true;
	}

	@Override
	public boolean sendPassword(String secretAnswer, String userName) {
	UserLogin login = userLoginRepository.loadUserByUsername(userName);
	boolean matchFound = secretAnswer.equalsIgnoreCase(login.getSecretQuestionAnswer());
	if(!matchFound)
		return false;
	mailService.sendUserLoginCredentials(login);
	return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void changePassword(UserLogin userLogin,String newPassword){
	userLogin.changePassword(newPassword);
	userLoginRepository.updatePassword(userLogin);
	}

	@Override
	public void resetFailedLoginCount(UserLogin userLogin) {
	userLoginRepository.resetFailedLoginCount(userLogin);
	}
	
	@Override
	public boolean isSecretQuestionConfigured(UserLogin login){
	return login.getSecretQuestion() == null ? false : true;
	}

	@Override
	public List<Person> getImpersonatedPersonFor(Person logedInPerson) {
	return userLoginRepository.getImpersonatedPersonFor(logedInPerson);
	}


	@Override
	public UserLogin getUserLogin(Person person) {
	return userLoginRepository.getUserLoginForPerson(person);
	}
	
	public List<UserLogin> getEmergencyLogins(){
	return userLoginRepository.getEmergencyLogins();
	}


	@Override
	public boolean hasPatientHavingUserLogin(Patient patient) {
	if(userLoginRepository.noOfUserLoginFor(patient)>0)
		return true;
	return false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void resetPasswordForSelectedUsers(List<UserLogin> userLogins) {
	for(UserLogin login : userLogins){
		//login.setPassword(UtilMisc.randomAlphaNumericGenerator(6));
		login.setPassword(login.getUsername());
		login.setRequirePasswordChange(true);
		login.setPwdChangedTime(new Timestamp(new Date().getTime()));
		save(login);
		if(UtilValidator.isNotEmpty(login.getPerson().getContacts().getEmail()))			
			mailService.sendUserLoginCredentials(login);
	}
	}


	// These apis are to make the emergency access list work

	@Override
	public <T> List<T> getAllIncludingInactivesPageWise(Class<T> klass, int pageSize, int firstRecord, String searchColumn, boolean desc) {
	return userLoginRepository.getAllEmergenyAcessUserLoginsPageWise(pageSize, firstRecord);
	}

	@Override
	public Long getCountForAllIncludingInactives(Class<?> klass) {
	return userLoginRepository.getCountForEmergenyAcessUserLogins();
	}

	@Override
	public List<?> search(String searchString, Class<?> entityClass, String searchColumn, boolean desc, String... fields) {
	return userLoginRepository.searchEmergenyAccessUserLogins(searchString, fields);
	}

	@Override
	public List<UserLogin> getUserLoginsFor(Collection<PartyType> partyTypes) {
	return userLoginRepository.getUserLoginsFor(partyTypes);
	}
	
	@Override
	public List<LabDepartment> getLabDepartments() {
	return userLoginRepository.getLabDepartments();
	}

	@Override
	public List<Laboratories> getLaboratories() {
	return userLoginRepository.getLaboratories();
	}

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public UserLogin createUserLoginForPatient(UserLogin userLogin) {
        userLogin.setPassword(userLogin.getPassword());
        userLogin.setRequirePasswordChange(false);
        userLogin.setPwdChangedTime(new Timestamp(new Date().getTime()));
        save(userLogin);
        if(UtilValidator.isNotEmpty(userLogin.getPerson().getContacts().getEmail()))
            mailService.sendMail(userLogin);

        return userLogin;
    }


}