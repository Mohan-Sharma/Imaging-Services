package com.nzion.dto;

import com.nzion.domain.Patient;
import com.nzion.domain.Salutable;
import com.nzion.util.UtilValidator;
import lombok.Data;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by Nthdimenzion on 3/25/2015.
 */
//@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto implements Serializable,Salutable {
    private String civilId;
    private String afyaId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private String endMostName;
    private String nationality;
    private String gender;
    private String religion;
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
    private String isdCode;
    private String preferredLanguage;
    private String registeredFrom;
    /** The prefix. */
    private String prefix;

    /** The suffix. */
    private String suffix;
    private String geographyCode;

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setPropertiesToPatientDto(Patient patient) {
        if(patient.getCivilId() != null)
            this.civilId = patient.getCivilId();
        if(patient.getSalutation() != null)
            this.salutation = patient.getSalutation();
        if(patient.getFirstName() != null)
            this.firstName = patient.getFirstName();
        if(patient.getMiddleName() != null)
            this.middleName = patient.getMiddleName();
        if(patient.getLastName() != null)
            this.lastName = patient.getLastName();
        if(patient.getEndMostName() != null)
            this.endMostName = patient.getEndMostName();
        if(patient.getNationality() != null)
            this.nationality = patient.getNationality();
        this.gender = patient.getGender().getDescription();
        this.dateOfBirth = patient.getDateOfBirth();
        if(patient.getAge() != null)
            this.age = patient.getAge();
        if(patient.getAfyaId() != null)
            this.afyaId = patient.getAfyaId();
        if(patient.getReligion() != null)
            this.religion = patient.getReligion().getDescription();
        if(patient.getMaritalStatus() != null)
            this.maritalStatus = patient.getMaritalStatus().getDescription();
        if(patient.getBloodGroup() != null)
            this.bloodGroup = patient.getBloodGroup();
        if(patient.getRh() != null)
            this.rh = patient.getRh();
        if(patient.getContacts() != null)
            this.emailId = patient.getContacts().getEmail();
        if(patient.getRemainderPreference() != null)
            this.communicationPreference = patient.getRemainderPreference().name();
        if(patient.getContacts() != null)
            this.mobileNumber = patient.getContacts().getMobileNumber();
        if(patient.getContacts() != null)
            this.faxNumber = patient.getContacts().getFaxNumber();
        if(patient.getContacts() != null)
            this.homePhone = patient.getContacts().getHomePhone();
        if(patient.getContacts() != null)
            this.officePhone = patient.getContacts().getOfficePhone();
        if(patient.getPatientType() != null)
            this.patientType = patient.getPatientType();
        if(patient.getContacts().getPostalAddress() != null)
            this.address = patient.getContacts().getPostalAddress().getAddress1();
        if(patient.getContacts().getPostalAddress() != null)
            this.additionalAddress = patient.getContacts().getPostalAddress().getAddress2();
        if(patient.getContacts().getPostalAddress() != null)
            this.city = patient.getContacts().getPostalAddress().getCity();
        if(patient.getContacts().getPostalAddress() != null)
            this.postalCode= patient.getContacts().getPostalAddress().getPostalCode();
        if(patient.getContacts().getPostalAddress() != null)
            this.state = patient.getContacts().getPostalAddress().getStateProvinceGeo();
        if(patient.getContacts().getPostalAddress() != null)
            this.country = patient.getContacts().getPostalAddress().getCountryGeo();
        if(patient.getRegisteredFrom() != null)
            this.registeredFrom = patient.getRegisteredFrom();
        this.geographyCode = "KW";
    }

    public void setPropertiesToPatient(Patient patient){
        if(this.afyaId != null)
            patient.setAfyaId(this.afyaId);
        if(this.salutation != null)
            patient.setSalutation(this.salutation);
        if(this.firstName != null)
            patient.setFirstName(this.firstName);
        if(this.middleName != null)
            patient.setMiddleName(this.middleName);
        if(this.lastName != null)
            patient.setLastName(this.lastName);
        if(this.endMostName != null)
            patient.setEndMostName(this.endMostName);
        if(this.nationality != null)
            patient.setNationality(this.nationality);
        if(this.age != null)
            patient.setAge(this.age);
        if(this.bloodGroup != null)
            patient.setBloodGroup(this.bloodGroup);
        if(this.rh != null)
            patient.setRh(this.rh);
        if(this.dateOfBirth != null)
            patient.setDateOfBirth(this.dateOfBirth);
        if(UtilValidator.isNotEmpty(patient.getContacts())) {
            if(this.country != null)
                patient.getContacts().getPostalAddress().setCountryGeo(this.country);
            if(this.state != null)
                patient.getContacts().getPostalAddress().setStateProvinceGeo(this.state);
            if(this.address != null)
                patient.getContacts().getPostalAddress().setAddress1(this.address);
            if(this.additionalAddress != null)
                patient.getContacts().getPostalAddress().setAddress2(this.additionalAddress);
            if(this.mobileNumber != null)
                patient.getContacts().setMobileNumber(this.mobileNumber);
            if(this.officePhone != null)
                patient.getContacts().setOfficePhone(this.officePhone);
            if(this.homePhone != null)
                patient.getContacts().setHomePhone(this.homePhone);
            if(this.emailId != null)
                patient.getContacts().setEmail(this.emailId);
            if(this.city != null)
                patient.getContacts().getPostalAddress().setCity(this.city);
            if(this.postalCode != null)
                patient.getContacts().getPostalAddress().setPostalCode(this.postalCode);
            if(this.faxNumber != null)
                patient.getContacts().setFaxNumber(this.faxNumber);
        }
        if(this.registeredFrom != null)
            patient.setRegisteredFrom(this.registeredFrom);

        if(this.civilId != null && this.firstName != null && !(this.firstName.equals("")) && this.lastName != null && !(this.lastName.equals("")))
            patient.setCivilId(this.civilId);


    }


    public class CustomDateSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws
                IOException, JsonProcessingException {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(value);

            gen.writeString(formattedDate);

        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
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

    public String getAfyaId() {
        return afyaId;
    }

    public void setAfyaId(String afyaId) {
        this.afyaId = afyaId;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

    public String getEndMostName() {
        return endMostName;
    }

    public void setEndMostName(String endMostName) {
        this.endMostName = endMostName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCommunicationPreference() {
        return communicationPreference;
    }

    public void setCommunicationPreference(String communicationPreference) {
        this.communicationPreference = communicationPreference;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdditionalAddress() {
        return additionalAddress;
    }

    public void setAdditionalAddress(String additionalAddress) {
        this.additionalAddress = additionalAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getRegisteredFrom() {
        return registeredFrom;
    }

    public void setRegisteredFrom(String registeredFrom) {
        this.registeredFrom = registeredFrom;
    }

    public String getGeographyCode() {
        return geographyCode;
    }

    public void setGeographyCode(String geographyCode) {
        this.geographyCode = geographyCode;
    }

    public void setPropertiesToPatientDtoFromMap(Map<String, Object> resultMap){
        if(UtilValidator.isNotEmpty(resultMap.get("afyaId")))
            this.setAfyaId(resultMap.get("afyaId").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("civilId")))
            this.setCivilId(resultMap.get("civilId").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("salutation")))
            this.setSalutation(resultMap.get("salutation").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("firstName")))
            this.setFirstName(resultMap.get("firstName").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("middleName")))
            this.setMiddleName(resultMap.get("middleName").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("lastName")))
            this.setLastName(resultMap.get("lastName").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("endMostName")))
            this.setEndMostName(resultMap.get("endMostName").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("gender")))
            this.setGender(resultMap.get("gender").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("additionalAddress")))
            this.setAdditionalAddress(resultMap.get("additionalAddress").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("address")))
            this.setAddress(resultMap.get("address").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("age")))
            this.setAge(resultMap.get("age").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("communicationPreference")))
            this.setCommunicationPreference(resultMap.get("communicationPreference").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("dateOfBirth"))) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.setDateOfBirth(formatter.parse(resultMap.get("dateOfBirth").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(UtilValidator.isNotEmpty(resultMap.get("emailId")))
            this.setEmailId(resultMap.get("emailId").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("faxNumber")))
            this.setFaxNumber(resultMap.get("faxNumber").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("maritalStatus")))
            this.setMaritalStatus(resultMap.get("maritalStatus").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("mobileNumber")))
            this.setMobileNumber(resultMap.get("mobileNumber").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("isdCode")))
            this.setIsdCode(resultMap.get("isdCode").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("officePhone")))
            this.setOfficePhone(resultMap.get("officePhone").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("patientType")))
            this.setPatientType(resultMap.get("patientType").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("postalCode")))
            this.setPostalCode(resultMap.get("postalCode").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("city")))
            this.setCity(resultMap.get("city").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("state")))
            this.setState(resultMap.get("state").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("country")))
            this.setCountry(resultMap.get("country").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("bloodGroup")))
            this.setBloodGroup(resultMap.get("bloodGroup").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("rh")))
            this.setRh(resultMap.get("rh").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("homePhone")))
            this.setHomePhone(resultMap.get("homePhone").toString());
        if(UtilValidator.isNotEmpty(resultMap.get("nationality")))
            this.setNationality(resultMap.get("nationality").toString());

    }

    public String getFullName(){
        StringBuilder br = new StringBuilder();
        if(this.getSalutation() != null)
            br.append(this.getSalutation()+".");
        if(this.getFirstName() != null)
            br.append(this.getFirstName());
        if(this.getMiddleName() != null)
            br.append(" "+this.getMiddleName());
        if(this.getLastName() != null)
            br.append(" "+this.getLastName());
        if(this.getEndMostName() != null)
            br.append(" "+this.getEndMostName());
        return br.toString();
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
