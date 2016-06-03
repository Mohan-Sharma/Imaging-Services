package com.nzion.superbill.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.service.PatientService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.superbill.dto.InvoiceItemDto;
import com.nzion.superbill.dto.PaymentDto;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilValidator;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/14/14
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class InvoicePaymentCommand {

    public List<InvoiceItemDto> invoiceItems;

    public List<PaymentDto> payments;

    public Long referredBy;

    public Long patient;

    public String referralType;

    public static Provider getProvider(Long providerId, CommonCrudService commonCrudService) {
        if (providerId == null)
            return null;
        Provider provider = commonCrudService.getById(Provider.class, providerId);
        return provider;
    }

    public static Referral getReferral(Long referralId, CommonCrudService commonCrudService) {
        if (referralId == null)
            return null;
        Referral referral = commonCrudService.getById(Referral.class, referralId);
        return referral;
    }

    public static Patient getPatient(Long patientId, PatientService patientService) {
        Patient patient = patientService.getPatientById(patientId);
        return patient;
    }

    public static Invoice createInvoice(Patient patient, Provider provider,Referral referral) {

        Invoice invoice = new Invoice(null, InvoiceType.OPD.name(), provider, patient, Infrastructure.getSelectedLocation());
        invoice.setInvoiceType(InvoiceType.OPD);
        invoice.setInvoiceDate(new Date());
        invoice.setInvoiceStatus(InvoiceStatusItem.READY.name());
        if(referral!=null)
        invoice.setReferralConsultantId(referral.getId());
        return invoice;
    }


    public static List<InvoiceItemDto> filterInvoiceItemDtosByInvoiceType(List<InvoiceItemDto> invoiceItemDtos, InvoiceType desiredType) {
        List<InvoiceItemDto> itemDtoList = new ArrayList<InvoiceItemDto>();
        for (InvoiceItemDto invoiceItemDto : invoiceItemDtos) {
            if (!desiredType.equals(invoiceItemDto.itemType)) {
                continue;
            }
            itemDtoList.add(invoiceItemDto);
        }
        return itemDtoList;
    }

    public static Long getDoctorId(List<InvoiceItemDto> consultationInvoiceItems) {
        if (UtilValidator.isEmpty(consultationInvoiceItems)) {
            return null;
        }
        return Long.valueOf(consultationInvoiceItems.get(0).itemCode);
    }


    public static LabOrderRequest createLabOrderRequest(List<InvoiceItemDto> labInvoiceItemDtos, Patient patient, BillingService billingService, Referral referredBy, Provider provider) {
        LabOrderRequest labOrderRequest = new LabOrderRequest();
        for (InvoiceItemDto labInvoiceItemDto : labInvoiceItemDtos) {
            PatientLabOrder patientLabOrder = new PatientLabOrder();
            //patientLabOrder.setPatient(patient);
            LabTestPanel labTestPanel = billingService.getLabtestPanelByPanelName(labInvoiceItemDto.itemName);
            //patientLabOrder.setLabTestPanel(labTestPanel);
            
            patientLabOrder.setLabOrderRequest(labOrderRequest);
            patientLabOrder.setBillingStatus(PatientLabOrder.BILLINGSTATUS.PAID);
            labOrderRequest.addPatientLabOrder(patientLabOrder);
        }
        labOrderRequest.setReferral(referredBy);
        labOrderRequest.setProvider(provider);
        labOrderRequest.setPatient(patient);
        return labOrderRequest;

    }

}
