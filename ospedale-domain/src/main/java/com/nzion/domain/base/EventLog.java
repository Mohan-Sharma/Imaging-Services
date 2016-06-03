package com.nzion.domain.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;
import com.nzion.enums.EventType;
import com.nzion.util.UtilDateTime;

/**
 * @author Sandeep Prusty
 * Mar 11, 2011
 */

@Entity
@Table(name = "EVENT_LOG")

public class EventLog extends IdGeneratingBaseEntity  {

	private EventType eventType;

	private String detail;



	private Patient patient;

	private UserLogin userLogin;

	private Date createdDate;

	private Date createdTime;

	public EventLog() {
	}

	public EventLog(EventType eventType, String detail) {
	this.eventType = eventType;
	this.detail = detail;
	this.createdDate = UtilDateTime.dateOnly(new Date());
	this.createdTime = UtilDateTime.timeOnly(new Date());
	}

	public EventLog(EventType eventType, String detail, Patient patient, UserLogin userLogin) {
	this(eventType, detail);
	this.patient = patient;
	this.userLogin = userLogin;
	}

	@Column(name = "EVENT_TYPE")
	@Enumerated(EnumType.STRING)
	public EventType getEventType() {
	return eventType;
	}

	public void setEventType(EventType eventType) {
	this.eventType = eventType;
	}

	@Column(name = "DETAIL")
	public String getDetail() {
	return detail;
	}

	public void setDetail(String detail) {
	this.detail = detail;
	}

	@OneToOne
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	@OneToOne
	public UserLogin getUserLogin() {
	return userLogin;
	}

	public void setUserLogin(UserLogin userLogin) {
	this.userLogin = userLogin;
	}

	public Date getCreatedDate() {
	return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
	}

	public Date getCreatedTime() {
	return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
	this.createdTime = createdTime;
	}

	private static final long serialVersionUID = 1L;
}