package com.nzion.external;

import java.util.Date;
import java.util.List;

/**
 * Created by Nthdimenzion on 4/7/2015.
 */
public class LabOrderDto {
    private String clinicId;
    private String afyaId;
    private List<LabLineItem> labLineItems;
    private String visitId;
    private Date visitDate;
    private String firstName;
    private String lastName;
    private String mobile;
    private String patientType;
    private String doctorName;
    private String clinicName;

   
	public String getClinicId() {
		return clinicId;
	}

	public void setClinicId(String clinicId) {
		this.clinicId = clinicId;
	}

	public String getAfyaId() {
		return afyaId;
	}

	public void setAfyaId(String afyaId) {
		this.afyaId = afyaId;
	}

	public List<LabLineItem> getLabLineItems() {
		return labLineItems;
	}

	public void setLabLineItems(List<LabLineItem> labLineItems) {
		this.labLineItems = labLineItems;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
    
    
}
