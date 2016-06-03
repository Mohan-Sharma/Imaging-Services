package com.nzion.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.PersonDelegation;
import com.nzion.domain.messaging.Message;
import com.nzion.repository.PersonRepository;
import com.nzion.service.PersonService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

@Service("personService")
public class PersonServiceImpl implements PersonService {

	private PersonRepository personRepository;
	
	private LabService labService;

	@Resource(name = "labService")
	@Required
	public void setLabService(LabService labService) {
	this.labService = labService;
	}

	
	@Resource(name = "personRepository")
	@Required
	public void setPersonRepository(PersonRepository personRepository) {
	this.personRepository = personRepository;
	}

		
	public <T> List<T> getPersonFavourites(Person person, Class<?> klass) {
	return personRepository.getPersonFavourites(person, klass);
	}
	
	
	

	@Override
	public List<Person> getPersonsFor(String personFirstName,String personLastName) {
	return personRepository.getPersonsFor(personFirstName,personLastName);
	}

	public Set<Message> getTodaysMessagesForPerson(Person person,Long personRole){
	Date from = UtilDateTime.getDayStart(new Date());
	Date thru = UtilDateTime.getDayEnd(new Date());
	return personRepository.getPersonsMessages(person,personRole, from, thru);
	}

	public Set<Message> getPastMessagesForPerson(Person person, Long personRole){
	Date thru = UtilDateTime.getDayStart(new Date());
	return personRepository.getPersonsMessages(person,personRole, null, thru);
	}

	
	@Override
	public List<Person> searchSchedulablePerson(String searchField, String value,Collection<Location> locations) {
	    return personRepository.searchSchedulablePerson(searchField,value,locations);
	}

	@Override
	public List<PersonDelegation> getPersonDelegationFor(Person person,Date fromDate,Date thruDate) {
	return personRepository.getProviderDelegationFor(person,fromDate,thruDate);
	}

	@Override
	public List<Person> getPersonsAccordingToUserLoginRole(Long personRole) {
	return personRepository.getAllPersonsAccordingToUserLoginRole(personRole);
	}
	
	@Override
	public List<Message> getDateRangeMessage(Date fromDate,Date thruDate) {
	return personRepository.getDateRangeMessage(fromDate,thruDate);
	}
}