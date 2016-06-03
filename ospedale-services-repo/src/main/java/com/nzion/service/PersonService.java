package com.nzion.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.PersonDelegation;
import com.nzion.domain.messaging.Message;

public interface PersonService {


	
	<T> List<T> getPersonFavourites(Person person, Class<?> klass);
	
	List<Person> getPersonsFor(String personFirstName,String personLastName);
	
	Set<Message> getTodaysMessagesForPerson(Person person,Long personRole);

	Set<Message> getPastMessagesForPerson(Person person,Long personRole);
		
	List<Person> searchSchedulablePerson(String searchField,String value,Collection<Location> locations);
	
	List<PersonDelegation> getPersonDelegationFor(Person person,Date fromDate,Date thruDate);
	
	List<Person> getPersonsAccordingToUserLoginRole(Long personRole);
	
	List<Message> getDateRangeMessage(Date fromDate,Date thruDate);
	
}