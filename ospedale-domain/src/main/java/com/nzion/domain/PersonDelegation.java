package com.nzion.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
public class PersonDelegation extends IdGeneratingBaseEntity {

	private Person person;

	private Person personToBeDelegated;

	private Date fromDate;

	private Date thruDate;
	
	private String comments;

	@OneToOne
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
	return person;
	}

	public void setPerson(Person person) {
	this.person = person;
	}

	@OneToOne
	@JoinColumn(name = "DELEGATED_PERSON_ID")
	public Person getPersonToBeDelegated() {
	return personToBeDelegated;
	}

	public void setPersonToBeDelegated(Person personToBeDelegated) {
	this.personToBeDelegated = personToBeDelegated;
	}

	public Date getFromDate() {
	return fromDate;
	}

	public void setFromDate(Date fromDate) {
	this.fromDate = fromDate;
	}

	public Date getThruDate() {
	return thruDate;
	}

	public void setThruDate(Date thruDate) {
	this.thruDate = thruDate;
	}
	
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}


	private static final long serialVersionUID = 1L;

}
