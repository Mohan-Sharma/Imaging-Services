package com.nzion.domain.emr;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.enums.HANDOUT;

@Entity
@Table(name = "VITAL_SIGN", uniqueConstraints = { @UniqueConstraint(name="UNIQUE_PRACTICE_VITAL_SIGN",columnNames = { "NAME"}) })
@Filters( {
		@Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL,region="com.nzion.domain")
public class VitalSign extends IdGeneratingBaseEntity {
	private String name;
	private UnitOfMeasurement unitOfMeasurement;
	private Integer sortOrder=100;
	private HANDOUT handout;
	private Boolean derived; 
	
	
	private Set<VitalSignCondition> conditions = new HashSet<VitalSignCondition>();
	
	public Boolean getDerived() {
	return derived;
	}

	public void setDerived(Boolean derived) {
	this.derived = derived;
	}

	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="VITAL_SIGN_ID")
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	public Set<VitalSignCondition> getConditions() {
	if(conditions == null)
		conditions = new HashSet<VitalSignCondition>();
	return conditions;
	}

	public void setConditions(Set<VitalSignCondition> conditions) {
	this.conditions = conditions;
	}

	@Column(name = "NAME")
	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}
	
	@OneToOne
	@JoinColumn(name="UNIT_OF_MEASUREMENT")
	public UnitOfMeasurement getUnitOfMeasurement() {
	return unitOfMeasurement;
	}

	public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
	this.unitOfMeasurement = unitOfMeasurement;
	}

	@Enumerated(EnumType.STRING)
	@Column
	public HANDOUT getHandout() {
	return handout;
	}

	public void setHandout(HANDOUT handout) {
	this.handout = handout;
	}
	
	public static final String BMI = "bmi";

	public static final String HEIGHT = "height";

	public static final String WETGHT = "weight";
	
	public static final String BP_SYSTOLIC = "Systolic";
	
	public static final String BP_DIASTOLIC = "Diastolic";

	private static final long serialVersionUID = 1l;

	@Column
	public Integer getSortOrder() {
	return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
	this.sortOrder = sortOrder;
	}
}