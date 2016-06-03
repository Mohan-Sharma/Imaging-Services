package com.nzion.domain.emr.lab;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.emr.lab.LabRequisition.LabRequisitionStatus;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.util.UtilValidator;


@Entity
@Table
public class LabRequisitionItem extends IdGeneratingBaseEntity{

	private LabTestProfile labTestProfile;
	
	private LabTestPanel labTestPanel;
	
	private LabTest labTest;
	
	private List<LabTestResult>  labTestResults;
	
	private LabRequisitionStatus status;
	
	@Enumerated(EnumType.STRING)
	public LabRequisitionStatus getStatus() {
		return status;
	}

	public void setStatus(LabRequisitionStatus status) {
		this.status = status;
	}

	@OneToMany(targetEntity = LabTestResult.class, cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.EAGER)
	public List<LabTestResult> getLabTestResults() {
		return labTestResults;
	}

	public void setLabTestResults(List<LabTestResult> labTestResults) {
		this.labTestResults = labTestResults;
	}


    @OneToOne
    @JoinColumn (name = "LAB_TEST_PROFILE_ID")
	public LabTestProfile getLabTestProfile() {
		return labTestProfile;
	}

	public void setLabTestProfile(LabTestProfile labTestProfile) {
		this.labTestProfile = labTestProfile;
	}


    @OneToOne
    @JoinColumn (name = "LAB_TEST_PANEL_ID")
	public LabTestPanel getLabTestPanel() {
		return labTestPanel;
	}

	public void setLabTestPanel(LabTestPanel labTestPanel) {
		this.labTestPanel = labTestPanel;
	}


    @OneToOne
    @JoinColumn (name = "LAB_TEST_ID")
	public LabTest getLabTest() {
		return labTest;
	}

	public void setLabTest(LabTest labTest) {
		this.labTest = labTest;
	}
	
	
    public static LabRequisitionItem createLabRequisitionItem(PatientLabOrder patientLabOrder) {
        
    	LabRequisitionItem labRequisitionItem = new LabRequisitionItem();
              
        List<LabTestResult>  labTestResults = new ArrayList<LabTestResult>();
        if(patientLabOrder.getLabTest() != null) {
        	labRequisitionItem.setLabTest(patientLabOrder.getLabTest());
        	labTestResults.add(createLabTestResult(patientLabOrder.getLabTest()));
        	}
        else if (patientLabOrder.getLabTestPanel() != null){
        	labRequisitionItem.setLabTestPanel(patientLabOrder.getLabTestPanel());
        	for(LabTest labTest: patientLabOrder.getLabTestPanel().getTests()){
        		labTestResults.add(createLabTestResult(labTest));
        	}
        } else{
        	labRequisitionItem.setLabTestProfile(patientLabOrder.getLabTestProfile());
        	for(LabTest labTest: patientLabOrder.getLabTestProfile().getTests()){
        		labTestResults.add(createLabTestResult(labTest));
        	}
        }
        labRequisitionItem.setStatus(LabRequisitionStatus.NEW);
        labRequisitionItem.setLabTestResults(labTestResults);
        return labRequisitionItem;
    }
    
    public static LabTestResult createLabTestResult(LabTest labTest){
    	LabTestResult labTestResult = new LabTestResult();
    	labTestResult.setTestName(labTest.getTestDescription());
    	labTestResult.setStatus(LabRequisitionStatus.NEW);
    	labTestResult.setLaboratory(labTest.getLaboratory().getLaboratory());
    	labTestResult.setLabTest(labTest);
    	//labTestResult.setLabTestResultItems(createLabTestResultItem(labTest));
    	//labTestResult.addLabTestResultItem(labTestResultItem)
    	createLabTestResultItem(labTest,labTestResult);
    	return labTestResult;
    }
    
    public static void createLabTestResultItem(LabTest labTest,LabTestResult labTestResult){
    	 
    	//Set<LabTestResultItem> labTestResultItems = new HashSet<LabTestResultItem>();
    	Set<Investigation> investigations = labTest.getInvestigations();
    	for(Investigation investigation : investigations){
    		LabTestResultItem labTestResultItem = new LabTestResultItem();
    		labTestResultItem.setInvestigationName(investigation.getInvestigationName());
    		labTestResultItem.setStatus(LabRequisitionStatus.NEW);
    		//labTestResultItems.add(labTestResultItem);
    		labTestResult.addLabTestResultItem(labTestResultItem);
    	}
    	//return labTestResultItems;
    	
    }
 }
