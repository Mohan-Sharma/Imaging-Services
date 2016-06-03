package com.nzion.zkoss.composer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nzion.domain.emr.lab.LabTestPanel;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

import com.nzion.domain.Enumeration;
import com.nzion.domain.File;
import com.nzion.domain.docmgmt.AttachedItem;
import com.nzion.domain.docmgmt.PatientEducation;
import com.nzion.domain.docmgmt.PatientEducationDocument;
import com.nzion.enums.MATERIALCATEGORY;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.EnumerationService;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.view.component.EnumerationDropdown;

public class PatientEducationController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	
	private PatientEducation education;
	
	private Enumeration male;
	
	private Enumeration female;
	
	private Enumeration both;
	
	private CommonCrudService commonCrudService;
	
	private EnumerationService enumerationService;
	
	private AttachedItem attachedItem;
	
	private PatientEducationDocument document;
	
	private FileBasedServiceImpl fileBasedServiceImpl;
	
	private boolean showDeactivatedDocuments = false;
	
	public boolean isShowDeactivatedDocuments() {
	return showDeactivatedDocuments;
	}

	public void setShowDeactivatedDocuments(boolean showDeactivatedDocuments) {
	this.showDeactivatedDocuments = showDeactivatedDocuments;
	}

	public PatientEducation getEducation() {
	return education;
	}

	public  void renderSelectedCategory(MATERIALCATEGORY category){
	education.setMaterialCategory(category);
	root.getFellow("categoryItemsListBox").setVisible(!(MATERIALCATEGORY.TOBACCOCESSATION.equals(category) || MATERIALCATEGORY.BMIFOLLOWUP.equals(category)));
	root.getFellow("addDiv").setVisible(MATERIALCATEGORY.MEDICATION.equals(category));
	if(MATERIALCATEGORY.ICD.equals(category) || MATERIALCATEGORY.CPT.equals(category) || MATERIALCATEGORY.LABORDER.equals(category)){
	String lookUpUrl = MATERIALCATEGORY.ICD.equals(category) ? "/emr/patient-education-icd-look-up.zul" : (MATERIALCATEGORY.CPT.equals(category) ? "/emr/patient-education-cpt-look-up.zul" : (MATERIALCATEGORY.LABORDER.equals(category)) ? "/emr/patient-education-lab-order-lookup.zul" : null);
	Executions.createComponents(lookUpUrl,root.getFellow("addPatientEducationWin"),com.nzion.util.UtilMisc.toMap("patientEducation",education,"listBox",root.getFellow("categoryItemsListBox"),"category",category,"patientEducationController",this));
	}
	}
		
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	male = enumerationService.findByEnumCodeAndEnumType("MALE","QUESTION_GENDER");
	female = enumerationService.findByEnumCodeAndEnumType("FEMALE","QUESTION_GENDER");
	both = enumerationService.findByEnumCodeAndEnumType("BOTH","QUESTION_GENDER");
	education = (PatientEducation)Executions.getCurrent().getArg().get("entity");
	if(education == null){
		education = new PatientEducation();
		education.setPatientEducationDocuments(new ArrayList<PatientEducationDocument>());
		education.setAttachedItems(new ArrayList<AttachedItem>());
		education.setGender(both);
	}else
		education = commonCrudService.getById(PatientEducation.class, education.getId());
	document = new PatientEducationDocument();
	document.setPatientEducation(education);
	return super.doBeforeCompose(page, parent, compInfo);
	}
	
	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	} 
	
	@SuppressWarnings("deprecation")
	public void uploadDocument(Event event){
	if(((EnumerationDropdown) root.getFellow("languageEnumDropDown")).getSelectedItem() == null){
		throw new WrongValueException(root.getFellow("languageEnumDropDown"),"Select language");
	}
	Media media = ((UploadEvent) event).getMedia();
	File file = new File();
	file.setFileName(media.getName());
	file.setFileType(media.getContentType());
	if (media.isBinary())
		file.setInputStream(media.getStreamData());
	else
		file.setInputStream(new java.io.StringBufferInputStream(media.getStringData()));
	((Textbox)root.getFellow("fileNameTxtBox")).setValue(file.getFileName());
	document.setFile(file);
	document.setLanguage((Enumeration) ((EnumerationDropdown)root.getFellow("languageEnumDropDown")).getSelectedItem().getValue());
	if(education.getMaterialCategory() == null)
		education.setMaterialCategory((MATERIALCATEGORY) ((Combobox)root.getFellow("categoryCombobox")).getSelectedItem().getValue());
	fileBasedServiceImpl.createFileForPatientEducation(file,education.getMaterialCategory().toString());
	}
	
	public void addPatientEducationDocument(){
	if(UtilValidator.isEmpty(((Textbox)root.getFellow("fileNameTxtBox")).getValue()))
		throw new WrongValueException(root.getFellow("fileNameTxtBox"),"Upload File");
	if(document.getLanguage() == null)
		throw new WrongValueException(root.getFellow("languageEnumDropDown"),"Select language");
	root.getFellow("patientEducationDocumentListBox").setVisible(true);
	education.getPatientEducationDocuments().add(document);
	document = new PatientEducationDocument();
	document.setPatientEducation(education);
	((Textbox)root.getFellow("fileNameTxtBox")).setValue(null);
	}
	
	public void savePatientEducation(){
	if(((Checkbox)root.getFellow("allChkBox")).isChecked()){
		education.setFromRange(0);
		education.setToRange(0);
	}
	if(root.getFellow("rangeComboboxDiv").isVisible() && ((Intbox)root.getFellow("toTxtBox")).getValue() < ((Intbox)root.getFellow("fromTxtBox")).getValue())
		throw new WrongValueException(root.getFellow("toTxtBox"),"To range should be greater than from range");
	if(com.nzion.util.UtilValidator.isEmpty(education.getPatientEducationDocuments())){
		com.nzion.util.UtilMessagesAndPopups.showError("Add atleast one document");
		return;
	}
	commonCrudService.save(education);
	com.nzion.util.UtilMessagesAndPopups.showSuccess();
	root.getFellow("addPatientEducationWin").detach();
	}
	
	public void addAttachedItem(){
	if(com.nzion.util.UtilValidator.isEmpty(((Textbox)root.getFellow("categoryInputTxtBox")).getValue())){
		throw new WrongValueException(root.getFellow("categoryInputTxtBox"),"Cannot be empty");
	}
	attachedItem = new AttachedItem();
	attachedItem.setMaterialcategory((MATERIALCATEGORY) ((Combobox)root.getFellow("categoryCombobox")).getSelectedItem().getValue());
	attachedItem.setDescription(((Textbox)root.getFellow("categoryInputTxtBox")).getValue());
	((Textbox)root.getFellow("categoryInputTxtBox")).setValue(null);
	education.addAttachedItem(attachedItem);
	root.getFellow("categoryItemsListBox").setVisible(true);
	attachedItem = new AttachedItem();
	}

	public Enumeration getMale() {
	return male;
	}

	public void setMale(Enumeration male) {
	this.male = male;
	}

	public Enumeration getFemale() {
	return female;
	}

	public void setFemale(Enumeration female) {
	this.female = female;
	}

	public Enumeration getBoth() {
	return both;
	}

	public void setBoth(Enumeration both) {
	this.both = both;
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public void setEnumerationService(EnumerationService enumerationService) {
	this.enumerationService = enumerationService;
	}

	public void setFileBasedServiceImpl(FileBasedServiceImpl fileBasedServiceImpl) {
	this.fileBasedServiceImpl = fileBasedServiceImpl;
	}
	
	public List<LabTestPanel> getFilteredLabTestPanels(String panelName,List<LabTestPanel> labTestPanels){
    List<LabTestPanel> testPanels = new ArrayList<LabTestPanel>();
    /*for (Iterator<LabTestPanel> i = labTestPanels.iterator(); i.hasNext();) {
        LabTestPanel testPanel = i.next();
        if (testPanel.getPanelName().toLowerCase().indexOf(panelName.trim().toLowerCase()) >= 0) 
        	testPanels.add(testPanel);
    }*/
    return testPanels;
	}
	
	public void clear(){
	((Combobox)root.getFellow("categoryCombobox")).setSelectedItem(null);
	((Combobox)root.getFellow("categoryCombobox")).setDisabled(false);
	getEducation().getAttachedItems().clear();
	root.getFellow("addDiv").setVisible(false);
	for(PatientEducationDocument document : education.getPatientEducationDocuments())
		commonCrudService.delete(document.getFile());
	getEducation().getPatientEducationDocuments().clear();
	}
	
	public ListitemRenderer getListitemRenderer(){
	return new ListitemRenderer() {
		
		@Override
		public void render(Listitem item, Object data,int index) throws Exception {
		PatientEducationDocument document = (PatientEducationDocument)data;
		item.setValue(document);
		Listcell fileListCell = new Listcell();
		fileListCell.setParent(item);
		fileListCell.setLabel(document.getFile().getFileName());
		Listcell languageListCell = new Listcell();
		languageListCell.setParent(item);
		languageListCell.setLabel(document.getLanguage().getDescription());
		Listcell imageLisceListcell = new Listcell();
		imageLisceListcell.setParent(item);
		Image image = new Image();
		image.setParent(imageLisceListcell);
		image.setSrc(document.isActive() ? "/images/tick.png" : "/images/cross.png");
		}
	};
	}
	
	public void activatePatientEducationDocuments(List<PatientEducationDocument> selectedDocuments){
	if(UtilValidator.isEmpty(selectedDocuments)){
		UtilMessagesAndPopups.displayError("Please select items to activate");
		return;
	}
	for(PatientEducationDocument document : selectedDocuments)
		document.activate();
	}
	
	public void deactivatePatientEducationDocuments(List<PatientEducationDocument> selectedDocuments){
	if(UtilValidator.isEmpty(selectedDocuments)){
		UtilMessagesAndPopups.displayError("Please select items to deactivate");
		return;
	}
	Executions.createComponents("/utilities/patient-education-document-deactivate.zul",null, com.nzion.util.UtilMisc.toMap("selectedDocuments", selectedDocuments,
			"patientEducationController",this));
	}
	
	public void deActivateClicked(String reason,List<PatientEducationDocument> documentsToBeDeActivated){
	commonCrudService.deActivate(documentsToBeDeActivated, reason);
	Events.postEvent("onReloadRequest", root.getFellow("patientEducationDocumentListBox"), null); 
	}
}
