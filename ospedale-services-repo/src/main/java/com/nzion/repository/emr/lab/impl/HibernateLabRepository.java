package com.nzion.repository.emr.lab.impl;

import com.nzion.domain.Location;
import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.lab.LabRequisition.LabRequisitionStatus;
import com.nzion.domain.emr.soap.PatientLabOrder;
//import com.nzion.domain.emr.soap.PatientSoapNote;
import com.nzion.repository.emr.lab.LabRepository;
import com.nzion.repository.impl.HibernateBaseRepository;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class HibernateLabRepository extends HibernateBaseRepository implements LabRepository {

    public List<LabTest> findLabTestByTestCode(String testCode) {
        Criteria criteria = getSession().createCriteria(LabTest.class);
        criteria.add(Restrictions.eq("testCode", testCode));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.setCacheable(true).list();
    }

    public List<LabTestPanel> findLabPanelByPanelCode(String panelCode) {
        Criteria criteria = getSession().createCriteria(LabTestPanel.class);
        criteria.add(Restrictions.eq("code", panelCode));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabTestResultItem> getLabTestResultItem(LabTestResult labTestResult, String labTestName) {
        Criteria criteria = getSession().createCriteria(LabTestResultItem.class);
        criteria.add(Restrictions.eq("labTestResult", labTestResult));
        criteria.add(Restrictions.eq("labTestName", labTestName));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<PatientLabOrder> getAllRequestedPatientLaborder(Set<Location> loggedinUserLocations) {
        Criteria criteria = getSession().createCriteria(PatientLabOrder.class);
        criteria.add(Restrictions.eq("status", PatientLabOrder.STATUS.NEW));
        if (UtilValidator.isNotEmpty(loggedinUserLocations))
            criteria.createCriteria("soapSection").createCriteria("soapNote").createCriteria("schedule").add(
                    Restrictions.in("location", loggedinUserLocations));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.setCacheable(true).list();
    }


    @Override
    public List<LabTestPanel> getLabTestPanelFor(String testName) {
        Criteria criteria = getSession().createCriteria(LabTestPanel.class);
        criteria.add(Restrictions.eq("panelName", testName));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabTestPanel> getLabTestPanelsForTest(String testName) {
        Criteria criteria = getSession().createCriteria(LabTestPanel.class);
        criteria.createCriteria("labTests").add(Restrictions.ilike("testName", testName, MatchMode.START));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<PatientLabOrder> getPatientLabOrdersFor(Patient patient) {
        Criteria criteria = getSession().createCriteria(PatientLabOrder.class);
        criteria.add(Restrictions.eq("patient", patient));
        criteria.add(Restrictions.eq("status", PatientLabOrder.STATUS.NEW));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabTest> getLabTestsFor(String testName) {
        Criteria criteria = getSession().createCriteria(LabTest.class);
        criteria.add(Restrictions.eq("testName", testName));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabTest> searchLabTestsBy(String testCode, String testName) {
        Criteria criteria = getSession().createCriteria(LabTest.class);
        if (UtilValidator.isNotEmpty(testCode)) criteria.add(Restrictions.ilike("testCode", testCode, MatchMode.START));
        if (UtilValidator.isNotEmpty(testName)) criteria.add(Restrictions.ilike("testName", testName, MatchMode.START));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabTestPanel> searchLabTestPanelsBy(String panelName) {
        Criteria criteria = getSession().createCriteria(LabTestPanel.class);
        if (panelName != null) criteria.add(Restrictions.like("panelName", panelName, MatchMode.START));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabOrderRequest> getLabRequestBy(Patient patient, Provider provider, PatientLabOrder.STATUS status,
                                                 String panelName, String panelCode) {
        Criteria criteria = getSession().createCriteria(LabOrderRequest.class);
        if (patient != null)
            criteria.add(Restrictions.eq("patient", patient));
        if (provider != null) {
            criteria.add(Restrictions.eq("provider", provider));
        }
        Criteria panelCriteria = criteria.createCriteria("patientLabOrders");
        if (status != null)
            panelCriteria.add(Restrictions.eq("status", status));
        if (panelName != null)
            panelCriteria.add(Restrictions.ilike("testName", panelName, MatchMode.ANYWHERE));
        if (panelName != null)
            panelCriteria.add(Restrictions.ilike("testCode", panelCode, MatchMode.ANYWHERE));

        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.addOrder(Order.desc("createdTxTimestamp"));
        return criteria.list();
    }


    @Override
    public List<LabOrderRequest> getLabRequestsForProcessing() {
        Criteria criteria = getSession().createCriteria(LabOrderRequest.class);
        criteria.add(Restrictions.ne("orderStatus", LabOrderRequest.ORDERSTATUS.BILLING_REQUIRED));
        return criteria.list();
    }


    @Override
    public List<LabOrderRequest> getOutPatientLabRequests(Patient patient) {
        Criteria criteria = getSession().createCriteria(LabOrderRequest.class);
        if (patient != null)
            criteria.add(Restrictions.eq("patient", patient));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<LabOrderRequest> getNonInPatientLabOrders() {
        Criteria criteria = getSession().createCriteria(LabOrderRequest.class);
        criteria.add(Restrictions.isNull("patientAdmission"));
        return criteria.setCacheable(true).list();
    }

    @Override
    public List<PatientLabOrder> getPatientLabOrderBy(Patient patient, Provider provider, PatientLabOrder.STATUS status,
                                                      String panelName, String panelCode) {
        Criteria criteria = getSession().createCriteria(PatientLabOrder.class);
        if (status != null)
            criteria.add(Restrictions.eq("status", status));
        if (UtilValidator.isNotEmpty(panelName))
            criteria.add(Restrictions.ilike("testName", panelName, MatchMode.ANYWHERE));
        if (UtilValidator.isNotEmpty(panelCode))
            criteria.add(Restrictions.ilike("testCode", panelCode, MatchMode.ANYWHERE));

        Criteria orderCriteria = criteria.createCriteria("labOrderRequest");
        if (patient != null)
            orderCriteria.add(Restrictions.eq("patient", patient));
        if (provider != null) {
            orderCriteria.add(Restrictions.eq("provider", provider));
        }
        criteria.addOrder(Order.desc("createdTxTimestamp"));
        return criteria.list();
    }

    @Override
    public List<LabRequisition> getLabRequisitionForProcessing(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,
    		String ipNumber, Set<Laboratories> loggedinUserLaboratories) {
    	/*
    	List<String> laboratoryIds = new ArrayList<String> ();
    	for(Laboratories laboratory : loggedinUserLaboratories){
    		laboratoryIds.add(laboratory.getLaboratoryCode());
    	}
   	  	*/
        Criteria criteria = getSession().createCriteria(LabRequisition.class);
        criteria.setReadOnly(true);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            criteria.add(Restrictions.in("status", new Object[]{LabRequisition.LabRequisitionStatus.NEW, LabRequisition.LabRequisitionStatus.COLLECTION_DONE, LabRequisition.LabRequisitionStatus.IN_PROCESS}));
       // criteria.addOrder(Order.asc("token")).addOrder(Order.desc("createdTxTimestamp"));
        criteria.addOrder(Order.desc("createdTxTimestamp"));
        if(UtilValidator.isNotEmpty(patient))
            criteria.add(Restrictions.eq("patient", patient));
        if(fromDate != null) criteria.add(Restrictions.ge("createdTxTimestamp",UtilDateTime.getDayStart(fromDate)));
    	if(thruDate != null) criteria.add(Restrictions.le("createdTxTimestamp",UtilDateTime.getDayEnd(thruDate)));
    	
    	
    	//if(!laboratoryIds.isEmpty())
    	//	criteria.createCriteria("labOrderRequest").createCriteria("laboratories").add(Restrictions.in("laboratoryCode", laboratoryIds));

      // 	if (UtilValidator.isNotEmpty(loggedinUserLaboratories)){
       //    criteria.createCriteria("labOrderRequest","A").add(Restrictions.in("A.laboratories", loggedinUserLaboratories));
     //  	}	
    		//Criteria lreq = criteria.createCriteria("labOrderRequest");
    		//lreq.add(Restrictions.in("laboratories", loggedinUserLaboratories));
    		//Criteria lab= lreq.createCriteria("laboratories");
    		//lab.add(Restrictions.in("laboratoryCode", loggedinUserLaboratories));
    //	}
    	//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        //return criteria.setCacheable(true).list();
       	
    	return criteria.list();
   }

    @Override
    public LabRequisition getLatestOPDLabRequisition(Date date) {
        Criteria criteria = getSession().createCriteria(LabRequisition.class);
       // criteria.add(Restrictions.isNull("patientAdmission"));
        criteria.add(Restrictions.ge("createdTxTimestamp", UtilDateTime.getDayStart(date)));
        criteria.addOrder(Order.desc("createdTxTimestamp"));
        List<LabRequisition> l = criteria.list();
        if (l.size() == 0) return null;
        return l.get(0);
    }

    @Override
    public LabRequisition getLatestIPLabRequisition(Date date) {
        Criteria criteria = getSession().createCriteria(LabRequisition.class);
        criteria.add(Restrictions.ge("createdTxTimestamp", UtilDateTime.getDayStart(date)));
       // criteria.add(Restrictions.isNotNull("patientAdmission"));
        criteria.addOrder(Order.desc("createdTxTimestamp"));
        List<LabRequisition> l = criteria.list();
        if (l.size() == 0) return null;
        return l.get(0);
    }

    @Override
    public List<SpecimenModel> getSpecimenList(LabRequisition labRequisition, LabTestResult labTestResult) {
        Criteria criteria = getSession().createCriteria(SpecimenModel.class).setCacheable(true);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("labRequisition", labRequisition));
        criteria.createAlias("labTestResultSet", "labTestResultSet").add(Restrictions.eq("labTestResultSet.id", labTestResult.getId()));
        return criteria.list();
    }

    @Override
    public List<OBRSegment> getLabResultForReviewByProvider(Provider provider) {
        Criteria criteria = getSession().createCriteria(OBRSegment.class).setCacheable(true);
        criteria.add(Restrictions.eq("reviewedByProvider", Boolean.FALSE));
        criteria.add(Restrictions.eq("provider", provider));
        return criteria.list();
    }

    @Override
    public List<OBXSegment> getLabResultForPatient(Patient patient) {
        Criteria criteria = getSession().createCriteria(OBXSegment.class).setCacheable(true);
        criteria.add(Restrictions.eq("patient", patient));
        return criteria.list();
    }

    @Override
    public List<OBXSegment> getLabResultFor(Patient patient, String testName, LabCategory labCategory, Integer numbOfDays) {
        Criteria criteria = getSession().createCriteria(OBXSegment.class);
        if (patient != null)
            criteria.add(Restrictions.eq("patient", patient));
        if (UtilValidator.isNotEmpty(testName))
            criteria.add(Restrictions.ilike("obxName", testName, MatchMode.EXACT));
        if (labCategory != null) {
            criteria.add(Restrictions.eq("labCategory", labCategory));
        }
        if (numbOfDays != null) {
            int noDays = numbOfDays.intValue();
            Date beforDate = UtilDateTime.addDaysToDate(new Date(), -noDays);
            criteria.add(Restrictions.ge("observationDateTime", beforDate));
        }
        criteria.add(Restrictions.eq("active", true));
        criteria.addOrder(Order.desc("observationDateTime"));
        return criteria.list();
    }

   /* @Override
    public List<OBXSegment> getLabTestResultsForOP(Patient patient, PatientSoapNote patientSoapNote) {
        Criteria criteria = getSession().createCriteria(OBXSegment.class);

        Criteria orderCriteria = criteria.createCriteria("obrSegment").createCriteria("patientLabOrder");
        orderCriteria.add(Restrictions.eq("status", PatientLabOrder.STATUS.COMPLETED));

        Criteria reqCriteria = orderCriteria.createCriteria("labOrderRequest");
        if (patient != null)
            reqCriteria.add(Restrictions.eq("patient", patient));

        List<OBXSegment> results = criteria.list();
        results.addAll(getExternalLabTestResultsForOP(patient, patientSoapNote));
        return results;
    }

    public List<OBXSegment> getExternalLabTestResultsForOP(Patient patient, PatientSoapNote patientSoapNote) {
        Criteria criteria = getSession().createCriteria(OBXSegment.class);
        criteria.add(Restrictions.eq("externalTest", true));
        if (patient != null)
            criteria.add(Restrictions.eq("patient", patient));
        return criteria.list();
    }*/
    
    @Override
    public List<OBRSegment> getLabResultForReviewByProviderOrPatient(Provider provider,Patient patient) {
        Criteria criteria = getSession().createCriteria(OBRSegment.class).setCacheable(true);
        if(provider != null)
        	criteria.add(Restrictions.eq("provider", provider));
        if(patient != null)
        	criteria.add(Restrictions.eq("patient", patient));
        return criteria.list();
    }
    
    @Override
    public List<LabRequisition> getCompletedLabRequisition(Boolean inPatient,Date fromDate,Date thruDate,Patient patient,String specimenLabel,String ipNumber) {
        Criteria criteria = getSession().createCriteria(LabRequisition.class);
        criteria.setReadOnly(true);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
               
       /* if (!inPatient)
            criteria.add(Restrictions.isNull("patientAdmission"));
        else
        {
        criteria.add(Restrictions.isNotNull("patientAdmission"));
        Criteria paCriteria=criteria.createCriteria("patientAdmission");
    	if(ipNumber != null && UtilValidator.isNotEmpty(ipNumber)) 
    		paCriteria.add(Restrictions.like("accountNumber",ipNumber,MatchMode.ANYWHERE));
        }
*/       
        if(specimenLabel != null && UtilValidator.isNotEmpty(specimenLabel)){
        	Criteria slCriteria=criteria.createCriteria("specimenModelList");	
        	slCriteria.add(Restrictions.like("specimenLabel",specimenLabel,MatchMode.ANYWHERE));
        }
        
        criteria.add(Restrictions.in("status", new Object[]{LabRequisition.LabRequisitionStatus.COMPLETED}));
       // criteria.addOrder(Order.asc("token")).addOrder(Order.desc("createdTxTimestamp"));
        if(UtilValidator.isNotEmpty(patient))
            criteria.add(Restrictions.eq("patient", patient));
        if(fromDate != null) criteria.add(Restrictions.ge("createdTxTimestamp",UtilDateTime.getDayStart(fromDate)));
    	if(thruDate != null) criteria.add(Restrictions.le("createdTxTimestamp",UtilDateTime.getDayEnd(thruDate)));
    	criteria.addOrder(Order.desc("createdTxTimestamp"));
    	return criteria.list();
    }
      
    @Override
    public List<LabRequisition> getLabRequsitionStatus(List<LabTestResult> labTestResults){
    	   List<Long> labTestResultIds = new ArrayList<Long>();
    	    for(LabTestResult labTestResult : labTestResults)
    	    	labTestResultIds.add(labTestResult.getId());
    	
    	Criteria criteria = getSession().createCriteria(LabRequisition.class);
        criteria.setReadOnly(true);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criteria reqItemCriteria = criteria.createCriteria("labRequisitionItems");
        /*Criteria labTestResultCriteria = reqItemCriteria.createCriteria("labTestResults","LTR");
        labTestResultCriteria.add(Restrictions.in("LTR.id", labTestResultIds));
        labTestResultCriteria.add(Restrictions.in("LTR.status",new Object[]{LabRequisition.LabRequisitionStatus.NEW,
        		LabRequisition.LabRequisitionStatus.COLLECTION_DONE}));
        */
        reqItemCriteria.createAlias("labTestResults", "labTestResults").
        add(Restrictions.and(Restrictions.in("labTestResults.id", labTestResultIds), Restrictions.in("labTestResults.status",new Object[]{LabRequisition.LabRequisitionStatus.NEW,
        		LabRequisition.LabRequisitionStatus.COLLECTION_DONE})));
        
        return criteria.list();
    }
  
}
