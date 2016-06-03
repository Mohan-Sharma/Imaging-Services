package com.nzion.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class ReminderSendBoxIdClass implements Serializable {

	private static final long serialVersionUID = 1L;

	private Patient patient;

	private Date reminderSent;

	public ReminderSendBoxIdClass() {
	}

	public ReminderSendBoxIdClass(Patient patient, Date date) {
	this.patient = patient;
	this.reminderSent = date;
	}

	@OneToOne
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	@Temporal(TemporalType.DATE)
	public Date getReminderSent() {
	return reminderSent;
	}

	public void setReminderSent(Date reminderSent) {
	this.reminderSent = reminderSent;
	}

}
