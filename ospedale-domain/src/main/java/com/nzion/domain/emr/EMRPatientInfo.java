package com.nzion.domain.emr;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name="EMR_PATIENT")
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class EMRPatientInfo extends IdGeneratingBaseEntity{

	private static final long serialVersionUID = 1L;

	private Patient patient;

	/** The blood group. */
	private Enumeration bloodGroup;
	

	/**
	 * Gets the medicalhistory.
	 * 
	 * @return the medicalhistory
	 */

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "BLOOGGROUP_CODE")
	public Enumeration getBloodGroup() {
	return bloodGroup;
	}

	public void setBloodGroup(Enumeration bloodGroup) {
	this.bloodGroup = bloodGroup;
	}

	@OneToOne
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}
}