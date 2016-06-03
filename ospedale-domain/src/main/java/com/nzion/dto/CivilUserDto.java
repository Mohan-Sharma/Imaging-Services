package com.nzion.dto;

import com.nzion.domain.Patient;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created by Nthdimenzion on 4/2/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CivilUserDto {
    private String civilId;
    private String afyaId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String endMostName;
    private String nationality;
    private String gender;
    private Date dateOfBirth;
    private String age;
    private String maritalStatus;
    private String bloodGroup;
    private String rh;
    private String emailId;
    private String communicationPreference;
    private String mobileNumber;
    private String faxNumber;
    private String homePhone;
    private String officePhone;
    private String patientType;
    private String address;
    private String additionalAddress;
    private String city;
    private String postalCode;
    private String state;
    private String country;
    private String notificationRequired;

    public void setPropertiesToPatient(Patient patient){
        if(this.firstName != null)
            patient.setFirstName(this.getFirstName());
        if(this.middleName != null)
            patient.setMiddleName(this.getMiddleName());
        if(this.lastName != null)
            patient.setLastName(this.getLastName());
        if(this.endMostName != null)
            patient.setEndMostName(this.getEndMostName());
        if(this.nationality != null)
            patient.setNationality(this.getNationality());
        if(this.age != null)
            patient.setAge(this.getAge());
        if(this.bloodGroup != null)
            patient.setBloodGroup(this.getBloodGroup());
        if(this.rh != null)
            patient.setRh(this.getRh());
        if(this.dateOfBirth != null)
            patient.setDateOfBirth(this.getDateOfBirth());
        if(this.country != null)
            patient.getContacts().getPostalAddress().setCountryGeo(this.getCountry());
        if(this.state != null)
            patient.getContacts().getPostalAddress().setStateProvinceGeo(this.getState());
        if(this.address != null)
            patient.getContacts().getPostalAddress().setAddress1(this.getAddress());
        if(this.mobileNumber != null)
            patient.getContacts().setMobileNumber(this.getMobileNumber());
        if(this.officePhone != null)
            patient.getContacts().setOfficePhone(this.getOfficePhone());
        if(this.emailId != null)
            patient.getContacts().setEmail(this.getEmailId());
        if(this.city != null)
            patient.getContacts().getPostalAddress().setCity(this.getCity());
        if(this.postalCode != null)
            patient.getContacts().getPostalAddress().setPostalCode(this.getPostalCode());
        if(this.faxNumber != null)
            patient.getContacts().setFaxNumber(this.getFaxNumber());
        if(this.notificationRequired != null)
            patient.setNotificationRequired(this.getNotificationRequired());
        if(this.civilId != null && this.firstName != null && !(this.firstName.equals("")) && this.lastName != null && !(this.lastName.equals("")))
            patient.setCivilId(this.getCivilId());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

    public String getAfyaId() {
        return afyaId;
    }

    public void setAfyaId(String afyaId) {
        this.afyaId = afyaId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getEndMostName() {
        return endMostName;
    }

    public void setEndMostName(String endMostName) {
        this.endMostName = endMostName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNotificationRequired() {
        return notificationRequired;
    }

    public void setNotificationRequired(String notificationRequired) {
        this.notificationRequired = notificationRequired;
    }
}
