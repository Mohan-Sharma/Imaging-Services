package com.nzion.view;

import java.sql.Timestamp;

import com.nzion.util.UtilDisplay;

public class AuditLog {

	private String label;
	private String newValue;
	private String oldValue;
	private Timestamp txnTimestamp;
	private String userId;

	public AuditLog() {
	}

	public AuditLog(String label, String oldValue, String newValue,
			Timestamp txnTimestamp, String userId) {
	super();
	this.label = label;
	this.newValue = newValue;
	this.oldValue = oldValue;
	this.userId=userId;
	this.txnTimestamp=txnTimestamp;
	}

	public String getLabel() {
	return label;
	}

	public void setLabel(String label) {
	this.label =  UtilDisplay.camelcaseToUiString(label);
	}

	public String getNewValue() {
	return newValue;
	}

	public void setNewValue(String newValue) {
	this.newValue = newValue;
	}

	public String getOldValue() {
	return oldValue;
	}

	public void setOldValue(String oldValue) {
	this.oldValue = oldValue;
	}

	public Timestamp getTxnTimestamp() {
	return txnTimestamp;
	}

	public void setTxnTimestamp(Timestamp txnTimestamp) {
	this.txnTimestamp = txnTimestamp;
	}

	public String getUserId() {
	return userId;
	}

	public void setUserId(String userId) {
	this.userId = userId;
	}

}
