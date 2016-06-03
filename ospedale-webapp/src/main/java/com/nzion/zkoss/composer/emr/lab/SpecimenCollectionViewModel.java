package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.UserLogin;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabRequisition.LabRequisitionStatus;
import com.nzion.domain.emr.lab.LabRequisitionItem;
import com.nzion.domain.emr.lab.LabSpecimenSource;
import com.nzion.domain.emr.lab.LabTest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.emr.lab.LabTestProfile;
import com.nzion.domain.emr.lab.LabTestResult;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.domain.emr.lab.SpecimenModel;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilDisplay;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 1/5/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver(DelegatingVariableResolver.class)
public class SpecimenCollectionViewModel {
    @WireVariable
    private LabService labService;
    @WireVariable
    private LabRequisition labRequisition;
    @WireVariable
    private CommonCrudService commonCrudService;
    private List<SpecimenModel> specimenModelList;
    private SpecimenModel specimenModel;

    private List<LabSpecimenSource> specimenSourceList;
    
    private List<LabTest> labTestList;
    
    private Set<LabRequisitionItem> labRequisitionItems;
    
    private List<LabTestResult> labTestResults;
    
    @Init
    public void init(@ExecutionArgParam("labRequisition") LabRequisition labRequisition) {
        labRequisition = commonCrudService.refreshEntity(labRequisition);
        this.labRequisition = labRequisition;
        specimenModelList = labRequisition.getSpecimenModelList();
        specimenSourceList = commonCrudService.getAll(LabSpecimenSource.class);
        specimenModel = new SpecimenModel();
        specimenModel.setLabRequisition(labRequisition);
        
        UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryNames = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryNames.add(laboratory.getLaboratory());
    	}
        
       labRequisitionItems  =  labRequisition.getLabRequisitionItems();
       for(LabRequisitionItem labRequisitionItem : labRequisitionItems){
        	List<LabTestResult> labTestResultList = labRequisitionItem.getLabTestResults();
        	for(LabTestResult labTestResult : labTestResultList ){
        		if(laboratoryNames.contains(labTestResult.getLaboratory())){
        			if(labTestResults == null) labTestResults=new ArrayList<LabTestResult>();
        			labTestResults.add(labTestResult);
        		}
        	}
         }
  }
    
    public Set<LabRequisitionItem> getLabRequisitionItems() {
		return labRequisitionItems;
	}

	public void setLabRequisitionItems(Set<LabRequisitionItem> labRequisitionItems) {
		this.labRequisitionItems = labRequisitionItems;
	}

	public List<LabTestResult> getLabTestResults() {
		return labTestResults;
	}

	public void setLabTestResults(List<LabTestResult> labTestResults) {
		this.labTestResults = labTestResults;
	}

	public List<LabTest> getLabTestList() {
		return labTestList;
	}

	public void setLabTestList(List<LabTest> labTestList) {
		this.labTestList = labTestList;
	}

	@Command("SaveSpecimenModel")
    @NotifyChange("specimenModelList")
    public void saveSpecimenModel() throws WrongValueException {
    	if(specimenModel.getSpecimenLabel()==null){
    		com.nzion.util.UtilMessagesAndPopups.showError("Specimen Label can't be empty");
    		return;
    	}
    	
    Set<LabTestResult> labTestResults = specimenModel.getLabTestResultSet();
    for(LabTestResult labTestResult: labTestResults){
    	    	labTestResult.setStatus(LabRequisitionStatus.SAMPLE_COLLECTED);
    	    	commonCrudService.save(labTestResult);
   }
   commonCrudService.save(specimenModel);
   labRequisition.setStatus(LabRequisition.LabRequisitionStatus.COLLECTION_DONE);
     
    commonCrudService.save(labRequisition);
    specimenModelList.add(specimenModel);
    specimenModel=new SpecimenModel();
    specimenModel.setLabRequisition(labRequisition);
    BindUtils.postGlobalCommand(null, null, "NewSpecimen", null);
    }

  
    @Command("RemoveSpecimenModel")
    @NotifyChange("specimenModelList")
    public void removeSpecimenModel(@BindingParam(value = "arg1")SpecimenModel specimenModel) {
    	try {
    		labRequisition.getSpecimenModelList().remove(specimenModel);
    		commonCrudService.delete(specimenModel);
           // commonCrudService.save(labRequisition);
    	} catch (Exception e) {
    	}
    }
       

    @Command("NewSpecimen")@GlobalCommand("NewSpecimen")
    @NotifyChange("specimenModel")
    public void newSpecimenModel() {
        specimenModel=new SpecimenModel();
        specimenModel.setLabRequisition(labRequisition);
    }


    public List<SpecimenModel> getSpecimenModelList() {
        return specimenModelList;
    }

    public void setSpecimenModelList(List<SpecimenModel> specimenModelList) {
        this.specimenModelList = specimenModelList;
    }

    public SpecimenModel getSpecimenModel() {
        return specimenModel;
    }

    public void setSpecimenModel(SpecimenModel specimenModel) {
        this.specimenModel = specimenModel;
    }

    public LabRequisition getLabRequisition() {
        return labRequisition;
    }

    public void setLabRequisition(LabRequisition labRequisition) {
        this.labRequisition = labRequisition;
    }

    public List<LabSpecimenSource> getSpecimenSourceList() {
        return specimenSourceList;
    }

    public void setSpecimenSourceList(List<LabSpecimenSource> specimenSourceList) {
        this.specimenSourceList = specimenSourceList;
    }
}
