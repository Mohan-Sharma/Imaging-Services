package com.nzion.domain.emr.lab;

import java.util.*;

import javax.persistence.*;

import com.nzion.domain.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.util.UtilValidator;

@Entity
@Table
public class LabOrderRequest extends IdGeneratingBaseEntity{

	private static final long serialVersionUID = 1L;

	private Set<PatientLabOrder>  patientLabOrders;


	private Referral referral;

	private ORDERSTATUS orderStatus;

	private Patient patient;
	
	private String tariffCategeory;

	private Provider provider;

    private String clinicalHistory;

    private String remarks;

    private Boolean self;
    
    private Set<Laboratories> laboratories;

	private Date startDate;

	private Date startTime;

	private Date endTime;

	private Location location;

	private boolean mobileOrPatinetPortal;
	private boolean fromMobileApp = Boolean.FALSE;
	private String paymentId;
	private Person phlebotomist;

	private Date phlebotomistStartDate;

	private Date phlebotomistStartTime;

	private Date phlebotomistEndTime;

	private boolean homeService;


	@ManyToMany(targetEntity=Laboratories.class,fetch=FetchType.EAGER)
    @JoinTable(name = "lab_order_request_laboratories",
    joinColumns = @JoinColumn(name = "LABORDERREQUEST_ID", unique=false),
    inverseJoinColumns = @JoinColumn(name = "LABORATORY_ID", unique=false))
	@Cascade(CascadeType.ALL)
    public Set<Laboratories> getLaboratories() {
		return laboratories;
	}

	public void setLaboratories(Set<Laboratories> laboratories) {
		this.laboratories = laboratories;
	}


    public String getTariffCategeory() {
		return tariffCategeory;
	}

	public void setTariffCategeory(String tariffCategeory) {
		this.tariffCategeory = tariffCategeory;
	}

	public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

	@OneToOne
	public Referral getReferral() {
	return referral;
	}

	public void setReferral(Referral referral) {
	this.referral = referral;
	}

	public static enum ORDERSTATUS{
		BILLING_REQUIRED("New"),INVOICED("Invoiced"),BILLING_DONE("Billed"), INPATIENT_BILLING("Inpatient Billing"), INPROCESS("In Process"), COMPLETED("Completed"), CANCELLED("Cancelled");

		private String description;

		public String getDescription() {
		return description;
		}

		public void setDescription(String description) {
		this.description = description;
		}

		ORDERSTATUS(String description){
		this.description = description;
		}

	}

	@OneToMany(targetEntity=PatientLabOrder.class,mappedBy="labOrderRequest",fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	public Set<PatientLabOrder> getPatientLabOrders() {
	return patientLabOrders;
	}

	public void setPatientLabOrders(Set<PatientLabOrder> patientLabOrders) {
	this.patientLabOrders = patientLabOrders;
	}


    @Enumerated(EnumType.STRING)
	public ORDERSTATUS getOrderStatus() {
	return orderStatus;
	}

	public void setOrderStatus(ORDERSTATUS orderStatus) {
	this.orderStatus = orderStatus;
	}

	public void addPatientLabOrder(PatientLabOrder patientLabOrder){
		if(UtilValidator.isEmpty(this.patientLabOrders))
		this.patientLabOrders = new HashSet<PatientLabOrder>();
		this.patientLabOrders.add(patientLabOrder);
	}
	
	
	public void addLaboratories(Laboratories laboratories){
		if(UtilValidator.isEmpty(this.laboratories))
			this.laboratories = new HashSet<Laboratories>();
		
		if(!checkExistingLaboratory(laboratories))
				this.laboratories.add(laboratories);
	}
	
	
	public boolean checkExistingLaboratory(Laboratories laboratories){
		for(Laboratories each : this.laboratories)
			if(each.getLaboratoryCode().equals(laboratories.getLaboratoryCode()))
				return true;
		return false;
	}

	@OneToOne
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	@OneToOne
	public Provider getProvider() {
	return provider;
	}

	public void setProvider(Provider provider) {
	this.provider = provider;
	}

    public String getClinicalHistory() {
        return clinicalHistory;
    }

    public void setClinicalHistory(String clinicalHistory) {
        this.clinicalHistory = clinicalHistory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@OneToOne
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public CalendarSlot buildCalendarSlot() {
		CalendarResourceAssoc association = new CalendarResourceAssoc();
		association.setPerson(null);
		association.setLocation(location);
		association.setFromDate(startDate);
		association.setThruDate(startDate);
		association.setStartTime(startTime);
		association.setEndTime(endTime);
		CalendarSlot slot = new CalendarSlot();
		slot.setAssociation(association);
		if (phlebotomist != null){
			slot.setEndTime(getPhlebotomistEndTime());
			slot.setStartTime(getPhlebotomistStartTime());
			slot.setSequenceNum(0);
		} else {
			slot.setEndTime(getEndTime());
			slot.setStartTime(getStartTime());
			slot.setSequenceNum(0);
		}

		return slot;
	}

	public boolean isMobileOrPatinetPortal() {
		return mobileOrPatinetPortal;
	}

	public void setMobileOrPatinetPortal(boolean mobileOrPatinetPortal) {
		this.mobileOrPatinetPortal = mobileOrPatinetPortal;
	}

	public boolean isFromMobileApp() {
		return fromMobileApp;
	}

	public void setFromMobileApp(boolean fromMobileApp) {
		this.fromMobileApp = fromMobileApp;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	@OneToOne
	public Person getPhlebotomist() {
		return phlebotomist;
	}

	public void setPhlebotomist(Person phlebotomist) {
		this.phlebotomist = phlebotomist;
	}

	@Temporal(TemporalType.DATE)
	public Date getPhlebotomistStartDate() {
		return phlebotomistStartDate;
	}

	public void setPhlebotomistStartDate(Date phlebotomistStartDate) {
		this.phlebotomistStartDate = phlebotomistStartDate;
	}

	public Date getPhlebotomistStartTime() {
		return phlebotomistStartTime;
	}

	public void setPhlebotomistStartTime(Date phlebotomistStartTime) {
		this.phlebotomistStartTime = phlebotomistStartTime;
	}

	public Date getPhlebotomistEndTime() {
		return phlebotomistEndTime;
	}

	public void setPhlebotomistEndTime(Date phlebotomistEndTime) {
		this.phlebotomistEndTime = phlebotomistEndTime;
	}

	public boolean isHomeService() {
		return homeService;
	}


	public void setHomeService(boolean homeService) {
		this.homeService = homeService;
	}
}
