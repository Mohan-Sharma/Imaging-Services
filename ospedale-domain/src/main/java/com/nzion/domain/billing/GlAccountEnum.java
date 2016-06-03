package com.nzion.domain.billing;

/**
 * @author: NthDimenzion
 * @since 1.0
 */
public enum GlAccountEnum {

    Procedure("Procedure"), LabCharges("Lab Charges"), Consultation("Consultation"), Registration("Registration"), Cash("Cash"), CreditCard("Credit Card"),

    WriteOff("Write Off"), Casualty("Casualty"), Pharmacy("Pharmacy"), Tax("Tax"), RoomCharges("Room Charges"), AdditionalRoomCharges("Additional Room Charges"), 
    ClinicalService("Clinical Service"), OutsidePatient("Outside_patient"),Radiology("Radiology");


    private String description;

    GlAccountEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
