package com.nzion.repository.impl;

import com.nzion.domain.*;
import com.nzion.domain.Schedule.STATUS;
import com.nzion.domain.Schedule.ScheduleType;
//import com.nzion.domain.emr.PatientVisit;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.screen.ScheduleCustomView;
import com.nzion.repository.ScheduleRepository;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;
//import com.nzion.view.ScheduleSearchValueObject;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 * @author Sandeep Prusty Apr 30, 2010
 * 
 *         Mohan Sharma - schedule search criteria implementation
 */

@SuppressWarnings("unchecked")
public class HibernateScheduleRepository extends HibernateBaseRepository implements ScheduleRepository {

	/*public Schedule getSchedule(Long scheduleId) {
		Schedule schedule = findByPrimaryKey(Schedule.class, scheduleId);
		if (schedule.getPatient() != null)
			getSession().refresh(schedule.getPatient());
		return schedule;
	}

	public List<Schedule> getSchedules(Person person, Location location, Date from, Date upto) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.le("startDate", upto));
		criteria.add(Restrictions.ge("startDate", from));
		if (location != null)
			criteria.add(Restrictions.eq("location", location));
		criteria.add(Restrictions.or(Restrictions.ne("status", STATUS.CANCELLED), Restrictions.isNull("status")));
		criteria.add(Restrictions.eq("person", person));
		return criteria.setCacheable(false).list();
	}

	public List<Schedule> getSchedules(Person person, Location location, Date from, Date upto, Date startTime, Date endTime) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.le("startDate", upto));
		criteria.add(Restrictions.ge("startDate", from));
		criteria.add(Restrictions.le("startTime", endTime));
		criteria.add(Restrictions.ge("endTime", startTime));
		criteria.add(Restrictions.eq("location", location));
		criteria.add(Restrictions.or(Restrictions.isNull("status"), Restrictions.ne("status", STATUS.CANCELLED)));
		criteria.add(Restrictions.eq("person", person));
		return criteria.setCacheable(true).list();
	}

	public List<CalendarResourceAssoc> getApplicableCalendarAssociations(Person person, Date fromDate, Date thruDate, Date startTime, Date endTime, Location location, Location notLocation, boolean nullThruDateAllowed) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		criteria.add(Restrictions.eq("person", person));
		criteria.add(Restrictions.or(Restrictions.isNull("thruDate"), Restrictions.le("fromDate", thruDate)));
		criteria.add(nullThruDateAllowed ? Restrictions.or(Restrictions.isNull("thruDate"), Restrictions.ge("thruDate", fromDate)) : Restrictions.ge("thruDate", fromDate));
		if (startTime != null)
			criteria.add(Restrictions.lt("startTime", endTime));
		if (endTime != null)
			criteria.add(Restrictions.gt("endTime", startTime));
		if (location != null)
			criteria.add(Restrictions.eq("location", location));
		if (notLocation != null)
			criteria.add(Restrictions.ne("location", notLocation));
		return criteria.setCacheable(true).list();
	}

	public CalendarResourceAssoc getLatestCalendarAssociation(Person person, Location location, Date fromDate) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		criteria.add(Restrictions.eq("person", person)).add(Restrictions.eq("location", location)).add(Restrictions.or(Restrictions.isNull("thruDate"), Restrictions.ge("thruDate", fromDate)));
		return (CalendarResourceAssoc) criteria.uniqueResult();
	}
*/
	public List<CalendarResourceAssoc> getCalendarAssociations(Person person, Date selectedDate, Date uptoDate, Location location, boolean asc) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.le("fromDate", uptoDate));
		criteria.add(Restrictions.or(Restrictions.ge("thruDate", selectedDate), Restrictions.isNull("thruDate")));
		if (location != null)
			criteria.add(Restrictions.eq("location", location));
		criteria.addOrder(asc ? Order.asc("fromDate") : Order.desc("fromDate"));

		if (person == null){
			criteria.add(Restrictions.isNull("person"));
		}

		List<CalendarResourceAssoc> calendarResourceAssocList =  criteria.list();
		if (person != null) {
			Iterator iterator = calendarResourceAssocList.iterator();
			while (iterator.hasNext()) {
				CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc) iterator.next();
				if ((calendarResourceAssoc.getPerson() == null) || (!calendarResourceAssoc.getPerson().getId().equals(person.getId()))) {
					iterator.remove();
				}
			}
		}
		return calendarResourceAssocList;
	}

	public List<CalendarResourceAssoc> getAllCalendarAssociationsFor(Person person) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (person != null){
			criteria.add(Restrictions.eq("person", person));
		} else {
			criteria.add(Restrictions.isNull("person"));
		}
		criteria.addOrder(Order.desc("fromDate"));
		return criteria.setCacheable(true).list();
	}

	public List<CalendarResourceAssoc> getApplicableCalendarAssociations(Person person, Date fromDate, Date thruDate, Date startTime, Date endTime, List selectedDays, Location location) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		if (person != null){
			criteria.add(Restrictions.eq("person", person));
		} else {
			criteria.add(Restrictions.isNull("person"));
		}
		if (location != null){
			criteria.add(Restrictions.eq("location", location));
		}

		criteria.add(Restrictions.and(Restrictions.and(Restrictions.or(Restrictions.isNull("thruDate"), Restrictions.le("fromDate", thruDate)), Restrictions.or(Restrictions.isNull("thruDate"), Restrictions.ge("thruDate", fromDate))),
				Restrictions.and(Restrictions.lt("startTime", endTime), Restrictions.gt("endTime", startTime))));

		List<CalendarResourceAssoc> calendarResourceAssocList =  criteria.list();
		if (calendarResourceAssocList != null){
			Iterator iterator = calendarResourceAssocList.iterator();
			while (iterator.hasNext()){
				CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator.next();
				if (calendarResourceAssoc.getWeek() != null){
					List list = calendarResourceAssoc.getWeek().getSelectedDays();
					for (int i = 0; i< selectedDays.size(); i++){
						if (list.contains(selectedDays.get(i))){
							return calendarResourceAssocList;
						}
					}
				}
			}
		}
		return null;
	}

	public List<LabOrderRequest> searchScheduleFor(Person person, Date date, Location location) {
		Criteria criteria = getSession().createCriteria(LabOrderRequest.class);
		//criteria.add(Restrictions.le("startDate", UtilDateTime.getDateOnly(date)));
		criteria.add(Restrictions.ne("orderStatus", Schedule.STATUS.CANCELLED));

		if (person != null){
			criteria.add(Restrictions.eq("phlebotomist", person));
			//criteria.add(Restrictions.eq("phlebotomistStartDate", UtilDateTime.getDateOnly(date)));

			criteria.add(Restrictions.le("phlebotomistStartDate", UtilDateTime.getDateOnly(date)));
			criteria.add(Restrictions.eq("phlebotomistStartDate", UtilDateTime.getDateOnly(date)));

			criteria.add(Restrictions.eq("location", location));
		} else {
			criteria.add(Restrictions.isNull("phlebotomist"));
			//criteria.add(Restrictions.eq("startDate", UtilDateTime.getDateOnly(date)));
			criteria.add(Restrictions.le("startDate", UtilDateTime.getDateOnly(date)));
			criteria.add(Restrictions.eq("startDate", UtilDateTime.getDateOnly(date)));

			criteria.add(Restrictions.eq("location", location));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	/*public List<ScheduleWaitingList> getAllWaitListedSchedules() {
		Criteria criteria = getSession().createCriteria(ScheduleWaitingList.class);
		criteria.add(Restrictions.isNull("assignedSchedule"));
		return criteria.setCacheable(true).list();
	}

	public List<Schedule> getSchedulesFor(STATUS status, Person person, Date date, Location location) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		if (person != null)
			criteria.add(Restrictions.eq("person", person));
		*//*if (status != null)
			criteria.add(Restrictions.eq("status", status));*//*
		if (date != null) {
			criteria.add(Restrictions.le("startDate", date));
			criteria.add(Restrictions.ge("startDate", date));
		}
		if (date == null)
			criteria.add(Restrictions.eq("startDate", UtilDateTime.getDayEnd(new Date())));
		if (location != null)
			criteria.add(Restrictions.eq("location", location));
		criteria.add(Restrictions.isNotNull("patient"));
		return criteria.setCacheable(true).list();
	}

	public List<Schedule> getSchedulesForGivenCriteria(STATUS status, Person person, Location location, Date searchFromDate, Date searchToDate, Patient patient) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		if (person != null)
			criteria.add(Restrictions.eq("person", person));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		if (searchFromDate != null) {
			criteria.add(Restrictions.ge("startDate", searchFromDate));
		}
		if (searchToDate != null) {
			criteria.add(Restrictions.le("startDate", searchToDate));
		}
		if (patient != null) {
			criteria.add(Restrictions.eq("patient", patient));
		}
		if (location != null)
			criteria.add(Restrictions.eq("location", location));
		criteria.addOrder(Order.desc("tentativeStatus"));
		criteria.add(Restrictions.isNotNull("patient"));
		return criteria.setCacheable(true).list();
	}
	
	
	public List<Schedule> getSchedulesForGivenCriteria(STATUS status, Person person, Date searchFromDate, Date searchToDate, String selectedBookingStatus,
    		String civilId, String fileNo, String mobileNo) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		if (person != null)
			criteria.add(Restrictions.eq("person", person));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		if (searchFromDate != null) {
			criteria.add(Restrictions.ge("startDate", searchFromDate));
		}
		if (searchToDate != null) {
			criteria.add(Restrictions.le("startDate", searchToDate));
		}
		if(UtilValidator.isNotEmpty(selectedBookingStatus)){
			criteria.add(Restrictions.eq("tentativeStatus", selectedBookingStatus));
		}
		
		if(UtilValidator.isNotEmpty(civilId)){
			criteria.createCriteria("patient").add(Restrictions.eq("civilId", civilId));
		}
		if(UtilValidator.isNotEmpty(fileNo)){
			criteria.createCriteria("patient").add(Restrictions.eq("fileNo", fileNo));
		}
		if(UtilValidator.isNotEmpty(mobileNo)){
			criteria.createCriteria("patient").add(Restrictions.eq("contacts.mobileNumber", mobileNo));
		}
		criteria.addOrder(Order.desc("tentativeStatus"));
		criteria.add(Restrictions.isNotNull("patient"));
		return criteria.setCacheable(true).list();
	}
	

	public List<PatientVisit> getPatientVisitsFor(Schedule schedule) {
		Criteria criteria = getSession().createCriteria(PatientVisit.class);
		criteria.add(Restrictions.eq("schedule", schedule));
		criteria.addOrder(Order.desc("time"));
		return criteria.setCacheable(true).list();
	}

	public List<Person> getAllConsultablePersonsByLocation(Collection<Location> locations) {
		List<Long> locationIds = new ArrayList<Long>(3);
		for (Location location : locations)
			locationIds.add(location.getId());
		Criteria criteria = getSession().createCriteria(Employee.class);
		criteria.createCriteria("locations").add(Restrictions.in("id", locationIds));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	public List<Person> getAllConsultablePersons(Collection<Location> locations) {
		List<Long> locationIds = new ArrayList<Long>(3);
		for (Location location : locations)
			locationIds.add(location.getId());
		Criteria criteria = getSession().createCriteria(Employee.class);
		criteria.createCriteria("locations").add(Restrictions.in("id", locationIds));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Person> personList = criteria.list();

		List<Person> tmpPersonsList = new ArrayList<Person>();
		Iterator iterator = personList.iterator();
		while (iterator.hasNext()) {
			Person person = (Person) iterator.next();
			UserLogin userLogin = person.getUserLogin();
			if (userLogin != null) {
				if (userLogin.hasRole(Roles.NURSE)) {
					tmpPersonsList.add(person);
				}
			}
		}
		personList = tmpPersonsList;
		return personList;
	}

	public Long findWaitingListCount() {
		return (Long) getSession().createQuery(" select count(o) from " + ScheduleWaitingList.class.getSimpleName() + " o where assignedSchedule is null").uniqueResult();
	}

	public Integer getMaxSequenceNumberUsed(Schedule scheduleWithoutSequenceNumber) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.eq("startDate", scheduleWithoutSequenceNumber.getStartDate()));
		criteria.add(Restrictions.eq("startTime", scheduleWithoutSequenceNumber.getStartTime()));
		criteria.add(Restrictions.eq("person", scheduleWithoutSequenceNumber.getPerson()));
		criteria.setProjection(Projections.max("sequenceNum"));
		return (Integer) criteria.uniqueResult();
	}

	public List<CalendarResourceAssoc> searchAssociationsFor(ScheduleSearchValueObject scheduleSearchValueObject) {
		Criteria criteria = getSession().createCriteria(CalendarResourceAssoc.class);
		if (scheduleSearchValueObject.getPerson() != null)
			criteria.add(Restrictions.eq("person", scheduleSearchValueObject.getPerson()));
		if (scheduleSearchValueObject.getLocation() != null)
			criteria.add(Restrictions.eq("location", scheduleSearchValueObject.getLocation()));
		criteria.add(Restrictions.le("fromDate", scheduleSearchValueObject.getThruDate()));
		criteria.add(Restrictions.ge("thruDate", scheduleSearchValueObject.getFromDate()));
		if (scheduleSearchValueObject.getThruTime() != null)
			criteria.add(Restrictions.le("startTime", scheduleSearchValueObject.getThruTime()));
		if (scheduleSearchValueObject.getFromTime() != null)
			criteria.add(Restrictions.ge("endTime", scheduleSearchValueObject.getFromTime()));
		return unify(criteria.list());
	}

	@Override
	public void deleteAllForceInserteds(Person person, Location location, Date startDate, Date endDate) {
		String queryString = "delete from Schedule where location=:location and startDate >= :startDate and startDate <= :endDate and scheduleType=:scheduleType and person=:person";
		Query query = getSession().createQuery(queryString);
		query.setParameter("location", location);
		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);
		query.setParameter("scheduleType", ScheduleType.FORCEINSERTED);
		query.setParameter("person", person);
		query.executeUpdate();
	}

	@Override
	public ScheduleCustomView getDefaultProvidersForLoggedInUser() {
		Query query = getSession().createQuery("from ScheduleCustomView where createdBy=:createdBy and type=:type");
		query.setParameter("createdBy", Infrastructure.getUserLogin().getUsername());
		query.setParameter("type", "DASHBOARD");
		return (ScheduleCustomView) query.uniqueResult();
	}

	@Override
	public List<Schedule> getSchedulesForPatient(Patient patient, boolean past, boolean future) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.eq("patient", patient));
		if (past)
			criteria.add(Restrictions.lt("startDate", UtilDateTime.getDayStart(new Date())));
		if (future)
			criteria.add(Restrictions.ge("startDate", UtilDateTime.getDayStart(new Date())));
		return criteria.list();
	}

	public List<Schedule> getSchedulesWaitingForSelf(Date selectedDate, Person person) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.eq("startDate", selectedDate));
		criteria.createCriteria("lastPatientVisit").add(Restrictions.eq("metWith", person));
		return criteria.list();
	}

	@Override
	public List<ScheduleBreak> getScheduleBreaks(Person person, Date from, Date thru) {
		Criteria criteria = getSession().createCriteria(ScheduleBreak.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("person", person));
		if (from != null && thru != null)
			criteria.createCriteria("recurrence").add(Restrictions.le("startDate", thru)).add(Restrictions.ge("endDate", from));
		return criteria.list();
	}

	@Override
	public List<Schedule> searchBookedSchedules(ScheduleSearchValueObject scheduleSearchValueObject) {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		if (scheduleSearchValueObject.getPerson() != null)
			criteria.add(Restrictions.eq("person", scheduleSearchValueObject.getPerson()));
		if (scheduleSearchValueObject.getLocation() != null)
			criteria.add(Restrictions.eq("location", scheduleSearchValueObject.getLocation()));
		criteria.add(Restrictions.ge("startDate", scheduleSearchValueObject.getFromDate()));
		criteria.add(Restrictions.le("startDate", scheduleSearchValueObject.getThruDate()));
		if (scheduleSearchValueObject.getFromTime() != null)
			criteria.add(Restrictions.ge("startTime", scheduleSearchValueObject.getFromTime()));
		if (scheduleSearchValueObject.getThruTime() != null)
			criteria.add(Restrictions.le("startTime", scheduleSearchValueObject.getThruTime()));
		if (scheduleSearchValueObject.getSlotType() != null)
			criteria.add(Restrictions.eq("slotType", scheduleSearchValueObject.getSlotType()));
		if (scheduleSearchValueObject.getStatus() != null)
			criteria.add(Restrictions.eq("status", scheduleSearchValueObject.getStatus()));
		return criteria.list();
	}

	public List<Schedule> getSchedulesWithTentativeStatus() {
		Criteria criteria = getSession().createCriteria(Schedule.class);
		criteria.add(Restrictions.eq("tentativeStatus", "Tentative"));
		criteria.add(Restrictions.eq("status", STATUS.SCHEDULED));
		criteria.add(Restrictions.ge("startDate", UtilDateTime.getDateOnly(new Date())));
		criteria.add(Restrictions.isNotNull("patient"));
		return criteria.setCacheable(true).list();
	}*/
}