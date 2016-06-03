package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 27-Apr-16.
 */
@Entity
public class LabCancellationPreference extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private RCMPreference rcmPreference;

    private RCMPreference.RCMVisitType visitType;

    private BigDecimal refundAdvanceAmountPercent;

    private BigDecimal labCancellationChargePercent;

    private BigDecimal cancellationTime;

    public LabCancellationPreference(){}

    public LabCancellationPreference(RCMPreference.RCMVisitType visitType){
        this.visitType = visitType;
    }

    public static Set<LabCancellationPreference> getEmptyLineItem(){
        Set<LabCancellationPreference> linkedSet = new HashSet<LabCancellationPreference>();
        for (RCMPreference.RCMVisitType rcmVisitType : RCMPreference.RCMVisitType.values()){
            LabCancellationPreference labCancellationPreference = new LabCancellationPreference(rcmVisitType);
            linkedSet.add(labCancellationPreference);
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

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getRefundAdvanceAmountPercent() {
        return refundAdvanceAmountPercent;
    }

    public void setRefundAdvanceAmountPercent(BigDecimal refundAdvanceAmountPercent) {
        this.refundAdvanceAmountPercent = refundAdvanceAmountPercent;
    }

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getLabCancellationChargePercent() {
        return labCancellationChargePercent;
    }

    public void setLabCancellationChargePercent(BigDecimal labCancellationChargePercent) {
        labCancellationChargePercent = labCancellationChargePercent;
    }

    public BigDecimal getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(BigDecimal cancellationTime) {
        this.cancellationTime = cancellationTime;
    }
}
