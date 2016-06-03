package com.nzion.service;

import com.nzion.domain.*;
import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.exception.TransactionException;
import com.nzion.repository.PatientRepository;
import com.nzion.view.PatientViewObject;

import java.util.Date;
import java.util.List;
public interface PatientService {

	public void saveOrUpdate(PatientViewObject patient);

    public Long savePatient(Patient patient);

	public void saveOrUpdate(Patient patient,String eventMessage);
	@Deprecated
	/**
	 * Use commonCrudService.getByAccountNumber instead
	 * **/
	public Patient getPatient(String accountNumber);

	public Patient getPatientById(Long patientId);

	public void setPatientRepository(PatientRepository repository);

	public List<Patient> getAllPatients();

	void deletePatient(Patient patient);

	void deleteAllPatient();

	public void saveOrUpdate(PMSPatientInfo patient);



	public PMSPatientInfo getPmsPatient(Long patientId);


	public void createPatient(PatientViewObject patientVO)throws TransactionException;
	
	List<Patient> searchPatientsBy(String accountNumber,String firstName,String lastName,Enumeration gender,Integer age);

    List<Patient> searchPatient(String accountNumber, String firstName, String lastName, Long genderId, Integer age,String mobileNumber, Integer lowerLimit, Integer upperLimit);
	

	
	PatientOtherContactDetail getPatientOtherContactDetailFor(Patient patient);
	
	List<File> getFilesForDocumentType(Patient patient, String documentType);

	
	List<PatientRemainder> getPatientRemainders(Patient patient);
	
	void createUserLoginForPatient(Patient patient);

    Patient populatePatientByAfyaId(String afyaId);

	List<PatientWithDraw> getPatientWithdrawByCriteria(Patient patient,Date fromDate, Date thruDate);

	
}
