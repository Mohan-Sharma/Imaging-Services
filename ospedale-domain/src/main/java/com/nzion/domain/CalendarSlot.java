package com.nzion.domain;

import static com.nzion.util.UtilDateTime.IGNORE_GRANULARITY;

import java.util.Date;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.util.UtilDateTime;

public class CalendarSlot extends IdGeneratingBaseEntity implements Comparable<CalendarSlot> {

	private static final long serialVersionUID = 1L;
	private Date startTime;
	private Date endTime;
	private Integer sequenceNum;

	private CalendarResourceAssoc association;

	public CalendarSlot() {
	}

	public CalendarSlot(Date startTime, Date endTime) {
	this.startTime = startTime;
	this.endTime = endTime;
	}

	public CalendarSlot(CalendarSlot slot, Integer sequenceNum, CalendarResourceAssoc association) {
	setStartTime(slot.getStartTime());
	setEndTime(slot.getEndTime());
	this.sequenceNum = sequenceNum;
	this.association = association;
	}

	public CalendarSlot(Date startTime, Date endTime, Integer sequenceNum) {
	this(startTime, endTime);
	this.sequenceNum = sequenceNum;
	}

	public CalendarSlot(Integer sequenceNum) {
	this.sequenceNum = sequenceNum;
	}

	/*public CalendarSlot(Schedule schedule, CalendarResourceAssoc association) {
	this(schedule.getStartTime(), schedule.getEndTime(), schedule.getSequenceNum());
	this.association = association;
	}*/

	public CalendarResourceAssoc getAssociation() {
	return association;
	}

	public void setAssociation(CalendarResourceAssoc association) {
	this.association = association;
	}

	public Date getStartTime() {
	return startTime;
	}

	public void setStartTime(Date startTime) {
	this.startTime = startTime == null ? null : new Date(startTime.getTime());
	}

	public Date getEndTime() {
	return endTime;
	}

	public void setEndTime(Date endTime) {
	this.endTime = endTime == null ? null : new Date(endTime.getTime());
	}

	public Integer getSequenceNum() {
	return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
	this.sequenceNum = sequenceNum;
	}

	public int compareTo(CalendarSlot o) {
	int cmp = compareStartTime(o);
	if (cmp != 0) return cmp;
	return sequenceNum.compareTo(o.sequenceNum);
	}

	public int compareStartTime(CalendarSlot o) {
	return compareStartTime(o.startTime);
	}

	public int compareStartTime(Date startTime) {
	return (int) ((this.startTime.getTime() / IGNORE_GRANULARITY) - (startTime.getTime() / IGNORE_GRANULARITY));
	}

	public Integer calculateLength() {
	if (getEndTime() == null || getStartTime() == null) return null;
	return (int) (getEndTime().getTime() - getStartTime().getTime()) / (1000 * 60);
	}

	public boolean collides(Date startTime, Date endTime) {
	return startTime.before(this.endTime) && endTime.after(this.startTime);
	}

	

	@Override
	public boolean equals(Object object) {
	if (object instanceof CalendarSlot) {
		CalendarSlot slot = (CalendarSlot) object;
		if (slot.getStartTime().equals(this.getStartTime()) && slot.getEndTime().equals(this.getEndTime())
				&& slot.getSequenceNum().equals(this.getSequenceNum()))
			return true;
		else
			return false;
	} else
		return false;
	}
	
	@Override
	public int hashCode() {
	return this.startTime.hashCode()+this.endTime.hashCode()+this.sequenceNum.hashCode();
	}
}