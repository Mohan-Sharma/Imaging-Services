package com.nzion.domain.docmgmt;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Enumeration;
import com.nzion.domain.File;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Filters( {
	@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")})
public class PatientEducationDocument extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	
	private Enumeration language;
	
	private File file;
	
	private PatientEducation patientEducation;
	
	@OneToOne(targetEntity = PatientEducation.class)
	@JoinColumn(name = "PATIENT_EDUCATION_ID")
	@Cascade(CascadeType.SAVE_UPDATE)
	public PatientEducation getPatientEducation() {
	return patientEducation;
	}

	public void setPatientEducation(PatientEducation patientEducation) {
	this.patientEducation = patientEducation;
	}

	@OneToOne(targetEntity = File.class)
	@JoinColumn(name = "FILE_ID")
	@Cascade(CascadeType.ALL)
	public File getFile() {
	return file;
	}

	public void setFile(File file) {
	this.file = file;
	}

	@OneToOne(targetEntity = Enumeration.class)
	@JoinColumn(name = "LANGUAGE_ID")
	public Enumeration getLanguage() {
	return language;
	}

	public void setLanguage(Enumeration language) {
	this.language = language;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) return true;
	if (obj == null) return false;
	if (getClass() != obj.getClass()) return false;
	PatientEducationDocument other = (PatientEducationDocument) obj;
	return other.getFile().getFileName().equals(this.getFile().getFileName());
	}
	
	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 0;
	result = prime * result + ((file == null) ? 0 : file.getFileName().hashCode());
	return result;
	}
}
