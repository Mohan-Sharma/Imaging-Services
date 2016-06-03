package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.Patient;
import com.nzion.domain.UserLogin;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 1/5/13
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class LabRequisitionViewModel {

    @WireVariable
    private LabService labService;
    @WireVariable
    private CommonCrudService commonCrudService;

    private List<LabRequisition> requisitionList;
    private List<LabRequisition> inPatientRequisitionList;
    
    private List<LabRequisition> completedRequisitionList;
    private List<LabRequisition> completedInPatientRequisitionList;
    
    private Patient patient;
    
    private Boolean inpatientFetch=false;
    
    private Boolean inpatientRequestFetch=false;

    private Date currentDate = new Date();

    public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<LabRequisition> getCompletedInPatientRequisitionList() {
		return completedInPatientRequisitionList;
	}

	public void setCompletedInPatientRequisitionList(
			List<LabRequisition> completedInPatientRequisitionList) {
		this.completedInPatientRequisitionList = completedInPatientRequisitionList;
	}

	public List<LabRequisition> getCompletedRequisitionList() {
		return completedRequisitionList;
	}

	public void setCompletedRequisitionList(
			List<LabRequisition> completedRequisitionList) {
		this.completedRequisitionList = completedRequisitionList;
	}

	@Init
    public void init(){

    	UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryIds = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryIds.add(laboratory.getLaboratoryCode());
    	}
    	List<LabRequisition> labRequisitionList;
    	labRequisitionList = labService.getLabRequisitionForProcessing(false, UtilDateTime.getDayStart(currentDate),
        		UtilDateTime.getDayEnd(currentDate),null,null,laboratories);
       	requisitionList = new ArrayList<LabRequisition>();
        for(LabRequisition labRequisition: labRequisitionList){
        	Set<Laboratories> laboratories2 = labRequisition.getLabOrderRequest().getLaboratories();
        	for(Laboratories laboratories3 : laboratories2){
        		if(laboratoryIds.contains(laboratories3.getLaboratoryCode())){
        			requisitionList.add(labRequisition);
        			break;
        		}
        	}
      }
  
      List<LabRequisition> completedLabRequisitionList = labService.getCompletedLabRequisition(false,UtilDateTime.getDayStart(currentDate),
    		  UtilDateTime.getDayEnd(currentDate),patient,null,null);
      
      completedRequisitionList = new ArrayList<LabRequisition>();
      for(LabRequisition labRequisition: completedLabRequisitionList){
      	Set<Laboratories> laboratories2 = labRequisition.getLabOrderRequest().getLaboratories();
      	for(Laboratories laboratories3 : laboratories2){
      		if(laboratoryIds.contains(laboratories3.getLaboratoryCode())){
      			completedRequisitionList.add(labRequisition);
      			break;
      		}
      	}
    }

    
    }

    @Command("INPATIENT_LAB")
    @NotifyChange("inPatientRequisitionList")
    public void fetchInPatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,@BindingParam("ipNumber")Textbox ipNumber){
    	inpatientRequestFetch=true;
    	
    	/*if(UtilValidator.isNotEmpty(ipNumber)){
    		if(fromDate!=null && thruDate!=null)
    			inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,fromDate.getValue(),thruDate.getValue(),patient,ipNumber.getValue());
        	else if(fromDate!=null)
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,fromDate.getValue(),null,patient,ipNumber.getValue());
        	else if(thruDate!=null)
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,null,thruDate.getValue(),patient,ipNumber.getValue());
        	else
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,UtilDateTime.getDayStart(currentDate),UtilDateTime.getDayEnd(currentDate),patient,ipNumber.getValue());
    	}
    	else{
    		if(fromDate!=null && thruDate!=null)
    			inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,fromDate.getValue(),thruDate.getValue(),patient,null);
        	else if(fromDate!=null)
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,fromDate.getValue(),null,patient,null);
        	else if(thruDate!=null)
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,null,thruDate.getValue(),patient,null);
        	else
        		inPatientRequisitionList = labService.getLabRequisitionForProcessing(true,UtilDateTime.getDayStart(currentDate),UtilDateTime.getDayEnd(currentDate),patient,null);
    	}*/
   }

    @Command("OPDPATIENTS")
    @NotifyChange("requisitionList")
    public void fetchOPDPatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,@BindingParam("ipNumber")Textbox ipNumber){
    	inpatientRequestFetch=false;
    	
    	UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryIds = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryIds.add(laboratory.getLaboratoryCode());
    	}
    	
    	List<LabRequisition> labRequisitionList;
    	  
    	if(fromDate!=null && thruDate!=null)
    		labRequisitionList = labService.getLabRequisitionForProcessing(false,fromDate.getValue(),thruDate.getValue(),patient,null,null);
    	else if(fromDate!=null)
    		labRequisitionList = labService.getLabRequisitionForProcessing(false,fromDate.getValue(),null,patient,null,null);
    	else if(thruDate!=null)
    		labRequisitionList = labService.getLabRequisitionForProcessing(false,null,thruDate.getValue(),patient,null,null);
    	else
    		labRequisitionList = labService.getLabRequisitionForProcessing(false,UtilDateTime.getDayStart(currentDate),
    				UtilDateTime.getDayEnd(currentDate),patient,null,null);
    	
    	requisitionList = new ArrayList<LabRequisition>();
        for(LabRequisition labRequisition: labRequisitionList){
        	Set<Laboratories> laboratories2 = labRequisition.getLabOrderRequest().getLaboratories();
        	for(Laboratories laboratories3 : laboratories2){
        		if(laboratoryIds.contains(laboratories3.getLaboratoryCode())){
        			requisitionList.add(labRequisition);
        			break;
        		}
        	}
      }
 }
    
    @Command("INPATIENT_LAB_OR_OPDPATIENTS")
    @NotifyChange({"inPatientRequisitionList","requisitionList"})
    public void fetchInPatientOrOutpatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,@BindingParam("ipNumber")Textbox ipNumber){
    	if(inpatientRequestFetch)
    		fetchInPatientLabRequistions(fromDate,thruDate,ipNumber);
    	else
    		fetchOPDPatientLabRequistions(fromDate,thruDate,ipNumber);
    }
    
   
    @Command("OPDPATIENTSCOMPLETED")
    @NotifyChange("completedRequisitionList")
    public void fetchOPDCompletedPatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,
    		@BindingParam("specimenLabel")Textbox specimenLabel){
    	inpatientFetch=false;
    	
    	UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryIds = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryIds.add(laboratory.getLaboratoryCode());
    	}
    	
    	List<LabRequisition> completedLabRequisitionList;
    	  
    	if(fromDate!=null && thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,fromDate.getValue(),thruDate.getValue(),patient,specimenLabel.getValue(),null);
    	else if(fromDate!=null && thruDate!=null )
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,fromDate.getValue(),thruDate.getValue(),patient,null,null);
    	else if(fromDate!=null && UtilValidator.isNotEmpty(specimenLabel))
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,fromDate.getValue(),null,patient,specimenLabel.getValue(),null);
    	else if(thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,null,thruDate.getValue(),patient,specimenLabel.getValue(),null);
    	else if(UtilValidator.isNotEmpty(specimenLabel))
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,null,null,patient,specimenLabel.getValue(),null);
    	else if(thruDate!=null)
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,null,thruDate.getValue(),patient,null,null);
    	else if(fromDate!=null)
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,fromDate.getValue(),null,patient,null,null);
    	else
    		completedLabRequisitionList = labService.getCompletedLabRequisition(false,UtilDateTime.getDayStart(currentDate),UtilDateTime.getDayEnd(currentDate),patient,null,null);
    
    	completedRequisitionList = new ArrayList<LabRequisition>();
         for(LabRequisition labRequisition: completedLabRequisitionList){
         	Set<Laboratories> laboratories2 = labRequisition.getLabOrderRequest().getLaboratories();
         	for(Laboratories laboratories3 : laboratories2){
         		if(laboratoryIds.contains(laboratories3.getLaboratoryCode())){
         			completedRequisitionList.add(labRequisition);
         			break;
         		}
         	}
       }
   }
 
    @Command("INPATIENT_LAB_COMPLETED")
    @NotifyChange("completedInPatientRequisitionList")
    public void fetchCompletedInPatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,
    		@BindingParam("specimenLabel")Textbox specimenLabel,@BindingParam("ipNumber")Textbox ipNumber){
    	inpatientFetch=true;
    	if(UtilValidator.isNotEmpty(ipNumber)){
    		if(fromDate!=null && thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
    			completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),thruDate.getValue(),patient,specimenLabel.getValue(),ipNumber.getValue());
            	else if(fromDate!=null && thruDate!=null )
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),thruDate.getValue(),patient,null,ipNumber.getValue());
            	else if(fromDate!=null && UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),null,patient,specimenLabel.getValue(),ipNumber.getValue());
            	else if(thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,thruDate.getValue(),patient,specimenLabel.getValue(),ipNumber.getValue());
            	else if(UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,null,patient,specimenLabel.getValue(),ipNumber.getValue());
            	else if(thruDate!=null)
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,thruDate.getValue(),patient,null,ipNumber.getValue());
            	else if(fromDate!=null)
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),null,patient,null,ipNumber.getValue());
            	else
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,null,patient,null,ipNumber.getValue());

    	}else{
    		if(fromDate!=null && thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
    			completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),thruDate.getValue(),patient,specimenLabel.getValue(),null);
            	else if(fromDate!=null && thruDate!=null )
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),thruDate.getValue(),patient,null,null);
            	else if(fromDate!=null && UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),null,patient,specimenLabel.getValue(),null);
            	else if(thruDate!=null && UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,thruDate.getValue(),patient,specimenLabel.getValue(),null);
            	else if(UtilValidator.isNotEmpty(specimenLabel))
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,null,patient,specimenLabel.getValue(),null);
            	else if(thruDate!=null)
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,null,thruDate.getValue(),patient,null,null);
            	else if(fromDate!=null)
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,fromDate.getValue(),null,patient,null,null);
            	else
            		completedInPatientRequisitionList = labService.getCompletedLabRequisition(true,UtilDateTime.getDayStart(currentDate),UtilDateTime.getDayEnd(currentDate),patient,null,null);

    	}
    	
    	
    }

    @Command("INPATIENT_OR_OUTPATIENT_LAB_COMPLETED")
    @NotifyChange({"completedInPatientRequisitionList","completedRequisitionList"})
    public void fetchCompletedInPatientOrOutpatientLabRequistions(@BindingParam("fromDate")Datebox fromDate,@BindingParam("thruDate")Datebox thruDate,
    		@BindingParam("specimenLabel")Textbox specimenLabel,@BindingParam("ipNumber")Textbox ipNumber){
    	if(inpatientFetch)
    		fetchCompletedInPatientLabRequistions(fromDate,thruDate,specimenLabel,ipNumber);
    	else
    		fetchOPDCompletedPatientLabRequistions(fromDate,thruDate,specimenLabel);
    }
    
    
    public List<LabRequisition> getInPatientRequisitionList() {
        return inPatientRequisitionList;
    }

    public void setInPatientRequisitionList(List<LabRequisition> inPatientRequisitionList) {
        this.inPatientRequisitionList = inPatientRequisitionList;
    }

    public List<LabRequisition> getRequisitionList() {
        return requisitionList;
    }

    public void setRequisitionList(List<LabRequisition> requisitionList) {
        this.requisitionList = requisitionList;
    }

    @Command("Collection")
    public void collection(@BindingParam(value = "arg1")LabRequisition labRequisition,@BindingParam(value = "arg2")Component tab){
        Map<String,Object> args = new HashMap<String,Object>();
        args.put("labRequisition",labRequisition);
        args.put("labResultTab", tab);
        Executions.createComponents("/lab/specimenCollection.zul",null,args);
    }
}
