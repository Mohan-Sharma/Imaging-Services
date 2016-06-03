package com.nzion.service;

import com.nzion.domain.PasswordPolicy;
import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;
public interface PasswordPolicyService {

	public PasswordPolicy getEffectivePasswordPolicy();

	public void saveOrUpdate(PasswordPolicy policy);

	public PasswordPolicy getDefaultPasswordPolicy();
	
	boolean validatePassword(String password);
	
	boolean checkWarningPeriodArrival(UserLogin userLogin);
	
	boolean checkPasswordExpiration(UserLogin userLogin);
}
