package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "PURGINGPOLICY")

public class PurgingPolicy extends IdGeneratingBaseEntity{
	
	private String policyName;

	private Integer numberOfDays;
	
	@Column(name="POLICY_NAME")
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	@Column(name="NUMBER_OF_DAYS")
	public Integer getNumberOfDays() {
	return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
	this.numberOfDays = numberOfDays;
	}

	private static final long serialVersionUID = 1L;
}