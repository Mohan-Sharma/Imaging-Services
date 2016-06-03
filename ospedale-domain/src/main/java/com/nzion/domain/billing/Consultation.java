package com.nzion.domain.billing;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Person;
import com.nzion.domain.Speciality;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.product.common.Money;

/**
 * @author Sandeep Prusty
 *
 * 16-Sep-2011
 */

@Entity
@Filters( {@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class Consultation extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	private Money price;

	private Speciality speciality;

	private Person person;
	
	public Consultation(){}
	
	/*public Consultation(Money price, Speciality speciality, Person person, SoapNoteType soapNoteType) {
		this.price = price;
		this.speciality = speciality;
		this.person = person;
		this.soapNoteType = soapNoteType;
	}*/

	
	@Embedded
	public Money getPrice() {
	return price = (price == null ? new Money() : price);
	}

	public void setPrice(Money price) {
	this.price = price;
	}

	@ManyToOne
	@JoinColumn(name = "SPECIALITY_ID")
	public Speciality getSpeciality() {
	return speciality;
	}

	public void setSpeciality(Speciality speciality) {
	this.speciality = speciality;
	}

	@OneToOne
	@JoinColumn(name = "PERSON_ID")
	public Person getPerson() {
	return person;
	}

	public void setPerson(Person person) {
	this.person = person;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((soapNoteType == null) ? 0 : soapNoteType.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == null || obj == null)
			return false;
		if(this!=obj)
			return false;
		Consultation other = (Consultation)obj;
		return this.getSoapNoteType().getName().equalsIgnoreCase(other.getSoapNoteType().getName());
	}
	*/
}
