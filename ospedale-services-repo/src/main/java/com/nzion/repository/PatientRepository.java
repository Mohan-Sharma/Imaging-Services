package com.nzion.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nzion.domain.*;
import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.domain.pms.Policy;

public interface PatientRepository extends BaseRepository {
	
	public void saveOrUpdate(Patient patient);

	public void saveOrUpdate(PMSPatientInfo patient);


	public List<Patient> getAllPatients();

	public void deleteAllPatient();

	public void deletePatient(Patient patient);

	public Patient getPatientByAccountNumber(String accountNumber);


	public PMSPatientInfo getPMSPatientInfo(long patientId);

	public Patient getPatientById(Long patientId);
	
	public List<Policy> getPoliciesForPatientId(Long patientId);

	public void savePolicy(Policy policy);
	
	public <T> List<T> getEntityInfo(Class<T> klass, Map<String, String> params);
	
	List<Policy> getPrimaryPolicyAndValidPolicies(Long patientId);
	
	
	List<Patient> searchPatientsBy(String accountNumber,String firstName,String lastName,Enumeration gender,Integer age);

    List<Patient> searchPatient(String accountNumber, String firstName, String lastName, Enumeration gender, Integer age,String mobileNumber, Integer startIndex, Integer noOfRecordsPerPage);

    Policy getPrimaryPolicy(Patient patient);
	
	
	List<PatientOtherContactDetail> getpatientOtherContactDetailFor(Patient patient);

	List<File> getFilesForDocumentType(Patient patient,String[] documentTypes);


	List<PatientRemainder> getPatientRemainders(Patient patient);

	List<PatientWithDraw> getPatientWithdrawByCriteria(Patient patient, Date fromDate, Date thruDate);
}
