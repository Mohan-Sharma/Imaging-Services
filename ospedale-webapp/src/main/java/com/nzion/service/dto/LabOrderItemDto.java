package com.nzion.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LabOrderItemDto {
    private String referralClinicId;
    private String referralDoctorName;
    private String labTestType;
    private String labTestCode;
    private String scheduleId;
    private String homeServiceCost;
    private boolean isPrescriptionScanned;

    public String getReferralClinicId() {
        return referralClinicId;
    }

    public void setReferralClinicId(String referralClinicId) {
        this.referralClinicId = referralClinicId;
    }

    public String getReferralDoctorName() {
        return referralDoctorName;
    }

    public void setReferralDoctorName(String referralDoctorName) {
        this.referralDoctorName = referralDoctorName;
    }

    public String getLabTestType() {
        return labTestType;
    }

    public void setLabTestType(String labTestType) {
        this.labTestType = labTestType;
    }

    public String getLabTestCode() {
        return labTestCode;
    }

    public void setLabTestCode(String labTestCode) {
        this.labTestCode = labTestCode;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getHomeServiceCost() {
        return homeServiceCost;
    }

    public void setHomeServiceCost(String homeServiceCost) {
        this.homeServiceCost = homeServiceCost;
    }

    public boolean isPrescriptionScanned() {
        return isPrescriptionScanned;
    }

    public void setIsPrescriptionScanned(boolean isPrescriptionScanned) {
        this.isPrescriptionScanned = isPrescriptionScanned;
    }
}
