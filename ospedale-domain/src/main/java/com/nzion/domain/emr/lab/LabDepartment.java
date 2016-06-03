package com.nzion.domain.emr.lab;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table
public class LabDepartment extends IdGeneratingBaseEntity{
	
	private String labDepartmentCode;
	
	private String department;

	public String getLabDepartmentCode() {
		return labDepartmentCode;
	}

	public void setLabDepartmentCode(String labDepartmentCode) {
		this.labDepartmentCode = labDepartmentCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	

}
