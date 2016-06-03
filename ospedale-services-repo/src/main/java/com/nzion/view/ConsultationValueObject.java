package com.nzion.view;

import java.util.List;

import com.nzion.domain.Person;
import com.nzion.domain.Speciality;
import com.nzion.domain.billing.Consultation;
import com.nzion.domain.emr.UnitOfMeasurement;

public class ConsultationValueObject {
	
	private Speciality speciality;
	
	private Person provider;
	
	private UnitOfMeasurement measurement;
	
	private List<Consultation> consultations;
	
	public void setConsultations(List<Consultation> consultations) {
		this.consultations = consultations;
	}

	public ConsultationValueObject() {
	}



	public ConsultationValueObject(List<Consultation> consultations,Speciality speciality){
		this.speciality = speciality;
		this.consultations = consultations;
	}
	
	public ConsultationValueObject(List<Consultation> consultations,Person person){
		this.provider = person;
		this.consultations = consultations;
	}
	
	public UnitOfMeasurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(UnitOfMeasurement measurement) {
		this.measurement = measurement;
	}

	public List<Consultation> getConsultations() {
		return consultations;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	
	public Person getProvider() {
		return provider;
	}

	public void setProvider(Person provider) {
		this.provider = provider;
	}
}