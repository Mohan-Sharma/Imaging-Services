package com.nzion.domain;

import javax.persistence.*;

import com.nzion.domain.emr.SpokenLanguage;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.annot.AccountNumberField;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
@AccountNumberField
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")

public class Employee extends Person {
	private static final long serialVersionUID = 1L;
	
	private String comments;
	private String experienceEn;
	private String nationality;
	private String certificationEng;
	private String certificationArb;
	private String registrationNo;
	private Set<SpokenLanguage> spokenLanguages;

//	private Boolean phlebotomist;
	private boolean phlebotomist = Boolean.FALSE;
	private String qualificationEng;
	private String qualificationArb;

	@Column(length = 1000)
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}

	public boolean isPhlebotomist() {
		return phlebotomist;
	}

	public void setPhlebotomist(boolean phlebotomist) {
		// setSchedulable(phlebotomist);
		this.phlebotomist = phlebotomist;
	}

	public Employee() {
	super(PartyType.EMPLOYEE);
	}
	
	public Employee(Long id,String firstName,String lastName,ContactFields cf){
	super(id,firstName,lastName,cf);
	setPartyType(PartyType.EMPLOYEE);
	}

	public Employee(PartyType partyType) {
	super.setPartyType(partyType);
	}

	static {
		Party.setPartyMap(Employee.class, PartyType.EMPLOYEE);
	}

	@ManyToMany(targetEntity = SpokenLanguage.class,fetch = FetchType.EAGER)
	@JoinTable(name = "EMPLOYEE_SPOKEN_LANGUAGE",joinColumns = {@JoinColumn(name = "EMPLOYEE_ID")},inverseJoinColumns = {@JoinColumn(name = "SPOKEN_LANGUAGE_ID")})
	@Fetch(FetchMode.SELECT)
	public Set<SpokenLanguage> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(Set<SpokenLanguage> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public String getExperienceEn() {
		return experienceEn;
	}

	public void setExperienceEn(String experienceEn) {
		this.experienceEn = experienceEn;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getCertificationEng() {
		return certificationEng;
	}

	public void setCertificationEng(String certificationEng) {
		this.certificationEng = certificationEng;
	}

	public String getCertificationArb() {
		return certificationArb;
	}

	public void setCertificationArb(String certificationArb) {
		this.certificationArb = certificationArb;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getQualificationEng() {
		return qualificationEng;
	}

	public void setQualificationEng(String qualificationEng) {
		this.qualificationEng = qualificationEng;
	}

	public String getQualificationArb() {
		return qualificationArb;
	}

	public void setQualificationArb(String qualificationArb) {
		this.qualificationArb = qualificationArb;
	}
}