package com.nzion.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.base.Weekdays;
import com.nzion.enums.RecurrenceMode;
import com.nzion.util.UtilDateTime;

/**
 * @author Sandeep Prusty
 * Mar 29, 2011
 */

@Entity
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class CalendarRecurrence extends IdGeneratingBaseEntity {

	private int day = 1;

	private Weekdays weekdays = new Weekdays();

	private int month = 0;

	private int qualifier = 1;

	private Date startDate;
	
	private Date endDate;
	
	private Date startTime;
	
	private Date endTime;
	
	private RecurrenceMode mode;
	
	public CalendarRecurrence() {}
	
	public CalendarRecurrence(RecurrenceMode mode) {
	this.mode = mode;
	}

	@Column
	@Enumerated(EnumType.STRING)
	public RecurrenceMode getMode() {
	return mode;
	}

	public void setMode(RecurrenceMode mode) {
	this.mode = mode;
	}

	public int getQualifier() {
	return qualifier;
	}

	public void setQualifier(int qualifier) {
	this.qualifier = qualifier;
	}

	public int getDay() {
	return day;
	}

	public void setDay(int day) {
	this.day = day;
	}

	@Embedded
	public Weekdays getWeekdays() {
	return weekdays;
	}

	public void setWeekdays(Weekdays weekdays) {
	this.weekdays = weekdays;
	}

	public int getMonth() {
	return month;
	}

	public void setMonth(int month) {
	this.month = month;
	}
	
	public void setMonth(String month) {
	this.month = Integer.parseInt(month);
	}

	public Date getStartDate() {
	return startDate;
	}

	public void setStartDate(Date startDate) {
	this.startDate = startDate == null ? null : UtilDateTime.getDayStart(startDate);
	}

	public Date getEndDate() {
	return endDate;
	}

	public void setEndDate(Date endDate) {
	this.endDate = endDate == null ? null : UtilDateTime.getDayEnd(endDate);
	}

	public Date getStartTime() {
	return startTime;
	}

	public void setStartTime(Date startTime) {
	this.startTime = startTime == null ? null : UtilDateTime.timeOnly(startTime);
	}

	public Date getEndTime() {
	return endTime;
	}

	public void setEndTime(Date endTime) {
	this.endTime = endTime == null?null:UtilDateTime.timeOnly(endTime);
	}
	
	public boolean check(Date date, Date startTime, Date endTime){
	return (!date.before(this.startDate) && (endDate == null || !date.after(endDate))) && 
														   mode.check(this, date) && 
			(startTime.before(this.endTime) && endTime.after(this.startTime));
	}

	private static final long serialVersionUID = 1L;
}