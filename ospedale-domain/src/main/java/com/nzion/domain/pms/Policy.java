/*
 * header file
 */
package com.nzion.domain.pms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;

import com.nzion.domain.DataResource;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Historical;
import com.nzion.domain.HistoricalModel;
import com.nzion.domain.Patient;
import com.nzion.domain.base.IdGeneratingBaseEntity;

/**
 * The Class Policy.
 */
@Entity
@Table(name = "POLICY")

public class Policy extends IdGeneratingBaseEntity implements Historical, Comparable<Policy> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8183086152769611806L;

	/** The policy name. */
	private String policyName; // mapped

	private String policyNumber; // Mapped

	/** The group name. */
	private String groupName; // mapped

	/** The group number. */
	private String groupNumber; // Mapped

	/** The relationship. */
	private Enumeration relationship; // mapped

	/** The subscriber id. */
	private String subscriberId; // mapped

	/** The historical model. */
	private HistoricalModel historicalModel; // MAPPED

	/** The policy limit. */
	private PolicyLimit policyLimit; // Mapped

	/** The priority. */
	private Integer priority; // Mapped

	/** The insurance provider. */
	private InsuranceProvider insuranceProvider;

	/** The pms patient info. */
	private Patient patient;

	private DataResource frontImage; // mapped

	private DataResource backImage; // mapped

	private PolicyHolder policyHolder; // Mapped

	private Boolean isEmployerGroupHealthPlan; // mapped
	private String employerGroupHealthPlanSize;

	private Boolean isLargeGroupHealthPlan; // mapped
	private String largeGroupHealthPlanSize;

	private Boolean coPayAmountAtChargeEntry;
	private Boolean coPayAmountAtScheduleEntry;
	private Double coPayDefaultAmount; // Mapped
	private Double coPayAlt1Amount; // mapped
	private Double coPayAlt2Amount; // mapped
	private String coPayAlt1Service; // mapped
	private String coPayAlt2Service; // mapped

	private Boolean authorizationRequired; // mapped
	
	private String insuranceType;


	@OneToOne(targetEntity = PolicyHolder.class)
	@JoinColumn(name = "POLICY_HOLDER_ID")
	@Cascade(value = { CascadeType.ALL })
	public PolicyHolder getPolicyHolder() {
	return policyHolder;
	}

	public void setPolicyHolder(PolicyHolder policyHolder) {
	this.policyHolder = policyHolder;
	}

	public Policy() {
	}

	/**
	 * Gets the group name.
	 *
	 * @return the group name
	 */
	@Column(name = "GROUP_NAME")
	public String getGroupName() {
	return groupName;
	}

	/**
	 * Gets the group number.
	 *
	 * @return the group number
	 */
	@Column(name = "GROUP_NUMBER")
	public String getGroupNumber() {
	return groupNumber;
	}

	/**
	 * Gets the historical model.
	 *
	 * @return the historical model
	 */
	public HistoricalModel getHistoricalModel() {
	return historicalModel;
	}

	/**
	 * Gets the insurance provider.
	 *
	 * @return the insurance provider
	 */
	@ManyToOne(targetEntity = InsuranceProvider.class)
	@JoinColumn(name = "INSURANCE_PROVIDER",nullable=false)
	public InsuranceProvider getInsuranceProvider() {
	return insuranceProvider;
	}

	/**
	 * Gets the pms patient info.
	 *
	 * @return the pms patient info
	 */
	@ManyToOne(targetEntity = Patient.class)
	@JoinColumns(value = { @JoinColumn(name = "PATIENT_ID") })
	public Patient getPatient() {
	return patient;
	}

	/**
	 * Gets the policy limit.
	 *
	 * @return the policy limit
	 */
	@Embedded
	public PolicyLimit getPolicyLimit() {
	return policyLimit;
	}

	/**
	 * Gets the policy name.
	 *
	 * @return the policy name
	 */
	@Column(name = "POLICY_NAME")
	public String getPolicyName() {
	return policyName;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Column(name = "SEQUENCE_NO")
	public Integer getPriority() {
	return priority;
	}

	/**
	 * Gets the relationship.
	 *
	 * @return the relationship
	 */
	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "RELATIONSHIP")
	public Enumeration getRelationship() {
	return relationship;
	}

	/**
	 * Gets the subscriber id.
	 *
	 * @return the subscriber id
	 */
	@Column(name = "SUBSCRIBER_ID")
	public String getSubscriberId() {
	return subscriberId;
	}

	/**
	 * Sets the group name.
	 *
	 * @param groupName the new group name
	 */
	public void setGroupName(String groupName) {
	this.groupName = groupName;
	}

	/**
	 * Sets the group number.
	 *
	 * @param groupNumber the new group number
	 */
	public void setGroupNumber(String groupNumber) {
	this.groupNumber = groupNumber;
	}

	/**
	 * Sets the historical model.
	 *
	 * @param historicalModel the new historical model
	 */
	public void setHistoricalModel(HistoricalModel historicalModel) {
	this.historicalModel = historicalModel;
	}

	/**
	 * Sets the insurance provider.
	 *
	 * @param insuranceProvider the new insurance provider
	 */
	public void setInsuranceProvider(InsuranceProvider insuranceProvider) {
	this.insuranceProvider = insuranceProvider;
	}

	/**
	 * Sets the pms patient info.
	 *
	 * @param patient the new pms patient info
	 */
	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	/**
	 * Sets the policy limit.
	 *
	 * @param policyLimits the new policy limit
	 */
	public void setPolicyLimit(PolicyLimit policyLimits) {
	this.policyLimit = policyLimits;
	}

	/**
	 * Sets the policy name.
	 *
	 * @param policyName the new policy name
	 */
	public void setPolicyName(String policyName) {
	this.policyName = policyName;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(Integer priority) {
	this.priority = priority;
	}

	@Transient
	public Boolean isPrimary() {
	if (this.priority != null && this.priority.intValue() == 1) {
		return true;
	}
	return false;
	}

	public void setPrimary(Boolean checked) {
	if (checked)
		this.priority = new Integer(1);
	else
		this.priority = null;
	}

	/**
	 * Sets the relationship.
	 *
	 * @param relationship the new relationship
	 */
	public void setRelationship(Enumeration relationship) {
	this.relationship = relationship;
	}

	/**
	 * Sets the subscriber id.
	 *
	 * @param subscriberId the new subscriber id
	 */
	public void setSubscriberId(String subscriberId) {
	this.subscriberId = subscriberId;
	}

	@OneToOne(fetch = FetchType.LAZY, targetEntity = DataResource.class)
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "FRONT_IMAGE")
	public DataResource getFrontImage() {
	return frontImage;
	}

	@OneToOne(fetch = FetchType.LAZY, targetEntity = DataResource.class)
	@Cascade(value = { CascadeType.ALL })
	@JoinColumn(name = "BACK_IMAGE")
	public DataResource getBackImage() {
	return backImage;
	}

	public void setFrontImage(DataResource frontImage) {
	this.frontImage = frontImage;
	}

	public void setBackImage(DataResource backImage) {
	this.backImage = backImage;
	}

	@Column(name = "IS_LARGE_GROUP_HEALTH_PLAN")
	public Boolean getIsLargeGroupHealthPlan() {
	return isLargeGroupHealthPlan;
	}

	public void setIsLargeGroupHealthPlan(Boolean isLargeGroupHealthPlan) {
	this.isLargeGroupHealthPlan = isLargeGroupHealthPlan;
	}

	@Column(name = "IS_EMPLOYER_GROUP_HEALTH_PLAN")
	public Boolean getIsEmployerGroupHealthPlan() {
	return isEmployerGroupHealthPlan;
	}

	public void setIsEmployerGroupHealthPlan(Boolean isEmployerGroupHealthPlan) {
	this.isEmployerGroupHealthPlan = isEmployerGroupHealthPlan;
	}

	public int compareTo(Policy otherPolicy) {
	if (otherPolicy.getPriority() == null && getPriority() == null) {
		return this.getId().compareTo(otherPolicy.getId());
	} else
		if (otherPolicy != null && otherPolicy.getPriority() != null && getPriority() != null)
			return this.getPriority().compareTo(otherPolicy.getPriority());
		else
			return 0;
	}

	@Transient
	public boolean isPolicyExprired(Date date) {
	return historicalModel.isExpired(date);
	}

	@Column(name = "POLICY_NUMBER")
	public String getPolicyNumber() {
	return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
	this.policyNumber = policyNumber;
	}

	@Column(name = "IS_COPAY_AMOUNT_AT_CHARGE_ENTRY")
	public Boolean getCoPayAmountAtChargeEntry() {
	return coPayAmountAtChargeEntry;
	}

	public void setCoPayAmountAtChargeEntry(Boolean coPayAmountAtChargeEntry) {
	this.coPayAmountAtChargeEntry = coPayAmountAtChargeEntry;
	}

	@Column(name = "IS_COPAY_AMOUNT_AT_SCHEDULE_ENTRY")
	public Boolean getCoPayAmountAtScheduleEntry() {
	return coPayAmountAtScheduleEntry;
	}

	public void setCoPayAmountAtScheduleEntry(Boolean coPayAmountAtScheduleEntry) {
	this.coPayAmountAtScheduleEntry = coPayAmountAtScheduleEntry;
	}

	@Column(name = "COPAY_DEFAULT_AMOUNT")
	public Double getCoPayDefaultAmount() {
	return coPayDefaultAmount;
	}

	public void setCoPayDefaultAmount(Double coPayDefaultAmount) {
	this.coPayDefaultAmount = coPayDefaultAmount;
	}

	@Column(name = "COPAY_ALT1_AMOUNT")
	public Double getCoPayAlt1Amount() {
	return coPayAlt1Amount;
	}

	public void setCoPayAlt1Amount(Double coPayAlt1Amount) {
	this.coPayAlt1Amount = coPayAlt1Amount;
	}

	@Column(name = "COPAY_ALT2_AMOUNT")
	public Double getCoPayAlt2Amount() {
	return coPayAlt2Amount;
	}

	public void setCoPayAlt2Amount(Double coPayAlt2Amount) {
	this.coPayAlt2Amount = coPayAlt2Amount;
	}

	@Column(name = "COPAY_ALT1_SERVICE")
	public String getCoPayAlt1Service() {
	return coPayAlt1Service;
	}

	public void setCoPayAlt1Service(String coPayAlt1Service) {
	this.coPayAlt1Service = coPayAlt1Service;
	}

	@Column(name = "COPAY_ALT2_SERVICE")
	public String getCoPayAlt2Service() {
	return coPayAlt2Service;
	}

	public void setCoPayAlt2Service(String coPayAlt2Service) {
	this.coPayAlt2Service = coPayAlt2Service;
	}

	@Column(name = "IS_AUTHORIZATION_REQUIRED")
	public Boolean isAuthorizationRequired() {
	return authorizationRequired;
	}

	public void setAuthorizationRequired(Boolean authorizationRequired) {
	this.authorizationRequired = authorizationRequired;
	}

	@Column(name = "EMPLOYER_GROUP_HEALTH_PLAN_SIZE")
	public String getEmployerGroupHealthPlanSize() {
	return employerGroupHealthPlanSize;
	}

	public void setEmployerGroupHealthPlanSize(String employerGroupHealthPlanSize) {
	this.employerGroupHealthPlanSize = employerGroupHealthPlanSize;
	}

	@Column(name = "LARGE_GROUP_HEALTH_PLAN_SIZE")
	public String getLargeGroupHealthPlanSize() {
	return largeGroupHealthPlanSize;
	}

	public void setLargeGroupHealthPlanSize(String largeGroupHealthPlanSize) {
	this.largeGroupHealthPlanSize = largeGroupHealthPlanSize;
	}

	public String getInsuranceType() {
	return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
	this.insuranceType = insuranceType;
	}
	
}