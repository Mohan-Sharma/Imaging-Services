package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 27-Apr-16.
 */
@Entity
public class PatientCancellationPreference extends IdGeneratingBaseEntity{

    private static final long serialVersionUID = 1L;

    private RCMPreference rcmPreference;

    private RCMPreference.RCMVisitType visitType;

    private BigDecimal cancellationTime;

    private BigDecimal patientCancellationChargePercent;

    private BigDecimal patientCancelationChargeAfyePercent;

    private BigDecimal refundTrigger;
    
    public PatientCancellationPreference() {}

    public PatientCancellationPreference(RCMPreference.RCMVisitType visitType){
        this.visitType = visitType;
    }

    public static Set<PatientCancellationPreference> getEmptyLineItem(){
       Set<PatientCancellationPreference> linkedSet = new LinkedHashSet<PatientCancellationPreference>();
       for (RCMPreference.RCMVisitType rcmVisitType : RCMPreference.RCMVisitType.values()){
           PatientCancellationPreference patientCancellationPreference = new PatientCancellationPreference(rcmVisitType);
           linkedSet.add(patientCancellationPreference);
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

    @Column(precision = 19,scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(BigDecimal cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    @Column(precision = 19,scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getPatientCancellationChargePercent() {
        return patientCancellationChargePercent;
    }

    public void setPatientCancellationChargePercent(BigDecimal patientCancellationChargePercent) {
        this.patientCancellationChargePercent = patientCancellationChargePercent;
    }

    @Column(precision = 19,scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getPatientCancelationChargeAfyePercent() {
        return patientCancelationChargeAfyePercent;
    }

    public void setPatientCancelationChargeAfyePercent(BigDecimal patientCancelationChargeAfyePercent) {
        this.patientCancelationChargeAfyePercent = patientCancelationChargeAfyePercent;
    }

    @Column(precision = 19,scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getRefundTrigger() {
        return refundTrigger;
    }

    public void setRefundTrigger(BigDecimal refundTrigger) {
        this.refundTrigger = refundTrigger;
    }
}
