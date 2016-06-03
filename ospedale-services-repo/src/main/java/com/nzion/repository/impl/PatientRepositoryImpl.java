package com.nzion.repository.impl;

import com.nzion.domain.*;
import com.nzion.domain.Party.PartyType;
import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.domain.pms.Policy;
import com.nzion.repository.PatientRepository;
import com.nzion.util.Constants;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PatientRepositoryImpl extends HibernateBaseRepository implements PatientRepository {

    public PatientRepositoryImpl() {
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getEntityInfo(Class<T> klass, Map<String, String> params) {
        Criteria criteria = getSession().createCriteria(klass);
        if (klass.equals(Person.class)) {
            criteria.add(Restrictions.ne("partyType", PartyType.PATIENT));
            criteria.add(Restrictions.ne("partyType", PartyType.PROVIDER));
        }
        criteria.add(Restrictions.isNotNull("partyType"));
        if (UtilValidator.isNotEmpty(params)) {
            for (Entry<String, String> param : params.entrySet()) {
                criteria.add(Restrictions.ilike(param.getKey(), param.getValue(), MatchMode.ANYWHERE));
            }
        }
        criteria.addOrder(Order.asc("partyType"));
        criteria.addOrder(Order.asc("accountNumber"));
        return criteria.list();
    }

    public void saveOrUpdate(Patient patient) {
        save(patient);
    }

    public void saveOrUpdate(PMSPatientInfo patientInfo) {
        save(patientInfo);
    }

    @SuppressWarnings("unchecked")
    public List<Patient> getAllPatients() {
        return getSession().createCriteria(Patient.class).list();
    }

    public void deleteAllPatient() {
        getSession().createQuery("delete from Patient").executeUpdate();
    }

    public void deletePatient(Patient patient) {
        getSession().delete(patient);
    }

    public Patient getPatientByAccountNumber(String accountNumber) {
        return (Patient) getSession().createCriteria(Patient.class).add(Restrictions.eq("accountNumber", accountNumber))
                .uniqueResult();

    }

   
    public PMSPatientInfo getPMSPatientInfo(long patientId) {
        return (PMSPatientInfo) getSession().createCriteria(PMSPatientInfo.class).add(
                Restrictions.eq("patient.id", patientId)).uniqueResult();
    }

    public Patient getPatientById(Long patientId) {
        return super.findByPrimaryKey(Patient.class, patientId);
    }

    @SuppressWarnings("unchecked")
    public List<Policy> getPrimaryPolicyAndValidPolicies(Long patientId) {
        return getSession().createCriteria(Policy.class).add(Restrictions.eq("patient.id", patientId)).add(
                Restrictions.or(Restrictions.or(Restrictions.eq("priority", Constants.PRIMARY_POLICY), Restrictions
                        .isNull("historicalModel.thruDate")), Restrictions.ge("historicalModel.thruDate", new Date())))
                .addOrder(Order.asc("priority")).list();
    }

    @SuppressWarnings("unchecked")
    public List<Policy> getPoliciesForPatientId(Long patientId) {
        return getSession().createCriteria(Policy.class).add(Restrictions.eq("patient.id", patientId)).addOrder(
                Order.asc("priority")).list();
    }

    public void savePolicy(Policy policy) {
        Patient patient = getPatientById(policy.getPatient().getId());
        policy.setPatient(patient);
        super.save(policy);
    }


    @SuppressWarnings("unchecked")
    public List<Patient> searchPatientsBy(String accountNumber, String firstName, String lastName, Enumeration gender,
                                          Integer age) {

        return searchPatient(accountNumber, firstName, lastName, gender, age, null, null, null);
    }

    @SuppressWarnings("unchecked")
    public List<Patient> searchPatient(String accountNumber, String firstName, String lastName, Enumeration gender,
                                       Integer age, String mobileNumber, Integer startIndex, Integer noOfRecordsPerPage) {
        Criteria criteria = getSession().createCriteria(Patient.class);
        if (UtilValidator.isNotEmpty(accountNumber))
            criteria.add(Restrictions.like("accountNumber", accountNumber, MatchMode.ANYWHERE));
        if (UtilValidator.isNotEmpty(firstName))
            criteria.add(Restrictions.like("firstName", firstName, MatchMode.ANYWHERE));
        if (UtilValidator.isNotEmpty(lastName))
            criteria.add(Restrictions.like("lastName", lastName, MatchMode.ANYWHERE));
        if (gender != null) criteria.add(Restrictions.eq("gender", gender));
        if (UtilValidator.isNotEmpty(mobileNumber))
            criteria.add(Restrictions.eq("contacts.mobileNumber", mobileNumber));
        if (age != null) {
            Date[] range = UtilDateTime.findDateRangeForAge(age);
            criteria.add(Restrictions.le("dateOfBirth", range[1]));
            criteria.add(Restrictions.ge("dateOfBirth", range[0]));
        }
        if (startIndex != null && noOfRecordsPerPage != null) {
            criteria.setFirstResult(startIndex - 1);
            criteria.setMaxResults(noOfRecordsPerPage);
        }
        return criteria.list();
    }

    public Policy getPrimaryPolicy(Patient patient) {
        return (Policy) getSession().createCriteria(Policy.class).add(Restrictions.eq("patient", patient)).add(
                Restrictions.eq("priority", Constants.PRIMARY_POLICY)).uniqueResult();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PatientOtherContactDetail> getpatientOtherContactDetailFor(Patient patient) {
        Criteria criteria = getSession().createCriteria(PatientOtherContactDetail.class);
        criteria.add(Restrictions.eq("patient", patient));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<File> getFilesForDocumentType(Patient patient, String[] documentTypes) {
        return getSession().createCriteria(File.class).add(Restrictions.in("documentType", documentTypes)).add(
                Restrictions.eq("imported", false)).createCriteria("folder").add(Restrictions.eq("patient", patient))
                .addOrder(Order.desc("id")).list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<PatientRemainder> getPatientRemainders(Patient patient) {
        return getSession().createCriteria(PatientRemainder.class).add(Restrictions.eq("patient", patient)).add(Restrictions.ge("expectedFollowUpDate", UtilDateTime.getDayEnd(new Date()))).addOrder(Order.asc("expectedFollowUpDate")).list();
    }

    @Override
    public List<PatientWithDraw> getPatientWithdrawByCriteria(Patient patient, Date fromDate, Date thruDate) {
        Criteria criteria = getSession().createCriteria(PatientWithDraw.class);
        if(patient != null)
            criteria.add(Restrictions.eq("patient",patient));
        if(fromDate != null)
            criteria.add(Restrictions.ge("withdrawDate", fromDate));
        if(thruDate != null)
            criteria.add(Restrictions.le("withdrawDate", thruDate));
        return criteria.list();

    }
}
