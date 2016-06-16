package com.nzion.zkoss.composer.emr.lab;

/**
 @Author S@MIR
 April 20,2011
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.*;

import com.nzion.domain.emr.lab.*;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Filedownload;

import com.nzion.domain.File;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.UserLogin;
import com.nzion.domain.emr.lab.LabRequisition.LabRequisitionStatus;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;

@VariableResolver(DelegatingVariableResolver.class)
public class LabTestResultController {

    private static final long serialVersionUID = 1L;

    @WireVariable
    private LabService labService;

    @WireVariable
    private CommonCrudService commonCrudService;

    @WireVariable
    private CommonCrudRepository commonCrudRepository;

    @WireVariable
    private FileBasedServiceImpl fileBasedServiceImpl;

    private OBRSegment obrSegment;

    private Set<LabTestPanel> labTestPanels;

    private LabTestPanel selectedLabTestPanel;

    private LabRequisition labRequisition;

    private Provider provider;

    private Referral referral;

    private SpecimenModel selectedSpecimenModel;

    private List<SpecimenModel> specimenModelList;

    private List<OBXSegment> observations;

   // private Map<LabTestPanel, OBRSegment> obrSegmentMap = null;
    
   // private Map<LabTest, OBRSegment> obrSegmentMap = null;
    
    private Map<LabTestResult,OBRSegment> obrSegmentMap = null;

    private LabTest selectedLabTest;
    
    private List<LabTest> labTestList;
    
    private LabTestResult selectedLabTestResult;
    
    private List<LabTestResult> labTestResultList;
    

    public List<LabTest> getLabTestList() {
		return labTestList;
	}

	public void setLabTestList(List<LabTest> labTestList) {
		this.labTestList = labTestList;
	}

	
	public List<LabTestResult> getLabTestResultList() {
		return labTestResultList;
	}

	public void setLabTestResultList(List<LabTestResult> labTestResultList) {
		this.labTestResultList = labTestResultList;
	}

    private Set<OBRSegmentAttachments> obrSegmentAttachmentSet = new HashSet<OBRSegmentAttachments>();

    private String fileName;

	@Init
    public void init(@ExecutionArgParam("labRequisition") LabRequisition labRequisition) {
        this.labRequisition = labRequisition;
        if (labRequisition.getProviderId() != null)
            this.provider = commonCrudService.getById(Provider.class, labRequisition.getProviderId());
        if (labRequisition.getReferralId() != null)
            this.referral = commonCrudService.getById(Referral.class, labRequisition.getReferralId());
       // labTestPanels = labRequisition.getLabTestPanels();
        
        
        UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryNames = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryNames.add(laboratory.getLaboratory());
    	}
        
      Set<LabRequisitionItem> labRequisitionItems  =  labRequisition.getLabRequisitionItems();
       for(LabRequisitionItem labRequisitionItem : labRequisitionItems){
        	List<LabTestResult> labTestResults = labRequisitionItem.getLabTestResults();
        	for(LabTestResult labTestResult : labTestResults ){
        		if(laboratoryNames.contains(labTestResult.getLaboratory())){
        			if(labTestResultList == null) labTestResultList=new ArrayList<LabTestResult>();
        			labTestResultList.add(labTestResult);
        		}
        	}
         }
        
        
        
        
       /* UserLogin login = Infrastructure.getUserLogin();
    	Set <Laboratories>laboratories = login.getLaboratories();
    	List<String> laboratoryIds = new ArrayList<String> ();
    	for(Laboratories laboratory : laboratories){
    		laboratoryIds.add(laboratory.getLaboratoryCode());
    	}
        
    	labTestList = new ArrayList<LabTest>();
    	Set<LabRequisitionItem> labRequisitionItems = labRequisition.getLabRequisitionItems();
    	for(LabRequisitionItem labRequisitionItem: labRequisitionItems){
    		if(labRequisitionItem.getLabTest() !=null){
    			if(laboratoryIds.contains(labRequisitionItem.getLabTest().getLaboratory().getLaboratoryCode()))
        			labTestList.add(labRequisitionItem.getLabTest());
    		}
    		else if(labRequisitionItem.getLabTestProfile() != null){
    			Set<LabTest> labTests = labRequisitionItem.getLabTestProfile().getTests();
            	for(LabTest labTest : labTests){
            		if(laboratoryIds.contains(labTest.getLaboratory().getLaboratoryCode()))
            			labTestList.add(labTest);
            	}
    		}else{
    			Set<LabTest> labTests = labRequisitionItem.getLabTestPanel().getTests();
            	for(LabTest labTest : labTests){
            		if(laboratoryIds.contains(labTest.getLaboratory().getLaboratoryCode()))
            			labTestList.add(labTest);
            	}
    		}
    		
    	}*/
    	
      /*  Set<LabTestProfile> labProfiles = labRequisition.getLabTestProfiles();
        for(LabTestProfile labTestProfile : labProfiles){
        	Set<LabTest> labTests = labTestProfile.getTests();
        	for(LabTest labTest : labTests){
        		if(laboratoryIds.contains(labTest.getLaboratory().getLaboratoryCode()))
        			labTestList.add(labTest);
        	}
        }
        
        Set<LabTestPanel> labPanels = labRequisition.getLabTestPanels();
        for(LabTestPanel labTestPanel : labPanels){
        	Set<LabTest> labTests = labTestPanel.getTests();
        	for(LabTest labTest : labTests){
        		if(laboratoryIds.contains(labTest.getLaboratory().getLaboratoryCode()))
        			labTestList.add(labTest);
        	}
        }
        
        Set<LabTest> labTests = labRequisition.getLabTests();
        for(LabTest labTest : labTests){
    		if(laboratoryIds.contains(labTest.getLaboratory().getLaboratoryCode()))
    			labTestList.add(labTest);
    	}
      */  
        //obrSegmentMap = new HashMap<LabTestPanel, OBRSegment>();
        //obrSegmentMap = new HashMap<LabTest, OBRSegment>();
       obrSegmentMap = new HashMap<LabTestResult, OBRSegment>();
    }

//    public OBXSegment getOBXSegment(LabTest labTest) {
//        OBRSegment obrSegment = commonCrudService.refreshEntity(getOBRSegment());
//        for (OBXSegment obxSeg : obrSegment.getObservations())
//            if (labTest.equals(obxSeg.getLabTest()))
//                return obxSeg;
//        return new OBXSegment(labTest);
//    }


    @Command("LoadLabTests")
    @NotifyChange("observations")
    public void populateObservationPanel(@BindingParam("editor") FCKeditor fcKeditor) {
        //Set<OBXSegment> obxSegments = obrSegmentMap.get(this.selectedLabTestPanel).getObservations();
    	//Set<OBXSegment> obxSegments = obrSegmentMap.get(this.selectedLabTest).getObservations();
    	
    	Set<OBXSegment> obxSegments = obrSegmentMap.get(this.selectedLabTestResult).getObservations();
      //  fcKeditor.setValue(obrSegmentMap.get(this.selectedLabTestPanel).getTechnicianComment());
    	 // fcKeditor.setValue(obrSegmentMap.get(this.selectedLabTest).getTechnicianComment());
    	 fcKeditor.setValue(obrSegmentMap.get(this.selectedLabTestResult).getTechnicianComment());  
    	List<OBXSegment> observations = new ArrayList<OBXSegment>(obxSegments);
    	  Collections.sort(observations);
    	  this.observations = observations;
    }

    @Command("uploadLabAttachment")
    @NotifyChange("observations")
    public void uploadLabAttachment(@BindingParam("eventType") UploadEvent uploadEvent, @BindingParam("obxSegmentObject") OBXSegment obxSegment) {
        Media media = uploadEvent.getMedia();
        File file = null;
        if (obxSegment.getFile() == null) {
            file = new File();
        }

        file.setFileType(media.getContentType());
        file.setInputStream(media.isBinary() ? media.getStreamData() : new StringBufferInputStream(media.getStringData()));
        file.setFileName(media.getName());
        try {
            fileBasedServiceImpl.createFilesForImageSection(file, obxSegment.getPatient());
        } catch (Exception e){
            fileBasedServiceImpl.createDefaultFolderStructure(labRequisition.getPatient());
            fileBasedServiceImpl.createFilesForImageSection(file, obxSegment.getPatient());
        }

        obxSegment.setFile(file);
        this.observations = updateFileAttachmentInfoInObx(this.observations, obxSegment);
    }

    @Command("removeAttachment")
    @NotifyChange("observations")
    public void removeAttachment(@BindingParam("obxSegmentObject") OBXSegment obxSegment) {
        if (obxSegment.getFile() != null) {
            File fileRecord = commonCrudRepository.findUniqueByEquality(File.class, new String[]{"id"}, new Object[]{obxSegment.getFile().getId()});
            obxSegment.setFile(null);
            if (obxSegment.getFile() != null)
                commonCrudService.save(obxSegment);

            if (fileRecord.getId() != null && obxSegment.getFile() != null)
                commonCrudService.delete(fileRecord);
        }
        List<OBXSegment> observations = updateFileAttachmentInfoInObx(this.observations, obxSegment);
        Collections.sort(observations);
        this.observations = observations;

    }

    @Command("removeAttachmentOBR")
    @NotifyChange("fileName")
    public void removeAttachmentOBR() {
        File fileRecord = null;
        if ((obrSegment != null) && (obrSegment.getAttachments() != null)) {
             fileRecord = commonCrudRepository.findUniqueByEquality(File.class, new String[]{"id"}, new Object[]{obrSegment.getAttachments().iterator().next().getFile().getId()});
        }
        if (fileRecord != null) {
            Set<OBRSegmentAttachments> attachments = obrSegment.getAttachments();
            OBRSegmentAttachments obrSegmentAttachments = attachments.iterator().next();
            obrSegmentAttachments.setFile(null);

            obrSegment.setAttachments(attachments);
            commonCrudService.save(obrSegment);

            if (fileRecord.getId() != null)
                commonCrudService.delete(fileRecord);
        } else {
            getObrSegmentAttachmentSet().clear();
        }
        fileName = null;


    }

    @Command("downloadAttachment")
    public void downloadAttachment(@BindingParam("obxSegmentObject") OBXSegment obxSegment) throws FileNotFoundException {
        File file = obxSegment.getFile();
        InputStream in = new FileInputStream(file.getFilePath());
        Filedownload.save(in, file.getFileType(), file.getFileName());
    }

    @Command("LoadSpecimen")
    @NotifyChange({"specimenModelList", "fileName"})
    public void loadSpecimen(@BindingParam("editor") FCKeditor fcKeditor) {
        if (selectedLabTestResult != null) {

            obrSegment = commonCrudService.findUniqueByEquality(OBRSegment.class, new String[]{"labTest", "requisitionNumber"}, new Object[]{this.selectedLabTestResult.getLabTest(), this.labRequisition.getId().toString()});
            if (obrSegment != null) {
                if (obrSegment.getAttachments().iterator().hasNext()){
                    if (obrSegment.getAttachments().iterator().next().getFile() != null) {
                        fileName = obrSegment.getAttachments().iterator().next().getFile().getFileName();
                    }
                }
                obrSegmentMap.put(this.selectedLabTestResult, obrSegment);
            } else {
                obrSegmentMap.put(this.selectedLabTestResult, labService.createOBRSegment(labRequisition, selectedSpecimenModel, selectedLabTestResult.getLabTest()));
            }
            specimenModelList = labService.getSpecimenList(labRequisition, selectedLabTestResult);
            fcKeditor.setValue("");
            fcKeditor.invalidate();
        }
    }

    @NotifyChange("specimenList")
    public void setSelectedLabTestPanel(LabTestPanel selectedLabTestPanel) {
        selectedLabTestPanel = commonCrudService.refreshEntity(selectedLabTestPanel);
        this.selectedLabTestPanel = selectedLabTestPanel;
    }

    
    
    private List<OBXSegment> updateFileAttachmentInfoInObx(List<OBXSegment> obxSegments, OBXSegment fileAttachedObxSegment) {
        for (OBXSegment obxSegment : obxSegments) {
           /* if (obxSegment.getLabTest().getTestName().equals(fileAttachedObxSegment.getLabTest().getTestName())) {
                obxSegment.setFile(fileAttachedObxSegment.getFile());
                break;
            }*/
        }
        return obxSegments;
    }

    public void setSelectedSpecimenModel(SpecimenModel selectedSpecimenModel) {
        this.selectedSpecimenModel = selectedSpecimenModel;
        /*if (obrSegmentMap.get(this.selectedLabTestPanel) == null) {
            obrSegmentMap.put(this.selectedLabTestPanel, labService.createOBRSegment(labRequisition, selectedSpecimenModel, selectedLabTestPanel));
        }*/
        /*if (obrSegmentMap.get(this.selectedLabTest) == null) {
            obrSegmentMap.put(this.selectedLabTest, labService.createOBRSegment(labRequisition, selectedSpecimenModel, selectedLabTest));
        }*/
        if (obrSegmentMap.get(this.selectedLabTestResult) == null) {
            obrSegmentMap.put(this.selectedLabTestResult, labService.createOBRSegment(labRequisition, selectedSpecimenModel, selectedLabTestResult.getLabTest()));
        } else {
            obrSegmentMap.get(this.selectedLabTestResult).setSpecimen(selectedSpecimenModel);
        }
    }


    @Command("MarkSave")
    public void saveBeforeComplete() {
        for (OBRSegment obrSegment : obrSegmentMap.values()) {
            for (OBXSegment obxSegment : obrSegment.getObservations()) {
                obxSegment.setObservationDateTime(obrSegment.getObservationDateTime());
               // obxSegment.setLabCategory(selectedLabTestPanel.getLabCategory());
            }
            commonCrudService.refreshEntity(labRequisition.getLabOrderRequest());
            if (labRequisition.getLabOrderRequest().getProvider() != null)
                obrSegment.setProvider(labRequisition.getLabOrderRequest().getProvider());
            obrSegment.setRequisitionNumber(String.valueOf(labRequisition.getId()));

            if(obrSegmentAttachmentSet.size() > 0) {
                for (OBRSegmentAttachments attachments : obrSegmentAttachmentSet) {
                    attachments.setObrSegment(obrSegment);
                }
                obrSegment.setAttachments(obrSegmentAttachmentSet);
            }

            commonCrudService.save(obrSegment);
            UtilMessagesAndPopups.showSuccess();
        }

    }

    @Command("MarkComplete")
    public void save(@BindingParam("overallComments") FCKeditor fcKeditor) {
        //labRequisition.setStatus(LabRequisition.LabRequisitionStatus.COMPLETED);
        //labRequisition.setRemarks(fcKeditor.getValue());
       // labRequisition = commonCrudService.save(labRequisition);
        UtilMessagesAndPopups.displaySuccess();
        if (selectedLabTestResult == null)
            return;
        selectedLabTestResult.setStatus(LabRequisitionStatus.COMPLETED);
        commonCrudService.save(selectedLabTestResult);
        
        
        
        List<LabTestResult> labTestResults = new ArrayList<LabTestResult>();
        Set<LabRequisitionItem> labRequisitionItems = labRequisition.getLabRequisitionItems();
        for(LabRequisitionItem labRequisitionItem : labRequisitionItems){
        	labTestResults.addAll(labRequisitionItem.getLabTestResults());
        }
       // List<LabRequisition> labRequisitions = labService.getLabRequsitionStatus(labTestResults);
        Integer count = labService.getLabRequisitionCount(labTestResults);
        if(count == 0){
        	labRequisition.setStatus(LabRequisition.LabRequisitionStatus.COMPLETED);
        	labRequisition = commonCrudService.save(labRequisition);
        }
        
  }

    public List<OBXSegment> getObservations() {
        return observations;
    }

    public List<SpecimenModel> getSpecimenModelList() {
        return specimenModelList;
    }

    public LabTest getSelectedLabTest() {
        return selectedLabTest;
    }

    public void setSelectedLabTest(LabTest selectedLabTest) {
    	selectedLabTest = commonCrudService.refreshEntity(selectedLabTest);
        this.selectedLabTest = selectedLabTest;
    }

     
    public LabTestResult getSelectedLabTestResult() {
		return selectedLabTestResult;
	}

	public void setSelectedLabTestResult(LabTestResult selectedLabTestResult) {
		//selectedLabTestResult = commonCrudService.refreshEntity(selectedLabTestResult);
		this.selectedLabTestResult = selectedLabTestResult;
	}

	public LabRequisition getLabRequisition() {
        return labRequisition;
    }

    public SpecimenModel getSelectedSpecimenModel() {
        return selectedSpecimenModel;
    }

    public Provider getProvider() {
        return provider;
    }

    public Referral getReferral() {
        return referral;
    }

    public LabTestPanel getSelectedLabTestPanel() {
        return selectedLabTestPanel;
    }

    public OBRSegment getObrSegment() {
        //return obrSegmentMap.get(this.selectedLabTestPanel);
    	  return obrSegmentMap.get(this.selectedLabTest);
    }

    public void setObrSegment(OBRSegment obrSegment) {
        this.obrSegment = obrSegment;
    }

    public OBRSegment getOBRSegment() {
       // return obrSegmentMap.get(this.selectedLabTestPanel);
    	 return obrSegmentMap.get(this.selectedLabTest);
    }

    public void setOBRSegment(OBRSegment obrSegment) {
       // obrSegmentMap.put(this.selectedLabTestPanel, obrSegment);
        //obrSegmentMap.put(this.selectedLabTest, obrSegment);
        obrSegmentMap.put(this.selectedLabTestResult, obrSegment);
    }

    public Set<OBRSegmentAttachments> getObrSegmentAttachmentSet() {
        return obrSegmentAttachmentSet;
    }

    public void setObrSegmentAttachmentSet(Set<OBRSegmentAttachments> obrSegmentAttachmentSet) {
        this.obrSegmentAttachmentSet = obrSegmentAttachmentSet;
    }

    @Command("uploadLabAttachmentOBR")
    @NotifyChange("fileName")
    public void uploadLabAttachmentOBR(@BindingParam("eventType") UploadEvent uploadEvent) {
        Media media = uploadEvent.getMedia();
        File file = new File();

        file.setFileType(media.getContentType());
        file.setInputStream(media.isBinary() ? media.getStreamData() : new StringBufferInputStream(media.getStringData()));
        file.setFileName(media.getName());
        try{
            fileBasedServiceImpl.createFilesForImageSection(file, labRequisition.getPatient());
        }catch(Exception e){

            fileBasedServiceImpl.createDefaultFolderStructure(labRequisition.getPatient());
            fileBasedServiceImpl.createFilesForImageSection(file, labRequisition.getPatient());
        }

        if ((obrSegment != null) && (obrSegment.getAttachments().iterator().hasNext()) && (obrSegment.getAttachments().iterator().next().getFile() == null)){
                    obrSegment.getAttachments().iterator().next().setFile(file);
            obrSegmentAttachmentSet = Collections.EMPTY_SET;
        } else {
            OBRSegmentAttachments attachment = new OBRSegmentAttachments();
            attachment.setFile(file);
            getObrSegmentAttachmentSet().clear();
            getObrSegmentAttachmentSet().add(attachment);
        }

        fileName = file.getFileName();
        //this.observations = updateFileAttachmentInfoInObx(this.observations, obxSegment);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}