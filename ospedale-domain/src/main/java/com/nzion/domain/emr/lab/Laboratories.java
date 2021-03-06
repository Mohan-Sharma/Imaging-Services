package com.nzion.domain.emr.lab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Laboratories implements Serializable {

	@Id
	@Column(name="LABORATORY_CODE")
	private String laboratoryCode;
	
	private String laboratory;
	
	private String department;
	
	private String description;

	public String getLaboratoryCode() {
		return laboratoryCode;
	}

	public void setLaboratoryCode(String laboratoryCode) {
		this.laboratoryCode = laboratoryCode;
	}

	public String getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(String laboratory) {
		this.laboratory = laboratory;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean  equals (Object object) {
	    boolean result = false;
	    if (object == null || object.getClass() != getClass()) {
	        result = false;
	    } else {
	        Laboratories laboratories = (Laboratories) object;
	        if (this.laboratoryCode.equals(laboratories.getLaboratoryCode())){
	            result = true;
	        }
	    }
	    return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.getLaboratoryCode().hashCode());
		return result;
	}

}
