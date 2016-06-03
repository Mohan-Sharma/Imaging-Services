package com.nzion.domain.pms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Practice;
import com.nzion.domain.base.BaseEntity;
import com.nzion.enums.PMSEnumerationType;

@Entity
@Table(name = "PMS_ENUMERATION")
@Filters( {  @Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@NamedQueries(value = { @NamedQuery(name = "LIST_OF_PMS_ENUMS", query = "from PMSEnumeration WHERE enumType =:enumType") })
public class PMSEnumeration extends BaseEntity  {

	private static final long serialVersionUID = -3438959825386359315L;
	private Long enumId;
	private PMSEnumerationType enumType;

	private String description;
	private String enumCode;

	public String getDescription() {
	return description;
	}

	@Column
	public String getEnumCode() {
	return enumCode;
	}

	@Id
	@Column(name = "ENUM_ID")
	@GeneratedValue
	public Long getEnumId() {
	return enumId;
	}

	@Enumerated(EnumType.STRING)
	public PMSEnumerationType getEnumType() {
	return enumType;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	public void setEnumCode(String enumCode) {
	if (enumCode != null) {
		this.enumCode = enumCode.toUpperCase();
	} else
		this.enumCode = enumCode;
	}

	public void setEnumId(Long enumId) {
	this.enumId = enumId;
	}

	public void setEnumType(PMSEnumerationType enumType) {
	this.enumType = enumType;
	}
}
