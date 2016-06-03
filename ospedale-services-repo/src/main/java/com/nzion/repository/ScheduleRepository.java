package com.nzion.repository;

import com.nzion.domain.*;
import com.nzion.domain.Schedule.STATUS;
//import com.nzion.domain.emr.PatientVisit;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.screen.ScheduleCustomView;
//import com.nzion.view.ScheduleSearchValueObject;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Sandeep Prusty
 * Apr 30, 2010
 *
 * Mohan Sharma -  schedule search criteria implementation
 */
public interface ScheduleRepository extends BaseRepository {
	
	List<CalendarResourceAssoc> getCalendarAssociations(Person person, Date selectedDate, Date uptoDate, Location location, boolean asc);
	
	List<CalendarResourceAssoc> getAllCalendarAssociationsFor(Person person);

	List<CalendarResourceAssoc> getApplicableCalendarAssociations(Person person, Date fromDate, Date thruDate, Date startTime, Date endTime, List selectedDays, Location location);

	List<LabOrderRequest> searchScheduleFor(Person person, Date date, Location location);
	
	/*CalendarResourceAssoc getLatestCalendarAssociation(Person person, Location location, Date fromDate);
	
	List<CalendarResourceAssoc> getApplicableCalendarAssociations(Person person, Date fromDate, Date thruDate,
																  Date startTime, Date endTime, Location location, Location notLocation, boolean nullThruDateAllowed);
	
	List<Schedule> getSchedules(Person person, Location location, Date from, Date upto);

	List<Schedule> getSchedules(Person person, Location location, Date from, Date upto, Date startTime, Date endTime);

	Schedule getSchedule(Long scheduleId);

	List<ScheduleWaitingList> getAllWaitListedSchedules();
	
	List<Schedule> getSchedulesFor(STATUS status, Person person, Date date, Location location);

    List<Schedule> getSchedulesForGivenCriteria(STATUS status, Person person, Location location, Date searchFromDate, Date searchToDate, Patient patient);
    
    List<Schedule> getSchedulesForGivenCriteria(STATUS status, Person person, Date searchFromDate, Date searchToDate, String selectedBookingStatus,
												String civilId, String fileNo, String mobileNo);
	
	List<PatientVisit> getPatientVisitsFor(Schedule schedule);
	
	List<Person> getAllConsultablePersonsByLocation(Collection<Location> locations);

    List<Person> getAllConsultablePersons(Collection<Location> locations);
	
	Long findWaitingListCount();
	
	Integer getMaxSequenceNumberUsed(Schedule scheduleWithoutSequenceNumber);
	
	List<CalendarResourceAssoc> searchAssociationsFor(ScheduleSearchValueObject scheduleSearchValueObject);

	void deleteAllForceInserteds(Person person, Location location, Date date, Date endDate);
	
	ScheduleCustomView getDefaultProvidersForLoggedInUser();
		
	List<Schedule> getSchedulesForPatient(Patient patient, boolean past, boolean future);

	List<Schedule> getSchedulesWaitingForSelf(Date selectedDate, Person loggedInPerson);

	List<ScheduleBreak> getScheduleBreaks(Person person, Date from, Date thru);

	List<Schedule> searchBookedSchedules(ScheduleSearchValueObject scheduleSearchValueObject);

    List<Schedule> getSchedulesWithTentativeStatus();*/
}