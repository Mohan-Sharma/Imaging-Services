package com.nzion.superbill.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nzion.domain.ContactFields;
import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.product.common.Money;
import com.nzion.service.PatientService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.superbill.command.InvoicePaymentCommand;
import com.nzion.superbill.dto.InvoiceItemDto;
import com.nzion.superbill.dto.PatientDto;
import com.nzion.superbill.dto.PaymentDto;
import com.nzion.superbill.util.SuperBillUtil;
import com.nzion.util.UtilValidator;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 6/14/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class SuperBillService {

    @Autowired
    private PatientService patientService;

    @Autowired
    private CommonCrudService commonCrudService;

    @Autowired
    private BillingService billingService;

    @Autowired
    private LabService labService;
    
    private final Long INHOUSE_REFERRAL_ID=9000L;

    public List<PatientDto> searchPatientBy(PatientDto searchItems) throws SQLException,IOException {
        Integer age = null;
        if (searchItems.age != null) {
            age = Integer.valueOf(searchItems.age);
        }
        List<Patient> patientList = patientService.searchPatient(searchItems.mrnNumber, searchItems.firstName, searchItems.lastName,
                searchItems.genderId, age, searchItems.mobileNumber, searchItems.startIndex, searchItems.noOfRecordsPerPage);
        List<PatientDto> patientDtos = new ArrayList<PatientDto>();
        for (Patient patient : patientList) {
            PatientDto patientDto = SuperBillUtil.transformToPatientDto(patient);
            patientDtos.add(patientDto);
        }
        return patientDtos;
    }

    public Long addPatient(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setFirstName(patientDto.firstName);
        patient.setLastName(patientDto.lastName);
        patient.setPersonalTitle(patientDto.personalTitle);
        Enumeration gender = commonCrudService.getById(Enumeration.class, patientDto.genderId);
        patient.setGender(gender);
        patient.setDateOfBirth(UtilDateTime.getDateOfBirth(Integer.valueOf(patientDto.age)));
        if (patientDto.mobileNumber != null){
            ContactFields contactFields = new ContactFields();
            contactFields.setMobileNumber(patientDto.mobileNumber);
            patient.setContacts(contactFields);
        }
        return patientService.savePatient(patient);
    }

    public Long generateInvoice(InvoicePaymentCommand invoicePaymentCommand) {

        List<InvoiceItemDto> invoiceItemDtos = invoicePaymentCommand.invoiceItems;
        List<InvoiceItemDto> consulationInvoiceItemList = InvoicePaymentCommand.filterInvoiceItemDtosByInvoiceType(invoiceItemDtos, InvoiceType.OPD_CONSULTATION);
        List<InvoiceItemDto> labInvoiceItemList = InvoicePaymentCommand.filterInvoiceItemDtosByInvoiceType(invoiceItemDtos, InvoiceType.OPD_LAB_CHARGES);
        List<InvoiceItemDto> casualtyInvoiceItemList = InvoicePaymentCommand.filterInvoiceItemDtosByInvoiceType(invoiceItemDtos, InvoiceType.CASUALTY);

        Patient patient = InvoicePaymentCommand.getPatient(invoicePaymentCommand.patient, patientService);
        Long doctorId = InvoicePaymentCommand.getDoctorId(consulationInvoiceItemList);
        Provider doctor = InvoicePaymentCommand.getProvider(doctorId, commonCrudService);
       
        Referral referredByReferral=null;
        Provider referredByDoctor=null;
        
        if("OUTSIDE".equals(invoicePaymentCommand.referralType)) 
        	referredByReferral=InvoicePaymentCommand.getReferral(invoicePaymentCommand.referredBy, commonCrudService);
        
        else if("INHOUSE".equals(invoicePaymentCommand.referralType))
        	referredByDoctor=InvoicePaymentCommand.getProvider(invoicePaymentCommand.referredBy, commonCrudService);
        
        if(referredByReferral == null && referredByDoctor==null){
        	referredByReferral = InvoicePaymentCommand.getReferral(INHOUSE_REFERRAL_ID, commonCrudService);	
        }

        if(doctor==null) doctor=referredByDoctor;
        
        Invoice invoice = InvoicePaymentCommand.createInvoice(patient, doctor,referredByReferral);

        List<InvoiceItem> consultationInvoiceItems = InvoiceItemDto.createInvoiceItem(consulationInvoiceItemList, invoice, commonCrudService,null,null);
        invoice.addInvoiceItem(consultationInvoiceItems);
        List<InvoiceItem> labInvoiceItems = InvoiceItemDto.createInvoiceItem(labInvoiceItemList, invoice, commonCrudService,referredByReferral,referredByDoctor);
        invoice.addInvoiceItem(labInvoiceItems);
        List<InvoiceItem> casualtyInvoiceItems = InvoiceItemDto.createInvoiceItem(casualtyInvoiceItemList, invoice, commonCrudService,null,null);
        invoice.addInvoiceItem(casualtyInvoiceItems);
        invoice.setCollectedByUser(Infrastructure.getUserName());
        invoice = commonCrudService.save(invoice);
        if (UtilValidator.isNotEmpty(labInvoiceItems)) {
            LabOrderRequest labOrderRequest = InvoicePaymentCommand.createLabOrderRequest(labInvoiceItemList, patient, billingService, referredByReferral,referredByDoctor);
            commonCrudService.save(labOrderRequest);
            labService.createLabRequisition(labOrderRequest, invoice);

        }

        receiveInvoicePayments(invoicePaymentCommand.payments, invoice);
        return invoice.getId();
    }

    public void receiveInvoicePayments(List<PaymentDto> paymentDtos, Invoice invoice) {

        for (PaymentDto paymentDto : paymentDtos) {
            List<Enumeration> paymentMethodList = commonCrudService.findByEquality(Enumeration.class, new String[]{"enumCode", "enumType"}, new Object[]{paymentDto.paymentMode.getPaymentMethodCode(), "PAYMENT_MODE"});
            Enumeration paymentMethod = UtilValidator.isNotEmpty(paymentMethodList) ? paymentMethodList.get(0) : null;
            InvoicePayment invoicePayment = new InvoicePayment(paymentMethod, invoice, new Money(paymentDto.amount), paymentDto.paymentMode);
            invoice.addInvoicePayment(invoicePayment);
            invoice.getCollectedAmount().setAmount(invoice.getCollectedAmount().getAmount().add(invoicePayment.getAmount().getAmount()));
        }
        invoice.setInvoiceStatus(InvoiceStatusItem.RECEIVED.toString());
        billingService.saveInvoiceStatus(invoice, InvoiceStatusItem.RECEIVED);

    }

}
