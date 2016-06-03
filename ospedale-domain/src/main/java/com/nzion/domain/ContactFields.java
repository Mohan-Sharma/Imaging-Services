package com.nzion.domain;

import com.nzion.util.UtilValidator;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author Nafis
 *
 */

@Embeddable
public class ContactFields implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private PostalAddressFields postalAddress;
	
	private String email;
	
	private String corporateEmail;
	
	private String faxNumber;
	
	private String mobileNumber;

	private String isdCode;
	
	private String pagerNumber;
	
	private String homePhone;

	private String officePhone;

	private String officeExt;
	
	private String alternatePhone;

	@Embedded
	public PostalAddressFields getPostalAddress() {
	if(postalAddress == null)
		postalAddress = new PostalAddressFields();
	return postalAddress;
	}

	public void setPostalAddress(PostalAddressFields postalAddress) {
	this.postalAddress = postalAddress;
	}

	@Column
	public String getEmail() {
	return email;
	}

	public void setEmail(String email) {
	this.email = email;
	}
	
	@Column
	public String getCorporateEmail() {
		return corporateEmail;
	}

	public void setCorporateEmail(String corporateEmail) {
		this.corporateEmail = corporateEmail;
	}

	@Column
	public String getFaxNumber() {
	return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
	this.faxNumber = faxNumber;
	}

	@Column
	public String getMobileNumber() {
	return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
	}

	@Column
	public String getPagerNumber() {
	return pagerNumber;
	}

	public void setPagerNumber(String pagerNumber) {
	this.pagerNumber = pagerNumber;
	}

	@Column
	public String getHomePhone() {
	return homePhone;
	}

	public void setHomePhone(String homePhone) {
	this.homePhone = homePhone;
	}

	@Column
	public String getOfficePhone() {
	return officePhone;
	}

	public void setOfficePhone(String officePhone) {
	this.officePhone = officePhone;
	}

	@Column
	public String getOfficeExt() {
	return officeExt;
	}

	public void setOfficeExt(String officeExt) {
	this.officeExt = officeExt;
	}
	
	@Column
	public String getAlternatePhone() {
	return alternatePhone;
	}

	public void setAlternatePhone(String alternatePhone) {
	this.alternatePhone = alternatePhone;
	}

	public String getIsdCode() {
		if(UtilValidator.isEmpty(isdCode))
			isdCode = "965";
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}
}