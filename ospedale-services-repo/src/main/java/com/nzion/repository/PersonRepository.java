package com.nzion.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.PersonDelegation;
import com.nzion.domain.messaging.Message;


public interface PersonRepository extends BaseRepository {
	
	
	
		

	<T> List<T> getPersonFavourites(Person person, Class<?> klass);
		
	List<Person> getPersonsFor(String personFirstName,String personLastName);
	
	Set<Message> getPersonsMessages(Person person,Long personRole, Date from, Date thru);
	
	
	
	List<Person> searchSchedulablePerson(String searchField,String value,Collection<Location> locations);
	
	List<PersonDelegation> getProviderDelegationFor(Person person,Date fromDate,Date thruDate);
	
	List<Person> getAllPersonsAccordingToUserLoginRole(Long personRole);
	
	List<Message> getDateRangeMessage(Date fromDate,Date thruDate);
	
}