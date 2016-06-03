package com.nzion.view;

import com.nzion.domain.Patient;
import com.nzion.domain.emr.EMRPatientInfo;
import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.util.UtilReflection;

import java.io.Serializable;

public class PatientViewObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Patient patient;
	private EMRPatientInfo emrPatientInfo;
	private PMSPatientInfo pmsPatientInfo;
    private String civilId;

	public PatientViewObject() {
	this.patient = new Patient();
	this.emrPatientInfo = new EMRPatientInfo();
	this.pmsPatientInfo = new PMSPatientInfo();
	}

	public Patient getPatient() {
	return patient;
	}

	public EMRPatientInfo getEmrPatientInfo() {
	emrPatientInfo.setPatient(patient);
	return emrPatientInfo;
	}

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public void setEmrPatientInfo(EMRPatientInfo emrPatient) {
	this.emrPatientInfo = emrPatient;
	}

	@Override
	public String toString() {
	return UtilReflection.buildStringRepresentation(this).toString();
	}

	public PMSPatientInfo getPmsPatientInfo() {
	pmsPatientInfo.setPatient(patient);
	return pmsPatientInfo;
	}

	public void setPmsPatientInfo(PMSPatientInfo pmsPatientInfo) {
	this.pmsPatientInfo = pmsPatientInfo;
	}

    public void setPropertiesToExistingPatient(Patient existingPatientInDb, Patient newPatient) {
        existingPatientInDb.setAfyaId(newPatient.getAfyaId());
        existingPatientInDb.setFirstName(newPatient.getFirstName());
        existingPatientInDb.setLastName(newPatient.getLastName());
        existingPatientInDb.setGender(newPatient.getGender());
        existingPatientInDb.setReligion(newPatient.getReligion());
        existingPatientInDb.setDateOfBirth(newPatient.getDateOfBirth());
        existingPatientInDb.setAge(newPatient.getAge());
        existingPatientInDb.setMaritalStatus(newPatient.getMaritalStatus());
        existingPatientInDb.setEmployeeStatus(newPatient.getEmployeeStatus());
        existingPatientInDb.setOccupation(newPatient.getOccupation());
        existingPatientInDb.setLanguage(newPatient.getLanguage());
        existingPatientInDb.setContacts(newPatient.getContacts());
        existingPatientInDb.setPatientType(newPatient.getPatientType());
        existingPatientInDb.setSendSms(newPatient.isSendSms());
        existingPatientInDb.setRemainderPreference(newPatient.getRemainderPreference());
        existingPatientInDb.setEthnicity(newPatient.getEthnicity());
    }
}