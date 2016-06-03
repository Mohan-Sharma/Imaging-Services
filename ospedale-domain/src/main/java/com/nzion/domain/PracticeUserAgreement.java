package com.nzion.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Filters( {
	@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class PracticeUserAgreement extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String termsAndConditions;
	
	@Lob
	public String getTermsAndConditions() {
	return termsAndConditions;
	}

	public void setTermsAndConditions(String termsAndConditions) {
	this.termsAndConditions = termsAndConditions;
	}
}
