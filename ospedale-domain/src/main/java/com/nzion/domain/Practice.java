/*
 * header file
 */
package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.pms.CustomFormField;
import com.nzion.enums.SUBSCRIPTION_TYPE;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

// TODO: Auto-generated Javadoc
/**
 * The Class Practice.
 */
@Entity
@Table(name = "PRACTICE")
public class Practice extends IdGeneratingBaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The contact person name. */
	private String contactPersonName;

	/** The practice name. */
	private String practiceName;

	/** The logo url. */
	private String logoUrl;

	/** The federal tax id. */
	private String federalTaxId;

	/** The npi number. */
	private String npiNumber;

	/** The employer id. */
	private String employerId;

	private TimeZone timeZone;

	private UserLogin adminUserLogin;

	private Set<String> secretQuestions;
	
	private Set<CustomFormField> customFormFields;
	
	private ContactFields contacts;
	
	private SUBSCRIPTION_TYPE subscriptionType = SUBSCRIPTION_TYPE.EMR;
	
	private String serviceTaxNumber;
	
	private String panNumber;
	
	private String drugLicence;
	
	private String accrNumber;
	
	private Date validFrom ;
	
	private Date validThru ;

	private String tenantId;

	private String imageUrl;

	private boolean logoWithAddress = Boolean.FALSE;
	
	
	public String getServiceTaxNumber() {
		return serviceTaxNumber;
	}

	public void setServiceTaxNumber(String serviceTaxNumber) {
		this.serviceTaxNumber = serviceTaxNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getDrugLicence() {
		return drugLicence;
	}

	public void setDrugLicence(String drugLicence) {
		this.drugLicence = drugLicence;
	}

	public String getAccrNumber() {
		return accrNumber;
	}

	public void setAccrNumber(String accrNumber) {
		this.accrNumber = accrNumber;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidThru() {
		return validThru;
	}

	public void setValidThru(Date validThru) {
		this.validThru = validThru;
	}

	@Enumerated(EnumType.STRING)
	public SUBSCRIPTION_TYPE getSubscriptionType() {
	return subscriptionType;
	}

	public void setSubscriptionType(SUBSCRIPTION_TYPE subscriptionType) {
	this.subscriptionType = subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
	this.subscriptionType = SUBSCRIPTION_TYPE.valueOf(subscriptionType);
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

	@ElementCollection
    @JoinTable(name="SECRET_QUESTIONS")
    @Fetch(FetchMode.SELECT)
	public Set<String> getSecretQuestions() {
	return secretQuestions;
	}

	public void setSecretQuestions(Set<String> secretQuestions) {
	this.secretQuestions = secretQuestions;
	}

	@OneToOne(targetEntity = UserLogin.class,cascade = CascadeType.ALL,fetch=FetchType.LAZY,optional=true)
	@JoinColumn(name = "ADMIN_USER_LOGIN_ID")
	public UserLogin getAdminUserLogin() {
	return adminUserLogin;
	}

	public void setAdminUserLogin(UserLogin userLogin) {
	this.adminUserLogin = userLogin;
	}

	@Column(name = "FEDERAL_TAX_ID")
	public String getFederalTaxId() {
	return federalTaxId;
	}

	public void setFederalTaxId(String federalTaxId) {
	this.federalTaxId = federalTaxId;
	}

	@Column(name = "NPI_NUMBER")
	public String getNpiNumber() {
	return npiNumber;
	}

	public void setNpiNumber(String npiNumber) {
	this.npiNumber = npiNumber;
	}

	@Column(name = "CONTACT_PERSON_NAME")
//	@Size(min=5, max=10,message="Size should be between 5 and 10 characters")
	public String getContactPersonName() {
	return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
	this.contactPersonName = contactPersonName;
	}

	public String getPracticeName() {
	return practiceName;
	}

	public void setPracticeName(String practiceName) {
	this.practiceName = practiceName;
	}


	@Column(name = "LOGO_URL")
	public String getLogoUrl() {
	return logoUrl;
	}

	/**
	 * Sets the logo url.
	 *
	 * @param logoUrl the new logo url
	 */
	public void setLogoUrl(String logoUrl) {
	this.logoUrl = logoUrl;
	}

	/**
	 * Gets the employer id.
	 *
	 * @return the employer id
	 */
	@Column(name = "EMPLOYER_ID")
	public String getEmployerId() {
	return employerId;
	}

	/**
	 * Sets the employer id.
	 *
	 * @param employerId the new employer id
	 */
	public void setEmployerId(String employerId) {
	this.employerId = employerId;
	}

	@Column(name = "TIMEZONE")
	public TimeZone getTimeZone() {
	return timeZone;
	}

	public void setTimeZone(TimeZone timeZone) {
	this.timeZone = timeZone;
	}

	@Column(name = "TENANT_ID")
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public boolean isLogoWithAddress() {
		return logoWithAddress;
	}

	public void setLogoWithAddress(boolean logoWithAddress) {
		this.logoWithAddress = logoWithAddress;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}