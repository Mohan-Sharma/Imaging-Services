package com.nzion.domain.pms;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.ContactFields;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

@Entity
@Table(name = "SCHEME")
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class Scheme extends IdGeneratingBaseEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String schemeName;
	
	private String organizationName;

	private String contactPerson;
	
	private ContactFields contacts;
	
	private Date fromDate;

	private Date thruDate;
	
	private Money allowedPrice;
	
	private Double discountPercentage=0.0;
	
	private DISCOUNTTYPE discountType = DISCOUNTTYPE.PRICE;	
	
	public String getSchemeName() {
	return schemeName;
	}

	public void setSchemeName(String schemeName) {
	this.schemeName = schemeName;
	}

	public String getContactPerson() {
	return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
	}

	@Embedded
	public ContactFields getContacts() {
	return  contacts = (contacts == null ? new ContactFields() : contacts);
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}	
	
	public String getOrganizationName() {
	return organizationName;
	}

	public void setOrganizationName(String organizationName) {
	this.organizationName = organizationName;
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
	
	@Embedded
	public Money getAllowedPrice() {
	allowedPrice = allowedPrice ==  null ? new Money() : allowedPrice;
	return allowedPrice;
	}

	public void setAllowedPrice(Money allowedPrice) {
	this.allowedPrice = allowedPrice;
	}
	
	public Double getDiscountPercentage() {
	return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
	this.discountPercentage = discountPercentage;
	}
	
	public static enum DISCOUNTTYPE{
		PRICE,PERCENTAGE;
	}
	
	public DISCOUNTTYPE getDiscountType() {
	return discountType;
	}

	public void setDiscountType(DISCOUNTTYPE discountType) {
	this.discountType = discountType;
	}


}
