package com.nzion.report.search.view;

public class LabResultSearchVo {

	private String testName;

	private String quantifier = "AND";

	private Integer resultValue = 0;

	private String operator = "Greater";

	public String getOperator() {
	return operator;
	}

	public void setOperator(String operator) {
	this.operator = operator;
	}

	public String getTestName() {
	return testName;
	}

	public void setTestName(String testName) {
	this.testName = testName;
	}

	public String getQuantifier() {
	return quantifier;
	}

	public void setQuantifier(String quantifier) {
	this.quantifier = quantifier;
	}

	public Integer getResultValue() {
	return resultValue;
	}

	public void setResultValue(Integer resultValue) {
	this.resultValue = resultValue;
	}

}
