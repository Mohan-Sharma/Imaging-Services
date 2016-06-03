package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 28-Apr-16.
 */
@Entity
public class PatientReschedulingPreference extends IdGeneratingBaseEntity{

    private static final long serialVersionUID = 1L;

    private RCMPreference rcmPreference;

    private RCMPreference.RCMVisitType visitType;

    private BigDecimal reschedulingTime;

    private BigDecimal patientCancellationChargeProviderPercent;

    private BigDecimal patientCancellationChargeAfyaPercent;

    private BigDecimal refundTrigger;

    public PatientReschedulingPreference(){}

    public PatientReschedulingPreference(RCMPreference.RCMVisitType visitType){
        this.visitType = visitType;
    }

    public static Set<PatientReschedulingPreference> getEmptyLineItem(){
        Set<PatientReschedulingPreference> linkedSet = new LinkedHashSet<PatientReschedulingPreference>();
        for (RCMPreference.RCMVisitType rcmVisitType : RCMPreference.RCMVisitType.values()){
            PatientReschedulingPreference patientReschedulingPreferences = new PatientReschedulingPreference(rcmVisitType);
            linkedSet.add(patientReschedulingPreferences);
        }
        return linkedSet;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public RCMPreference getRcmPreference() {
        return rcmPreference;
    }

    public void setRcmPreference(RCMPreference rcmPreference) {
        this.rcmPreference = rcmPreference;
    }

    @Enumerated(EnumType.STRING)
    public RCMPreference.RCMVisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(RCMPreference.RCMVisitType visitType) {
        this.visitType = visitType;
    }

    public BigDecimal getReschedulingTime() {
        return reschedulingTime;
    }

    public void setReschedulingTime(BigDecimal reschedulingTime) {
        this.reschedulingTime = reschedulingTime;
    }

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getPatientCancellationChargeProviderPercent() {
        return patientCancellationChargeProviderPercent;
    }

    public void setPatientCancellationChargeProviderPercent(BigDecimal patientCancellationChargeProviderPercent) {
        this.patientCancellationChargeProviderPercent = patientCancellationChargeProviderPercent;
    }

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getPatientCancellationChargeAfyaPercent() {
        return patientCancellationChargeAfyaPercent;
    }

    public void setPatientCancellationChargeAfyaPercent(BigDecimal patientCancellationChargeAfyaPercent) {
        this.patientCancellationChargeAfyaPercent = patientCancellationChargeAfyaPercent;
    }

    public BigDecimal getRefundTrigger() {
        return refundTrigger;
    }

    public void setRefundTrigger(BigDecimal refundTrigger) {
        this.refundTrigger = refundTrigger;
    }
}
