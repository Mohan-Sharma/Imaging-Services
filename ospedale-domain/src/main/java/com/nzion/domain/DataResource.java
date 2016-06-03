package com.nzion.domain;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

@Entity
@Table(name = "DATA_RESOURCE")
@org.hibernate.annotations.Entity(dynamicInsert = false, dynamicUpdate = false)
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class DataResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private long resourceId;

	private Blob resource;

	@Column(name = "BINARY_DATA")
	@Lob
	public Blob getResource() {
	return resource;
	}

	public void setResource(Blob resource) {
	this.resource = resource;
	}

	@Id
	@GeneratedValue
	@Column(name = "RESOURCE_ID")
	public long getResourceId() {
	return resourceId;
	}

	public void setResourceId(long resourceId) {
	this.resourceId = resourceId;
	}

}
