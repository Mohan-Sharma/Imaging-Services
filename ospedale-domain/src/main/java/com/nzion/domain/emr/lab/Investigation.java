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

	private String referenceRange;

	private boolean attachment;

	private boolean freeText;

	private boolean numericalValue;

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

	public String getReferenceRange() {
		return referenceRange;
	}

	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}

	public boolean isAttachment() {
		return attachment;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	public boolean isFreeText() {
		return freeText;
	}

	public void setFreeText(boolean freeText) {
		this.freeText = freeText;
	}

	public boolean isNumericalValue() {
		return numericalValue;
	}

	public void setNumericalValue(boolean numericalValue) {
		this.numericalValue = numericalValue;
	}
}
