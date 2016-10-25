package com.nzion.domain.emr.lab;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.*;

import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.Fetch;

@Entity
@AccountNumberField("panelCode")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "panelCode"}))
public class LabTestPanel extends IdGeneratingBaseEntity implements Serializable{
	
	@Column(length = 20)
	private String panelCode;
	
	private String panelDescription;
	
	private String panelPneumonic;
	
	private String department;

	private boolean prescriptionRequired;
	private String specialInstruction;
	private String specialInstructionAr;
	private String turnaroundTime;
	private BigDecimal homeServiceAmount;
	private BigDecimal billableAmount;
	private Integer displayOrder;
	private String gender;

	private Set<LabTest> tests;

	public String getPanelCode() {
		return panelCode;
	}

	public void setPanelCode(String panelCode) {
		this.panelCode = panelCode;
	}

	public String getPanelDescription() {
		return panelDescription;
	}

	public void setPanelDescription(String panelDescription) {
		this.panelDescription = panelDescription;
	}

	public String getPanelPneumonic() {
		return panelPneumonic;
	}

	public void setPanelPneumonic(String panelPneumonic) {
		this.panelPneumonic = panelPneumonic;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@ManyToMany(targetEntity = LabTest.class,fetch = FetchType.EAGER)
	@Fetch(org.hibernate.annotations.FetchMode.SELECT)
	@JoinTable(name = "lab_test_panel_lab_test",
			joinColumns = {@JoinColumn(name = "PANEL_CODE")},
			inverseJoinColumns = { @JoinColumn(name = "TEST_CODE") })
	public Set<LabTest> getTests() {
		return tests;
	}

	public void setTests(Set<LabTest> tests) {
		this.tests = tests;
	}

	public boolean isPrescriptionRequired() {
		return prescriptionRequired;
	}

	public void setPrescriptionRequired(boolean prescriptionRequired) {
		this.prescriptionRequired = prescriptionRequired;
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

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
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
		this.turnaroundTime = turnaroundTime != null ? turnaroundTime.equals("") ? null : turnaroundTime : turnaroundTime;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender != null ? gender : "Both";
	}
}
