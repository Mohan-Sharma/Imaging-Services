package com.nzion.external;

import com.nzion.domain.emr.soap.PatientLabOrder;

/**
 * Created by Nthdimenzion on 4/7/2015.
 */
public class LabLineItem {
    private String testName;
    private String details;
    private boolean homeService;
    private String testCode;
    private String typeOfTest;
    
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public boolean isHomeService() {
		return homeService;
	}
	public void setHomeService(boolean homeService) {
		this.homeService = homeService;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getTypeOfTest() {
		return typeOfTest;
	}
	public void setTypeOfTest(String typeOfTest) {
		this.typeOfTest = typeOfTest;
	}
    
    
}
