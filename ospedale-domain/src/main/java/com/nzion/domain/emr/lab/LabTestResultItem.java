package com.nzion.domain.emr.lab;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ForeignKey;

import com.nzion.domain.Enumeration;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.domain.emr.lab.LabRequisition.LabRequisitionStatus;

@Entity
@Table
public class LabTestResultItem extends IdGeneratingBaseEntity {

    private String labTestName;

    private String uom;

    private String references;

    private String observationValue;

    private Enumeration resultStatus;

    private String testNote;

    private LabTestResult labTestResult;

    private LabTest labTest;
    
    private String investigationName;
    
    private LabRequisitionStatus status;
    
    
    @Enumerated(EnumType.STRING)
    public LabRequisitionStatus getStatus() {
		return status;
	}

	public void setStatus(LabRequisitionStatus status) {
		this.status = status;
	}

	public String getInvestigationName() {
		return investigationName;
	}

	public void setInvestigationName(String investigationName) {
		this.investigationName = investigationName;
	}

	public LabTestResultItem() {
    }

    public LabTestResultItem(String labTestName, String uom, String referernces) {
        this.labTestName = labTestName;
        this.uom = uom;
        this.references = referernces;
    }

   /* public LabTestResultItem(LabTest labTest) {
        this(labTest.getTestName(), labTest.getUnit(), labTest
                .getReferenceRange());
        this.labTest = labTest;
    }
*/
    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "LAB_TEST_RESULT_ID")
    @ForeignKey(name = "FK_LAB_TEST_RESULT")
    public LabTestResult getLabTestResult() {
        return labTestResult;
    }

    public void setLabTestResult(LabTestResult labTestResult) {
        this.labTestResult = labTestResult;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public void setLabTestName(String labTestName) {
        this.labTestName = labTestName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Column(length = 512, name = "REFERENCE_VALUES")
    public String getReferences() {
        return references;
    }

    public void setReferences(String references) {
        this.references = references;
    }

    public String getObservationValue() {
        return observationValue;
    }

    public void setObservationValue(String observationValue) {
        this.observationValue = observationValue;
    }

    @ManyToOne
    @JoinColumn(name = "RESULT_STATUS")
    public Enumeration getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(Enumeration resultStatus) {
        this.resultStatus = resultStatus;
    }

    private static final long serialVersionUID = 1L;

    @OneToOne
    public LabTest getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTest labTest) {
        this.labTest = labTest;
    }

}
