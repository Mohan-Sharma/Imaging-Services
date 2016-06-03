package com.nzion.view;

import org.apache.commons.beanutils.BeanUtils;

import com.nzion.domain.ContactFields;
import com.nzion.domain.HistoricalModel;
import com.nzion.domain.Patient;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.domain.pms.Policy;
import com.nzion.domain.pms.PolicyHolder;
import com.nzion.domain.pms.PolicyLimit;

public class PolicyViewObject {
	private Policy policy;
	private PolicyHolder policyHolder;
	private ContactFields contacts;
	private InsuranceProvider insuranceProvider;

	public PolicyViewObject(Policy policy) {
	this.policy = policy;
	this.policyHolder = policy.getPolicyHolder();
	this.setContacts(policyHolder.getContacts());
	this.insuranceProvider = policy.getInsuranceProvider();
	}

	public PolicyViewObject(Patient patient) {
	policy = new Policy();
	policy.setHistoricalModel(new HistoricalModel());
	policy.setPolicyLimit(new PolicyLimit());
	policyHolder = new PolicyHolder();
	try {
		BeanUtils.copyProperties(policyHolder, patient);
	} catch (Throwable e) {
		e.printStackTrace();
	}
	policyHolder.setPolicy(policy);
	policyHolder.setId(null);
	policy.setPolicyHolder(policyHolder);
	setContacts(patient.getContacts());
	}


	public Policy getPolicy() {
	return policy;
	}

	public void setPolicy(Policy policy) {
	this.policy = policy;
	}

	public PolicyHolder getPolicyHolder() {
	return policyHolder;
	}

	public void setPolicyHolder(PolicyHolder policyHolder) {
	this.policyHolder = policyHolder;
	}

	public InsuranceProvider getInsuranceProvider() {
	return insuranceProvider;
	}

	public void setInsuranceProvider(InsuranceProvider insuranceProvider) {
	if (insuranceProvider != null)
		this.insuranceProvider = insuranceProvider;
	else
		this.insuranceProvider = new InsuranceProvider();
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}

	public ContactFields getContacts() {
	return contacts;
	}
}
