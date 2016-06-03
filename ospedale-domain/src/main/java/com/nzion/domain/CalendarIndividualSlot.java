package com.nzion.domain;

import com.nzion.domain.base.IdGeneratingBaseEntity;
//import com.nzion.domain.emr.VisitTypeSoapModule;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CALENDAR_INDIVIDUAL_SLOT_DETAIL")
public class CalendarIndividualSlot extends IdGeneratingBaseEntity {
    private static final long serialVersionUID = 1L;
    private Date startTime;
    private Date endTime;
    private Integer sequenceNum;
    private CalendarResourceAssoc association;
    //private VisitTypeSoapModule visitTypeSoapModule;
    private String visitType;

    public CalendarIndividualSlot() {}

    public CalendarIndividualSlot(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public CalendarIndividualSlot(CalendarIndividualSlot slot, Integer sequenceNum, CalendarResourceAssoc association) {
        setStartTime(slot.getStartTime());
        setEndTime(slot.getEndTime());
        this.sequenceNum = sequenceNum;
        this.association = association;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @ManyToOne
    @JoinColumn(name = "CALENDAR_RESOURCE_ASSOC_ID")
    public CalendarResourceAssoc getAssociation() {
        return association;
    }

    public void setAssociation(CalendarResourceAssoc association) {
        this.association = association;
    }

    /*@OneToOne
    @JoinColumn(name = "VISIT_TYPE_SOAP_MODULE_ID")
    public VisitTypeSoapModule getVisitTypeSoapModule() {
        return visitTypeSoapModule;
    }

    public void setVisitTypeSoapModule(VisitTypeSoapModule visitTypeSoapModule) {
        this.visitTypeSoapModule = visitTypeSoapModule;
    }*/

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof CalendarIndividualSlot) {
            CalendarIndividualSlot slot = (CalendarIndividualSlot) object;
            if (slot.getStartTime().equals(this.getStartTime()) && slot.getEndTime().equals(this.getEndTime())
                    && slot.getAssociation().equals(this.getAssociation()))
                return true;
            else
                return false;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return this.startTime.hashCode()+this.endTime.hashCode()+this.association.hashCode();
    }
}
