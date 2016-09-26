package com.nzion.report.search.view;

import java.util.Date;

import com.nzion.domain.*;
import com.nzion.domain.product.common.Money;

public class BillingSearchVO {
	
	private Patient patient;
	
	private String ipNumber;
	
	private Employee consultant;
	
	private Date fromDate;
	
	private Date thruDate;
	
	private String invoiceStatus;
	
	private Money lowAmntRange;
	
	private Money highAmntRange;
	
	private String status;
	
	private String orStatus;
	
	
	private String lowEndAmtQuantifier;
	
	private String highEndAmtQuantifier;
	
	private Enumeration paymentMethod;
	
	private String collectedByUser;

	private Referral referral;

	private Provider selectedReferralDoctor;
	
	

	public String getCollectedByUser() {
		return collectedByUser;
	}

	public void setCollectedByUser(String collectedByUser) {
		this.collectedByUser = collectedByUser;
	}

	public Enumeration getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Enumeration paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public String getIpNumber() {
	return ipNumber;
	}

	public void setIpNumber(String ipNumber) {
	this.ipNumber = ipNumber;
	}

	public Employee getConsultant() {
	return consultant;
	}

	public void setConsultant(Employee consultant) {
	this.consultant = consultant;
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

	public String getInvoiceStatus() {
	return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
	this.invoiceStatus = invoiceStatus;
	}

	public Money getLowAmntRange() {
	if(lowAmntRange == null)
		lowAmntRange = new Money();
	return lowAmntRange;
	}

	public void setLowAmntRange(Money lowAmntRange) {
	this.lowAmntRange = lowAmntRange;
	}

	public Money getHighAmntRange() {
	if(highAmntRange == null)
		highAmntRange = new Money();
	return highAmntRange;
	}

	public void setToAmnt(Money highAmntRange) {
	this.highAmntRange = highAmntRange;
	}

	public String getStatus() {
	return status;
	}

	public void setStatus(String status) {
	this.status = status;
	}
	
	public String getOrStatus() {
		return orStatus;
	}

	public void setOrStatus(String orStatus) {
		this.orStatus = orStatus;
	}


	public String getLowEndAmtQuantifier() {
	return lowEndAmtQuantifier;
	}

	public void setLowEndAmtQuantifier(String lowEndAmtQuantifier) {
	this.lowEndAmtQuantifier = lowEndAmtQuantifier;
	}

	public String getHighEndAmtQuantifier() {
	return highEndAmtQuantifier;
	}

	public void setHighEndAmtQuantifier(String highEndAmtQuantifier) {
	this.highEndAmtQuantifier = highEndAmtQuantifier;
	}

	public Referral getReferral() {
		return referral;
	}

	public void setReferral(Referral referral) {
		this.referral = referral;
	}

	public Provider getSelectedReferralDoctor() {
		return selectedReferralDoctor;
	}

	public void setSelectedReferralDoctor(Provider selectedReferralDoctor) {
		this.selectedReferralDoctor = selectedReferralDoctor;
	}
}
