package com.nzion.service.emr.lab;

import com.nzion.domain.*;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.exception.TransactionException;
import com.nzion.external.LabOrderDto;

import java.util.*;

public interface LabService {

    void saveLabTest(LabTest labTest);

    List<LabTest> findLabTestByTestCode(String testCode);

    LabTestPanel findLabPanelByPanelCode(String panelCode);

    void saveLabTestResult(LabTestResult labTestResult);

    LabTestResultItem getLabTestResultItem(LabTestResult labTestResult, String labTestName);

    List<PatientLabOrder> getPatientLabOrderByLoggedInUserLocation(Set<Location> loggedinUserLocations);

    List<Person> getPersonsForTestName(String testName, Collection<Location> locations);

    List<PatientLabOrder> getPatientLabOrdersFor(Patient patient);

    LabTest getLabTestFor(String testName);

    List<LabTest> searchLabTestsBy(String testCode, String testName);

    List<LabTestPanel> searchLabTestPanelsBy(String panelName);

    List<LabOrderRequest> getLabRequestBy(Patient patient, Provider provider, PatientLabOrder.STATUS status, String panelName, String panelCode);

    List<PatientLabOrder> getPatientLabOrderBy(Patient patient, Provider provider, PatientLabOrder.STATUS status, String panelName, String panelCode);

    List<LabOrderRequest> getLabRequestsForProcessing();

    List<LabOrderRequest> getOutPatientLabRequests(Patient patient);

    List<LabOrderRequest> getNonInPatientLabOrders();

    List<LabRequisition> getLabRequisitionForProcessing(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,String ipNumber, 
    		Set <Laboratories>loggedinUserLaboratories);

    LabRequisition createLabRequisition(LabOrderRequest labOrderRequest,Invoice invoice) ;

    OBRSegment createOBRSegment(LabRequisition labRequisition,SpecimenModel specimenModel,LabTest labTest);

    List<SpecimenModel> getSpecimenList(LabRequisition labRequisition, LabTestResult selectedLabTestResult);

    List<OBRSegment> getLabResultForReviewByProvider(Provider provider);

    List<OBXSegment> getLabResultForPatient(Patient patient);
    List<OBXSegment> getLabResultFor(Patient patient, String testName, LabCategory labCategory, Integer numbOfDays);
    
    List<OBRSegment> getLabResultForReviewByProviderOrPatient(Provider provider , Patient patient);
    
    List<LabRequisition> getCompletedLabRequisition(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,String specimenLabel,String ipNumber);
    
    List<LabRequisition> getLabRequsitionStatus(List<LabTestResult> labTestResults);
    
    Integer getLabRequisitionCount(List<LabTestResult> labTestResults);

    String createLabOrderRequest(LabOrderDto labOrderDto) ;
    
}
