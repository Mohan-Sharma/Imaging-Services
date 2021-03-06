package com.nzion.domain.masterDataLIS;

import com.nzion.domain.base.IdGeneratingBaseEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Created by Saikiran Chepuri on 03-Jun-16.
 */
@Entity
@Table(name = "Equipment")
public class Equipment extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;
    private Integer eqpCode;
    private String eqpName;
    private String eqpNameForPatient;
    private String eqpNameForPatientAr;
    private String eqpAlias;
    private String eqpType;
    private String baudRate;
    private Integer dataBit;
    private String parity;
    private Integer stopBit;
    private String accessionNo;
    private String pcName;
    private String briefDescription;
    private String briefDescriptionForPatientEng;
    private String briefDescriptionForPatientAr;
    private String imageUrl;

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getAccessionNo() {
        return accessionNo;
    }

    public void setAccessionNo(String accessionNo) {
        this.accessionNo = accessionNo;
    }

    public Integer getDataBit() {
        return dataBit;
    }

    public void setDataBit(Integer dataBit) {
        this.dataBit = dataBit;
    }

    public Integer getStopBit() {
        return stopBit;
    }

    public void setStopBit(Integer stopBit) {
        this.stopBit = stopBit;
    }

    public String getParity() {
        return parity;
    }

    public void setParity(String parity) {
        this.parity = parity;
    }



    public String getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(String baudRate) {
        this.baudRate = baudRate;
    }

    public String getEqpType() {
        return eqpType;
    }

    public void setEqpType(String eqpType) {
        this.eqpType = eqpType;
    }

    public String getEqpAlias() {
        return eqpAlias;
    }

    public void setEqpAlias(String eqpAlias) {
        this.eqpAlias = eqpAlias;
    }

    public String getEqpName() {
        return eqpName;
    }

    public void setEqpName(String eqpName) {
        this.eqpName = eqpName;
    }

    public Integer getEqpCode() {
        return eqpCode;
    }

    public void setEqpCode(Integer eqpCode) {
        this.eqpCode = eqpCode;
    }

    public String getEqpNameForPatient() {
        return eqpNameForPatient;
    }

    public void setEqpNameForPatient(String eqpNameForPatient) {
        this.eqpNameForPatient = eqpNameForPatient;
    }

    public String getEqpNameForPatientAr() {
        return eqpNameForPatientAr;
    }

    public void setEqpNameForPatientAr(String eqpNameForPatientAr) {
        this.eqpNameForPatientAr = eqpNameForPatientAr;
    }

    @Lob
    @Column(name = "BRIEF_DESCRIPTION", length = 1024)
    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    @Lob
    @Column(name = "BRIEF_DESCRIPTION_FOR_PATIENT_ENG", length = 1024)
    public String getBriefDescriptionForPatientEng() {
        return briefDescriptionForPatientEng;
    }

    public void setBriefDescriptionForPatientEng(String briefDescriptionForPatientEng) {
        this.briefDescriptionForPatientEng = briefDescriptionForPatientEng;
    }

    @Lob
    @Column(name = "BRIEF_DESCRIPTION_FOR_PATIENT_AR", length = 1024)
    public String getBriefDescriptionForPatientAr() {
        return briefDescriptionForPatientAr;
    }

    public void setBriefDescriptionForPatientAr(String briefDescriptionForPatientAr) {
        this.briefDescriptionForPatientAr = briefDescriptionForPatientAr;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
