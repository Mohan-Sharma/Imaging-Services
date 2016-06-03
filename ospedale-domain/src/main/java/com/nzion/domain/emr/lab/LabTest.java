package com.nzion.domain.emr.lab;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.AccessType;

import com.nzion.domain.Person;
import com.nzion.domain.annot.AccountNumberField;
import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@AccountNumberField("testCode")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "testCode"}))
public class LabTest extends IdGeneratingBaseEntity implements Serializable{

	@Column(length = 20)
	private String testCode;
	
	private String testDescription;
	
	private String testPneumonic;
	
	private Laboratories laboratory;
	
	private String department;
	
	private String specimen;
	
	private String container;
	
	private String method;

	private boolean prescriptionRequired;

	
	private Set<Investigation> investigations;

	
	@ManyToMany(targetEntity = Investigation.class,fetch = FetchType.EAGER)
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
}
