package com.nzion.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
@Filters( {@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")})
public class PatientOtherContactDetail extends IdGeneratingBaseEntity {

	private Patient patient;

	private PostalAddressFields internationalContactFields;

	private Referral referral;

	private ContactFields emergencyContactFields;
	
	private PostalAddressFields alternateContactFields;
	
	public PatientOtherContactDetail(){}
	
	public PatientOtherContactDetail(Patient patient){
	this.patient = patient;
	}

	@OneToOne
	@JoinColumn(name = "Referral_ID")
	public Referral getReferral() {
	if(referral == null)
		referral = new Referral();
	return referral;
	}

	public void setReferral(Referral referral) {
	this.referral = referral;
	}

	@OneToOne
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}


	@Embedded
	@AttributeOverrides( { @AttributeOverride(name = "toName", column = @Column(name = "INTERNATIONAL_TO_NAME")),
			@AttributeOverride(name = "attnName", column = @Column(name = "INTERNATIONAL_ATTN_NAME")),
			@AttributeOverride(name = "address1", column = @Column(name = "INTERNATIONAL_ADDRESS1")),
			@AttributeOverride(name = "address2", column = @Column(name = "INTERNATIONAL_ADDRESS2")),
			@AttributeOverride(name = "directions", column = @Column(name = "INTERNATIONAL_DIRECTIONS")),
			@AttributeOverride(name = "city", column = @Column(name = "INTERNATIONAL_CITY")),
			@AttributeOverride(name = "postalCode", column = @Column(name = "INTERNATIONAL_POSTAL_CODE")),
			@AttributeOverride(name = "postalCodeExt", column = @Column(name = "INTERNATIONAL_POSTAL_CODE_EXT")),
			@AttributeOverride(name = "countryGeo", column = @Column(name = "INTERNATIONAL_COUNTRY_GEO")),
			@AttributeOverride(name = "stateProvinceGeo", column = @Column(name = "INTERNATIONAL_STATE_PROVINCE_GEO")),
			@AttributeOverride(name = "countyGeo", column = @Column(name = "INTERNATIONAL_COUNTY_GEO")),
			@AttributeOverride(name = "geoPoint", column = @Column(name = "INTERNATIONAL_GEO_POINT")) })
	public PostalAddressFields getInternationalContactFields() {
	if(internationalContactFields == null)
		internationalContactFields = new PostalAddressFields();
	return internationalContactFields;
	}

	public void setInternationalContactFields(PostalAddressFields internationalContactFields) {
	this.internationalContactFields = internationalContactFields;
	}

	@Embedded
	public ContactFields getEmergencyContactFields() {
	if(emergencyContactFields == null)
		emergencyContactFields = new ContactFields();
	return emergencyContactFields;
	}

	public void setEmergencyContactFields(ContactFields emergencyContactFields) {
	this.emergencyContactFields = emergencyContactFields;
	}

	@Embedded
	@AttributeOverrides( { @AttributeOverride(name = "toName", column = @Column(name = "ALTERNATE_TO_NAME")),
			@AttributeOverride(name = "attnName", column = @Column(name = "ALTERNATE_ATTN_NAME")),
			@AttributeOverride(name = "address1", column = @Column(name = "ALTERNATE_ADDRESS1")),
			@AttributeOverride(name = "address2", column = @Column(name = "ALTERNATE_ADDRESS2")),
			@AttributeOverride(name = "directions", column = @Column(name = "ALTERNATE_DIRECTIONS")),
			@AttributeOverride(name = "city", column = @Column(name = "ALTERNATE_CITY")),
			@AttributeOverride(name = "postalCode", column = @Column(name = "ALTERNATE_POSTAL_CODE")),
			@AttributeOverride(name = "postalCodeExt", column = @Column(name = "ALTERNATE_POSTAL_CODE_EXT")),
			@AttributeOverride(name = "countryGeo", column = @Column(name = "ALTERNATE_COUNTRY_GEO")),
			@AttributeOverride(name = "stateProvinceGeo", column = @Column(name = "ALTERNATE_STATE_PROVINCE_GEO")),
			@AttributeOverride(name = "countyGeo", column = @Column(name = "ALTERNATE_COUNTY_GEO")),
			@AttributeOverride(name = "geoPoint", column = @Column(name = "ALTERNATE_GEO_POINT")) })
	public PostalAddressFields getAlternateContactFields() {
	if(alternateContactFields == null)
		alternateContactFields = new PostalAddressFields();
	return alternateContactFields;
	}

	public void setAlternateContactFields(PostalAddressFields alternateContactFields) {
	this.alternateContactFields = alternateContactFields;
	}
	private static final long serialVersionUID = 1L;

}
