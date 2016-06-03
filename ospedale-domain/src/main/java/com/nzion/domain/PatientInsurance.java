package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: USER
 * Date: 4/10/15
 * Time: 4:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PatientInsurance extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;

    private String insuranceProviderId;

    private String insurancePlanId;

    private String insuranceName;

    private String planName;

    private String patientPlanId;

    private String patientRegistrationNo;

    @Column(nullable = false)
    public String getInsuranceProviderId() {
        return insuranceProviderId;
    }

    public void setInsuranceProviderId(String insuranceProviderId) {
        this.insuranceProviderId = insuranceProviderId;
    }

    @Column(nullable = false)
    public String getInsurancePlanId() {
        return insurancePlanId;
    }

    public void setInsurancePlanId(String insurancePlanId) {
        this.insurancePlanId = insurancePlanId;
    }

    public String getInsuranceName() {
        return insuranceName;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName = insuranceName;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPatientPlanId() {
        return patientPlanId;
    }

    public void setPatientPlanId(String patientPlanId) {
        this.patientPlanId = patientPlanId;
    }

    public String getPatientRegistrationNo() {
        return patientRegistrationNo;
    }

    public void setPatientRegistrationNo(String patientRegistrationNo) {
        this.patientRegistrationNo = patientRegistrationNo;
    }
}
