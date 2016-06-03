/*
 * header file
 */
package com.nzion.domain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.util.UtilValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class Provider.
 */
@Entity
@Table(name = "PROVIDER")
@AccountNumberField
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "com.nzion.domain")
public class Provider extends Employee implements Comparable<Provider>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4251635378986229662L;

	private ProviderDetail detail;

	private DataResource signatureImage;

	private Map<Long, String> preferenceValues;

	private boolean providerAssistant;

	private Provider reportingProvider;

	private Set<Speciality> specialities;


	private String comments;

	private String qualifications;
	
	private String regdNo;
	
	public String getRegdNo() {
		return regdNo;
	}

	public void setRegdNo(String regdNo) {
		this.regdNo = regdNo;
	}

	
	
	private FixedAsset room;
	
	public String getQualifications() {
	return qualifications;
	}

	public void setQualifications(String qualifications) {
	this.qualifications = qualifications;
	}

	public Provider() {
	super(PartyType.PROVIDER);
	setSchedulable(true);
	}

	public Provider(Long id, String firstName, String lastName, ContactFields cf) {
	super(id, firstName, lastName, cf);
	setPartyType(PartyType.PROVIDER);
	setSchedulable(true);
	}

	static {
		Party.setPartyMap(Provider.class, PartyType.PROVIDER);
	}

	public Provider(PartyType providerType) {
	super(providerType);
	}

	@ElementCollection
	@MapKeyColumn(name = "PREF_DEF_ID")
	@Column(name = "VALUE")
	@CollectionTable(name = "PROVIDER_PREFERENCES", joinColumns = { @JoinColumn(name = "PROVIDER_ID", nullable = false) })
	@Cascade(CascadeType.ALL)
	public Map<Long, String> getPreferenceValues() {
	return preferenceValues;
	}

	public void setPreferenceValues(Map<Long, String> preferenceValues) {
	this.preferenceValues = preferenceValues;
	}

	@OneToOne(fetch = FetchType.LAZY, targetEntity = DataResource.class)
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "SIGNATURE_IMAGE")
	public DataResource getSignatureImage() {
	return signatureImage;
	}

	public void setSignatureImage(DataResource signatureImage) {
	this.signatureImage = signatureImage;
	}

	public boolean isProviderAssistant() {
	return providerAssistant;
	}

	public void setProviderAssistant(boolean providerAssistant) {
	this.providerAssistant = providerAssistant;
	setSchedulable(!providerAssistant);
	}

	@OneToOne
	@JoinColumn(name = "REPORTING_PROVIDER_ID")
	public Provider getReportingProvider() {
	return reportingProvider;
	}

	public void setReportingProvider(Provider reportingProvider) {
	this.reportingProvider = reportingProvider;
	}

	public void addSpeciality(Speciality speciality) {
	if (UtilValidator.isEmpty(specialities)) 
		specialities = new HashSet<Speciality>();
	specialities.add(speciality);
	}

	@ManyToMany(targetEntity = Speciality.class,fetch = FetchType.EAGER)
	@JoinTable(name = "PROVIDER_SPECIALITY", joinColumns = { @JoinColumn(name = "PROVIDER_ID") }, inverseJoinColumns = { @JoinColumn(name = "SPECIALITY_ID") })
	@Fetch(FetchMode.SELECT)
	public Set<Speciality> getSpecialities() {
	return specialities;
	}

	public void setSpecialities(Set<Speciality> specialities) {
	this.specialities = specialities;
	}

	

	@Embedded
	public ProviderDetail getDetail() {
	if (detail == null) detail = new ProviderDetail();
	return detail;
	}

	public void setDetail(ProviderDetail detail) {
	this.detail = detail;
	}

	@Override
	@Column(length = 1000)
	public String getComments() {
	return comments;
	}

	@Override
	public void setComments(String comments) {
	this.comments = comments;
	}

	

	public void clearProviderAssistantship() {
	setProviderAssistant(false);
	setReportingProvider(null);
	}
	
	@OneToOne
	@JoinColumn(name = "DEFUALT_VISIT_ROOM")
	public FixedAsset getRoom() {
	return room;
	}

	public void setRoom(FixedAsset room) {
	this.room = room;
	}
	
	@Override
	public String toString() {
	StringBuilder buffer = new StringBuilder();
	buffer.append(super.getFirstName().toUpperCase()).append("  ").append(super.getLastName().toUpperCase());
	return buffer.toString();
	}

	@Override
	public int compareTo(Provider o) {
	return	Long.valueOf(this.id.toString()).compareTo(Long.valueOf(o.id.toString()));
	}

}