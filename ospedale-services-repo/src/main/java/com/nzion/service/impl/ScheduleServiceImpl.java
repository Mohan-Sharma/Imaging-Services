package com.nzion.service.impl;

import com.nzion.domain.*;
import com.nzion.domain.Schedule.STATUS;
import com.nzion.domain.Schedule.ScheduleType;
import com.nzion.domain.base.Weekdays;

import com.nzion.domain.util.SlotAvailability;
import com.nzion.repository.ScheduleRepository;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.ScheduleService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Sandeep Prusty
 * Apr 29, 2010
 */

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

	private ScheduleRepository scheduleRepository;

	private CommonCrudRepository commonCrudRepository;
	
	private CommonCrudService commonCrudService;

	@Required
	@Resource
	public void setScheduleRepository(ScheduleRepository scheduleRepository) {
		this.scheduleRepository = scheduleRepository;
	}

	@Required
	@Resource
	public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
	this.commonCrudRepository = commonCrudRepository;
	}
	
	@Required
	@Resource
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}
	
	public List<Person> getSchedulablePersons(){
	return commonCrudRepository.findByEquality(Person.class, new String[]{"schedulable"}, new Object[]{true});
	}

	public List<CalendarResourceAssoc> getAllCalendarAssociationsFor(Person person){
		return scheduleRepository.getAllCalendarAssociationsFor(person);
	}

	public CalendarResourceAssoc checkCalendarCollision(CalendarResourceAssoc newAssociation){
		// To check for other locations in the same date and time..
		Person person = (newAssociation.getPerson().getId() != null) ? newAssociation.getPerson() : null;
		List selectedDays = newAssociation.getWeek().getSelectedDays();
		List<CalendarResourceAssoc> assocs = scheduleRepository.getApplicableCalendarAssociations(person, newAssociation.getFromDate()
				, newAssociation.getThruDate(), newAssociation.getStartTime(), newAssociation.getEndTime(), selectedDays, newAssociation.getLocation());
		return UtilValidator.isEmpty(assocs) ? null : assocs.get(0);

		// To check for same location in the given date range
		//assocs = scheduleRepository.getApplicableCalendarAssociations(newAssociation.getPerson(), newAssociation.getFromDate()
		//, newAssociation.getThruDate(), null, null, newAssociation.getLocation(), null, false);
		//return UtilValidator.isEmpty(assocs) ? null : assocs.get(0);
	}

	@Transactional(propagation=Propagation.REQUIRED, readOnly=false)
	public CalendarResourceAssoc saveCalendarAssociation(CalendarResourceAssoc association)throws Exception{
		//CalendarResourceAssoc lastAssociation = scheduleRepository.getLatestCalendarAssociation(association.getPerson(), association.getLocation(), association.getFromDate());
		//if(lastAssociation != null && !lastAssociation.isAfter(association)){
		//lastAssociation.setThruDate(UtilDateTime.getDayEnd(UtilDateTime.addDaysToDate(association.getFromDate(), -1)));
		//scheduleRepository.save(lastAssociation);
		//}
		association.setFromDate(UtilDateTime.getDayStart(association.getFromDate()));
		scheduleRepository.save(association);
		return association;
	}

	public Set<SlotAvailability> searchAvailableSchedules(Person person, Date date, Weekdays weekdays, Location location) {
		ScheduleScanner scanner = new ScheduleScanner(date, date, null, null, weekdays);
		//List<ScheduleBreak> breaks = getScheduleBreaks(searchVo.getPerson(), searchVo.getFromDate(), searchVo.getThruDate());
		scanner.extractAllSlots(getCalendarAssociations(person, date, date, location));
		scanner.setBookedSchedules(scheduleRepository.searchScheduleFor(person, date, location));
		scanner.subtract();
		return scanner.getUnifiedAvailableSlots();
	}

	public List<CalendarResourceAssoc> getCalendarAssociations(Person person, Date fromDate, Date thruDate, Location location) {
		TimeZone timeZone = TimeZone.getDefault();
		Date convertedFromDate = UtilDateTime.getDayStart(fromDate, timeZone, Locale.getDefault());
		Date convertedThruDate = UtilDateTime.getDayEnd(thruDate, timeZone, Locale.getDefault());
		return  scheduleRepository.getCalendarAssociations(person, convertedFromDate, convertedThruDate, location, true);
	}
}