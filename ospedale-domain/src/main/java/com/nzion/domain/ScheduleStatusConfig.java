package com.nzion.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.Schedule.STATUS;
import com.nzion.domain.base.IdGeneratingBaseEntity;

/**
 * @author Sandeep Prusty
 * Mar 23, 2011
 */

@Entity
@Filters(@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)"))
public class ScheduleStatusConfig extends IdGeneratingBaseEntity{
	
	private static final long serialVersionUID = 1L;

	private STATUS status;
	
	private String color = "#FFFFFF".intern();
	


	@Enumerated(EnumType.STRING)
	public STATUS getStatus() {
	return status;
	}

	public void setStatus(STATUS status) {
	this.status = status;
	}

	public String getColor() {
	return color;
	}

	public void setColor(String color) {
	this.color = color;
	}

}
