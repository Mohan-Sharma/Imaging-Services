package com.nzion.domain.screen;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;

import com.nzion.domain.Person;
import com.nzion.domain.base.IdGeneratingBaseEntity;

/**
 * @author Sandeep Prusty
 * Jun 9, 2010
 */
@Entity
@Table(name="SCHEDULE_CUSTOM_VIEW")

public class ScheduleCustomView extends IdGeneratingBaseEntity {

	private String name;

	private String type = "NORMAL";
	
	private Set<Person> persons;
	
	public ScheduleCustomView() {}
	
	public ScheduleCustomView(Collection<Person> persons) {
	this.persons = new HashSet<Person>(persons);
	}

	public String getType() {
	return type;
	}

	public void setType(String type) {
	this.type = type;
	}

	@Column(name="NAME")
	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	@ManyToMany(targetEntity = Person.class, fetch=FetchType.EAGER)
	@JoinTable(name = "SCHEDULE_CUSTOM_VIEW_PERSON", joinColumns = { @JoinColumn(name = "VIEW_ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "PERSON_ID") })
	@Fetch(FetchMode.SELECT)
	public Set<Person> getPersons() {
	if(persons == null)
		persons = new HashSet<Person>();
	return persons;
	}

	public void setPersons(Set<Person> persons) {
	this.persons = persons;
	}

	public void clearAndAddPersons(Collection<Person> persons){
	this.persons.clear();
	this.persons.addAll(persons);
	}

	public int numberOfSchedulableEntities(){
	return persons == null ? 0 : persons.size();
	}
	
	@Override
	public String toString() {
	return name;
	}

	private static final long serialVersionUID = 1L;
}