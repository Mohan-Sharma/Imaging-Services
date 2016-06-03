package com.nzion.domain.emr.lab;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinColumn;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@AccountNumberField("investigationCode")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "investigationCode"}))
public class Investigation extends IdGeneratingBaseEntity {

	private String investigationName;
	
	private String investigationCode;
	
	private String investigationPneumonic;
	
	private String unit;
	
	private String resultType;
	
	
	public String getInvestigationName() {
		return investigationName;
	}

	public void setInvestigationName(String investigationName) {
		this.investigationName = investigationName;
	}

	public String getInvestigationCode() {
		return investigationCode;
	}

	public void setInvestigationCode(String investigationCode) {
		this.investigationCode = investigationCode;
	}

	public String getInvestigationPneumonic() {
		return investigationPneumonic;
	}

	public void setInvestigationPneumonic(String investigationPneumonic) {
		this.investigationPneumonic = investigationPneumonic;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

}
