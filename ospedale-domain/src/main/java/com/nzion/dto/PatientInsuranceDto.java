package com.nzion.dto;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: USER
 * Date: 4/10/15
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientInsuranceDto implements Serializable {

    private String insuranceProviderId;

    private String insurancePlanId;

    private String insuranceName;

    private String planName;

    private String patientPlanId;

    private String patientRegistrationNo;

    public  PatientInsuranceDto(){}

    public PatientInsuranceDto(String insuranceProviderId,String insurancePlanId, String insuranceName, String planName, String patientPlanId, String patientRegistrationNo){
        this.insuranceProviderId = insuranceProviderId;
        this.insurancePlanId = insurancePlanId;
        this.insuranceName = insuranceName;
        this.planName = planName;
        this.patientPlanId = patientPlanId;
        this.patientRegistrationNo = patientRegistrationNo;
    }

}
