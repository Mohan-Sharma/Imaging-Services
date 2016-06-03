package com.nzion.repository.emr.lab;

import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.soap.PatientLabOrder;
//import com.nzion.domain.emr.soap.PatientSoapNote;
import com.nzion.repository.BaseRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface LabRepository extends BaseRepository {

	List<LabTest> findLabTestByTestCode(String testCode);

	List<LabTestPanel> findLabPanelByPanelCode(String panelCode);

	/*List<PatientLabOrderAdapter> findLabTests(Class<? extends PatientLabOrderAdapter> klass, String searchField,
			String searchFieldValue);*/

	List<LabTestResultItem> getLabTestResultItem(LabTestResult labTestResult, String labTestName);

	List<PatientLabOrder> getAllRequestedPatientLaborder(Set<Location> loggedinUserLocations);

	List<LabTestPanel> getLabTestPanelFor(String testName);

	List<LabTestPanel> getLabTestPanelsForTest(String testName);

	List<PatientLabOrder> getPatientLabOrdersFor(Patient patient);

	List<LabTest> getLabTestsFor(String testName);

	List<LabTest> searchLabTestsBy(String testCode,String testName);
	
	List<LabTestPanel> searchLabTestPanelsBy(String panelName);

	List<LabOrderRequest> getLabRequestBy(Patient patient, Provider provider, PatientLabOrder.STATUS status, String panelName, String panelCode);
	
	List<PatientLabOrder> getPatientLabOrderBy(Patient patient, Provider provider, PatientLabOrder.STATUS status, String panelName, String panelCode);
	
	List<LabOrderRequest> getLabRequestsForProcessing();
	
	List<LabOrderRequest> getOutPatientLabRequests(Patient patient);
	
    List<LabOrderRequest> getNonInPatientLabOrders();
    
    List<LabRequisition> getLabRequisitionForProcessing(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,String ipNumber
    		,Set<Laboratories> loggedinUserLaboratories);

    LabRequisition getLatestOPDLabRequisition(Date date);
    LabRequisition getLatestIPLabRequisition(Date date);

    List<SpecimenModel> getSpecimenList(LabRequisition labRequisition, LabTestResult selectedLabTestResult);

    List<OBRSegment> getLabResultForReviewByProvider(Provider provider);

    List<OBXSegment> getLabResultForPatient(Patient patient);

    List<OBXSegment> getLabResultFor(Patient patient, String testName, LabCategory labCategory, Integer numbOfDays);

   // List<OBXSegment> getLabTestResultsForOP(Patient patient, PatientSoapNote patientSoapNote);
    
    List<OBRSegment> getLabResultForReviewByProviderOrPatient(Provider provider , Patient patient);
    
    List<LabRequisition> getCompletedLabRequisition(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,String specimenLabel,String ipNumber);
    
    List<LabRequisition> getLabRequsitionStatus(List<LabTestResult> labTestResults);
}
