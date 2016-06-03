package com.nzion.service;

import com.nzion.domain.*;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.util.SlotAvailability;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Sandeep Prusty
 * Apr 29, 2010
 */
public interface ScheduleService {
	
	List<Person> getSchedulablePersons();

	List<CalendarResourceAssoc> getAllCalendarAssociationsFor(Person person);

	CalendarResourceAssoc checkCalendarCollision(CalendarResourceAssoc newAssociation);

	CalendarResourceAssoc saveCalendarAssociation(CalendarResourceAssoc assoc) throws Exception;

	Set<SlotAvailability> searchAvailableSchedules(Person person, Date date, Weekdays weekdays, Location location);

}