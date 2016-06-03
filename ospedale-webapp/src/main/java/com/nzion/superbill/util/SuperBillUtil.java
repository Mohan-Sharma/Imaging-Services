package com.nzion.superbill.util;

import com.lowagie.text.pdf.codec.Base64;
import com.nzion.domain.*;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.superbill.dto.*;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/17/14
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SuperBillUtil {

    public static String transferServletRequestToString(BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String string;
        while ((string = bufferedReader.readLine()) != null) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    public static DoctorDto transformToDoctorDto(Provider provider) {
        //INHOUSE
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.doctorId = provider.getId();
        doctorDto.firstName = provider.getFirstName();
        doctorDto.middleName = provider.getMiddleName();
        doctorDto.lastName = provider.getLastName();
        return doctorDto;
    }

    public static ReferralDto transformToReferralDto(Referral referral) {
        ReferralDto referralDto = new ReferralDto();
        referralDto.referralType ="OUTSIDE";
        referralDto.referralId = referral.getId();
        referralDto.firstName = referral.getFirstName();
        referralDto.middleName = referral.getMiddleName();
        referralDto.lastName = referral.getLastName();
        return referralDto;
    }

    public static ReferralDto transformToReferralDto(Provider provider) {
        ReferralDto referralDto = new ReferralDto();
        referralDto.referralType ="INHOUSE";
        referralDto.referralId = provider.getId();
        referralDto.firstName = provider.getFirstName();
        referralDto.middleName = provider.getMiddleName();
        referralDto.lastName = provider.getLastName();
        return referralDto;
    }
    public static PatientDto transformToPatientDto(Patient patient) throws SQLException, IOException {
        PatientDto patientDto = new PatientDto();
        if (patient == null) {
            return patientDto;
        }
        patientDto.patientId = patient.getId();
        patientDto.firstName = patient.getFirstName();
        patientDto.middleName = patient.getMiddleName();
        patientDto.lastName = patient.getLastName();
        patientDto.dateOfBirth = patient.getDateOfBirth();
        patientDto.age = patient.getAge();
        patientDto.genderId = patient.getGender() != null ? patient.getGender().getEnumId() : patientDto.genderId;
        patientDto.mrnNumber = patient.getAccountNumber();
        ContactFields contactFields = patient.getContacts();
        PostalAddressFields postalAddressFields = contactFields != null ? contactFields.getPostalAddress() : new PostalAddressFields();
        patientDto.mobileNumber = contactFields != null ? contactFields.getMobileNumber() : patientDto.mobileNumber;

        patientDto.postalAddress.put("address1", postalAddressFields.getAddress1());
        patientDto.postalAddress.put("address2", postalAddressFields.getAddress2());
        patientDto.postalAddress.put("city", postalAddressFields.getCity());
        patientDto.postalAddress.put("state", postalAddressFields.getStateProvinceGeo());
        patientDto.postalAddress.put("postalCode", postalAddressFields.getPostalCode());
        if (patient.getProfilePicture() != null)
            patientDto.image = getTheBase64EncodedImage(patient.getProfilePicture());
        return patientDto;
    }

    public static LabTestDto transformToLabTestDto(LabTestPanel labTestPanel) {
        LabTestDto labTestDto = new LabTestDto();
      //  labTestDto.panelCode = labTestPanel.getCode();
       // labTestDto.panelName = labTestPanel.getPanelName();
        //labTestDto.amount = labTestPanel.getPrice();
        return labTestDto;
    }

    public static EnumerationDto transformToEnumerationDto(Enumeration enumeration) {
        EnumerationDto enumerationDto = new EnumerationDto();
        enumerationDto.id = enumeration.getEnumId();
        enumerationDto.enumCode = enumeration.getEnumCode();
        enumerationDto.enumType = enumeration.getEnumType();
        enumerationDto.description = enumeration.description;
        return enumerationDto;

    }

    private static String getTheBase64EncodedImage(DataResource profilePicture) throws SQLException, IOException {
        InputStream patientImage = profilePicture.getResource().getBinaryStream();
        byte[] images = IOUtils.toByteArray(patientImage);
        StringBuilder base64EncodedImage = new StringBuilder();
        base64EncodedImage.append("data:image/png;base64,");
        base64EncodedImage.append(Base64.encodeBytes(images));
        return base64EncodedImage.toString();
    }
}
