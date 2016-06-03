package com.nzion.domain.emr.lab;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.pms.Modifier;

@Entity
@Table
public class LabTestCpt extends IdGeneratingBaseEntity {


	private Integer unit;

	private Modifier modifier1;

	private Modifier modifier2;

	private Modifier modifier3;

	private Modifier modifier4;

	private String derivedFrom;

	public LabTestCpt() {
	}

	
	
	public Integer getUnit() {
	return unit;
	}

	public void setUnit(Integer unit) {
	this.unit = unit;
	}

	@OneToOne
	@JoinColumn(name = "MODIFIER1_ID")
	public Modifier getModifier1() {
	return modifier1;
	}

	public void setModifier1(Modifier modifier1) {
	this.modifier1 = modifier1;
	}

	@OneToOne
	@JoinColumn(name = "MODIFIER2_ID")
	public Modifier getModifier2() {
	return modifier2;
	}

	public void setModifier2(Modifier modifier2) {
	this.modifier2 = modifier2;
	}

	@OneToOne
	@JoinColumn(name = "MODIFIER3_ID")
	public Modifier getModifier3() {
	return modifier3;
	}

	public void setModifier3(Modifier modifier3) {
	this.modifier3 = modifier3;
	}

	@OneToOne
	@JoinColumn(name = "MODIFIER4_ID")
	public Modifier getModifier4() {
	return modifier4;
	}

	public void setModifier4(Modifier modifier4) {
	this.modifier4 = modifier4;
	}

	@Transient
	private transient Integer hash = null;

	/*@Override
	public int hashCode() {
	if (hash != null) return hash;
	if (this.getCpt() == null) {
		hash = 0;
		return hash;
	}
	if (this.getCpt().getId() == null) {
		hash = 0;
		return hash;
	}
	return this.getCpt().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
	if (this == null || obj == null) return false;
	if (this != obj) return false;
	LabTestCpt otherObj = (LabTestCpt) obj;
	if (this.getCpt() == null || otherObj.getCpt() == null) return false;
	if (this.getCpt().getId().equalsIgnoreCase(otherObj.getCpt().getId())) return true;
	return false;
	}
*/
	public String getDerivedFrom() {
	return derivedFrom;
	}

	public void setDerivedFrom(String derivedFrom) {
	this.derivedFrom = derivedFrom;
	}

	private static final long serialVersionUID = 1L;

}
