package com.nzion.domain.base;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Filter;

import com.nzion.util.UtilReflection;

/**
 * @author Sandeep Prusty
 * Aug 3, 2011
 */

@Entity

public class FieldRestriction extends IdGeneratingBaseEntity{

	private String field;

	private String value;

	private String entityName;

	private String displayName;
	
	private int sortOrder;
	
	private RESTRICTION_TYPE restrictionType;
	
	@Enumerated(EnumType.STRING)
	public RESTRICTION_TYPE getRestrictionType() {
	return restrictionType;
	}

	public void setRestrictionType(RESTRICTION_TYPE restrictionType) {
	this.restrictionType = restrictionType;
	}

	public int getSortOrder() {
	return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
	this.sortOrder = sortOrder;
	}

	public String getDisplayName() {
	return displayName;
	}

	public void setDisplayName(String displayName) {
	this.displayName = displayName;
	}

	public String getField() {
	return field;
	}

	public void setField(String field) {
	this.field = field;
	}

	public String getValue() {
	return value;
	}

	public void setValue(String value) {
	this.value = value;
	}

	public String getEntityName() {
	return entityName;
	}

	public void setEntityName(String entityName) {
	this.entityName = entityName;
	}
	
	public void populate(Object bean){
	UtilReflection.setNestedFieldValue(bean, field, value);
	}
	
	public static enum RESTRICTION_TYPE {
		DEFAULT_VALUE, MANDATORY;
	}

	private static final long serialVersionUID = 1L;
}