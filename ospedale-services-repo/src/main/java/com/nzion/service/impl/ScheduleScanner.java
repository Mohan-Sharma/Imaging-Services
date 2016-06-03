package com.nzion.service.impl;

import com.nzion.domain.CalendarResourceAssoc;
import com.nzion.domain.CalendarSlot;
import com.nzion.domain.Schedule;
import com.nzion.domain.ScheduleBreak;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

import java.util.*;

public class ScheduleScanner {

	private final Set<SlotAvailability> allSlots = new TreeSet<SlotAvailability>(SLOTS_COMPARATOR);

	private final Set<SlotAvailability> availableSlots = new TreeSet<SlotAvailability>(SLOTS_COMPARATOR);

	private final Date fromDate, thruDate, startTime, endTime;
	
	private final Weekdays choosenDays;
	
	private final List<SlotAvailability> conflicts = new ArrayList<SlotAvailability>();
	
	//private Collection<Schedule> bookedSchedules;
	private Collection<LabOrderRequest> bookedSchedules;
	
	private Collection<ScheduleBreak> breaks;
	
	public ScheduleScanner(Date fromDate, Date thruDate, Date startTime, Date endTime, Weekdays choosenDays) {
	this.fromDate = fromDate == null ? null : UtilDateTime.getDayStart(fromDate);
	this.thruDate = thruDate == null ? null : UtilDateTime.getDayEnd(thruDate);
	this.startTime = startTime==null? UtilDateTime.timeOnly(new Date(70, 00, 01, 0, 0, 0)):startTime;
	this.endTime = endTime==null? UtilDateTime.timeOnly(new Date(70, 00, 01, 23, 59, 59)):endTime;
	this.choosenDays = choosenDays == null ? Weekdays.allSelectedWeekdays() : choosenDays;
	}
	
	public Set<SlotAvailability> getAllSlots() {
	return allSlots;
	}

	public Collection<LabOrderRequest> getBookedSchedules() {
	return bookedSchedules;
	}

	public void setBookedSchedules(Collection<LabOrderRequest> bookedSchedules) {
	this.bookedSchedules = bookedSchedules;
	}

	public void extractAllSlots(Collection<CalendarResourceAssoc> assocs, Collection<ScheduleBreak> breaks) {
	this.breaks = breaks;
	for (CalendarResourceAssoc assoc : assocs) {
		extractAllSlots(assoc);
	}
	}

	public void extractAllSlots(Collection<CalendarResourceAssoc> assocs) {
		//List<ScheduleBreak> scheduleBreaks = new ArrayList<ScheduleBreak>();
		breaks = Collections.EMPTY_LIST;
		for (CalendarResourceAssoc assoc : assocs) {
			extractAllSlots(assoc);
		}
	}

	protected void extractAllSlots(CalendarResourceAssoc assoc) {
	Date currentDate = fromDate;
	while (!currentDate.after(thruDate)) {
		if(choosenDays.satisfiedBy(currentDate) && assoc.isSatisfiedBy(currentDate))
			extractAllSlots(assoc, currentDate);
		currentDate = UtilDateTime.addDaysToDate(currentDate, 1);
	}
	}

	protected void extractAllSlots(CalendarResourceAssoc assoc, Date selectedDate) {
	for(CalendarSlot calendarSlot : assoc.getSlots()){
		if(calendarSlot.collides(startTime,endTime))
			allSlots.add(new SlotAvailability(selectedDate, calendarSlot));
	}
	}
	
	public void subtract(){
	availableSlots.addAll(allSlots);
	if(UtilValidator.isNotEmpty(bookedSchedules)){
		for(LabOrderRequest schedule : bookedSchedules){
			availableSlots.remove(new SlotAvailability(schedule));
		}
	}
	// Respecting breaks..
	/*if(UtilValidator.isEmpty(this.breaks))
		return;*/
	List<SlotAvailability> nonAvailabilities = new ArrayList<SlotAvailability>();
	for(SlotAvailability availability : availableSlots)
		if(isBrokenSlot(availability))
			nonAvailabilities.add(availability);
		
	availableSlots.removeAll(nonAvailabilities);
	}
	
	private boolean isBrokenSlot(SlotAvailability availability){
	for(ScheduleBreak brek : this.breaks)
		if(brek.check(availability.getOn(), availability.getSlot().getStartTime(), availability.getSlot().getEndTime()))
			return true;	
	return false;
	}
	
	public Set<SlotAvailability> getAvailableSlots() {
	return availableSlots;
	}
	
	public Set<SlotAvailability> getUnifiedAvailableSlots() {
	Set<SlotAvailability> unified = new TreeSet<SlotAvailability>(IGNORE_SEQUENCE_NUMBER_COMPARATOR);
	unified.addAll(availableSlots);
	return unified;
	}
	
	public List<SlotAvailability> getConflicts() {
	Set<SlotAvailability> allSlotsComparedByDate = new TreeSet<SlotAvailability>(DATE_COMPARATOR);
	allSlotsComparedByDate.addAll(allSlots);
	Set<SlotAvailability> availableSlotsComparedByDate = new TreeSet<SlotAvailability>(DATE_COMPARATOR);
	availableSlotsComparedByDate.addAll(availableSlots);
	Date currentDate = UtilDateTime.addDaysToDate(fromDate, -1);
	SlotAvailability dummyObject = new SlotAvailability();
	do{
		currentDate = UtilDateTime.addDaysToDate(currentDate, 1);
		if(currentDate.after(thruDate)) break;
		dummyObject.setOn(currentDate); 
		if(!choosenDays.satisfiedBy(currentDate))
			continue;
		if(!allSlotsComparedByDate.contains(dummyObject)){
			conflicts.add(new SlotAvailability(currentDate, null, "Doctor not available"));
			continue;
		}
		if(!availableSlotsComparedByDate.contains(dummyObject))
			conflicts.add(new SlotAvailability(currentDate, null, "Slot not available"));
	}while(currentDate.before(thruDate));
	
	return conflicts;
	}

	private static final Comparator<SlotAvailability> DATE_COMPARATOR = new Comparator<SlotAvailability>() {
		public int compare(SlotAvailability o1, SlotAvailability o2) {
			return o1.getOn().compareTo(o2.getOn());
		}
	}; 
	
	private static final Comparator<SlotAvailability> SLOTS_COMPARATOR = new Comparator<SlotAvailability>() {
		public int compare(SlotAvailability o1, SlotAvailability o2) {
			int dateCmp = o1.compareOn(o2);
			if(dateCmp != 0)
				return dateCmp;
			int slotCmp = o1.getSlot().compareTo(o2.getSlot());
			if(slotCmp != 0)
				return slotCmp;
			if ((o1.getPerson() != null) && (o2.getPerson() != null)){
				return o1.getPerson().getId().compareTo(o2.getPerson().getId());
			} else {
				return o1.getSlot().getAssociation().getLocation().compareTo(o2.getSlot().getAssociation().getLocation());
			}
		}
	}; 
	
	private static final Comparator<SlotAvailability> IGNORE_SEQUENCE_NUMBER_COMPARATOR = new Comparator<SlotAvailability>() {
		public int compare(SlotAvailability o1, SlotAvailability o2) {
			int dateCmp = o1.getOn().compareTo(o2.getOn());
			if(dateCmp != 0)
				return dateCmp;
			int slotCmp = o1.getSlot().compareStartTime(o2.getSlot());
			if(slotCmp != 0)
				return slotCmp;
			//return o1.getPerson().getId().compareTo(o2.getPerson().getId());
			if ((o1.getPerson() != null) && (o2.getPerson() != null)){
				return o1.getPerson().getId().compareTo(o2.getPerson().getId());
			} else {
				return o1.getSlot().getAssociation().getLocation().compareTo(o2.getSlot().getAssociation().getLocation());
			}
		}
	}; 
}