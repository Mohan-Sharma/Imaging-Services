package com.nzion.domain;

import java.math.BigDecimal;

import javax.persistence.AssociationOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.annot.SystemAuditable;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.soap.PatientLabOrder.STATUS;
import com.nzion.util.UtilReflection;
import java.math.*;

@Entity
@Table(name = "SLOT_TYPE",uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "CHILD_TYPE")
@Filters( { @Filter(name = "EnabledFilter",condition="(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@SystemAuditable
public class SlotType extends IdGeneratingBaseEntity implements Comparable<SlotType> {

	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private CHARGETYPE  chargeType;
	private BigDecimal defaultPrice=BigDecimal.ZERO;


@Enumerated(EnumType.STRING)
	public CHARGETYPE getChargeType() {
		return chargeType;
	}

	public void setChargeType(CHARGETYPE chargeType) {
		this.chargeType = chargeType;
	}

	@Column(name = "NAME",nullable=false)
	public String getName() {
	return name;
	}

	public void setName(String name) {
	this.name = name;
	}

	
	
	public BigDecimal getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(BigDecimal defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	@Override
	public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	return UtilReflection.areEqual(this, obj, new String[] {"name"});
	}

	public boolean hasSameSlotTypeName(SlotType slotType) {
		return slotType==null? false : this.name.equals(slotType.getName());
	}

	public static enum CHARGETYPE {
		INPATIENT("InPatient"), OUTPATIENT("OutPatient");

		private String description;

			public String getDescription() {
				return description;
			}

			public void setDescription(String description) {
				this.description = description;
			}

			CHARGETYPE(String description) {
				this.description = description;
			}
		}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(SlotType s) {
		return this.name.compareToIgnoreCase(s.name);
	}

}