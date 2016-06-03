package com.nzion.dto;

import com.nzion.domain.Practice;
import com.nzion.util.UtilValidator;


public class PracticeDto {
    private String practiceName;
    private String accrNumber;
    private String contactPersonName;
    private String alternatePhone;
    private String email;
    private String corporateEmail;
    private String faxNumber;
    private String homePhone;
    private String mobileNumber;
    private String officeExt;
    private String officePhone;
    private String pagerNumber;
    private String address1;
    private String address2;
    private String attnName;
    private String city;
    private String countryGeo;
    private String countyGeo;
    private String postalCode;
    private String postalCodeExt;
    private String stateProvinceGeo;
    private String drugLicence;
    private String federalTaxId;
    private String logoUrl;
    private String npiNumber;
    private String panNumber;
    private String serviceTaxNumber;
    private String subscriptionType;
    private String isdCode;
    private String imageUrl;
    private String tenantId;

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getAccrNumber() {
        return accrNumber;
    }

    public void setAccrNumber(String accrNumber) {
        this.accrNumber = accrNumber;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getAlternatePhone() {
        return alternatePhone;
    }

    public void setAlternatePhone(String alternatePhone) {
        this.alternatePhone = alternatePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorporateEmail() {
        return corporateEmail;
    }

    public void setCorporateEmail(String corporateEmail) {
        this.corporateEmail = corporateEmail;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOfficeExt() {
        return officeExt;
    }

    public void setOfficeExt(String officeExt) {
        this.officeExt = officeExt;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
    }

    public String getPagerNumber() {
        return pagerNumber;
    }

    public void setPagerNumber(String pagerNumber) {
        this.pagerNumber = pagerNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAttnName() {
        return attnName;
    }

    public void setAttnName(String attnName) {
        this.attnName = attnName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryGeo() {
        return countryGeo;
    }

    public void setCountryGeo(String countryGeo) {
        this.countryGeo = countryGeo;
    }

    public String getCountyGeo() {
        return countyGeo;
    }

    public void setCountyGeo(String countyGeo) {
        this.countyGeo = countyGeo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPostalCodeExt() {
        return postalCodeExt;
    }

    public void setPostalCodeExt(String postalCodeExt) {
        this.postalCodeExt = postalCodeExt;
    }

    public String getStateProvinceGeo() {
        return stateProvinceGeo;
    }

    public void setStateProvinceGeo(String stateProvinceGeo) {
        this.stateProvinceGeo = stateProvinceGeo;
    }

    public String getDrugLicence() {
        return drugLicence;
    }

    public void setDrugLicence(String drugLicence) {
        this.drugLicence = drugLicence;
    }

    public String getFederalTaxId() {
        return federalTaxId;
    }

    public void setFederalTaxId(String federalTaxId) {
        this.federalTaxId = federalTaxId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getNpiNumber() {
        return npiNumber;
    }

    public void setNpiNumber(String npiNumber) {
        this.npiNumber = npiNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getServiceTaxNumber() {
        return serviceTaxNumber;
    }

    public void setServiceTaxNumber(String serviceTaxNumber) {
        this.serviceTaxNumber = serviceTaxNumber;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getIsdCode() {
        return isdCode;
    }

    public void setIsdCode(String isdCode) {
        this.isdCode = isdCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Practice setPropertiesToPractice(Practice practice) {
        if(UtilValidator.isNotEmpty(this.accrNumber))
            practice.setAccrNumber(this.accrNumber);
        if(UtilValidator.isNotEmpty(this.practiceName))
            practice.setPracticeName(this.practiceName);
        if(UtilValidator.isNotEmpty(this.contactPersonName))
            practice.setContactPersonName(this.contactPersonName);
        if(UtilValidator.isNotEmpty(this.drugLicence))
            practice.setDrugLicence(this.drugLicence);
        if(UtilValidator.isNotEmpty(this.federalTaxId))
            practice.setFederalTaxId(this.federalTaxId);
        if(UtilValidator.isNotEmpty(this.npiNumber))
            practice.setNpiNumber(this.npiNumber);
        if(UtilValidator.isNotEmpty(this.panNumber))
            practice.setPanNumber(this.panNumber);
        if(UtilValidator.isNotEmpty(this.serviceTaxNumber))
            practice.setServiceTaxNumber(this.serviceTaxNumber);
        if(UtilValidator.isNotEmpty(this.subscriptionType))
            practice.setSubscriptionType(this.subscriptionType);
        /*if(UtilValidator.isNotEmpty(this.imageUrl))
            practice.setImageUrl(this.imageUrl);*/
        if(UtilValidator.isNotEmpty(practice.getContacts())) {
            if (UtilValidator.isNotEmpty(this.alternatePhone))
                practice.getContacts().setAlternatePhone(this.alternatePhone);
            if (UtilValidator.isNotEmpty(this.email))
                practice.getContacts().setEmail(this.email);
            if (UtilValidator.isNotEmpty(this.corporateEmail))
                practice.getContacts().setCorporateEmail(this.corporateEmail);
            if (UtilValidator.isNotEmpty(this.faxNumber))
                practice.getContacts().setFaxNumber(this.faxNumber);
            if (UtilValidator.isNotEmpty(this.homePhone))
                practice.getContacts().setHomePhone(this.homePhone);
            if (UtilValidator.isNotEmpty(this.mobileNumber))
                practice.getContacts().setMobileNumber(this.mobileNumber);
            if (UtilValidator.isNotEmpty(this.officeExt))
                practice.getContacts().setOfficeExt(this.officeExt);
            if (UtilValidator.isNotEmpty(this.officePhone))
                practice.getContacts().setOfficePhone(this.officePhone);
            if (UtilValidator.isNotEmpty(this.pagerNumber))
                practice.getContacts().setPagerNumber(this.pagerNumber);
            if (UtilValidator.isNotEmpty(this.isdCode))
                practice.getContacts().setIsdCode(this.isdCode);
            if(UtilValidator.isNotEmpty(practice.getContacts().getPostalAddress())) {
                if (UtilValidator.isNotEmpty(this.address1))
                    practice.getContacts().getPostalAddress().setAddress1(this.address1);
                if (UtilValidator.isNotEmpty(this.address2))
                    practice.getContacts().getPostalAddress().setAddress2(this.address2);
                if (UtilValidator.isNotEmpty(this.attnName))
                    practice.getContacts().getPostalAddress().setAttnName(this.attnName);
                if (UtilValidator.isNotEmpty(this.city))
                    practice.getContacts().getPostalAddress().setCity(this.city);
                if (UtilValidator.isNotEmpty(this.countryGeo))
                    practice.getContacts().getPostalAddress().setCountryGeo(this.countryGeo);
                if (UtilValidator.isNotEmpty(this.countyGeo))
                    practice.getContacts().getPostalAddress().setCountyGeo(this.countyGeo);
                if (UtilValidator.isNotEmpty(this.postalCode))
                    practice.getContacts().getPostalAddress().setPostalCode(this.postalCode);
                if (UtilValidator.isNotEmpty(this.postalCodeExt))
                    practice.getContacts().getPostalAddress().setPostalCodeExt(this.postalCodeExt);
                if (UtilValidator.isNotEmpty(this.stateProvinceGeo))
                    practice.getContacts().getPostalAddress().setStateProvinceGeo(this.stateProvinceGeo);
            }
        }
        return practice;
    }
}
