package com.nzion.report.search.view;

import java.util.Date;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Location;
import com.nzion.domain.Referral;
import com.nzion.util.UtilDateTime;

public class PatientSearchVO {
	
	private String firstName;
	
	private String lastName;
	
	private int age;
	
	private Enumeration gender;
	
	private Enumeration ethincity;
	
	private Enumeration race;
	
	private Enumeration state;
	
	private Location location;
	
	private Referral referral;
	
	private Date createdFromDate;
	
	private Date createdThruDate;
	
	private Date dateOfBirth;
	
	private String operator;
	
	public String getOperator() {
	return operator;
	}

	public void setOperator(String operator) {
	this.operator = operator;
	}

	public String getFirstName() {
	return firstName;
	}

	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	public String getLastName() {
	return lastName;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public int getAge() {
	return age;
	}

	public void setAge(int age) {
	dateOfBirth = UtilDateTime.getDateOfBirth(age);
	this.age = age;
	}

	public Enumeration getGender() {
	return gender;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}

	public Enumeration getEthincity() {
	return ethincity;
	}

	public void setEthincity(Enumeration ethincity) {
	this.ethincity = ethincity;
	}

	public Enumeration getRace() {
	return race;
	}

	public void setRace(Enumeration race) {
	this.race = race;
	}

	public Enumeration getState() {
	return state;
	}

	public void setState(Enumeration state) {
	this.state = state;
	}

	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}

	public Referral getReferral() {
	return referral;
	}

	public void setReferral(Referral referral) {
	this.referral = referral;
	}

	public Date getCreatedFromDate() {
	return createdFromDate;
	}

	public void setCreatedFromDate(Date createdFromDate) {
	this.createdFromDate = createdFromDate;
	}

	public Date getCreatedThruDate() {
	return createdThruDate;
	}

	public void setCreatedThruDate(Date createdThruDate) {
	this.createdThruDate = createdThruDate;
	}

	public Date getDateOfBirth() {
	return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
	}

}
