package com.nzion.domain.olap;

import java.io.Serializable;

import com.nzion.util.UtilReflection;

public class StarSchemaId implements Serializable {

	private static final long serialVersionUID = 1L;
	private String accountNumber;

	public StarSchemaId() {};

	public StarSchemaId(String accountNumber, Long practiceId) {
	super();
	this.accountNumber = accountNumber;
	this.practiceId = practiceId;
	}

	private Long practiceId;

	public String getAccountNumber() {
	return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public void setPracticeId(Long practiceId) {
	this.practiceId = practiceId;
	}

	public Long getPracticeId() {
	return practiceId;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
	result = prime * result + ((practiceId == null) ? 0 : practiceId.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	return UtilReflection.areEqual(this, obj, new String[] {"accountNumber","practiceId"});
	}

}
