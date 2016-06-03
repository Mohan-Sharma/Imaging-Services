package com.nzion.domain.emr.lab;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
public class PanelTechnician extends IdGeneratingBaseEntity {

	private Location location;

	private Set<Person> technicians;

	@ManyToMany(targetEntity=Person.class,fetch=FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	public Set<Person> getTechnicians() {
	return technicians;
	}

	public void setTechnicians(Set<Person> technicians) {
	this.technicians = technicians;
	}

	@OneToOne
	@JoinColumn(name = "LOCATION_ID")
	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}


	private static final long serialVersionUID = 1L;

}
