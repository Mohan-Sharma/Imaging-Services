package com.nzion.dto;

import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;


public class NetworkOrderResultDto {
    private String afyaId;
    private String doctorId;
    private String orderId;
    private String tenantId;
    private String facilityName;
    private String orderDate;
    private String status;
    private String facilityType;

    public String getAfyaId() {
        return afyaId;
    }

    public void setAfyaId(String afyaId) {
        this.afyaId = afyaId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }
    public void setPropertiesToDto(LabRequisition labRequisition){
        this.setAfyaId(labRequisition.getPatient().getAfyaId());
        this.setDoctorId(labRequisition.getLabOrderRequest().getReferralDoctorId());
        this.setFacilityType("IMAGING");
        this.setStatus(LabRequisition.LabRequisitionStatus.COMPLETED.name());
        this.setOrderId(labRequisition.getLabOrderRequest().getId().toString());
        this.setFacilityName(Infrastructure.getPractice().getPracticeName());
        this.setTenantId(Infrastructure.getPractice().getTenantId());
        this.setOrderDate(UtilDateTime.getDateOnly(labRequisition.getLabOrderRequest().getCreatedTxTimestamp()).toString());
    }
}
