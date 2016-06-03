package com.nzion.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProviderDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	private String upinNumber;
	private String npiNumber;
	private String deaNumber;
	private String registrationId;
	private String stateLicense;
	private String qualification;
	private String ssnNumber;
	private String tinNumber;
	private Boolean waiverSigned;
	
	public String getTinNumber() {
	return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
	this.tinNumber = tinNumber;
	}
	public Boolean getWaiverSigned() {
	return waiverSigned;
	}
	public void setWaiverSigned(Boolean waiverSigned) {
	this.waiverSigned = waiverSigned;
	}
	public String getSsnNumber() {
	return ssnNumber;
	}
	public void setSsnNumber(String ssnNumber) {
	this.ssnNumber = ssnNumber;
	}
	public String getUpinNumber() {
	return upinNumber;
	}
	public void setUpinNumber(String upinNumber) {
	this.upinNumber = upinNumber;
	}
	public String getNpiNumber() {
	return npiNumber;
	}
	public void setNpiNumber(String npiNumber) {
	this.npiNumber = npiNumber;
	}
	public String getDeaNumber() {
	return deaNumber;
	}
	public void setDeaNumber(String deaNumber) {
	this.deaNumber = deaNumber;
	}
	public String getRegistrationId() {
	return registrationId;
	}
	public void setRegistrationId(String registrationId) {
	this.registrationId = registrationId;
	}
	public String getStateLicense() {
	return stateLicense;
	}
	public void setStateLicense(String stateLicense) {
	this.stateLicense = stateLicense;
	}
	public String getQualification() {
	return qualification;
	}
	public void setQualification(String qualification) {
	this.qualification = qualification;
	}
}
