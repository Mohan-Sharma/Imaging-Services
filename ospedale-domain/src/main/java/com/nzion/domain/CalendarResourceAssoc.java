package com.nzion.domain;

import java.util.*;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilDateTime;

@Entity
@Table(name = "CALENDAR_RESOURCE_ASSOC")
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class CalendarResourceAssoc extends IdGeneratingBaseEntity {
	private Person person;
	private Location location;
	private Date fromDate;
	private Date thruDate;
	private Date startTime;
	private Date endTime;
	private Integer calendarInterval;
	private Integer visitsPerInterval=1;
	private List<CalendarSlot> calendarSlots;
	private Set<CalendarIndividualSlot> calendarIndividualSlots;

	private Week week;

	public CalendarResourceAssoc(){ }
	
	public CalendarResourceAssoc(CalendarResourceAssoc that){
	this.startTime = that.startTime;
	this.endTime = that.endTime;
	this.calendarInterval = that.calendarInterval;
	this.visitsPerInterval = that.visitsPerInterval;
	}

	public CalendarResourceAssoc(Person person, Location location) {
	this.person = person;
	this.location = location;
	init();
	}
	
	public void init(){
	this.startTime = UtilDateTime.createTime(8, 0, 0);
	this.endTime = UtilDateTime.createTime(18, 0, 0);
	}
	
	public static CalendarResourceAssoc create(){
	CalendarResourceAssoc assoc = new CalendarResourceAssoc();
	assoc.init();
	return assoc;
	}
	
	@ManyToOne(targetEntity=Person.class)
	@JoinColumn(name="PERSON_ID")
	public Person getPerson() {
	return person;
	}

	public void setPerson(Person person) {
	this.person = person;
	}

	public Integer getCalendarInterval() {
	return calendarInterval;
	}

	public void setCalendarInterval(Integer calendarInterval) {
	this.calendarInterval = calendarInterval;
	}

	public Integer getVisitsPerInterval() {
	return visitsPerInterval;
	}

	public void setVisitsPerInterval(Integer visitsPerInterval) {
	this.visitsPerInterval = visitsPerInterval;
	}

	public void setStartTime(Date startTime) {
	this.startTime = startTime == null ? null : UtilDateTime.timeOnly(startTime);
	}

	public void setEndTime(Date endTime) {
	this.endTime = endTime == null?null:UtilDateTime.timeOnly(endTime);
	}

	@ManyToOne(targetEntity = Location.class)
	@JoinColumn(name = "LOCATION_ID")
	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}


	public Date getStartTime() {
	return startTime;
	}

	public Date getEndTime() {
	return endTime;
	}

	@Transient
	public List<CalendarSlot> getSlots() {
	if(calendarSlots == null)
		calendarSlots = buildBlankSLots();
	return calendarSlots;
	}

	@Column(name = "FROM_DATE")
	public Date getFromDate() {
	return fromDate;
	}

	public void setFromDate(Date fromDate) {
	this.fromDate = fromDate == null ? null : UtilDateTime.getDayStart(fromDate);
	}

	@Column(name = "TO_DATE")
	public Date getThruDate() {
	return thruDate;
	}

	public void setThruDate(Date thruDate) {
	this.thruDate = thruDate == null ? null : UtilDateTime.getDayEnd(thruDate);
	}

	public boolean isSatisfiedBy(Date givenDate) {
	if(fromDate != null && givenDate.before(fromDate))
		return false;
	if(thruDate != null && givenDate.after(thruDate))
		return false;
	return true;
	}

	public boolean isColliding(CalendarResourceAssoc another) {
	return (!fromDate.after(another.thruDate) && !thruDate.before(another.fromDate));
	}
	
	public boolean isAfter(CalendarResourceAssoc that){
	if(that.thruDate == null) 
		return false;
	return this.fromDate.after(that.thruDate); 
	}

	public List<CalendarSlot> buildBlankSLots() {
	long intervalInMillis = getCalendarInterval() * 60 * 1000;
	long numberOfIntervals = (getEndTime().getTime() - getStartTime().getTime())/intervalInMillis;
	List<CalendarSlot> slots = new ArrayList<CalendarSlot>((int)numberOfIntervals);
	for(long l = getStartTime().getTime() ; l < getEndTime().getTime() ; l = l + intervalInMillis){
		CalendarSlot referenceSlot = new CalendarSlot(new Date(l), new Date(l + intervalInMillis));
		for(int i = 0,sequenceNum = 0 ; i < 1 ; ++i){
			CalendarSlot slot = new CalendarSlot(referenceSlot, 0, this);
			slots.add(slot);
		}
	}
	return slots;
	}

	public CalendarResourceAssoc createDefalutAssociation(Person person, Location location, Date from, Date thru){
	CalendarResourceAssoc association = new CalendarResourceAssoc(this);
	association.setPerson(person);
	association.setLocation(location);
	association.setFromDate(from);
	association.setThruDate((this.thruDate == null || this.thruDate.getTime() > thru.getTime()) ? thru : this.thruDate);
	return association;
	}
	
	public static final Comparator<CalendarResourceAssoc> START_DATE_COMPARATOR = new Comparator<CalendarResourceAssoc>() {
		@Override
		public int compare(CalendarResourceAssoc o1, CalendarResourceAssoc o2) {
		return o1.fromDate.compareTo(o2.fromDate);
		}
	};

	private static final long serialVersionUID = 1L;

	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Week getWeek() {
		if(week == null)
			week = new Week();
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "association")
	public Set<CalendarIndividualSlot> getCalendarIndividualSlots() {
		return calendarIndividualSlots;
	}

	public void setCalendarIndividualSlots(Set<CalendarIndividualSlot> calendarIndividualSlots) {
		this.calendarIndividualSlots = calendarIndividualSlots;
	}

	public Set<CalendarIndividualSlot> buildBlankCalendarIndividualSlots() {
		long intervalInMillis = getCalendarInterval() * 60 * 1000;
		long numberOfIntervals = (getEndTime().getTime() - getStartTime().getTime())/intervalInMillis;
		Set<CalendarIndividualSlot> slots = new HashSet<CalendarIndividualSlot>((int)numberOfIntervals);
		for(long l = getStartTime().getTime() ; l < getEndTime().getTime() ; l = l + intervalInMillis){
			CalendarIndividualSlot referenceSlot = new CalendarIndividualSlot(new Date(l), new Date(l + intervalInMillis));
			for(int i = 0,sequenceNum = 0 ; i < getVisitsPerInterval() ; ++i, ++sequenceNum){
				CalendarIndividualSlot slot = new CalendarIndividualSlot(referenceSlot, sequenceNum, this);
				slots.add(slot);
			}
		}
		return slots;
	}
}