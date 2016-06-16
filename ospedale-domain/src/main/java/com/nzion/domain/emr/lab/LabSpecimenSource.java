package com.nzion.domain.emr.lab;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
public class LabSpecimenSource extends IdGeneratingBaseEntity{

	private String code;
	
	private String description;

	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	private static final long serialVersionUID = 1L;

}
