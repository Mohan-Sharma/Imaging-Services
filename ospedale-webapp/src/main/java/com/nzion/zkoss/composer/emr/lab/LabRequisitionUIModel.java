package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.Patient;
import com.nzion.domain.emr.lab.LabRequisition;
//import com.nzion.domain.inpatient.PatientBedTransfer;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 1/21/13
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class LabRequisitionUIModel {

    private String token;
    private Patient patient;
    private Date orderedOn;
    //private PatientBedTransfer patientBedTransfer;
    private LabRequisition.LabRequisitionStatus status;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(Date orderedOn) {
        this.orderedOn = orderedOn;
    }

    public LabRequisition.LabRequisitionStatus getStatus() {
        return status;
    }

    public void setStatus(LabRequisition.LabRequisitionStatus status) {
        this.status = status;
    }

  /*  public PatientBedTransfer getPatientBedTransfer() {
        return patientBedTransfer;
    }

    public void setPatientBedTransfer(PatientBedTransfer patientBedTransfer) {
        this.patientBedTransfer = patientBedTransfer;
    }*/
}
