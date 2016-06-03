package com.nzion.domain.pms;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Type;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.enums.FieldType;
import com.nzion.util.Constants;

@Entity
@Table(name = "CUSTOM_FORM_FIELD",uniqueConstraints={@UniqueConstraint(columnNames={"FIELD_LABEL"})})
public class CustomFormField extends IdGeneratingBaseEntity{

	private static final long serialVersionUID = 1L;
	private String fieldLabel;
	private FieldType fieldType;
	private Boolean isMandatory = Boolean.FALSE;
	
	@Column(name="FIELD_LABEL")
	public String getFieldLabel() {
	return fieldLabel;
	}
	public void setFieldLabel(String fieldLabel) {
	this.fieldLabel = fieldLabel;
	}
	
	@Column(name="IS_MANDATORY")
	@Type(type="yes_no")
	public Boolean getIsMandatory() {
	return isMandatory;
	}
	public void setIsMandatory(Boolean isMandatory) {
	this.isMandatory = isMandatory;
	}

	@Transient
	public Map<String,Object> buildViewGenerationMap(){
	Map<String,Object> viewDataMap = new HashMap<String, Object>();
	viewDataMap.put(Constants.FIELD_TYPE, getFieldType());
	viewDataMap.put(Constants.LABEL, this.getFieldLabel());
	viewDataMap.put(Constants.ISMANDATORY, this.getIsMandatory());
	viewDataMap.put(Constants.ID, this.getId());
	return viewDataMap;
	}
	@Column(name = "FIELD_TYPE")
	@Enumerated(EnumType.STRING)
	public FieldType getFieldType() {
	return fieldType;
	}
	public void setFieldType(FieldType fieldType) {
	this.fieldType = fieldType;
	}
	@Override
	public int hashCode() {
	return practiceIndependentHashCode();
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (!super.equals(obj)) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	CustomFormField other = (CustomFormField) obj;
	if (fieldLabel == null) {
		if (other.fieldLabel != null) {
			return false;
		}
	} else
		if (!fieldLabel.equals(other.fieldLabel)) {
			return false;
		}
	return true;
	}
}
