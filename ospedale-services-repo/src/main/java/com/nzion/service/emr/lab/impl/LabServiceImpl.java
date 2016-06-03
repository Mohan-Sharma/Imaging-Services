package com.nzion.service.emr.lab.impl;

import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.Provider;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.external.LabLineItem;
import com.nzion.external.LabOrderDto;
import com.nzion.repository.emr.lab.LabRepository;
import com.nzion.service.PatientService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.UtilValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;


import java.math.BigDecimal;
import java.util.*;

@Transactional
@Service("labService")
public class LabServiceImpl implements LabService {

    private final Logger logger = Logger.getLogger(LabServiceImpl.class);
    
    @Autowired
    private PatientService patientService;
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    private String insertToClinc = "INSERT INTO clinic(CLINIC_ID,CLINIC_REFERRAL_ID,CLINIC_NAME) VALUES(:clinicId ,:referralId,:clinicName)";
    
    
    private String countLabRequisition =" SELECT COUNT(*) FROM lab_requisition  lr1 WHERE 	lr1.id IN ( " +
    		"SELECT ll.LABREQUISITION	FROM labrequisition_lab_requisition_items  ll 	WHERE ll.LABREQUISITIONITEMS IN( " +
    		" SELECT lr.LABREQUISITIONITEM 	FROM labrequisitionitem_lab_test_results lr INNER JOIN lab_test_result ltr " +
    		" ON lr.LABTESTRESULTS = ltr.id	WHERE 	lr.LABTESTRESULTS IN(:labTestResultIds) AND ltr.status IN ('NEW','SAMPLE_COLLECTED')))	";

    
    @Autowired(required = true)
    private LabRepository labRepository;
    private BillingService billingService;
    @Autowired
    private CommonCrudService commonCrudService;

    public void setLabRepository(LabRepository labRepository) {
        this.labRepository = labRepository;
    }

    public LabRepository getLabRepository() {
        return labRepository;
    }

    public BillingService getBillingService() {
        return billingService;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    @Resource
    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    @Override
    public void saveLabTest(LabTest labTest) {
//	for (PanelTechnician panelTechnician : labTestPanel.getPanelTechnicians()) {
//		for (Person person : panelTechnician.getTechnicians())
//			person.setSchedulable(true);
//	}
        labRepository.save(labTest);
    }

    @Override
    public List<LabTest> findLabTestByTestCode(String testCode) {
        return labRepository.findLabTestByTestCode(testCode);
    }

    @Override
    public LabTestPanel findLabPanelByPanelCode(String panelCode) {
        List<LabTestPanel> labTestPanels = labRepository.findLabPanelByPanelCode(panelCode);
        return UtilValidator.isNotEmpty(labTestPanels) ? labTestPanels.get(0) : null;
    }

    @Override
    public void saveLabTestResult(LabTestResult labTestResult) {
        labRepository.save(labTestResult);
    }

    @Override
    public LabTestResultItem getLabTestResultItem(LabTestResult labTestResult, String labTestName) {
        List<LabTestResultItem> labTestResultItems = labRepository.getLabTestResultItem(labTestResult, labTestName);
        if (UtilValidator.isNotEmpty(labTestResultItems)) return labTestResultItems.get(0);
        return null;
    }

    @Override
    public List<PatientLabOrder> getPatientLabOrderByLoggedInUserLocation(Set<Location> loggedinUserLocations) {
        return labRepository.getAllRequestedPatientLaborder(loggedinUserLocations);
    }


    @Override
    public List<Person> getPersonsForTestName(String testName, Collection<Location> locations) {
        Set<LabTestPanel> labTestPanels = new HashSet<LabTestPanel>();
        labTestPanels.addAll(labRepository.getLabTestPanelsForTest(testName));
        labTestPanels.addAll(labRepository.getLabTestPanelFor(testName));
        List<Person> persons = new ArrayList<Person>();
        List<PanelTechnician> panelTechnicians = new ArrayList<PanelTechnician>();
       /* for (LabTestPanel labTestPanel : labTestPanels)
            panelTechnicians.addAll(labTestPanel.getPanelTechnicians());
        for (PanelTechnician panelTechnician : panelTechnicians)
            if (UtilValidator.isEmpty(locations) || locations.contains(panelTechnician.getLocation()))
                persons.addAll(panelTechnician.getTechnicians());*/
        return persons;
    }

    @Override
    public List<PatientLabOrder> getPatientLabOrdersFor(Patient patient) {
        return labRepository.getPatientLabOrdersFor(patient);
    }

    @Override
    public LabTest getLabTestFor(String testName) {
        List<LabTest> labTests = labRepository.getLabTestsFor(testName);
        if (UtilValidator.isNotEmpty(labTests)) return labTests.get(0);
        return null;
    }

    @Override
    public List<LabTest> searchLabTestsBy(String testCode, String testName) {
        return labRepository.searchLabTestsBy(testCode, testName);
    }

    @Override
    public List<LabTestPanel> searchLabTestPanelsBy(String panelName) {
        return labRepository.searchLabTestPanelsBy(panelName);
    }

    @Override
    public List<LabOrderRequest> getLabRequestBy(Patient patient, Provider provider, PatientLabOrder.STATUS status,
                                                 String panelName, String panelCode) {
        return labRepository.getLabRequestBy(patient, provider, status, panelName, panelCode);

    }

    @Override
    public List<LabOrderRequest> getLabRequestsForProcessing() {
        return labRepository.getLabRequestsForProcessing();

    }

    @Override
    public List<LabOrderRequest> getOutPatientLabRequests(Patient patient) {
        return labRepository.getOutPatientLabRequests(patient);
    }

    @Override
    public List<LabOrderRequest> getNonInPatientLabOrders() {
        return labRepository.getNonInPatientLabOrders();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientLabOrder> getPatientLabOrderBy(Patient patient, Provider provider, PatientLabOrder.STATUS status,
                                                      String panelName, String panelCode) {
        return labRepository.getPatientLabOrderBy(patient, provider, status, panelName, panelCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabRequisition> getLabRequisitionForProcessing(Boolean inPatient, Date fromDate, Date thruDate, Patient patient, String ipNumber
    		,Set<Laboratories> loggedinUserLaboratories) {
        return labRepository.getLabRequisitionForProcessing(inPatient, fromDate, thruDate, patient, ipNumber, loggedinUserLaboratories);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LabRequisition> getCompletedLabRequisition(Boolean inPatient, Date fromDate, Date thruDate, Patient patient, String specimenLabel, String ipNumber) {
        return labRepository.getCompletedLabRequisition(inPatient, fromDate, thruDate, patient, specimenLabel, ipNumber);
    }

    /**
     * If Invoice is null its signifies that the Lab Test
     * is being done for the IN PATIENT.
     *
     * @param labOrderRequest
     * @param invoice
     */
    @Override
    public LabRequisition createLabRequisition(LabOrderRequest labOrderRequest, Invoice invoice) {
        int nextToken = 1;
        String token = "";
        if (invoice != null) {
            LabRequisition latestLabRequisition = labRepository.getLatestOPDLabRequisition(invoice.getInvoiceDate());
            if (latestLabRequisition != null)
                nextToken = Integer.valueOf(latestLabRequisition.getToken()) + 1;
            token = "" + nextToken;
        }
        
        LabRequisition labRequisition = LabRequisition.createLabRequisition(labOrderRequest, invoice != null ? invoice.getInvoiceNumber() : "", token);
       // labRepository.save(labRequisition);
        Set<LabRequisitionItem> labRequisitionItems = new HashSet<LabRequisitionItem>();
        for(PatientLabOrder patientLabOrder: labOrderRequest.getPatientLabOrders()){
        	LabRequisitionItem labRequisitionItem = LabRequisitionItem.createLabRequisitionItem(patientLabOrder);
        	labRepository.save(labRequisitionItem);
        	labRequisitionItems.add(labRequisitionItem);
        }
        labRequisition.setLabRequisitionItems(labRequisitionItems);
        labRepository.save(labRequisition);
        return labRequisition;
    }

    @Override
    public OBRSegment createOBRSegment(LabRequisition labRequisition, SpecimenModel specimenModel, LabTest labTest) {
        OBRSegment obrSegment = null;
        obrSegment = commonCrudService.findUniqueByEquality(OBRSegment.class, new String[]{"labTest", "specimen"}, new Object[]{labTest, specimenModel});
        if (obrSegment != null) {
            return obrSegment;
        }
        obrSegment = new OBRSegment();
        obrSegment.setLabTest(labTest);
        obrSegment.setPatient(labRequisition.getPatient());
        labTest = commonCrudService.getById(LabTest.class, labTest.getId());
       /* for (LabTest labTest : labTestPanel.getLabTests()) {
            OBXSegment obxSegment = new OBXSegment(labTest);
            obxSegment.setSoapNote(patientSoapNote);
            obrSegment.addOBX(obxSegment);
        }*/
        
        for (Investigation investigation : labTest.getInvestigations()) {
            OBXSegment obxSegment = new OBXSegment(investigation);
            obrSegment.addOBX(obxSegment);
        }
        obrSegment.setObservationDateTime(new Date());
        obrSegment.setSpecimen(specimenModel);
        return obrSegment;
    }

    @Override
    public List<SpecimenModel> getSpecimenList(LabRequisition labRequisition, LabTestResult selectedLabTestResult) {
        return labRepository.getSpecimenList(labRequisition, selectedLabTestResult);
    }

    @Override
    public List<OBRSegment> getLabResultForReviewByProvider(Provider provider) {
        return labRepository.getLabResultForReviewByProvider(provider);
    }

    @Override
    
    public List<OBXSegment> getLabResultForPatient(Patient patient) {
        return labRepository.getLabResultForPatient(patient);
    }

    @Override
    public List<OBXSegment> getLabResultFor(Patient patient, String testName, LabCategory labCategory, Integer numbOfDays) {
        return labRepository.getLabResultFor(patient, testName, labCategory, numbOfDays);
    }

    @Override
    public List<OBRSegment> getLabResultForReviewByProviderOrPatient(Provider provider, Patient patient) {
        return labRepository.getLabResultForReviewByProviderOrPatient(provider, patient);
    }

    
    @Override
    //@Transactional(propagation= Propagation.NEVER)
    public List<LabRequisition> getLabRequsitionStatus(List<LabTestResult> labTestResults){
    	return labRepository.getLabRequsitionStatus(labTestResults);
    }
    
   @Override
   public Integer getLabRequisitionCount(List<LabTestResult> labTestResults){
    	Integer totalCount = 0;
    	List<Long> labTestResultIds = new ArrayList<Long>();
	    for(LabTestResult labTestResult : labTestResults)
	    	labTestResultIds.add(labTestResult.getId());
	
    	SqlParameterSource namedParameters = new MapSqlParameterSource("labTestResultIds",labTestResultIds);
    	try{
    		totalCount = namedParameterJdbcTemplate.queryForInt(countLabRequisition, namedParameters);
    	}catch(Exception e){}
    
    	return totalCount;
    }
  
   @Override
    public String createLabOrderRequest(LabOrderDto labOrderDto){
    	
    	String clinicId = labOrderDto.getClinicId();
    	String clinicName = labOrderDto.getClinicName();
    	String clinicReferralId = null;
    	SqlParameterSource namedParameterClinicId = new MapSqlParameterSource().addValue("id", clinicId);
        Object clinicRecord = namedParameterJdbcTemplate.queryForList("select * from clinic where clinic_id = :id",namedParameterClinicId);
        if(UtilValidator.isEmpty(clinicRecord)){
        	SqlParameterSource namedParameter = new MapSqlParameterSource("clinicId",clinicId).addValue("referralId", clinicReferralId).addValue("clinicName", clinicName);
        	namedParameterJdbcTemplate.update(insertToClinc, namedParameter);
         }
    	
    	String afyaId = labOrderDto.getAfyaId();
    	Patient patient = patientService.populatePatientByAfyaId(afyaId);
        
    	LabOrderRequest labOrderRequest = new LabOrderRequest();
    	labOrderRequest.setPatient(patient);
	
    	List<LabLineItem> labLineItems = labOrderDto.getLabLineItems();
    	for(LabLineItem labLineItem: labLineItems){
    		PatientLabOrder patientLabOrder = new PatientLabOrder();
    		String testType = labLineItem.getTypeOfTest();
    		String code = labLineItem.getTestCode();
    		boolean homeService = labLineItem.isHomeService();
    		patientLabOrder.setHomeService(homeService);
    		String details = labLineItem.getDetails();
    		patientLabOrder.setTestNotes(details);
    		if("LabTest".equals(testType)){
    			LabTest labTest = commonCrudService.findUniqueByEquality(LabTest.class, new String[]{"testCode"}, new Object[]{code});
                patientLabOrder.setLabTest(labTest);
                labOrderRequest.addLaboratories(labTest.getLaboratory());
    		}
    		else if("LabPanel".equals(testType)){
    			LabTestPanel labTestPanel = commonCrudService.findUniqueByEquality(LabTestPanel.class, new String[]{"panelCode"}, new Object[]{code});
                patientLabOrder.setLabTestPanel(labTestPanel);
                for(LabTest test: labTestPanel.getTests())
                	labOrderRequest.addLaboratories(test.getLaboratory());
            }
    		else if("LabProfile".equals(testType)){
    			LabTestProfile labTestProfile = commonCrudService.findUniqueByEquality(LabTestProfile.class, new String[]{"profileCode"},new Object[]{code});
                patientLabOrder.setLabTestProfile(labTestProfile);
                for(LabTest test: labTestProfile.getTests())
                	labOrderRequest.addLaboratories(test.getLaboratory());
            	
    		}
    		labOrderRequest.addPatientLabOrder(patientLabOrder);
    	    patientLabOrder.setLabOrderRequest(labOrderRequest);
    	}
    	
    	labOrderRequest.setOrderStatus(LabOrderRequest.ORDERSTATUS.BILLING_REQUIRED);
        commonCrudService.save(labOrderRequest);
        return "success";
    }
}
