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
public class LabReschedulingPreference extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private RCMPreference rcmPreference;

    private RCMPreference.RCMVisitType visitType;

    private BigDecimal refundAdvanceAmountPercent;

    private BigDecimal labCancellationPercent;

    private BigDecimal reschedulingTime;

    public LabReschedulingPreference(){}

    public LabReschedulingPreference(RCMPreference.RCMVisitType visitType){
        this.visitType = visitType;
    }

    public static Set<LabReschedulingPreference> getEmptyLineItem(){
        Set<LabReschedulingPreference> linkedSet = new LinkedHashSet<LabReschedulingPreference>();
        for (RCMPreference.RCMVisitType rcmVisitType : RCMPreference.RCMVisitType.values()){
            LabReschedulingPreference labReschedulingPreference = new LabReschedulingPreference(rcmVisitType);
            linkedSet.add(labReschedulingPreference);
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
    public BigDecimal getRefundAdvanceAmountPercent() {
        return refundAdvanceAmountPercent;
    }

    public void setRefundAdvanceAmountPercent(BigDecimal refundAdvanceAmountPercent) {
        this.refundAdvanceAmountPercent = refundAdvanceAmountPercent;
    }

    @Column(precision = 19, scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getLabCancellationPercent() {
        return labCancellationPercent;
    }

    public void setLabCancellationPercent(BigDecimal labCancellationPercent) {
        this.labCancellationPercent = labCancellationPercent;
    }

    public BigDecimal getReschedulingTime() {
        return reschedulingTime;
    }

    public void setReschedulingTime(BigDecimal reschedulingTime) {
        this.reschedulingTime = reschedulingTime;
    }
}
