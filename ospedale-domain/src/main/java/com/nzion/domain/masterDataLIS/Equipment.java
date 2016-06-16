package com.nzion.domain.masterDataLIS;

import com.nzion.domain.base.IdGeneratingBaseEntity;



import javax.persistence.Entity;
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
    private String eqpAlias;
    private String eqpType;
    private String baudRate;
    private Integer dataBit;
    private String parity;
    private Integer stopBit;
    private String accessionNo;
    private String pcName;

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
}
