package com.nzion.domain;

import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import com.nzion.domain.base.IdGeneratingBaseEntity;
//import com.nzion.domain.emr.PatientVisit;
import com.nzion.domain.emr.soap.PatientLabOrder;

@Entity
@Table(name = "SCHEDULE")

@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "com.nzion.domain")
public class Schedule extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	private STATUS status;
	private Person person;
	private Patient patient;
	private Date startDate;
	private Date startTime;
	private Date endTime;
	private Date signedInTime;
	private Date signedOutTime;
	private Location location;
	private String comments;
	private Integer sequenceNum;
	private String patientContactNumber;
	private String chiefComplaint;
	private Integer length;
	//private SlotType slotType;
	private ScheduleType scheduleType;
	//private SortedSet<PatientVisit> patientVisits;
//	private PatientVisit lastPatientVisit;
//	private ScheduleWaitingList waitingList;
	private PatientLabOrder patientLabOrder;
	private Referral referral;
	private Provider internalReferral;
	
	private String patientClass;
	
	

	public Schedule() {
	}

	public Schedule(CalendarSlot slot) {
	startTime = slot.getStartTime();
	endTime = slot.getEndTime();
	sequenceNum = slot.getSequenceNum();
	}

	public Schedule(Schedule existing, CalendarSlot slot) {
	this(slot);
	patient = existing.getPatient();
	comments = existing.getComments();
	patientContactNumber = existing.getPatientContactNumber();
	chiefComplaint = existing.getChiefComplaint();
	}

	public Schedule(Person person, Patient patient, Date startDate, Date startTime) {
	this.person = person;
	this.patient = patient;
	this.startDate = startDate;
	this.startTime = startTime;
	this.status = STATUS.SCHEDULED;
	}

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
	return person;
	}

	public void setPerson(Person person) {
	this.person = person;
	}

	/*@OneToMany(targetEntity = PatientVisit.class, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SCHEDULE_ID")
	@Sort(type = SortType.NATURAL)
	@Cascade(value = CascadeType.ALL)
	public SortedSet<PatientVisit> getPatientVisits() {
	if (patientVisits == null) patientVisits = new TreeSet<PatientVisit>();
	return patientVisits;
	}

	public void addPatientVisit(PatientVisit visit) {
	getPatientVisits().add(visit);
	lastPatientVisit = visit;
	}

	public void setPatientVisits(SortedSet<PatientVisit> patientVisits) {
	this.patientVisits = patientVisits;
	}

	@OneToOne(optional = true)
	@JoinColumn(name = "LAST_PATIENT_VISIT_ID")
	public PatientVisit getLastPatientVisit() {
	return lastPatientVisit;
	}

	public void setLastPatientVisit(PatientVisit lastPatientVisit) {
	this.lastPatientVisit = lastPatientVisit;
	}*/

	@Column(name = "SIGNED_IN_TIME")
	public Date getSignedInTime() {
	return signedInTime;
	}

	public void setSignedInTime(Date signedInTime) {
	this.signedInTime = signedInTime;
	}

	@Column(name = "SIGNED_OUT_TIME")
	public Date getSignedOutTime() {
	return signedOutTime;
	}

	public void setSignedOutTime(Date signedOutTime) {
	this.signedOutTime = signedOutTime;
	}

	@Column(name = "LENGTH")
	public Integer getLength() {
	return length;
	}

	public void setLength(Integer length) {
	this.length = length;
	}

	/*@OneToOne(targetEntity = SlotType.class)
	@JoinColumn(name = "SOAP_NOTE_TYPE")
	public SlotType getVisitType() {
	return slotType;
	}

	public void setVisitType(SlotType slotType) {
	this.slotType = slotType;
	}*/

	public String getChiefComplaint() {
	return chiefComplaint;
	}

	public void setChiefComplaint(String chiefComplaint) {
	this.chiefComplaint = chiefComplaint;
	}

	@Column(name = "SEQUENCE_NUMBER")
	public Integer getSequenceNum() {
	return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
	this.sequenceNum = sequenceNum;
	}

	@Column(name = "PATIENT_CONTACT_NUMBER")
	public String getPatientContactNumber() {
	return patientContactNumber;
	}

	public void setPatientContactNumber(String patientContactNumber) {
	this.patientContactNumber = patientContactNumber;
	}

	@Column(name = "COMMENTS")
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	public STATUS getStatus() {
	return status;
	}

	public void setStatus(STATUS status) {
	this.status = status;
	}

	public void setStatus(String status) {
	STATUS.valueOf(STATUS.class, status);
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "SCHEDULE_TYPE")
	public ScheduleType getScheduleType() {
	return scheduleType;
	}

	public void setScheduleType(ScheduleType scheduleType) {
	this.scheduleType = scheduleType;
	}

	@ManyToOne(targetEntity = Patient.class)
	@JoinColumn(name = "PATIENT_ID")
	public Patient getPatient() {
	return patient;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
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
	
	@OneToOne
	@JoinColumn(name ="INTERNAL_REFERRAL")
	public Provider getInternalReferral() {
		return internalReferral;
	}

	public void setInternalReferral(Provider internalReferral) {
		this.internalReferral = internalReferral;
	}

	@Column(name = "END_TIME")
	public Date getEndTime() {
	return endTime;
	}

	public void setEndTime(Date endTime) {
	this.endTime = endTime;
	}

	@ManyToOne(targetEntity = Location.class)
	@JoinColumn(name = "LOCATION_ID", nullable = false)
	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}

	@OneToOne
	@JoinColumn(name = "PATIENT_LAB_ORDER_ID")
	public PatientLabOrder getPatientLabOrder() {
	return patientLabOrder;
	}

	public void setPatientLabOrder(PatientLabOrder patientLabOrder) {
	this.patientLabOrder = patientLabOrder;
	}

	public CalendarSlot buildCalendarSlot() {
	CalendarResourceAssoc association = new CalendarResourceAssoc();
	association.setPerson(person);
	association.setLocation(location);
	association.setFromDate(startDate);
	association.setThruDate(startDate);
	association.setStartTime(startTime);
	association.setEndTime(endTime);
	CalendarSlot slot = new CalendarSlot();
	slot.setAssociation(association);
	slot.setEndTime(getEndTime());
	slot.setStartTime(getStartTime());
	slot.setSequenceNum(getSequenceNum());
	return slot;
	}

	/*public void populateFromWaitingList(ScheduleWaitingList waitingList) {
	setPatient(waitingList.getPatient());
	setComments(waitingList.getComments());
	setPatientContactNumber(waitingList.getPreferedContactNumber());
	//this.waitingList = waitingList;
	}*/

	public void populateFromSchedule(Schedule schedule) {
	this.patient = schedule.patient;
	this.person = schedule.getPerson();
	this.length = schedule.length;
	//this.slotType = schedule.slotType;
	this.chiefComplaint = schedule.chiefComplaint;
	this.patientContactNumber = schedule.patientContactNumber;
	this.status = schedule.getStatus();
	this.comments = schedule.comments;
	}

	public Schedule createCopy() {
	Schedule newSchedule = new Schedule();
	newSchedule.setPatient(patient);
	newSchedule.setChiefComplaint(chiefComplaint);
	newSchedule.setPatientContactNumber(patientContactNumber);
	newSchedule.setComments(comments);
	newSchedule.startTime = startTime;
	newSchedule.endTime = endTime;
	newSchedule.sequenceNum = sequenceNum;
	newSchedule.status = STATUS.SCHEDULED;
	newSchedule.setPerson(person);
	newSchedule.setLocation(location);
	newSchedule.setLength(length);
	//newSchedule.setVisitType(slotType);
	return newSchedule;
	}

	/*@Transient
	public ScheduleWaitingList getWaitingList() {
	return waitingList;
	}*/

	@Transient
	public boolean isForSameSlot(Schedule schedule) {
	boolean cmp = true;
	if (this.person != null) cmp = cmp && person.equals(schedule.getPerson());
	CalendarSlot thisSlot = new CalendarSlot(getStartTime(), schedule.getEndTime(), schedule.getSequenceNum());
	CalendarSlot thatSlot = new CalendarSlot(schedule.getStartTime(), schedule.getEndTime(), schedule.getSequenceNum());
	cmp = cmp && (thisSlot.compareTo(thatSlot) == 0);
	return cmp;
	}

	public static enum ScheduleType {
		NORMAL, FORCEINSERTED, CREATEDFROM_SIGNIN, BLOCKED;
	}

	/*
	 * *
	 * @author Sandeep Prusty Jul 15, 2010 Statuses used for scheduling. The getAllowedModifications() gives the
	 * statuses to which a given status can be changed to.
	 */

	public static enum STATUS {

		SCHEDULED("SCHEDULED") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { SCHEDULED, CHECKEDIN, NOSHOW, CANCELLED };
			}
		},
		CHECKEDIN("CHECKEDIN") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { CHECKEDIN, READY_FOR_BILLING, CHECKEDOUT, SCHEDULED };
			}
		},
        READY_FOR_BILLING("READY FOR BILLING"){
            @Override
            public STATUS[] getAllowedModifications() {
                return new STATUS[] {CHECKEDOUT};
            }
        },
		ROOMED("ROOMED") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { ROOMED, READY_FOR_BILLING,CHECKEDOUT };
			}
		},
		EXAMINING("EXAMINING") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { EXAMINING,READY_FOR_BILLING, CHECKEDOUT };
			}
		},
		SOAPSIGNEDOUT("SIGNEDOUT") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { SOAPSIGNEDOUT,READY_FOR_BILLING,CHECKEDOUT };
			}
		},
		CHECKEDOUT("CHECKEDOUT") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { CHECKEDOUT };
			}
		},
		CANCELLED("CANCELLED") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { CANCELLED, SCHEDULED };
			}
		},
		NOSHOW("NOSHOW") {
			@Override
			public STATUS[] getAllowedModifications() {
			return new STATUS[] { NOSHOW, SCHEDULED };
			}
		};

		private String description;

		public String getDescription() {
		return description;
		}
		public void setDescription(String description) {
		this.description = description;
		}

		STATUS(String description){
		this.description = description;
		}
		public abstract STATUS[] getAllowedModifications();
	}

	@OneToOne
	public Referral getReferral() {
	return referral;
	}

	public void setReferral(Referral referral) {
	this.referral = referral;
	}

	public String getPatientClass() {
	return patientClass;
	}

	public void setPatientClass(String patientClass) {
	this.patientClass = patientClass;
	}
	
	public void assignInternalReferral(boolean isInternalReferral){
		if(isInternalReferral)
			setReferral(null);
		else
			setInternalReferral(null);
	}
}