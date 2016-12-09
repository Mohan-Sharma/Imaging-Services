package com.nzion.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LabOrderDto {
    private String afyaId;
    private Date appointmentStartDate;
    private Date appointmentEndDate;
    private String location;
    private String notes;
    private String visitType;
    private boolean fromMobileApp = Boolean.FALSE;
    private String paymentId;
    private boolean homeService;
    private String homeAddress;
    private String phlebotomistId;

    private String scheduleId;

    private List<LabOrderItemDto> rows;

    private String paymentGateWay;
    private String refId;
    private String paymentTrackId;

    public Date getAppointmentStartDate() {
        return appointmentStartDate;
    }

    public void setAppointmentStartDate(Date appointmentStartDate) {
        this.appointmentStartDate = appointmentStartDate;
    }

    public Date getAppointmentEndDate() {
        return appointmentEndDate;
    }

    public void setAppointmentEndDate(Date appointmentEndDate) {
        this.appointmentEndDate = appointmentEndDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getVisitType() {
        return visitType;
    }

    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }

    public boolean isFromMobileApp() {
        return fromMobileApp;
    }

    public void setFromMobileApp(boolean fromMobileApp) {
        this.fromMobileApp = fromMobileApp;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAfyaId() {
        return afyaId;
    }

    public void setAfyaId(String afyaId) {
        this.afyaId = afyaId;
    }

    public boolean isHomeService() {
        return homeService;
    }

    public void setHomeService(boolean homeService) {
        this.homeService = homeService;
    }

    public List<LabOrderItemDto> getRows() {
        return rows;
    }

    public void setRows(List<LabOrderItemDto> rows) {
        this.rows = rows;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getPhlebotomistId() {
        return phlebotomistId;
    }

    public void setPhlebotomistId(String phlebotomistId) {
        this.phlebotomistId = phlebotomistId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getPaymentGateWay() {
        return paymentGateWay;
    }

    public void setPaymentGateWay(String paymentGateWay) {
        this.paymentGateWay = paymentGateWay;
    }

    public String getPaymentTrackId() {
        return paymentTrackId;
    }

    public void setPaymentTrackId(String paymentTrackId) {
        this.paymentTrackId = paymentTrackId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
