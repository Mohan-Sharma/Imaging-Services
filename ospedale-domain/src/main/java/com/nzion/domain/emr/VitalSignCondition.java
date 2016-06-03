package com.nzion.domain.emr;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Feb 24, 2011
 */

@Entity
@Table
public class VitalSignCondition extends IdGeneratingBaseEntity {

	private Long ageFrom;
	
	private Long ageTo;

	private Enumeration ethinicity;

	private Enumeration race;

	private Enumeration gender;
	
	private String lowerBound;
	
	private String upperBound;
	
	public Long getAgeFrom() {
	return ageFrom;
	}

	public void setAgeFrom(Long ageFrom) {
	this.ageFrom = ageFrom;
	}

	public Long getAgeTo() {
	return ageTo;
	}

	public void setAgeTo(Long ageTo) {
	this.ageTo = ageTo;
	}

	@OneToOne
	@JoinColumn(name="ETHINICITY_ID")
	public Enumeration getEthinicity() {
	return ethinicity;
	}

	public void setEthinicity(Enumeration ethinicity) {
	this.ethinicity = ethinicity;
	}

	@OneToOne
	@JoinColumn(name="RACE_ID")
	public Enumeration getRace() {
	return race;
	}

	public void setRace(Enumeration race) {
	this.race = race;
	}

	@OneToOne
	@JoinColumn(name="GENDER_ID")
	public Enumeration getGender() {
	return gender;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}
	
	public String getLowerBound() {
	return lowerBound;
	}

	public void setLowerBound(String lowerBound) {
	this.lowerBound = lowerBound;
	}

	public String getUpperBound() {
	return upperBound;
	}

	public void setUpperBound(String upperBound) {
	this.upperBound = upperBound;
	}
	
	public boolean validate(Patient patient, String value){
	if(patient == null || (lowerBound == null && upperBound == null) || (UtilValidator.isEmpty(value)))
		return true;
	double lb = lowerBound == null ? Double.MIN_VALUE : Double.parseDouble(lowerBound) ;
	double ub = upperBound == null ? Double.MAX_VALUE : Double.parseDouble(upperBound);
	if(ethinicity != null && patient.getEthnicity() != null && !ethinicity.equals(patient.getEthnicity()))
		return true;
	if(race != null && patient.getRace() != null && !race.equals(patient.getRace()))
		return true;
	if(gender != null && patient.getGender() != null && !gender.equals(patient.getGender()))
		return true;
	if(!UtilDateTime.checkAgeRangeInyears(patient.getDateOfBirth(), ageFrom, ageTo))
		return true;
	double numericValue = Double.parseDouble(value);
	return numericValue >= lb && numericValue <= ub;
	}

	private static final long serialVersionUID = 1L;
}
