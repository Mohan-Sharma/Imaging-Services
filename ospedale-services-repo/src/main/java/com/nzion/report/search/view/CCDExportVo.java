package com.nzion.report.search.view;

public class CCDExportVo {
	
	private String mailTo;
	
	private String mailsubject;
	
	private String mailContent;
	
	private String fileName;
	
	private Boolean encryptFile = Boolean.TRUE;
	
	private Boolean passwordRequire = Boolean.FALSE;
	
	private Boolean integrityAlgorithmRequire = Boolean.TRUE;
	
	private String integrityAlgorithm = "MD5";
	
	private String password;

	public String getMailTo() {
	return mailTo;
	}

	public void setMailTo(String mailTo) {
	this.mailTo = mailTo;
	}

	public String getMailsubject() {
	return mailsubject;
	}

	public void setMailsubject(String mailsubject) {
	this.mailsubject = mailsubject;
	}

	public String getMailContent() {
	return mailContent;
	}

	public void setMailContent(String mailContent) {
	this.mailContent = mailContent;
	}

	public String getFileName() {
	return fileName;
	}

	public void setFileName(String fileName) {
	this.fileName = fileName;
	}

	public Boolean getEncryptFile() {
	return encryptFile;
	}

	public void setEncryptFile(Boolean encryptFile) {
	this.encryptFile = encryptFile;
	}

	public Boolean getPasswordRequire() {
	return passwordRequire;
	}

	public void setPasswordRequire(Boolean passwordRequire) {
	this.passwordRequire = passwordRequire;
	}

	public Boolean getIntegrityAlgorithmRequire() {
	return integrityAlgorithmRequire;
	}

	public void setIntegrityAlgorithmRequire(Boolean integrityAlgorithmRequire) {
	this.integrityAlgorithmRequire = integrityAlgorithmRequire;
	}

	public String getIntegrityAlgorithm() {
	return integrityAlgorithm;
	}

	public void setIntegrityAlgorithm(String integrityAlgorithm) {
	this.integrityAlgorithm = integrityAlgorithm;
	}

	public String getPassword() {
	return password;
	}

	public void setPassword(String password) {
	this.password = password;
	}
}
