package com.nzion.domain.emr.lab;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.nzion.domain.Enumeration;
import com.nzion.domain.File;
import com.nzion.domain.Patient;
import com.nzion.domain.base.BaseEntity;

/**
 * 
 * There might be scenarios where in the results are directly entered to Tests
 * that are done outside the HOSPITAL. IN such cases the OBX is directly
 * created. without OBR.
 * 
 */
@Entity
public class OBXSegment extends BaseEntity implements Comparable<OBXSegment> {

	/* Test Name */
	private String obxName;
	private String observationValue;
	private String units;
	private String referenceRange;
	private String abnormalFlag;
	private Enumeration resultStatus;
	private OBRSegment obrSegment;
	//private LabTest labTest;
	private Investigation investigation;
	private Long id;
	private Patient patient;
	private boolean externalTest;
	private Date observationDateTime;
	private String testNotes;
	private String inPatientAdmNumber;

	private LabTestPanel labTestPanel;
	private LabCategory labCategory;

	private String freeText;

	private File file;

	@OneToOne
	@Cascade(CascadeType.ALL)
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFreeText() {
		return freeText;
	}

	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	public OBXSegment() {
	}

	public OBXSegment(String labTestName, String referernces) {
		this.obxName = labTestName;
		this.referenceRange = referernces;
	}

	/*public OBXSegment(LabTest labTest) {
		this(labTest.getTestName(), labTest.getReferenceRange());
		this.labTest = labTest;
		this.units = labTest.getUnit();
	}*/

	public OBXSegment(Investigation investigation) {
		this(investigation.getInvestigationName(), "");
		this.investigation = investigation;
		this.units = investigation.getUnit();
	}
	
	/*@OneToOne
	public LabTest getLabTest() {
		return labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}
*/
	@OneToOne
	public Investigation getInvestigation() {
		return investigation;
	}

	public void setInvestigation(Investigation investigation) {
		this.investigation = investigation;
	}

	
	
	@ManyToOne
	@JoinColumn(name = "RESULT_STATUS")
	public Enumeration getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(Enumeration resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getObxName() {
		return obxName;
	}

	public void setObxName(String obxName) {
		this.obxName = obxName;
	}

	public String getObservationValue() {
		return observationValue;
	}

	public void setObservationValue(String observationValue) {
		this.observationValue = observationValue;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	@Column(length = 512)
	public String getReferenceRange() {
		return referenceRange;
	}

	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}

	public String getAbnormalFlag() {
		return abnormalFlag;
	}

	public void setAbnormalFlag(String abnormalFlag) {
		this.abnormalFlag = abnormalFlag;
	}

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((investigation == null) ? 0 : investigation.getInvestigationCode().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OBXSegment other = (OBXSegment) obj;
		if (investigation == null) {
			if (other.investigation != null)
				return false;
		} else if (!investigation.getInvestigationCode().equals(other.investigation.getInvestigationCode()))
			return false;
		return true;
	}

	private static final long serialVersionUID = 1L;
/*
	@Override
	public int compareTo(OBXSegment o) {
		if (o.getObxName() == null || getObxName() == null)
			return 0;
		return o.getObxName().compareToIgnoreCase(getObxName());
	}*/
	
	@Override
	public int compareTo(OBXSegment other) {
	if(this == null || other == null){
		return -1;
	}
	if(this.getInvestigation() == null || other.getInvestigation() == null){
		return -1;
	}
	return this.getInvestigation().getInvestigationCode().hashCode()>other.getInvestigation().getInvestigationCode().hashCode()?1:-1;
	}

	@ManyToOne
	@JoinColumn(name = "OBR_ID")
	public OBRSegment getObrSegment() {
		return obrSegment;
	}

	public void setObrSegment(OBRSegment obrSegment) {
		this.obrSegment = obrSegment;
	}

	public boolean isExternalTest() {
		return externalTest;
	}

	public void setExternalTest(boolean externalTest) {
		this.externalTest = externalTest;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getObservationDateTime() {
		return observationDateTime;
	}

	public void setObservationDateTime(Date observationDateTime) {
		this.observationDateTime = observationDateTime;
	}

	@Column(length = 1024)
	public String getTestNotes() {
		return testNotes;
	}

	public void setTestNotes(String testNotes) {
		this.testNotes = testNotes;
	}


	@OneToOne(fetch = FetchType.LAZY)
	public LabTestPanel getLabTestPanel() {
		return labTestPanel;
	}

	public void setLabTestPanel(LabTestPanel labTestPanel) {
		this.labTestPanel = labTestPanel;
	}

	@OneToOne(fetch = FetchType.LAZY)
	public LabCategory getLabCategory() {
		return labCategory;
	}

	public void setLabCategory(LabCategory labCategory) {
		this.labCategory = labCategory;
	}

	public String getInPatientAdmNumber() {
		return inPatientAdmNumber;
	}

	public void setInPatientAdmNumber(String inPatientAdmNumber) {
		this.inPatientAdmNumber = inPatientAdmNumber;
	}
}
