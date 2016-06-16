package com.nzion.domain.util;

import com.nzion.domain.CalendarSlot;
import com.nzion.domain.Person;
import com.nzion.domain.Schedule;
import com.nzion.domain.emr.lab.LabOrderRequest;

import java.util.Date;

import static com.nzion.util.UtilDateTime.IGNORE_GRANULARITY;

public class SlotAvailability {

	private Date on;

	private CalendarSlot slot;

	private boolean available;

	private String nonAvailabilityReason;

	public SlotAvailability() {
	}

	public SlotAvailability(Date on, CalendarSlot slot) {
	this.on = on;
	this.slot = slot;
	available = true;
	}

	public SlotAvailability(Date on, CalendarSlot slot, String message) {
	this.on = on;
	this.slot = slot;
	this.nonAvailabilityReason = message;
	}

	/*public SlotAvailability(Schedule schedule) {
	this.slot = schedule.buildCalendarSlot();
	setOn(schedule.getStartDate());
	}*/
	public SlotAvailability(LabOrderRequest schedule) {
		this.slot = schedule.buildCalendarSlot();
		if (schedule.getPhlebotomist() != null){
			setOn(schedule.getPhlebotomistStartDate());
		} else {
			setOn(schedule.getStartDate());
		}

	}

	public Date getOn() {
	return on;
	}

	public void setOn(Date on) {
	this.on = on;
	}

	public CalendarSlot getSlot() {
	return slot;
	}

	public boolean isAvailable() {
	return available;
	}

	public String getNonAvailabilityReason() {
	return nonAvailabilityReason;
	}

	public Person getPerson() {
	return slot == null ? null : slot.getAssociation().getPerson();
	}

	@Override
	public boolean equals(Object object) {
	if (object instanceof SlotAvailability) {
		SlotAvailability tmp = (SlotAvailability) object;
		if (tmp.getOn().equals(tmp.getOn()) && tmp.getSlot().equals(this.getSlot()))
			return true;
		else
			return false;
	} else {
		return false;
	}
	}
	
	public int compareOn(SlotAvailability that){
	return (int) ((this.on.getTime() / IGNORE_GRANULARITY) - (that.on.getTime() / IGNORE_GRANULARITY));
	}

	@Override
	public int hashCode() {
	return on.hashCode() + slot.hashCode();
	}
}