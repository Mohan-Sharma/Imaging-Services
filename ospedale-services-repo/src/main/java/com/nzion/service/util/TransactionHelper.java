package com.nzion.service.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nzion.domain.Location;
import com.nzion.domain.Provider;
import com.nzion.domain.billing.AcctgTransTypeEnum;
import com.nzion.domain.billing.AcctgTransaction;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.domain.billing.DebitCreditEnum;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.billing.PaymentType;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 12/24/12
 * Time: 3:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransactionHelper {

    private static String getProviderId(Invoice invoice) {
        String providerId = "";
        if (invoice.getConsultant() != null) {
            Provider provider = (Provider) invoice.getConsultant();
            providerId = provider.getId().toString();
        }
        return providerId;
    }

    private static String getSpecialityCode(Invoice invoice,CommonCrudService commonCrudService) {
    	BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
    
    	/*if(invoice.getItemType().equals("com.nzion.domain.emr.soap.PatientSoapNote") && !"patientVisit".equals(billingDisplayConfig.getIsConsultationPriceTriggered())){
    		PatientSoapNote patientSoapNote=null;
    		Long soapNoteId=Long.parseLong(invoice.getItemId());
			try {
					patientSoapNote = (PatientSoapNote)commonCrudService.getById(Class.forName(invoice.getItemType()),soapNoteId);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(patientSoapNote!=null)
			return patientSoapNote.getSpeciality().getCode();
			}*/
    	
        return "";
    }

    
    
    
    public static AcctgTransaction createAccountingTransactionEntryForWriteoff (Invoice invoice, List<InvoicePayment> invoicePayments, CommonCrudService commonCrudService) {
  
    	String providerId = getProviderId(invoice);
        String specialityCode = getSpecialityCode(invoice,commonCrudService);
        String patientId = getPatientId(invoice);
        List<AcctgTransactionEntry> transactionEntries = new ArrayList<AcctgTransactionEntry>();
        
        AcctgTransTypeEnum accountingTransactionType = invoice.getInvoiceType().getAccountingTransType();
        Long accountingTransactionId = createAccountingTransactionFromInvoice(invoice.getId(), accountingTransactionType, commonCrudService, new Date(), invoice.getLocation());
        AcctgTransaction acctgTrans = commonCrudService.getById(AcctgTransaction.class, accountingTransactionId);
        String acctgTransId = accountingTransactionId.toString();

        List<AcctgTransactionEntry> accountingTransactionEntryFromWriteoff = createAccountingTransactionEntryForPayment(invoicePayments, providerId, patientId, specialityCode, acctgTransId);
        transactionEntries.addAll(accountingTransactionEntryFromWriteoff);
        
        List<AcctgTransactionEntry> patientPaidTranactionEntriesFromWriteoff = createPaidTransactionEntriesForPatient(invoicePayments,invoice,providerId,
        		patientId, specialityCode, acctgTransId);
        transactionEntries.addAll(patientPaidTranactionEntriesFromWriteoff);
                acctgTrans.addAllAccountingTransactionEntries(transactionEntries);
        return acctgTrans;
}
    
    
    private static String getPatientId(Invoice invoice) {
        String patientId = "";
        if(UtilValidator.isNotEmpty(invoice.getExternalPatient()))
        	patientId=invoice.getExternalPatient();
        if(invoice.getPatient()!=null)
        	   	patientId=invoice.getPatient().getId().toString();
                    
        return patientId;
    }
    
    public static AcctgTransaction createAccountingTransactionAndTransactionEntries(Invoice invoice, List<InvoicePayment> invoicePayments, CommonCrudService commonCrudService) {

        String providerId = getProviderId(invoice);
        String specialityCode = getSpecialityCode(invoice,commonCrudService);
        String patientId =getPatientId(invoice);
        
        AcctgTransTypeEnum accountingTransactionType = invoice.getInvoiceType().getAccountingTransType();

        Long accountingTransactionId = createAccountingTransactionFromInvoice(invoice.getId(), accountingTransactionType, commonCrudService, new Date(), invoice.getLocation());
        AcctgTransaction acctgTrans = commonCrudService.getById(AcctgTransaction.class, accountingTransactionId);
        String acctgTransId = accountingTransactionId.toString();
        
        

        List<AcctgTransactionEntry> accountingTransactionEntriesFromInvoice = createAccountingTransactionEntryForInvoiceItem(invoice.getInvoiceItems(),
        		providerId, patientId, specialityCode, acctgTransId);
        BigDecimal totalPatientPayableAmount = getPatientPayableAmount(accountingTransactionEntriesFromInvoice);
        
        List<AcctgTransactionEntry> patientPayableTranactionEntries = createPayableTransactionEntriesForPatient(invoice, totalPatientPayableAmount
        		,providerId, patientId, specialityCode, acctgTransId);
        accountingTransactionEntriesFromInvoice.addAll(patientPayableTranactionEntries);

        List<AcctgTransactionEntry> accountingTransactionEntriesFromInvoicePayment = createAccountingTransactionEntryForPayment(invoicePayments, providerId, patientId, specialityCode, acctgTransId);
        accountingTransactionEntriesFromInvoice.addAll(accountingTransactionEntriesFromInvoicePayment);
        
        List<AcctgTransactionEntry> patientPaidTranactionEntries = createPaidTransactionEntriesForPatient(invoicePayments,invoice,providerId,
        		patientId, specialityCode, acctgTransId);
        accountingTransactionEntriesFromInvoice.addAll(patientPaidTranactionEntries);		
        acctgTrans.addAllAccountingTransactionEntries(accountingTransactionEntriesFromInvoice);
        return acctgTrans;
    }

    // TODO   check the invoice type and create appropriate transaction entries
    public static List<AcctgTransactionEntry> createPayableTransactionEntriesForPatient(Invoice invoice, BigDecimal payableAmount,
    	 String doctorId, String patientId, String specialityCode, String acctgTransId) {
        List<AcctgTransactionEntry> acctgTransactionEntries = new ArrayList<AcctgTransactionEntry>();
        
        AcctgTransTypeEnum acctgTransType=null;
    	InvoiceType invoiceType= invoice.getInvoiceType();
    	
    	if(invoiceType.name().equals(InvoiceType.OPD.name()))
    		acctgTransType=AcctgTransTypeEnum.OPD_VISIT;
    	else
    		acctgTransType=AcctgTransTypeEnum.CASUALTY_CHARGES;
    	
    	AcctgTransactionEntry acctgTransactionEntry = acctgTransType.createAccountingTransactionEntry(null, 1, doctorId, patientId, specialityCode, acctgTransId);
         acctgTransactionEntry.setAmount(payableAmount);
         acctgTransactionEntries.add(acctgTransactionEntry);
        return acctgTransactionEntries;
    }

    // TODO   check the invoice type and create appropriate transaction entries
    public static List<AcctgTransactionEntry> createPaidTransactionEntriesForPatient(List<InvoicePayment> invoicePayments,Invoice invoice,
    		String doctorId, String patientId, String specialityCode, String acctgTransId) {
        List<AcctgTransactionEntry> acctgTransactionEntries = new ArrayList<AcctgTransactionEntry>();
        AcctgTransTypeEnum acctgTransType=null;
    	InvoiceType invoiceType= invoice.getInvoiceType();
    	
    	if(invoiceType.name().equals(InvoiceType.OPD.name()))
    		acctgTransType=AcctgTransTypeEnum.OPD_PAYMENT;
    	else
    		acctgTransType=AcctgTransTypeEnum.CASUALTY_PAYMENT;
    	
        for(InvoicePayment invoicePayment:invoicePayments){
        	AcctgTransTypeEnum acctgTransEntryType=acctgTransType;
        	AcctgTransactionEntry acctgTransactionEntry = acctgTransEntryType.createAccountingTransactionEntry(null, invoicePayments.indexOf(invoicePayment), doctorId, patientId, specialityCode, acctgTransId);
        	acctgTransactionEntry.setAmount(invoicePayment.getAmount().getAmount());
        	if(invoicePayment.isAdvance())
        		acctgTransactionEntry.setDebitOrCredit(DebitCreditEnum.DEBIT);
        	acctgTransactionEntries.add(acctgTransactionEntry);
       }        
        return acctgTransactionEntries;
    }

    public static Long createAccountingTransactionFromInvoice(Long invoiceId, AcctgTransTypeEnum transactionType, CommonCrudService commonCrudService, Date transactionDate, Location location) {
        AcctgTransaction acctgTrans = AcctgTransaction.createWithInvoice(invoiceId, transactionType.getDescription(), transactionDate, location, transactionType);
        acctgTrans = commonCrudService.save(acctgTrans);
        return acctgTrans.getId();
    }

    public static List<AcctgTransactionEntry> createAccountingTransactionEntryForPayment(List<InvoicePayment> invoicePayments, String doctorId, String patientId, String specialityCode, 
    		String acctgTransId) {
        List<AcctgTransactionEntry> transactionEntries = new ArrayList<AcctgTransactionEntry>();
        for (InvoicePayment invoicePayment : invoicePayments) {
            AcctgTransactionEntry acctgTransactionEntry = invoicePayment.getPaymentType().createAccountingTransactionEntry(invoicePayment, invoicePayments.indexOf(invoicePayment), doctorId, patientId, specialityCode, acctgTransId);
        //    acctgTransactionEntry.setAmount(invoicePayment.getAmount().getAmount());
            if(invoicePayment.isAdvance())
            	acctgTransactionEntry.setDebitOrCredit(DebitCreditEnum.CREDIT);
            transactionEntries.add(acctgTransactionEntry);
        }
        return transactionEntries;
    }

    public static List<AcctgTransactionEntry> createAccountingTransactionEntryForInvoiceItem(List<InvoiceItem> invoiceItems, String doctorId, String patientId, String specialityCode, String acctgTransId) {
        List<AcctgTransactionEntry> transactionEntries = new ArrayList<AcctgTransactionEntry>();
        for (InvoiceItem invoiceItem : invoiceItems) {
            AcctgTransTypeEnum acctgTransTypeEnum = invoiceItem.getItemType().getAccountingTransType();
            AcctgTransactionEntry acctgTransactionEntry = acctgTransTypeEnum.createAccountingTransactionEntry(invoiceItem, invoiceItems.indexOf(invoiceItem), doctorId, patientId, specialityCode, acctgTransId);
          //  acctgTransactionEntry.setAmount(invoiceItem.getPriceValue());
           if(UtilValidator.isNotEmpty(invoiceItem.getProviderId()))
        	   acctgTransactionEntry.setDoctorId(invoiceItem.getProviderId().toString());
           if(UtilValidator.isNotEmpty(invoiceItem.getReferralId()))
            	acctgTransactionEntry.setReferralId(invoiceItem.getReferralId().toString());
            transactionEntries.add(acctgTransactionEntry);
        }
        return transactionEntries;
    }

    public static BigDecimal getPatientPayableAmount(List<AcctgTransactionEntry> acctgTransactionEntriesFromInvoice) {
        BigDecimal patientPayableAmount = BigDecimal.ZERO;
        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntriesFromInvoice) {
            BigDecimal transactionAmount = acctgTransactionEntry.getDebitOrCredit().equals(DebitCreditEnum.CREDIT) ? acctgTransactionEntry.getAmount() : (BigDecimal.valueOf(-1d).multiply(acctgTransactionEntry.getAmount()));
            patientPayableAmount = patientPayableAmount.add(transactionAmount);
        }
        return patientPayableAmount;
    }
    

    public static AcctgTransaction createAccountingTransactionEntryForAdvance (Invoice invoice,List<InvoicePayment> invoicePaymentList, CommonCrudService commonCrudService) {
        List<InvoicePayment> invoicePayments=new ArrayList<InvoicePayment>();
        		invoicePayments.addAll(invoicePaymentList);
    	String providerId = getProviderId(invoice);
         String specialityCode = getSpecialityCode(invoice,commonCrudService);
         String patientId =getPatientId(invoice);
         AcctgTransTypeEnum accountingTransactionType = invoice.getInvoiceType().getAccountingTransType();
         Long accountingTransactionId = createAccountingTransactionFromInvoice(invoice.getId(), accountingTransactionType, commonCrudService, new Date(), invoice.getLocation());
         AcctgTransaction acctgTrans = commonCrudService.getById(AcctgTransaction.class, accountingTransactionId);
         String acctgTransId = accountingTransactionId.toString();
         List<AcctgTransactionEntry> transactionEntries = new ArrayList<AcctgTransactionEntry>();
     	 List<AcctgTransactionEntry> accountingTransactionEntryFromAdvance = createAccountingTransactionEntryForAdvancePayment(invoicePayments, providerId, patientId, specialityCode, 
     			 acctgTransId);
         transactionEntries.addAll(accountingTransactionEntryFromAdvance);
         acctgTrans.addAllAccountingTransactionEntries(transactionEntries);
        return acctgTrans;
     }
        

    public static List<AcctgTransactionEntry> createAccountingTransactionEntryForAdvancePayment(List<InvoicePayment> invoicePayments, String doctorId, String patientId, String specialityCode, 
    		String acctgTransId) {
        List<AcctgTransactionEntry> transactionEntries = new ArrayList<AcctgTransactionEntry>();
        for (InvoicePayment invoicePayment : invoicePayments) {
            AcctgTransactionEntry acctgTransactionEntry = invoicePayment.getPaymentType().createAccountingTransactionEntry(invoicePayment, invoicePayments.indexOf(invoicePayment), doctorId, patientId, specialityCode, acctgTransId);
                transactionEntries.add(acctgTransactionEntry);
        }
        return transactionEntries;
    }

}


