package com.nzion.domain.emr.lab;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;

import com.nzion.domain.Person;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

@Entity
@AccountNumberField("testCode")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "testCode"}))
public class LabTest extends IdGeneratingBaseEntity implements Serializable{

	@Column(length = 20)
	private String testCode;
	
	private String testDescription;

	private String testDescriptionAr;
	
	private String testPneumonic;
	
	private Laboratories laboratory;
	
	private String department;
	
	private String specimen;
	
	private String container;
	
	private String method;

	private boolean prescriptionRequired;
	private String specialInstruction;
	private String specialInstructionAr;
	private String turnaroundTime;

	
	private Set<Investigation> investigations;
	private Integer displayOrder;
	private BigDecimal homeServiceAmount;
	private BigDecimal billableAmount;
	private String gender;

	
	@ManyToMany(targetEntity = Investigation.class,fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SELECT)
	@JoinTable(name = "lab_test_investigation", 
	joinColumns = {@JoinColumn(name = "TEST_CODE")},
	inverseJoinColumns = { @JoinColumn(name = "INVESTIGATION_CODE") })
	public Set<Investigation> getInvestigations() {
		return investigations;
	}

	
	public void setInvestigations(Set<Investigation> investigations) {
		this.investigations = investigations;
	}

	@OneToOne(targetEntity = Laboratories.class)
	@JoinColumn(name="LABORATORY_ID")
	public Laboratories getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(Laboratories laboratory) {
		this.laboratory = laboratory;
	}
	
	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public String getTestDescriptionAr() {
		return testDescriptionAr;
	}

	public void setTestDescriptionAr(String testDescriptionAr) {
		this.testDescriptionAr = testDescriptionAr;
	}

	public String getTestPneumonic() {
		return testPneumonic;
	}

	public void setTestPneumonic(String testPneumonic) {
		this.testPneumonic = testPneumonic;
	}

	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getSpecimen() {
		return specimen;
	}

	public void setSpecimen(String specimen) {
		this.specimen = specimen;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public boolean isPrescriptionRequired() {
		return prescriptionRequired;
	}

	public void setPrescriptionRequired(boolean prescriptionRequired) {
		this.prescriptionRequired = prescriptionRequired;
	}

	public String getSpecialInstruction() {
		return specialInstruction;
	}

	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	public String getSpecialInstructionAr() {
		return specialInstructionAr;
	}

	public void setSpecialInstructionAr(String specialInstructionAr) {
		this.specialInstructionAr = specialInstructionAr;
	}

	public String getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(String turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Transient
	public BigDecimal getHomeServiceAmount() {
		return homeServiceAmount;
	}

	public void setHomeServiceAmount(BigDecimal homeServiceAmount) {
		this.homeServiceAmount = homeServiceAmount;
	}

	@Transient
	public BigDecimal getBillableAmount() {
		return billableAmount;
	}

	public void setBillableAmount(BigDecimal billableAmount) {
		this.billableAmount = billableAmount;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender != null ? gender : "Both";
	}
}
