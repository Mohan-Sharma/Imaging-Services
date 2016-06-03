package com.nzion.domain.emr;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

@Entity
@Table(name = "UNIT_OF_MEASUREMENT", uniqueConstraints = { @UniqueConstraint(columnNames = { "CODE"}) })
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
public class UnitOfMeasurement extends MasterEntity {
	private static final long serialVersionUID = -8693018949811522332L;
	private String uomType;
	
	public String getUomType() {
	return uomType;
	}
	public void setUomType(String uomType) {
	this.uomType = uomType;
	}
	
}