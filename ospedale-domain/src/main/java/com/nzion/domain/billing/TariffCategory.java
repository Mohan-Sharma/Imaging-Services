package com.nzion.domain.billing;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.Entity;

/**
 * Created by Nthdimenzion on 28-Jul-16.
 */
@Entity
public class TariffCategory extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;
    private String tariffCode;
    private String tariffName;
    private String patientCategory;

    public String getTariffCode() {
        return tariffCode;
    }

    public void setTariffCode(String tariffCode) {
        this.tariffCode = tariffCode;
    }

    public String getTariffName() {
        return tariffName;
    }

    public void setTariffName(String tariffName) {
        this.tariffName = tariffName;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }
}
