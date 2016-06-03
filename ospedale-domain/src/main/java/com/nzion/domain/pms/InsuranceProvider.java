package com.nzion.domain.pms;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.ContactFields;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Historical;
import com.nzion.domain.HistoricalModel;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.billing.Contract;

@Entity
@Table(name = "INSURANCE_PROVIDER")
@AccountNumberField
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class InsuranceProvider extends IdGeneratingBaseEntity implements Historical {

	private static final long serialVersionUID = 1L;

	private String accountNumber; // Mapped
	private Integer priority;
	private HistoricalModel historicalModel;
	private String insuranceProviderName; // Mapped
	private Enumeration primaryBillingMethod; // MAPPED
	private Enumeration eligibilityMethod;
	private Enumeration hfcaPayerType;
	private String clearingId;
	private String eligibilityId;
	private String medigapNumber; // mapped
	private Integer paymentPercent; // mapped
	private Integer printTracerDays;
	private String naicNumber; // mapped
	private Enumeration adjustment;
	private Enumeration payment;
	private Enumeration feetable; // Mapped
	private Enumeration physicianId;
	private Enumeration diagnosisId;
	private Enumeration insTypeId; // mapped
	private Enumeration groupDesc;
	private Boolean acceptAssignment=true; // mapped
	private Boolean writeDown; // mapped
	private Boolean medigapCrossOver;
	private Boolean managedCareFee; // mapped
	private Boolean managedCarePcp;
	private Boolean taxChange;// mapped
	private Boolean billAllowedAmount; // mapped
	private String contactPerson;
	private String claimsNumber;
	
	private String benefitsNumber;
	
	private String authorizationNumber;
	
	private String providerRelationsNumber;

	private ContactFields contacts;
	
	public String getClaimsNumber() {
	return claimsNumber;
	}

	public void setClaimsNumber(String claimsNumber) {
	this.claimsNumber = claimsNumber;
	}

	public String getBenefitsNumber() {
	return benefitsNumber;
	}

	public void setBenefitsNumber(String benefitsNumber) {
	this.benefitsNumber = benefitsNumber;
	}

	public String getAuthorizationNumber() {
	return authorizationNumber;
	}

	public void setAuthorizationNumber(String authorizationNumber) {
	this.authorizationNumber = authorizationNumber;
	}

	public String getProviderRelationsNumber() {
	return providerRelationsNumber;
	}

	public void setProviderRelationsNumber(String providerRelationsNumber) {
	this.providerRelationsNumber = providerRelationsNumber;
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

	@Column(name = "INSURANCE_CODE")
	public String getAccountNumber() {
	return accountNumber;
	}

	@Column(name = "PRIORITY_SEQ")
	public Integer getPriority() {
	return priority;
	}

	@Embedded
	public HistoricalModel getHistoricalModel() {
	return historicalModel;
	}

	public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
	}

	public void setHistoricalModel(HistoricalModel historicalModel) {
	this.historicalModel = historicalModel;
	}

	public void setPriority(Integer priority) {
	this.priority = priority;
	}

	@Column(name = "INSURANCE_PROVIDER_NAME")
	public String getInsuranceProviderName() {
	return insuranceProviderName;
	}

	public void setInsuranceProviderName(String insuranceProviderName) {
	this.insuranceProviderName = insuranceProviderName;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "PRIMARY_BILLING_METHOD_CODE")
	public Enumeration getPrimaryBillingMethod() {
	return primaryBillingMethod;
	}

	public void setPrimaryBillingMethod(Enumeration primaryBillingMethod) {
	this.primaryBillingMethod = primaryBillingMethod;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "ELIGIBILITY_METHOD_CODE")
	public Enumeration getEligibilityMethod() {
	return eligibilityMethod;
	}

	public void setEligibilityMethod(Enumeration eligibilityMethod) {
	this.eligibilityMethod = eligibilityMethod;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "HFCA_PAYER_TYPE_CODE")
	public Enumeration getHfcaPayerType() {
	return hfcaPayerType;
	}

	public void setHfcaPayerType(Enumeration hfcaPayerType) {
	this.hfcaPayerType = hfcaPayerType;
	}

	@Column(name = "CLEARING_ID")
	public String getClearingId() {
	return clearingId;
	}

	public void setClearingId(String clearingId) {
	this.clearingId = clearingId;
	}

	@Column(name = "ELIGIBILITY_ID")
	public String getEligibilityId() {
	return eligibilityId;
	}

	public void setEligibilityId(String eligibilityId) {
	this.eligibilityId = eligibilityId;
	}

	@Column(name = "MEDIGAP_NUMBER")
	public String getMedigapNumber() {
	return medigapNumber;
	}

	public void setMedigapNumber(String medigapNumber) {
	this.medigapNumber = medigapNumber;
	}

	@Column(name = "PAYMENT_PERCENT")
	public Integer getPaymentPercent() {
	return paymentPercent;
	}

	public void setPaymentPercent(Integer paymentPercent) {
	this.paymentPercent = paymentPercent;
	}

	@Column(name = "PRINT_TRACER_DAYS")
	public Integer getPrintTracerDays() {
	return printTracerDays;
	}

	public void setPrintTracerDays(Integer printTracerDays) {
	this.printTracerDays = printTracerDays;
	}

	@Column(name = "NAIC_NUMBER")
	public String getNaicNumber() {
	return naicNumber;
	}

	public void setNaicNumber(String naicNumber) {
	this.naicNumber = naicNumber;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "ADJUSTMENT_ID")
	public Enumeration getAdjustment() {
	return adjustment;
	}

	public void setAdjustment(Enumeration adjustment) {
	this.adjustment = adjustment;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "PAYMENT_ID")
	public Enumeration getPayment() {
	return payment;
	}

	public void setPayment(Enumeration payment) {
	this.payment = payment;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "FEETABLE_ID")
	public Enumeration getFeetable() {
	return feetable;
	}

	public void setFeetable(Enumeration feetable) {
	this.feetable = feetable;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "PHYSICIAN_ID")
	public Enumeration getPhysicianId() {
	return physicianId;
	}

	public void setPhysicianId(Enumeration physicianId) {
	this.physicianId = physicianId;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "DIAGNOSIS_ID")
	public Enumeration getDiagnosisId() {
	return diagnosisId;
	}

	public void setDiagnosisId(Enumeration daignosisId) {
	this.diagnosisId = daignosisId;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "INSTYPE_ID")
	public Enumeration getInsTypeId() {
	return insTypeId;
	}

	public void setInsTypeId(Enumeration insTypeId) {
	this.insTypeId = insTypeId;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "GROUP_DESC")
	public Enumeration getGroupDesc() {
	return groupDesc;
	}

	public void setGroupDesc(Enumeration groupDesc) {
	this.groupDesc = groupDesc;
	}

	@Column(name = "ACCEPT_ASSIGNMENT")
	public Boolean getAcceptAssignment() {
	return acceptAssignment;
	}

	public void setAcceptAssignment(Boolean acceptAssignment) {
	this.acceptAssignment = acceptAssignment;
	}

	@Column(name = "WRITE_DOWN")
	public Boolean getWriteDown() {
	return writeDown;
	}

	public void setWriteDown(Boolean writeDown) {
	this.writeDown = writeDown;
	}

	@Column(name = "MEDI_GAP_CROSSOVER")
	public Boolean getMedigapCrossOver() {
	return medigapCrossOver;
	}

	public void setMedigapCrossOver(Boolean medigapCrossOver) {
	this.medigapCrossOver = medigapCrossOver;
	}

	@Column(name = "MANAGED_CARE_FEE")
	public Boolean getManagedCareFee() {
	return managedCareFee;
	}

	public void setManagedCareFee(Boolean managedCareFee) {
	this.managedCareFee = managedCareFee;
	}

	@Column(name = "TAX_CHANGE")
	public Boolean getTaxChange() {
	return taxChange;
	}

	public void setTaxChange(Boolean taxChange) {
	this.taxChange = taxChange;
	}

	@Column(name = "BILL_ALLOWED_AMOUNT")
	public Boolean getBillAllowedAmount() {
	return billAllowedAmount;
	}

	public void setBillAllowedAmount(Boolean billAllowedAmount) {
	this.billAllowedAmount = billAllowedAmount;
	}

	@Column(name = "MANAGED_CARE_PCP")
	public Boolean getManagedCarePcp() {
	return managedCarePcp;
	}

	public void setManagedCarePcp(Boolean managedCarePcp) {
	this.managedCarePcp = managedCarePcp;
	}

	@Column(name = "CONTACT_PERSON")
	public String getContactPerson() {
	return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
	this.contactPerson = contactPerson;
	}

}
