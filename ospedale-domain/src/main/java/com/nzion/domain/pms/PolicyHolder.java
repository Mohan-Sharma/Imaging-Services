package com.nzion.domain.pms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nzion.domain.ContactFields;
import com.nzion.domain.Enumeration;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "POLICY_HOLDER")
public class PolicyHolder extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = -951944820319713973L;
	private String firstName;
	private String middleName;
	private String lastName;
	private String ssnNumber;
	private Date dateOfBirth;
	private Enumeration gender;

	private Policy policy;

	private ContactFields contacts;

	public ContactFields getContacts() {
	if(contacts == null)
		contacts = new ContactFields();
	return contacts;
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}

	@OneToOne(targetEntity = Policy.class, mappedBy="policyHolder")
	public Policy getPolicy() {
	return policy;
	}

	public void setPolicy(Policy policy) {
	this.policy = policy;
	}

	@Column(name = "FIRST_NAME")
	public String getFirstName() {
	return firstName;
	}

	@Column(name = "MIDDLE_NAME")
	public String getMiddleName() {
	return middleName;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
	return lastName;
	}

	@Column(name = "SSN_NUMBER")
	public String getSsnNumber() {
	return ssnNumber;
	}

	@Column(name = "DATE_OF_BIRTH")
	public Date getDateOfBirth() {
	return dateOfBirth;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "GENDER")
	public Enumeration getGender() {
	return gender;
	}

	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
	this.middleName = middleName;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public void setSsnNumber(String ssnNumber) {
	this.ssnNumber = ssnNumber;
	}

	public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}
}