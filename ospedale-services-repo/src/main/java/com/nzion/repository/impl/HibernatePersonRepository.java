package com.nzion.repository.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.PersonDelegation;
import com.nzion.domain.Provider;
import com.nzion.domain.UserLogin;
import com.nzion.domain.messaging.Message;
import com.nzion.repository.PersonRepository;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

@SuppressWarnings("unchecked")
public class HibernatePersonRepository extends HibernateBaseRepository implements PersonRepository {
	
	/*public List<VitalSign> searchPersonVitalSigns(String searchString, Person person) {
	Criteria criteria = getSession().createCriteria(VitalSign.class);
	criteria.add(Restrictions.eq("person", person));
	criteria.createCriteria("vitalSign").add(Restrictions.like("name", searchString, MatchMode.ANYWHERE));
	return criteria.setCacheable(true).list();
	}*/

	

	

	@Override
	public <T> List<T> getPersonFavourites(Person person, Class<?> klass) {
	Criteria criteria = getSession().createCriteria(klass);
	criteria.add(Restrictions.eq("person", person));
	return criteria.setCacheable(true).list();
	}

	@Override
	public List<Person> getPersonsFor(String personFirstName, String personLastName) {
	Criteria criteria = getSession().createCriteria(Person.class);
	criteria.add(Restrictions.eq("firstName", personFirstName)).add(Restrictions.eq("lastName", personLastName));
	// criteria.add(Restrictions.or(Restrictions.eq("firstName", personName), Restrictions.eq("lastName", personName)));
	return criteria.setCacheable(true).list();
	}

	@Override
	public Set<Message> getPersonsMessages(Person person, Long personRole, Date from, Date thru) {
	Set<Message> messages = new HashSet<Message>();
	if (person != null) {
		Criteria personCriteria = getSession().createCriteria(Message.class);
		addFromThruToCriteria(personCriteria, from, thru);
		personCriteria.createCriteria("persons").add(Restrictions.idEq(person.getId()));
		messages.addAll(personCriteria.list());
	}
	if (personRole != null) {
		Criteria roleCriteria = getSession().createCriteria(Message.class).add(
				Restrictions.sqlRestriction("{alias}.roles & " + String.valueOf(personRole) + " > 0"));
		addFromThruToCriteria(roleCriteria, from, thru);
		messages.addAll(roleCriteria.list());
	}
	return messages;
	}

	

	
	@Override
	public List<Person> searchSchedulablePerson(String searchField, String value,Collection<Location> locations) {
	List<Long> locationIds = new ArrayList<Long>();
	for(Location location : locations)
		locationIds.add(location.getId());
	if ("specialities".equals(searchField)) {
		Criteria criteria = getSession().createCriteria(Provider.class);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.createCriteria("specialities").add(Restrictions.ilike("description", value, MatchMode.START));
		criteria.add(Restrictions.eq("schedulable", true));
		if(!locationIds.isEmpty())
			criteria.createCriteria("locations").add(Restrictions.in("id", locationIds));
		return criteria.list();
	}
	Criteria criteria = getSession().createCriteria(Person.class);
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	criteria.add(Restrictions.like(searchField, value,MatchMode.START));
	criteria.add(Restrictions.eq("schedulable", true));
	if(!locationIds.isEmpty())
		criteria.createCriteria("locations").add(Restrictions.in("id", locationIds));
	return criteria.setCacheable(true).list();
	}

	@Override
	public List<PersonDelegation> getProviderDelegationFor(Person person,Date fromDate,Date thruDate) {
	Criteria criteria = getSession().createCriteria(PersonDelegation.class);
	criteria.add(Restrictions.eq("person", person));
	if(fromDate!=null && thruDate!=null){
		criteria.add(Restrictions.le("fromDate", thruDate));
		criteria.add(Restrictions.ge("thruDate", fromDate));
	}
	return criteria.setCacheable(true).list();
	}

	@Override
	public List<Person> getAllPersonsAccordingToUserLoginRole(Long personRole) {
	Criteria criteria = getSession().createCriteria(UserLogin.class);
	criteria.add(Restrictions.sqlRestriction("{alias}.roles & " + String.valueOf(personRole) + " > 0"));
	criteria.setProjection(Projections.property("person"));
	return criteria.setCacheable(true).list();
	}
	@Override
	public List<Message> getDateRangeMessage(Date fromDate,Date thruDate) {
	Criteria criteria = getSession().createCriteria(Message.class);
	if (fromDate != null) criteria.add(Restrictions.ge("createdTxTimestamp",UtilDateTime.getDayStart(fromDate)));
	if (thruDate != null) criteria.add(Restrictions.le("createdTxTimestamp",UtilDateTime.getDayEnd(thruDate)));
	return criteria.list();
	}
}