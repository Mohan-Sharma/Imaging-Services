package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: USER
 * Date: 4/10/15
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PatientCorporate extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;

    private String corporateId;

    private String corporatePlanId;

    private String corporateName;

    private String corporatePlanName;

    private String contactName;

    private String landline;

    private String employeeId;

    private String employeeRole;

    @Column(nullable = false)
    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    @Column(nullable = false)
    public String getCorporatePlanId() {
        return corporatePlanId;
    }

    public void setCorporatePlanId(String corporatePlanId) {
        this.corporatePlanId = corporatePlanId;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getCorporatePlanName() {
        return corporatePlanName;
    }

    public void setCorporatePlanName(String corporatePlanName) {
        this.corporatePlanName = corporatePlanName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
