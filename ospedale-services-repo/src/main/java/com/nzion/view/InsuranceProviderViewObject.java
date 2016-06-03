package com.nzion.view;

import com.nzion.domain.ContactFields;
import com.nzion.domain.HistoricalModel;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.util.Infrastructure;

public class InsuranceProviderViewObject {

	private InsuranceProvider insuranceProvider;
	
	private ContactFields contacts;
	
	private String claimsNumber;
	
	private String benefitsNumber;
	
	private String authorizationNumber;
	
	private String providerRelationsNumber;
	
	public String getClaimsNumber() {
	return claimsNumber;
	}

	public void setClaimsNumber(String claimsNumber) {
	this.claimsNumber = claimsNumber;
	}

	public String getBenefitsNumber() {
	return benefitsNumber;
	}

	public void setBenefitsNumber(String benefitsNumber) {
	this.benefitsNumber = benefitsNumber;
	}

	public String getAuthorizationNumber() {
	return authorizationNumber;
	}

	public void setAuthorizationNumber(String authorizationNumber) {
	this.authorizationNumber = authorizationNumber;
	}

	public String getProviderRelationsNumber() {
	return providerRelationsNumber;
	}

	public void setProviderRelationsNumber(String providerRelationsNumber) {
	this.providerRelationsNumber = providerRelationsNumber;
	}

	public ContactFields getContacts() {
	if(contacts == null)
		contacts = new ContactFields();
	return contacts;
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}

	public InsuranceProviderViewObject( InsuranceProvider insuranceProvider){
	this.insuranceProvider = insuranceProvider;
	}

	public InsuranceProviderViewObject() {
	this.insuranceProvider = new InsuranceProvider();
	this.insuranceProvider.setHistoricalModel(new HistoricalModel());
	}


	public InsuranceProvider getInsuranceProvider() {
	return insuranceProvider;
	}

	public void setInsuranceProvider(InsuranceProvider insuranceProvider) {
	this.insuranceProvider = insuranceProvider;
	}
}
