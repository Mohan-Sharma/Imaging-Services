package com.nzion.domain.pms;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.ContactFields;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "CORPORATE")
@AccountNumberField
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class Corporate extends IdGeneratingBaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String corporateName;
	
	private String contactPerson;
	
	private ContactFields contacts;

	public String getCorporateName() {
	return corporateName;
	}

	public void setCorporateName(String corporateName) {
	this.corporateName = corporateName;
	}

	public String getContactPerson() {
	return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
	}

	@Embedded
	public ContactFields getContacts() {
	return contacts;
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}	
	
}
