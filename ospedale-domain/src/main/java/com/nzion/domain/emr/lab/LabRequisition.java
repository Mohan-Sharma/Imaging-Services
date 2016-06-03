package com.nzion.domain.emr.lab;

import com.nzion.domain.Patient;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.docmgmt.PatientEducationDocument;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.util.UtilValidator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
public class LabRequisition extends IdGeneratingBaseEntity {

    private static final long serialVersionUID = 1L;
    //private Set<LabTestPanel> labTestPanels;
   /* private Set<LabTest> labTests;
    
    private Set<LabTestProfile> labTestProfiles;
    
    private Set<LabTestPanel> labTestPanels;
     */  
    
    private List<SpecimenModel> specimenModelList;
    private Long patientSoapNoteId;
    private Long referralId;
    private LabRequisitionStatus status;
    private Patient patient;
    private Long providerId;
    private Boolean self;
    private Long inPatientDoctorVisitId;
    private String clinicalHistory;
    private String remarks;
    private String token;
    private String invoiceNumber;
    private LabOrderRequest labOrderRequest;
    
    private Set<LabRequisitionItem> labRequisitionItems;
   

    public static LabRequisition createLabRequisition(LabOrderRequest labOrderRequest,String invoiceNumber,String token) {
        
    	LabRequisition labRequisition = new LabRequisition();
        
    	labRequisition.setClinicalHistory(labOrderRequest.getClinicalHistory());
        Set<LabTest> labTestList = new HashSet<LabTest>();
        Set<LabTestProfile> labTestProfileList = new HashSet<LabTestProfile>();
        Set<LabTestPanel> labTestPanelList = new HashSet<LabTestPanel>();
        
        for (PatientLabOrder each : labOrderRequest.getPatientLabOrders()){
            if (each.getBillingStatus().equals(PatientLabOrder.BILLINGSTATUS.PAID) || UtilValidator.isEmpty(invoiceNumber))
            	if(each.getLabTest() != null) labTestList.add(each.getLabTest());
            	if(each.getLabTestProfile() != null) labTestProfileList.add(each.getLabTestProfile());
            	if(each.getLabTestPanel() != null) labTestPanelList.add(each.getLabTestPanel());
        }

       // labRequisition.setLabTests(labTestList);
       // labRequisition.setLabTestProfiles(labTestProfileList);
       // labRequisition.setLabTestPanels(labTestPanelList);
        labRequisition.setStatus(LabRequisitionStatus.NEW);
        labRequisition.setPatient(labOrderRequest.getPatient());
        if(labOrderRequest.getProvider()!=null)
        labRequisition.setProviderId(labOrderRequest.getProvider().getId());
        labRequisition.setRemarks(labOrderRequest.getRemarks());
        if(labOrderRequest.getProvider()==null && labOrderRequest.getReferral()==null)
        labRequisition.setSelf(Boolean.TRUE);
        if(labOrderRequest.getReferral()!=null)
        labRequisition.setReferralId(labOrderRequest.getReferral().getId());
        labRequisition.setInvoiceNumber(invoiceNumber);
        labRequisition.setLabOrderRequest(labOrderRequest);
        labRequisition.setToken(token);
        return labRequisition;
    }

    public static enum LabRequisitionStatus {
        NEW("New"), COLLECTION_DONE("Collection Done"), IN_PROCESS("In Process"), REVIEWED("Reviewed"), COMPLETED("Completed"),
        SAMPLE_COLLECTED("Sample Collected");
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        LabRequisitionStatus(String description) {
            this.description = description;
        }

    }

   /* @ManyToMany(targetEntity = LabTestPanel.class, fetch = FetchType.EAGER)
    public Set<LabTestPanel> getLabTestPanels() {
        return labTestPanels;
    }

    public void setLabTestPanels(Set<LabTestPanel> labTestPanels) {
        this.labTestPanels = labTestPanels;
    }*/

    @OneToMany(targetEntity = LabRequisitionItem.class, fetch = FetchType.EAGER)
    public Set<LabRequisitionItem> getLabRequisitionItems() {
		return labRequisitionItems;
	}

	public void setLabRequisitionItems(Set<LabRequisitionItem> labRequisitionItems) {
		this.labRequisitionItems = labRequisitionItems;
	}
    
  /*  @ManyToMany(targetEntity = LabTest.class, fetch = FetchType.EAGER)
    public Set<LabTest> getLabTests() {
        return labTests;
    }

    

	public void setLabTests(Set<LabTest> labTests) {
        this.labTests = labTests;
    }
    
    @ManyToMany(targetEntity = LabTestProfile.class, fetch = FetchType.EAGER)
    public Set<LabTestProfile> getLabTestProfiles() {
        return labTestProfiles;
    }

    public void setLabTestProfiles(Set<LabTestProfile> labTestProfiles) {
        this.labTestProfiles = labTestProfiles;
    }
    
    @ManyToMany(targetEntity = LabTestPanel.class, fetch = FetchType.EAGER)
    public Set<LabTestPanel> getLabTestPanels() {
        return labTestPanels;
    }

    public void setLabTestPanels(Set<LabTestPanel> labTestPanels) {
        this.labTestPanels = labTestPanels;
    }
   */ 
    public Long getPatientSoapNoteId() {
        return patientSoapNoteId;
    }

    public void setPatientSoapNoteId(Long patientSoapNoteId) {
        this.patientSoapNoteId = patientSoapNoteId;
    }

    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(Long referralId) {
        this.referralId = referralId;
    }

    @Enumerated(EnumType.STRING)
    public LabRequisitionStatus getStatus() {
        return status;
    }

    public void setStatus(LabRequisitionStatus status) {
        this.status = status;
    }

    @OneToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Boolean getSelf() {
        return self;
    }

    public void setSelf(Boolean self) {
        this.self = self;
    }

    public Long getInPatientDoctorVisitId() {
        return inPatientDoctorVisitId;
    }

    public void setInPatientDoctorVisitId(Long inPatientDoctorVisitId) {
        this.inPatientDoctorVisitId = inPatientDoctorVisitId;
    }

    public String getClinicalHistory() {
        return clinicalHistory;
    }

    public void setClinicalHistory(String clinicalHistory) {
        this.clinicalHistory = clinicalHistory;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @OneToMany(mappedBy = "labRequisition",targetEntity=SpecimenModel.class,fetch=FetchType.EAGER)
    public List<SpecimenModel> getSpecimenModelList() {
        return specimenModelList;
    }
    	

    public void setSpecimenModelList(List<SpecimenModel> specimenModelList) {
        this.specimenModelList = specimenModelList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
  
    public void addSpecimenModel(SpecimenModel model){
        if(getSpecimenModelList()==null)
            setSpecimenModelList(new ArrayList<SpecimenModel>());
        getSpecimenModelList().add(model);
    }

    @OneToOne(fetch = FetchType.EAGER)
    public LabOrderRequest getLabOrderRequest() {
        return labOrderRequest;
    }

    public void setLabOrderRequest(LabOrderRequest labOrderRequest) {
        this.labOrderRequest = labOrderRequest;
    }
}
