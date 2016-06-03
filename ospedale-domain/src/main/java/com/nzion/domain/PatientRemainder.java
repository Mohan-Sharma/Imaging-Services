package com.nzion.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class PatientRemainder {

	private String remainderText;
	private Patient patient;
	private Date expectedFollowUpDate;
	private Long id;
	
	public String getRemainderText() {
	return remainderText;
	}

	public void setRemainderText(String remainderText) {
	this.remainderText = remainderText;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	@Temporal(TemporalType.DATE)
	public Date getExpectedFollowUpDate() {
	return expectedFollowUpDate;
	}

	public void setExpectedFollowUpDate(Date expectedFollowUpDate) {
	this.expectedFollowUpDate = expectedFollowUpDate;
	}

	@Id
	@GeneratedValue
	public Long getId() {
	return id;
	}

	public void setId(Long id) {
	this.id = id;
	}

}
