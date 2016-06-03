package com.nzion.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "REFERRAL",uniqueConstraints = { @UniqueConstraint(name="UNIQUE_PRACTICE_ACCOUNT_NUMBER",columnNames = { "ACCOUNT_NUMBER"})})
@AccountNumberField
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class Referral extends IdGeneratingBaseEntity implements Salutable {

	private static final long serialVersionUID = 1L;
	private String firstName;
	private String middleName;
	private String lastName;
	private String personalTitle;
	private String prefix;
	private String suffix;
	private Enumeration gender;
	private String salutation;
	private ContactFields contacts;
	private String accountNumber;
	private Date dateOfBirth;
	private String comments;
	private ProviderDetail detail;
	private Set<Speciality> specialities;
	private String referralType;
	private String clinicName;
	private String afyaRegisteredYesNo;
	private boolean afyaRegistered = Boolean.FALSE;
	private String tenantId;

	public String getAfyaRegisteredYesNo() {
		if(isAfyaRegistered())
			afyaRegisteredYesNo = "Yes";
		else
			afyaRegisteredYesNo = "No";
		return afyaRegisteredYesNo;
	}

	public void setAfyaRegisteredYesNo(String afyaRegisteredYesNo) {

		this.afyaRegisteredYesNo = afyaRegisteredYesNo;
	}

	public boolean isAfyaRegistered(){ return  afyaRegistered; }

	public void setAfyaRegistered(boolean afyaRegistered) {
		this.afyaRegistered = afyaRegistered;
	}

	@ManyToMany(targetEntity = Speciality.class,fetch=FetchType.EAGER)
	@JoinTable(name = "REFERRAL_SPECIALITY", joinColumns = { @JoinColumn(name = "REFERRAL_ID") }, inverseJoinColumns = { @JoinColumn(name = "SPECIALITY_ID") })
	@Fetch(FetchMode.SELECT)
	public Set<Speciality> getSpecialities() {
	return specialities;
	}

	public void setSpecialities(Set<Speciality> specialities) {
	this.specialities = specialities;
	}

	@Embedded
	public ProviderDetail getDetail() {
	if(detail == null)
		detail = new ProviderDetail();
	return detail;
	}

	public void setDetail(ProviderDetail detail) {
	this.detail = detail;
	}

	@Column(length = 1000)
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}

	@Column(name = "DATE_OF_BIRTH")
	@Temporal(TemporalType.DATE)
	public Date getDateOfBirth() {
	return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "ACCOUNT_NUMBER", nullable = false)
	public String getAccountNumber() {
	return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public String getFirstName() {
	return firstName;
	}

	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}

	public String getMiddleName() {
	return middleName;
	}

	public void setMiddleName(String middleName) {
	this.middleName = middleName;
	}

	public String getLastName() {
	return lastName;
	}

	public void setLastName(String lastName) {
	this.lastName = lastName;
	}

	public String getPersonalTitle() {
	return personalTitle;
	}

	public void setPersonalTitle(String personalTitle) {
	this.personalTitle = personalTitle;
	}

	public String getPrefix() {
	return prefix;
	}

	public void setPrefix(String prefix) {
	this.prefix = prefix;
	}

	public String getSuffix() {
	return suffix;
	}

	public void setSuffix(String suffix) {
	this.suffix = suffix;
	}
	
	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "GENDER_CODE")
	public Enumeration getGender() {
	return gender;
	}

	public void setGender(Enumeration gender) {
	this.gender = gender;
	}

	public String getSalutation() {
	return salutation;
	}

	public void setSalutation(String salutation) {
	this.salutation = salutation;
	}

	public String getReferralType() {
		return referralType;
	}

	public void setReferralType(String referralType) {
		this.referralType = referralType;
	}

	public String getClinicName() {
		if(clinicName == null)
			clinicName = "";
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Embedded
	public ContactFields getContacts() {
	if(contacts == null)
		contacts = new ContactFields();
	return contacts;
	}

	public void setContacts(ContactFields contacts) {
	this.contacts = contacts;
	}
	
	@Override
	public String toString() {
	StringBuilder buffer = new StringBuilder();
	buffer.append(getFirstName().toUpperCase()).append("  ").append(getLastName().toUpperCase());
	return buffer.toString();
	}

}
