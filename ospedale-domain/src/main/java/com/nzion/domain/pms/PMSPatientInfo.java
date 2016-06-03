package com.nzion.domain.pms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "PMS_PATIENT")

@NamedQueries(value = { @NamedQuery(name = "GET_PMS_PAT", query = "FROM PMSPatientInfo WHERE patient.id=:patientId") })
public class PMSPatientInfo extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	private Patient patient;

	private Enumeration statBillingFormName;

	private Enumeration statBillingMethod;

	private Enumeration recallMethod;

	private Enumeration billType;

	private Date statHoldUntil;

	@OneToOne(fetch = FetchType.LAZY)
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "STAT_BILLING_FORM_NAME_ID")
	public Enumeration getStatBillingFormName() {
		return statBillingFormName;
	}

	public void setStatBillingFormName(Enumeration statBillingFormName) {
		this.statBillingFormName = statBillingFormName;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "BILLING_METHOD_ID")
	public Enumeration getStatBillingMethod() {
		return statBillingMethod;
	}

	public void setStatBillingMethod(Enumeration statBillingMethod) {
		this.statBillingMethod = statBillingMethod;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "RECALL_METHOD_ID")
	public Enumeration getRecallMethod() {
		return recallMethod;
	}

	public void setRecallMethod(Enumeration recallMethod) {
		this.recallMethod = recallMethod;
	}

	@Column(name = "STAT_HOLD_UNIT")
	public Date getStatHoldUntil() {
		return statHoldUntil;
	}

	public void setStatHoldUntil(Date statHoldUntil) {
		this.statHoldUntil = statHoldUntil;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "BILL_TYPE_ID")
	public Enumeration getBillType() {
		return billType;
	}

	public void setBillType(Enumeration billType) {
		this.billType = billType;
	}

}
