package com.nzion.domain;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.annot.DisplayNameField;
import com.nzion.domain.base.IdGeneratingBaseEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 */
@Entity
@Table(name = "LOCATION", uniqueConstraints = { @UniqueConstraint(name="UNIQUE_PRACTICE_LOCATION_CODE",columnNames = { "LOCATION_CODE"})})
@AccountNumberField("locationCode")
@DisplayNameField("name")
@Filters( { @Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class Location extends IdGeneratingBaseEntity  implements Comparable<Location>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The location code. */
	private String locationCode;

	/** The description. */
	private String name;

	private HistoricalModel historicalModel;

	@Embedded
	public HistoricalModel getHistorical() {
	return historicalModel;
	}

	public void setHistorical(HistoricalModel historicalModel) {
	this.historicalModel = historicalModel;
	}

	/** The facility id. */
	private String facilityId;

	/** The medicare id. */
	private String medicareId;

	private String medicaidId;

	/** The clia labs. */
	private String cliaLabs;

	/** The npi. */
	private String npi;

	/** Ask about this. */
	// private HCFAPlaceOfService HCFAPlaceOfService:
	private Enumeration hcfaPlaceOfService;

	/** The location type. */
	private Enumeration locationType;

	private Time teaFrom;
	private Time teaTo;

	private Time lunchFrom;
	private Time lunchTo;

	private Person dean;

	private ContactFields contacts;
	
	private String comment;
	
	private String pharmacyContactNumber;
	
	public String getPharmacyContactNumber() {
	return pharmacyContactNumber;
	}

	public void setPharmacyContactNumber(String pharmacyContactNumber) {
	this.pharmacyContactNumber = pharmacyContactNumber;
	}

	@Column(length = 1000)
	public String getComment() {
	return comment;
	}

	public void setComment(String comment) {
	this.comment = comment;
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

	/**
	 * Gets the clia labs.
	 *
	 * @return the clia labs
	 */
	@Column(name = "CLIALABS_ID")
	public String getCliaLabs() {
	return cliaLabs;
	}

	/**
	 * Gets the facility id.
	 *
	 * @return the facility id
	 */
	@Column(name = "FACILITY_ID")
	public String getFacilityId() {
	return facilityId;
	}

	
	@OneToOne
	@JoinColumn(name = "HCFA_PLACE_OF_SERVICE")
	public Enumeration getHcfaPlaceOfService() {
	return hcfaPlaceOfService;
	}

	/**
	 * Gets the location code.
	 *
	 * @return the location code
	 */
	@Column(name = "LOCATION_CODE")
	public String getLocationCode() {
	return locationCode;
	}

	/**
	 * Gets the location type.
	 *
	 * @return the location type
	 */
	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "LOCATION_TYPE_ID")
	public Enumeration getLocationType() {
	return locationType;
	}


	/**
	 * Gets the medicare id.
	 *
	 * @return the medicare id
	 */
	@Column(name = "MEDICARE_ID")
	public String getMedicareId() {
	return medicareId;
	}

	/**
	 * Gets the npi.
	 *
	 * @return the npi
	 */
	@Column(name = "NPI")
	public String getNpi() {
	return npi;
	}


	/**
	 * Sets the clia labs.
	 *
	 * @param cliaLabs
	 *            the new clia labs
	 */
	public void setCliaLabs(String cliaLabs) {
	this.cliaLabs = cliaLabs;
	}

	/**
	 * Sets the facility id.
	 *
	 * @param facilityId
	 *            the new facility id
	 */
	public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
	}

	/**
	 * Sets the hcfa place of service.
	 *
	 * @param hcfaPlaceOfService
	 *            the new hcfa place of service
	 */
	public void setHcfaPlaceOfService(Enumeration hcfaPlaceOfService) {
	this.hcfaPlaceOfService = hcfaPlaceOfService;
	}

	/**
	 * Sets the location code.
	 *
	 * @param locationCode
	 *            the new location code
	 */
	public void setLocationCode(String locationCode) {
	this.locationCode = locationCode;
	}

	/**
	 * Sets the location type.
	 *
	 * @param locationType
	 *            the new location type
	 */
	public void setLocationType(Enumeration locationType) {
	this.locationType = locationType;
	}

	/**
	 * Sets the medicare id.
	 *
	 * @param medicareId
	 *            the new medicare id
	 */
	public void setMedicareId(String medicareId) {
	this.medicareId = medicareId;
	}

	/**
	 * Sets the npi.
	 *
	 * @param npi
	 *            the new npi
	 */
	public void setNpi(String npi) {
	this.npi = npi;
	}

	@Column(name = "TEA_FROM")
	public Time getTeaFrom() {
	return teaFrom;
	}

	@Column(name = "TEA_TO")
	public Time getTeaTo() {
	return teaTo;
	}

	@Column(name = "LUNCH_FROM")
	public Time getLunchFrom() {
	return lunchFrom;
	}

	@Column(name = "LUNCH_TO")
	public Time getLunchTo() {
	return lunchTo;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAN_ID")
	public Person getDean() {
	return dean;
	}

	public void setTeaFrom(Time teaFrom) {
	this.teaFrom = teaFrom;
	}

	public void setTeaTo(Time teaTo) {
	this.teaTo = teaTo;
	}

	public void setLunchFrom(Time lunchFrom) {
	this.lunchFrom = lunchFrom;
	}

	public void setLunchTo(Time lunchTo) {
	this.lunchTo = lunchTo;
	}

	public void setDean(Person dean) {
	this.dean = dean;
	}

	@Column(name = "LOCATION_NAME")
	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	@Column(name = "MEDICAID_ID")
	public String getMedicaidId() {
	return medicaidId;
	}

	public void setMedicaidId(String medicaidId) {
	this.medicaidId = medicaidId;
	}

	@Override
	public String toString() {
	return name;
	}

	@Override
	public boolean equals(Object obj) {
	/* if (this == obj) return true;
	if (this.getClass() != obj.getClass()) return false;
	Location other = (Location) obj;
	if (this.locationCode == null) {
		if (other.locationCode != null) return false;
	} else {
		if (!this.locationCode.equals(other.locationCode)) return false;
	} */
	return true;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 0;
	result = prime * result + ((locationCode == null) ? 0 : locationCode.hashCode());
	return result;
	}
	
	@Override
	public int compareTo(Location o) {
		 return this.name.compareToIgnoreCase(o.name);
	}
   
}
