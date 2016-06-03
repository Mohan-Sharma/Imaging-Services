package com.nzion.view;

import java.io.Serializable;
import java.util.Collection;

import com.nzion.domain.Provider;
import com.nzion.domain.Speciality;

/**
 * @author Sandeep Prusty
 * Apr 20, 2010
 */
public class ProviderValueObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private Provider provider;

	public ProviderValueObject() {
	provider = new Provider();
	}

	public ProviderValueObject(Provider provider) {
	this.provider = provider;
	}

	public String getQualifications() {
	return provider.getDetail().getQualification();
	}

	public Collection<Speciality> getSpecialities() {
	return provider.getSpecialities();
	}

	public Provider getProvider() {
	return provider;
	}

	public void setProvider(Provider provider) {
	this.provider = provider;
	}
}