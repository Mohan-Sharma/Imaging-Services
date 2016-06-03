package com.nzion.domain.emr;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.Practice;
import com.nzion.domain.base.IdGeneratingBaseEntity;


@MappedSuperclass
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class MasterEntity extends IdGeneratingBaseEntity  {

	private static final long serialVersionUID = 1L;

	private String code;
	private String description;


	@Column(name = "CODE")
	public String getCode() {
	return code;
	}

	public void setCode(String code) {
	this.code = code;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	@Override
	public String toString() {
	return description;
	}
}
