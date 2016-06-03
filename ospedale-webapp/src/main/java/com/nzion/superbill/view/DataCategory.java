package com.nzion.superbill.view;

import com.google.gson.Gson;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.service.billing.impl.BillingServiceImpl;
import com.nzion.service.common.CommonCrudService;
import com.nzion.superbill.dto.*;
import com.nzion.superbill.util.SuperBillUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/14/14
 * Time: 2:45 PM
 * To change this template use File | Settings | File Templates.
 */
public enum DataCategory {

    DOCTOR {
        @Override
        public String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService) {
            List<Provider> providerList = commonCrudService.getAll(Provider.class);
            Collections.sort(providerList);
            List<DoctorDto> doctorDtos = new ArrayList<DoctorDto>();
            for (Provider provider : providerList) {
                DoctorDto doctorDto = SuperBillUtil.transformToDoctorDto(provider);
                if(doctorDto!=null && doctorDto.doctorId==9000l)
                	continue;
               // BigDecimal amount = billingService.getOutPatientConsultationCharge(provider).getPrice().getAmount();
                //doctorDto.amount = amount;
                doctorDtos.add(doctorDto);
            }
            return gson.toJson(doctorDtos);
        }
    },

    PATIENT {
        @Override
        public String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService) {
            List<Patient> patientList = commonCrudService.getAll(Patient.class);
            List<PatientDto> patientDtos = new ArrayList<PatientDto>();
            try {
                for (Patient patient : patientList) {
                    PatientDto patientDto = SuperBillUtil.transformToPatientDto(patient);
                    patientDtos.add(patientDto);
                }
            }catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return gson.toJson(patientDtos);
        }
    },

    REFERRAL {
        @Override
        public String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService) {
            List<Referral> referralList = commonCrudService.getAll(Referral.class);
            List<Provider> providerList = commonCrudService.getAll(Provider.class);
            List<ReferralDto> referralDtos = new ArrayList<ReferralDto>();
            for (Referral referral : referralList) {
                ReferralDto referralDto = SuperBillUtil.transformToReferralDto(referral);
                if(referralDto!=null && referralDto.referralId==9000l)
                	continue;
                referralDtos.add(referralDto);
            }
            for (Provider provider : providerList) {
                ReferralDto referralDto = SuperBillUtil.transformToReferralDto(provider);
                if(referralDto!=null && referralDto.referralId==9000l)
                	continue;
                referralDtos.add(referralDto);
            }
            return gson.toJson(referralDtos);
        }
    },
    LABTEST {
        @Override
        public String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService) {
            List<LabTestPanel> panelList = commonCrudService.getAll(LabTestPanel.class);
           // Collections.sort(panelList);
            List<LabTestDto> labTestDtos = new ArrayList<LabTestDto>();
            for (LabTestPanel labTestPanel : panelList) {
                LabTestDto labTestDto = SuperBillUtil.transformToLabTestDto(labTestPanel);
                labTestDtos.add(labTestDto);
            }
            return gson.toJson(labTestDtos);
        }
    }, GENDER {
        @Override
        public String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService) {
            List<Enumeration> genderList = commonCrudService.findByEquality(Enumeration.class, new String[]{"enumType"}, new String[]{"GENDER"});
            List<EnumerationDto> enumerationDtos = new ArrayList<EnumerationDto>();
            for (Enumeration enumeration : genderList) {
                EnumerationDto enumerationDto = SuperBillUtil.transformToEnumerationDto(enumeration);
                enumerationDtos.add(enumerationDto);
            }
            return gson.toJson(enumerationDtos);
        }
    };

    public abstract String getDataInJson(CommonCrudService commonCrudService, BillingServiceImpl billingService);

    Gson gson = new Gson();
}
