package com.nzion.service.emr;

import java.util.Date;
import java.util.List;

import com.nzion.domain.Patient;
import com.nzion.domain.docmgmt.PatientEducationDocument;
import com.nzion.enums.MATERIALCATEGORY;


/**
 * @author Sandeep Prusty
 * May 24, 2011
 */

public interface PlanAndRecommendationService {

	List<PatientEducationDocument> getPatientEducationDocumentsFor(MATERIALCATEGORY materialcategory, String languageCode,Patient patient,Date encounteredDate);

	List<PatientEducationDocument> getPatientEducationDocumentsFor(MATERIALCATEGORY materialcategory, String code, String desc, String pkOfItem,Date encounteredDate,Patient patient);
}