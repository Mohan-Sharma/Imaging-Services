/*
 * header file
 */
package com.nzion.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.ForeignKey;

import com.nzion.domain.annot.SystemAuditable;
import com.nzion.domain.base.IdGeneratingBaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Identification.
 */
@Entity
@Table(name = "IDENTIFICATION")
@Inheritance(strategy = InheritanceType.JOINED)
@SystemAuditable
@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
public abstract class Identification extends IdGeneratingBaseEntity {

	public enum IdentificationType {
		DRIVING_LICENSE, PASSPORT
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the identification type.
	 * 
	 * @return the identification type
	 */
	@Transient
	public abstract IdentificationType getIdentificationType();

	private Patient patient;

	@ManyToOne(targetEntity = Patient.class)
	@JoinColumns(value = { @JoinColumn(name = "PATIENT_ID") })
	@ForeignKey(name = "FK_PERSON_IDENTIFICATION")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

}
