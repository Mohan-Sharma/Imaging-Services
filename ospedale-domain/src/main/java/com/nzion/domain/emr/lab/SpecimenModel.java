package com.nzion.domain.emr.lab;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class SpecimenModel extends IdGeneratingBaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String specimenLabel;
	private String collectionMethod;
	private String collectionBodyLocation;
	private String collectionVolume;
	private String collectionVolumeUnits;
	private String actionCode;
	private String specimenCondition;
    private Date specimenReceivedDate;
    private Date specimenCollectedDateTime;
    private LabSpecimenSource labSpecimenSource;
    private LabRequisition labRequisition;
   // private Set<LabTestPanel> labTestPanelSet;
    private Set<LabTest> labTestSet;
    
    private Set<LabTestResult>  labTestResultSet;
    
   // private LabTest labTest;

    public String getSpecimenLabel() {
	return specimenLabel;
	}

	public void setSpecimenLabel(String label) {
	this.specimenLabel = label;
	}

	public String getCollectionMethod() {
	return collectionMethod;
	}

	public void setCollectionMethod(String collectionMethod) {
	this.collectionMethod = collectionMethod;
	}

	public String getCollectionBodyLocation() {
	return collectionBodyLocation;
	}

	public void setCollectionBodyLocation(String collectionBodyLocation) {
	this.collectionBodyLocation = collectionBodyLocation;
	}

	public String getCollectionVolume() {
	return collectionVolume;
	}

	public void setCollectionVolume(String collectionVolume) {
	this.collectionVolume = collectionVolume;
	}

	public String getCollectionVolumeUnits() {
	return collectionVolumeUnits;
	}

	public void setCollectionVolumeUnits(String collectionVolumeUnits) {
	this.collectionVolumeUnits = collectionVolumeUnits;
	}

	public String getActionCode() {
	return actionCode;
	}

	public void setActionCode(String actionCode) {
	this.actionCode = actionCode;
	}

	public void setSpecimenCondition(String specimenCondition) {
	this.specimenCondition = specimenCondition;
	}

	public String getSpecimenCondition() {
	return specimenCondition;
	}

    @Temporal(TemporalType.TIMESTAMP)
    public Date getSpecimenReceivedDate() {
        return specimenReceivedDate;
    }

    public void setSpecimenReceivedDate(Date specimenReceivedDate) {
        this.specimenReceivedDate = specimenReceivedDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getSpecimenCollectedDateTime() {
        return specimenCollectedDateTime;
    }

    public void setSpecimenCollectedDateTime(Date specimenCollectedDateTime) {
        this.specimenCollectedDateTime = specimenCollectedDateTime;
    }

    @OneToOne
    public LabSpecimenSource getLabSpecimenSource() {
        return labSpecimenSource;
    }

    public void setLabSpecimenSource(LabSpecimenSource labSpecimenSource) {
        this.labSpecimenSource = labSpecimenSource;
    }

    @ManyToOne
    @JoinColumn(name = "LAB_REQUISIION_ID")
    public LabRequisition getLabRequisition() {
        return labRequisition;
    }

    public void setLabRequisition(LabRequisition labRequisition) {
        this.labRequisition = labRequisition;
    }

   /* @ManyToMany(targetEntity = LabTestPanel.class, fetch = FetchType.EAGER)
    public Set<LabTestPanel> getLabTestPanelSet() {
        return labTestPanelSet;
    }

    public void setLabTestPanelSet(Set<LabTestPanel> labTestPanelSet) {
        this.labTestPanelSet = labTestPanelSet;
    }*/
    
    @ManyToMany(targetEntity = LabTest.class, fetch = FetchType.EAGER)
    public Set<LabTest> getLabTestSet() {
        return labTestSet;
    }

    public void setLabTestSet(Set<LabTest> labTestSet) {
        this.labTestSet = labTestSet;
    }

    @ManyToMany(targetEntity = LabTestResult.class, fetch = FetchType.EAGER)
	public Set<LabTestResult> getLabTestResultSet() {
		return labTestResultSet;
	}

	public void setLabTestResultSet(Set<LabTestResult> labTestResultSet) {
		this.labTestResultSet = labTestResultSet;
	}
    
    

   /* @OneToOne
    @JoinColumn (name = "LAB_TEST_ID")
	public LabTest getLabTest() {
		return labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}
    */
    
}
