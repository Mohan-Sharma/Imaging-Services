package com.nzion.view;

import com.nzion.domain.Person;
import com.nzion.domain.UserLogin;

public class ImpersonateValueObject {
	
	private UserLogin userLogin;
	
	private Person impersonatedPerson;
	
	private Person originalPerson;
	
	private boolean impersonated = false;
	
	public ImpersonateValueObject(){}
	
	public ImpersonateValueObject(UserLogin userLogin){
	this.userLogin = userLogin;
	}

	public void enableImpersonateSetting(){
	
	}
	
	public void resetImpersonateSetting(){
	
	}
	
	public UserLogin getUserLogin() {
	return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
	this.userLogin = userLogin;
	}

	public Person getImpersonatedPerson() {
	return impersonatedPerson;
	}

	public void setImpersonatedPerson(Person impersonatedPerson) {
	this.impersonatedPerson = impersonatedPerson;
	}

	public Person getOriginalPerson() {
	return originalPerson;
	}

	public void setOriginalPerson(Person originalPerson) {
	this.originalPerson = originalPerson;
	}
	
	public boolean isImpersonated() {
	return impersonated;
	}

	public void setImpersonated(boolean impersonated) {
	this.impersonated = impersonated;
	}
}
