package com.nzion.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Nthdimenzion on 20-Sep-16.
 */
@Entity
public class ReportsConfiguration {
    @Id
    private Long id;

    private String reportName;

    private boolean admin;

    private boolean technician;

    private boolean reception;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isTechnician() {
        return technician;
    }

    public void setTechnician(boolean technician) {
        this.technician = technician;
    }

    public boolean isReception() {
        return reception;
    }

    public void setReception(boolean reception) {
        this.reception = reception;
    }
}
