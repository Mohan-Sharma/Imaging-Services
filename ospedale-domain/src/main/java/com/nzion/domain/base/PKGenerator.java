package com.nzion.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="PKGENERATOR")
public class PKGenerator implements Serializable{

	private static final long serialVersionUID = 1L;
	private String entityClassName;
	private Long pkValue;
	
	@Id
	@Column(name="CLASSNAME")
	public String getEntityClassName() {
	return entityClassName;
	}
	public void setEntityClassName(String entityClassName) {
	this.entityClassName = entityClassName;
	}
	
	@Column(name="PK_VALUE",nullable=false)
	public Long getPkValue() {
	return pkValue;
	}
	public void setPkValue(Long pkValue) {
	this.pkValue = pkValue;
	}
	
	@Transient
	public Long getNewPK() {
	return ++pkValue;
	}
}
