package com.nzion.service.meaningful;

import java.util.List;
import java.util.Set;

import com.nzion.domain.Patient;
import com.nzion.report.search.view.PatientSearchVO;

public interface ReportService {
	
	//Set<EncounterSearchResult> getPatientSoapNote(PatientEncounterSearchVo patientEncounterSearchVo);
	
	List<String> getAllObxTestNames();
	
	List<Patient> searchPatient(PatientSearchVO patientSearchVO);
 
}
