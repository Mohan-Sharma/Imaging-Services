package com.nzion.dto;

import com.nzion.util.UtilValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Nthdimenzion on 11-Jul-16.
 */
public class ReferralOrderDto {
    private String clinicTenantId;
    private String clinicName;
    private String doctorName;
    private Date visitDate;
    private String patientAfyaId;
    private String patientName;
    private String patientMobileNumber;
    private String labTest;
    private boolean active;
    private String referralTenant;
    private String scheuleId;
    private String labOrderSectionId;

    public String getReferralTenant() {
        return referralTenant;
    }

    public void setReferralTenant(String referralTenant) {
        this.referralTenant = referralTenant;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getPatientAfyaId() {
        return patientAfyaId;
    }

    public void setPatientAfyaId(String patientAfyaId) {
        this.patientAfyaId = patientAfyaId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientMobileNumber() {
        return patientMobileNumber;
    }

    public void setPatientMobileNumber(String patientMobileNumber) {
        this.patientMobileNumber = patientMobileNumber;
    }

    public String getLabTest() {
        return labTest;
    }

    public void setLabTest(String labTest) {
        this.labTest = labTest;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getClinicTenantId() {
        return clinicTenantId;
    }

    public void setClinicTenantId(String clinicTenantId) {
        this.clinicTenantId = clinicTenantId;
    }

    public String getScheuleId() {
        return scheuleId;
    }

    public void setScheuleId(String scheuleId) {
        this.scheuleId = scheuleId;
    }

    public String getLabOrderSectionId() {
        return labOrderSectionId;
    }

    public void setLabOrderSectionId(String labOrderSectionId) {
        this.labOrderSectionId = labOrderSectionId;
    }

    public void setPropertiesToReferralOrderDtoFromMap(Map<String, Object> resultMap){
        if(UtilValidator.isNotEmpty(resultMap.get("clinic_tenant_id")))
            this.setClinicTenantId(resultMap.get("clinic_tenant_id").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("clinic_name")))
            this.setClinicName(resultMap.get("clinic_name").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("doctor_name")))
            this.setDoctorName(resultMap.get("doctor_name").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("visit_date"))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.setVisitDate(formatter.parse(resultMap.get("visit_date").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(UtilValidator.isNotEmpty(resultMap.get("patient_afya_id")))
            this.setPatientAfyaId(resultMap.get("patient_afya_id").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("patient_name")))
            this.setPatientName(resultMap.get("patient_name").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("patient_mobile_number")))
            this.setPatientMobileNumber(resultMap.get("patient_mobile_number").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("lab_test")))
            this.setLabTest(resultMap.get("lab_test").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("active")))
            this.setActive((Boolean)resultMap.get("active"));
        if(UtilValidator.isNotEmpty(resultMap.get("referral_tenant")))
            this.setReferralTenant(resultMap.get("referral_tenant").toString());
        if (UtilValidator.isNotEmpty(resultMap.get("schedule_id")))
            this.setScheuleId(resultMap.get("schedule_id").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("lab_order_section_id")))
            this.setLabOrderSectionId(resultMap.get("lab_order_section_id").toString());
    }
}
