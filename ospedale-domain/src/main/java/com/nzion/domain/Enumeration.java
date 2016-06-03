package com.nzion.domain;

import com.nzion.domain.base.BaseEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ENUMERATION", uniqueConstraints = { @UniqueConstraint(columnNames = { "ENUM_CODE"}) })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
@Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)")
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = false)
public class Enumeration extends BaseEntity {

	// POSTAL_ADDRESS,EMERGENCY_ADDRESS,

	private static final long serialVersionUID = 1L;
	public String description;
	private String enumCode;
	private String enumType;
	private Long id;
	
	private String codeSystemName;
	
	private String codeSystem;
	@Id
	@Column(name="ENUM_ID")
	@GeneratedValue
	public Long getEnumId() {
	return id;
	}

	public void setEnumId(Long id) {
	this.id = id;
	}

	public Enumeration() {
	}

	public Enumeration(String enumType, String enumCode, String description) {
	this.description = description;
	this.enumCode = enumCode;
	this.enumType = enumType;
	}

	@NaturalId
	@Column(name = "ENUM_CODE")
	public String getEnumCode() {
	return enumCode;
	}

	public void setEnumCode(String enumCode) {
	this.enumCode = enumCode;
	}

	@NaturalId
	@Column(name = "ENUM_TYPE")
	public String getEnumType() {
	return enumType;
	}

	public void setEnumType(String enumType) {
	this.enumType = enumType;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	public boolean sameAs(String s) {
	return enumCode.equals(s);
	}

	public String getCodeSystemName() {
	return codeSystemName;
	}

	public void setCodeSystemName(String codeSystemName) {
	this.codeSystemName = codeSystemName;
	}

	public String getCodeSystem() {
	return codeSystem;
	}

	public void setCodeSystem(String codeSystem) {
	this.codeSystem = codeSystem;
	}

	
	@Override
	public String toString() {
	return description;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((enumCode == null) ? 0 : enumCode.hashCode());
	result = prime * result + ((enumType == null) ? 0 : enumType.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (obj == null) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	Enumeration other = (Enumeration) obj;
	if (enumCode == null) {
		if (other.enumCode != null) {
			return false;
		}
	} else
		if (!enumCode.equalsIgnoreCase(other.enumCode)) {
			return false;
		}
	if (enumType == null) {
		if (other.enumType != null) {
			return false;
		}
	} else
		if (!enumType.equals(other.enumType)) {
			return false;
		}
	return true;
	}
}