package com.ecosmos.superbill.view;

import com.google.common.collect.Lists;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.billing.Consultation;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.product.common.Money;
import com.nzion.service.billing.impl.BillingServiceImpl;
import com.nzion.service.common.CommonCrudService;
import com.nzion.superbill.view.DataCategory;
import com.nzion.util.UtilDateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/19/14
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataCategoryTest {

    private CommonCrudService commonCrudService;

    private BillingServiceImpl billingService;

    List<Provider> providers;

    Provider provider;

    Consultation consultation;

    @Before
    public void setUp(){
        commonCrudService = mock(CommonCrudService.class);
        billingService = mock(BillingServiceImpl.class);


        providers = Lists.newArrayList();
        consultation = new Consultation();
        provider = new Provider();
        Money money = new Money();
        money.setAmount(BigDecimal.valueOf(12));

        provider.setId(12l);
        provider.setFirstName("test doctor");
        provider.setLastName("one");
        provider.setMiddleName("xyz");
        consultation.setPrice(money);


        providers.add(provider);
    }

    @Test
    public void  givenListOfProviders_whenEachProviderIteratedAndSetThePropertiesToDoctorDto_itShouldConvertDoctorDtoAndReturnTheDataInJson(){


        String expectedJsonData = "[{\"doctorId\":12,\"firstName\":\"test doctor\",\"middleName\":\"xyz\",\"lastName\":\"one\",\"amount\":12}]";

        when(commonCrudService.getAll(Provider.class)).thenReturn(providers);
      //  when(billingService.getOutPatientConsultationCharge(provider)).thenReturn(consultation);

        String dataInJson = DataCategory.DOCTOR.getDataInJson(commonCrudService, billingService);

        assertThat(dataInJson, is(expectedJsonData));
    }

    @Test
    public void givenListOfReferrals_whenEachReferralIteratedAndSetThePropertiesToReferralDto_itShouldConvertReferralDtoAndReturnTheDataInJson(){
        List<Referral> referralList = Lists.newArrayList();
        Referral referral = new Referral();
        Enumeration enumeration = new Enumeration();
        enumeration.description = "Male";

        referral.setFirstName("test referral");
        referral.setLastName("one");
        referral.setId(900l);
        referral.setMiddleName("ABC");
        referral.setDateOfBirth(UtilDateTime.parseDate("20131010000000"));
        referral.setGender(enumeration);
        referralList.add(referral);

        when(commonCrudService.getAll(Referral.class)).thenReturn(referralList);
        when(commonCrudService.getAll(Provider.class)).thenReturn(providers);
      //  when(billingService.getConsultationChargeByProviderAndVisitType(provider, provider.getSoapNoteType())).thenReturn(consultation);

        String expectedJsonData = "[{\"referralType\":\"OUTSIDE\",\"referralId\":900,\"firstName\":\"test referral\",\"middleName\":\"ABC\",\"lastName\":\"one\"}," +
                "{\"referralType\":\"INHOUSE\",\"referralId\":12,\"firstName\":\"test doctor\",\"middleName\":\"xyz\",\"lastName\":\"one\"}]";

        String dataInJson = DataCategory.REFERRAL.getDataInJson(commonCrudService, billingService);

        assertThat(dataInJson, is(expectedJsonData));
    }

    @Test
    public void givenListOfLabTestPanels_whenEachLabTestPanelIteratedAndSetThePropertiesToLabTestDto_itShouldConvertLabTestDtoDtoAndReturnTheDataInJson(){
        List<LabTestPanel> labTestPanels = Lists.newArrayList();
        LabTestPanel labTestPanel = new LabTestPanel();
      /*  labTestPanel.setPanelName("ABC");
        labTestPanel.setCode("12");
        labTestPanel.setPrice(BigDecimal.valueOf(120));

        LabTestPanel labTestPanelTwo = new LabTestPanel();
        labTestPanelTwo.setPanelName("XYZ");
        labTestPanelTwo.setCode("120");
        labTestPanelTwo.setPrice(BigDecimal.valueOf(12));
*/
        labTestPanels.add(labTestPanel);
//        labTestPanels.add(labTestPanelTwo);

        String expectedJsonData = "[{\"panelCode\":\"12\",\"panelName\":\"ABC\",\"amount\":120},{\"panelCode\":\"120\",\"panelName\":\"XYZ\",\"amount\":12}]";

        when(commonCrudService.getAll(LabTestPanel.class)).thenReturn(labTestPanels);

        String dataInJson = DataCategory.LABTEST.getDataInJson(commonCrudService, billingService);

        assertThat(dataInJson, is(expectedJsonData));
    }
}
