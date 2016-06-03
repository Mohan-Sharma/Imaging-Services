package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Fetch;
import com.nzion.domain.RCMPreference.RCMVisitType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 26-Apr-16.
 */
@Entity
public class SchedulingPreference extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;

    private RCMPreference rcmPreference;

    private RCMPreference.RCMVisitType visitType;

    private BigDecimal advanceAmountPercent;

    private BigDecimal advanceAmount;

    private BigDecimal convenienceFeePercent;

    private BigDecimal convenienceFee;

    private String showConvenienceFee;

    private BigDecimal leadTimeAllowed;

    private BigDecimal maxTimeAllowed;

    public SchedulingPreference() {

    }

    public SchedulingPreference(RCMPreference.RCMVisitType visitType,String showConvenienceFee){
        this.visitType = visitType;
        this.showConvenienceFee = showConvenienceFee;
    }

    public static Set<SchedulingPreference> getEmptyLineItem(){
        Set<SchedulingPreference> linkedSet = new LinkedHashSet<SchedulingPreference>();
        for (RCMPreference.RCMVisitType rcmVisitType : RCMPreference.RCMVisitType.values()){
            SchedulingPreference schedulingPreference = new SchedulingPreference(rcmVisitType,"Y");
            linkedSet.add(schedulingPreference);
        }
        return linkedSet;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    public RCMPreference getRcmPreference(){
        return rcmPreference;
    }
    public void setRcmPreference(RCMPreference rcmPreference){
        this.rcmPreference = rcmPreference;
    }

    @Enumerated(EnumType.STRING)
    public RCMPreference.RCMVisitType getVisitType(){
        return visitType;
    }
    public void setVisitType(RCMPreference.RCMVisitType visitType){
        this.visitType = visitType;
    }

    @Column(precision = 19,scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getAdvanceAmountPercent(){
        return advanceAmountPercent;
    }
    public void setAdvanceAmountPercent(BigDecimal advanceAmountPercent){
        this.advanceAmountPercent = advanceAmountPercent;
    }

    @Column(precision = 19,scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getAdvanceAmount(){
        return advanceAmount;
    }
    public void setAdvanceAmount(BigDecimal advanceAmount){
        this.advanceAmount = advanceAmount;
    }

    @Column(precision = 19, scale = 3, columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getConvenienceFeePercent() {
        return convenienceFeePercent;
    }
    public void setConvenienceFeePercent(BigDecimal convenienceFeePercent){
        this.convenienceFeePercent = convenienceFeePercent;
    }

    @Column(precision = 19, scale = 3,columnDefinition = "DECIMAL(19,3)")
    public BigDecimal getConvenienceFee(){
        return convenienceFee;
    }
    public void setConvenienceFee(BigDecimal convenienceFee){
        this.convenienceFee = convenienceFee;
    }


    public String getShowConvenienceFee(){
        return showConvenienceFee;
    }
    public void setShowConvenienceFee(String showConvenienceFee){
        this.showConvenienceFee = showConvenienceFee;
    }


    public BigDecimal getLeadTimeAllowed(){
        return leadTimeAllowed;
    }
    public void setLeadTimeAllowed(BigDecimal leadTimeAllowed){
        this.leadTimeAllowed = leadTimeAllowed;
    }


    public BigDecimal getMaxTimeAllowed(){
        return maxTimeAllowed;
    }
    public void setMaxTimeAllowed(BigDecimal maxTimeAllowed){
        this.maxTimeAllowed = maxTimeAllowed;
    }
}
