package com.nzion.factory;

import java.util.List;

import com.nzion.domain.Patient;
import com.nzion.domain.base.FieldRestriction;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.util.Infrastructure;
import com.nzion.view.PatientViewObject;

public final class PatientFactory {

	private static final CommonCrudRepository repository = Infrastructure.getSpringBean("commonCrudRepository");

	public static PatientViewObject createPatientViewObject() {
	PatientViewObject patientViewObject = new PatientViewObject();
	Patient patient = patientViewObject.getPatient();
	List<FieldRestriction> defaultValues = repository.findByEquality(FieldRestriction.class, new String[] { "entityName", "restrictionType" }, 
					new Object[] {Patient.class.getSimpleName(), FieldRestriction.RESTRICTION_TYPE.DEFAULT_VALUE });
	for (FieldRestriction defaultValue : defaultValues)
		defaultValue.populate(patient);
	return patientViewObject;
	}
}