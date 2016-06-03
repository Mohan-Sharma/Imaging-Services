package com.nzion.domain.base;

import java.io.Serializable;

public class CompositePracticePK implements Serializable {

	private static final long serialVersionUID = -7120675076902804379L;
	
	private String id;
	
	private String praticeId;

	public String getId() {
	return id;
	}

	public String getPraticeId() {
	return praticeId;
	}

	public void setId(String id) {
	this.id = id;
	}

	public void setPraticeId(String praticeId) {
	this.praticeId = praticeId;
	}
}