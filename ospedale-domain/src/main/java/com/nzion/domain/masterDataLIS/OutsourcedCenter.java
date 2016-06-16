package com.nzion.domain.masterDataLIS;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Saikiran Chepuri on 03-Jun-16.
 */
@Entity
@Table(name = "OUTSOURCED_CENTER")
public class OutsourcedCenter extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;
    private Integer osCode;
    private String OsName;
    private String address;
    private String emailId;
    private String telPhone;
    private String contactPerson;

    public Integer getOsCode() {
        return osCode;
    }

    public void setOsCode(Integer osCode) {
        this.osCode = osCode;
    }

    public String getOsName() {
        return OsName;
    }

    public void setOsName(String osName) {
        OsName = osName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
}
