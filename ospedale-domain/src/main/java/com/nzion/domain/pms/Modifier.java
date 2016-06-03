package com.nzion.domain.pms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "Modifier" ,uniqueConstraints = { @UniqueConstraint(name="UNIQUE_PRACTICE_MODIFIER_CODE",columnNames = { "CODE"}) })
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class Modifier extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;
	private String description;
	private String code;
	private String comments;
	
	@Column(name="CODE")
	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}

	@Lob
	public String getComments() {
	return comments;
	}

	public void setComments(String comments) {
	this.comments = comments;
	}

	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}
}