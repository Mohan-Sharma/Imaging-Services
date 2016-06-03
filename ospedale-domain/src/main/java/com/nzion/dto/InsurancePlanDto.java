package com.nzion.dto;

/**
 * Created with IntelliJ IDEA.
 * User: USER
 * Date: 4/10/15
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsurancePlanDto {

    private String planCode;

    private String planName;

    private String insuranceCode;

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getInsuranceCode() {
        return insuranceCode;
    }

    public void setInsuranceCode(String insuranceCode) {
        this.insuranceCode = insuranceCode;
    }

}
