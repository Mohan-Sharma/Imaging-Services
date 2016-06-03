package com.nzion.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;


@JsonIgnoreProperties(ignoreUnknown = true)
public class BookAppointmentDto {
    private Long providerId;
    private String civilId;
    private String afyaId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mobileNumber;
    private String emailId;
    private Date dateOfBirth;
    private Date appointmentStartDate;
    private Date appointmentEndDate;
    private String location;
    private String notes;
    private String visitType;
    private String gender;
    private boolean fromMobileApp = Boolean.FALSE;
    private String nationality;
    private String preferredLanguage;
    private String fileNo;
    private String registeredFrom;
    private String paymentId;
    private String referralClinicId;
    private String referralDoctorName;
    private Long soapReferralId;

    private String labTestProfile;
    private String labTestPanel;
    private String labTest;
    private boolean homeService;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isFromMobileApp() {
        return fromMobileApp;
    }

    public void setFromMobileApp(boolean fromMobileApp) {
        this.fromMobileApp = fromMobileApp;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPreferredLanguage() {
		return preferredLanguage;
	}

	public void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getRegisteredFrom(){
        return this.registeredFrom;
    }

    public void setRegisteredFrom(String registeredFrom){
        this.registeredFrom = registeredFrom;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReferralClinicId() {
        return referralClinicId;
    }

    public void setReferralClinicId(String referralClinicId) {
        this.referralClinicId = referralClinicId;
    }

    public String getReferralDoctorName() {
        return referralDoctorName;
    }

    public void setReferralDoctorName(String referralDoctorName) {
        this.referralDoctorName = referralDoctorName;
    }

    public Long getSoapReferralId() {
        return soapReferralId;
    }

    public void setSoapReferralId(Long soapReferralId) {
        this.soapReferralId = soapReferralId;
    }

    public String getAfyaId() {
        return afyaId;
    }

    public void setAfyaId(String afyaId) {
        this.afyaId = afyaId;
    }

    public String getLabTestProfile() {
        return labTestProfile;
    }

    public void setLabTestProfile(String labTestProfile) {
        this.labTestProfile = labTestProfile;
    }

    public String getLabTestPanel() {
        return labTestPanel;
    }

    public void setLabTestPanel(String labTestPanel) {
        this.labTestPanel = labTestPanel;
    }

    public String getLabTest() {
        return labTest;
    }

    public void setLabTest(String labTest) {
        this.labTest = labTest;
    }

    public boolean isHomeService() {
        return homeService;
    }

    public void setHomeService(boolean homeService) {
        this.homeService = homeService;
    }
}
