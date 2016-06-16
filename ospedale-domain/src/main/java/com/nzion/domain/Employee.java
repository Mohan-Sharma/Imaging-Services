package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.annot.AccountNumberField;

@Entity
@Table(name = "EMPLOYEE")
@AccountNumberField
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")

public class Employee extends Person {
	private static final long serialVersionUID = 1L;
	
	private String comments;

//	private Boolean phlebotomist;
	private boolean phlebotomist = Boolean.FALSE;

	@Column(length = 1000)
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}

	public boolean isPhlebotomist() {
		return phlebotomist;
	}

	public void setPhlebotomist(boolean phlebotomist) {
		setSchedulable(phlebotomist);
		this.phlebotomist = phlebotomist;
	}

	public Employee() {
	super(PartyType.EMPLOYEE);
	}
	
	public Employee(Long id,String firstName,String lastName,ContactFields cf){
	super(id,firstName,lastName,cf);
	setPartyType(PartyType.EMPLOYEE);
	}

	public Employee(PartyType partyType) {
	super.setPartyType(partyType);
	}

	static {
		Party.setPartyMap(Employee.class, PartyType.EMPLOYEE);
	}
}