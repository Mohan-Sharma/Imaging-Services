package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Saikiran Chepuri on 24-May-16.
 */
@Entity
public class PatientWithDraw extends IdGeneratingBaseEntity{

    private static final long serialVersionUID = 1L;

    private Patient patient;

    private Date withdrawDate;

    private BigDecimal withdrawAmount;

    private String status;

    private Person createdPerson;

    private String withdrawMode;

    private String withdrawNotes;

    private String cancelReason;

    private String cancelNotes;

    @OneToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Temporal(TemporalType.DATE)
    public Date getWithdrawDate() {
        return withdrawDate;
    }

    public void setWithdrawDate(Date withdrawDate) {
        this.withdrawDate = withdrawDate;
    }

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToOne(fetch = FetchType.EAGER)
    public Person getCreatedPerson() {
        return createdPerson;
    }

    public void setCreatedPerson(Person createdPerson) {
        this.createdPerson = createdPerson;
    }

    public String getWithdrawMode() {
        return withdrawMode;
    }

    public void setWithdrawMode(String withdrawMode) {
        this.withdrawMode = withdrawMode;
    }

    public String getWithdrawNotes() {
        return withdrawNotes;
    }

    public void setWithdrawNotes(String withdrawNotes) {
        this.withdrawNotes = withdrawNotes;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getCancelNotes() {
        return cancelNotes;
    }

    public void setCancelNotes(String cancelNotes) {
        this.cancelNotes = cancelNotes;
    }

    @Transient
    public boolean cancelled(){
        if("Cancelled".equals(status))
            return false;
        return true;
    }
}
