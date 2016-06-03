package com.nzion.service.impl;

import com.nzion.domain.*;
import com.nzion.domain.pms.PMSPatientInfo;
import com.nzion.dto.CivilUserDto;
import com.nzion.enums.EventType;
import com.nzion.exception.TransactionException;
import com.nzion.repository.PatientRepository;
import com.nzion.service.PatientService;
import com.nzion.service.UserLoginService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.ApplicationEvents;
import com.nzion.util.AfyaPortalServiceConsumer;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;
import com.nzion.view.PatientViewObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("patientService")
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private CommonCrudService commonCrudService;

    private UserLoginService userLoginService;

    @Autowired(required = true)
    private BillingService billingService;


    public Patient getPatient(String accountNumber) {
        return commonCrudService.getByAccountNumber(Patient.class, accountNumber);
    }

    @Resource(name = "patientRepo")
    @Required
    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void saveOrUpdate(Patient patient, String eventMessage) {
        patientRepository.saveOrUpdate(patient);
        if (UtilValidator.isNotEmpty(eventMessage))
            ApplicationEvents.postEvent(EventType.PATIENT_RECORD, patient, Infrastructure.getUserLogin(), eventMessage);
    }

    public void saveOrUpdate(PatientViewObject patientVO) {
        Patient patient = patientVO.getPatient();
        patientRepository.saveOrUpdate(patient);
        createPatientForSubscriptionType(patientVO);
    }

    public void createPatient(PatientViewObject patientVO) throws TransactionException {
        Patient patient = patientVO.getPatient();
        patientRepository.save(patient);
        createPatientForSubscriptionType(patientVO);
    }

    public void createPatientForSubscriptionType(PatientViewObject patientVO) {
        saveOrUpdate(patientVO.getPmsPatientInfo());
    }

    public void saveOrUpdate(PMSPatientInfo patient) {
        patientRepository.saveOrUpdate(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    public void deleteAllPatient() {
        patientRepository.deleteAllPatient();
    }

    public void deletePatient(Patient patient) {
        patientRepository.deletePatient(patient);

    }

  
    public PMSPatientInfo getPmsPatient(Long patientId) {
        return patientRepository.getPMSPatientInfo(patientId);
    }

    public Patient getPatientById(Long patientId) {
        return patientRepository.getPatientById(patientId);
    }

    @Resource
    @Required
    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

   

    @Override
    public List<Patient> searchPatientsBy(String accountNumber, String firstName, String lastName, Enumeration gender,
                                          Integer age) {
        return patientRepository.searchPatientsBy(accountNumber, firstName, lastName, gender, age);
    }

    @Override
    public List<Patient> searchPatient(String accountNumber, String firstName, String lastName,Long genderId, Integer age, String mobileNumber, Integer lowerLimit, Integer upperLimit){
        if (genderId != null) {
            Enumeration  gender = commonCrudService.getById(Enumeration.class, genderId);
            return patientRepository.searchPatient(accountNumber, firstName, lastName, gender, age, mobileNumber,lowerLimit,upperLimit);
        }
        else {
            return patientRepository.searchPatient(accountNumber, firstName, lastName, null, age, mobileNumber,lowerLimit,upperLimit);
        }
    }


    @Override
    public PatientOtherContactDetail getPatientOtherContactDetailFor(Patient patient) {
        List<PatientOtherContactDetail> patientOtherContactDetails = patientRepository.getpatientOtherContactDetailFor(patient);
        if (UtilValidator.isNotEmpty(patientOtherContactDetails)) return patientOtherContactDetails.get(0);
        return new PatientOtherContactDetail(patient);
    }

    @Override
    public List<File> getFilesForDocumentType(Patient patient, String documentType) {
        return patientRepository.getFilesForDocumentType(patient, new String[]{documentType});
    }

   

    public UserLoginService getUserLoginService() {
        return userLoginService;
    }

    @Resource
    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Override
    public List<PatientRemainder> getPatientRemainders(Patient patient) {
        return patientRepository.getPatientRemainders(patient);
    }

    @Override
    public void createUserLoginForPatient(Patient patient) {
        patient = patientRepository.getPatientById(patient.getId());
        if (StringUtils.isNotBlank(patient.getContacts().getEmail())) {
            UserLogin userLogin = new UserLogin();
            String username = null;
            String firstName = patient.getFirstName();
            String lastName = patient.getLastName();
            if (firstName.length() >= 2 && lastName.length() >= 2)
                username = firstName.substring(0, 2) + lastName.substring(0, 2);
            else {
                username = firstName.substring(0, 1) + lastName.substring(0, 1);
                username = RandomStringUtils.random(6, username);
            }
            boolean userLoginExists = true;
            int i = 1;
            Infrastructure.getSessionFactory().getCurrentSession().disableFilter("PracticeFilter");
            while (userLoginExists) {
                try {
                    userLoginService.getUserByUsername(username);
                    username = username + "00" + i;
                    i++;
                } catch (UsernameNotFoundException unfe) {
                    userLoginExists = false;
                }
            }
            username = username.toUpperCase();
            userLogin.setUsername(username);
            userLogin.setPerson(patient);
            userLogin.addRole(Roles.PATIENT);
            userLoginService.createUserLogin(userLogin);
            patientRepository.save(patient);
        }

    }

    public Long savePatient(Patient patient) {
        patient = commonCrudService.save(patient);
        return patient.getId();
    }

    @Override
    public Patient populatePatientByAfyaId(String afyaId) {
        Patient patient = commonCrudService.findUniqueByEquality(Patient.class,new String[]{"afyaId"},new Object[]{afyaId});
        if(patient == null){
            CivilUserDto civilUserDto = AfyaPortalServiceConsumer.fetchUserByAfyaId(afyaId);
            patient = new Patient();
            civilUserDto.setPropertiesToPatient(patient);
            patient.setAfyaId(afyaId);
            commonCrudService.save(patient);
        }
        return patient;
    }

    @Override
    public List<PatientWithDraw> getPatientWithdrawByCriteria(Patient patient, Date fromDate, Date thruDate) {
        return patientRepository.getPatientWithdrawByCriteria(patient,fromDate,thruDate);
    }
}
