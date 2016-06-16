package com.nzion.service.billing.impl;

import com.nzion.domain.*;
import com.nzion.domain.billing.*;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.exception.TransactionException;
import com.nzion.report.search.view.BillingSearchVO;
import com.nzion.repository.billing.BillingRepository;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.billing.BillingService;
import com.nzion.service.billing.InvoiceManager;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.service.util.TransactionHelper;
import com.nzion.util.Constants;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;
import com.nzion.view.ConsultationValueObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.zhtml.Filedownload;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.nzion.util.Constants.COMMA;
import static com.nzion.util.Constants.NEWLINE;

public class BillingServiceImpl implements BillingService {

    private BillingRepository billingRepository;

    private CommonCrudRepository commonCrudRepository;

    private CommonCrudService commonCrudService;

    private Map<String, ? extends InvoiceManager> billingManagers;



    private final Long CONSULT_VISIT_ID = 5l;

    private final Long INHOUSE_DOCTOR_ID = 9000L;

    private LabService labService;

    public LabService getLabService() {
        return labService;
    }

    @Resource
    public void setLabService(LabService labService) {
        this.labService = labService;
    }


    @Resource
    public void setBillingRepository(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }



    @Override
    public List<InvoiceItem> getTransactionItemsFor(Invoice billingTransaction) {
        return billingRepository.getTransactionItemsFor(billingTransaction);
    }

    public void getAllConsultations(ConsultationValueObject vo) {
        Set<Consultation> consultations = null;
        List<Consultation> temp = new ArrayList<Consultation>();
        if (vo.getSpeciality() != null) {
            temp = billingRepository.getConsultationBySpeciality(vo.getSpeciality());
            consultations = new HashSet<Consultation>(temp);
        } else {
            temp = billingRepository.getConsultationByProvider(vo.getProvider());
            consultations = new HashSet<Consultation>(temp);
        }
        /*List<SoapNoteType> visitTypes = billingRepository.getAll(SoapNoteType.class);
        for (SoapNoteType visitType : visitTypes) {
            boolean isPresent = false;
            for (Consultation tmpCons : temp) {
                if (visitType.equals(tmpCons.getSoapNoteType())) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent)
                consultations.add(new Consultation(new Money(BigDecimal.ZERO, Currency.getInstance(vo.getMeasurement().getCode())), vo.getSpeciality(), vo.getProvider(), visitType));
        }*/
        vo.setConsultations(new ArrayList<Consultation>(consultations));
    }

   /* @Override
    public Consultation getConsultationChargeFor(PatientSoapNote soapNote) {
        List<Consultation> providerConsultations = billingRepository.getConsultationChargeFor(soapNote.getProvider(),
                soapNote.getEncounterType());
        if (UtilValidator.isNotEmpty(providerConsultations)) return providerConsultations.get(0);
        List<Consultation> specialityConsulations = billingRepository.getConsultationChargeFor(soapNote.getSpeciality(),
                soapNote.getEncounterType());
        if (UtilValidator.isNotEmpty(specialityConsulations)) return specialityConsulations.get(0);
        return null;
    }
*/
    @Override
    public Invoice generateInvoiceFor(Object object) throws TransactionException {
        InvoiceManager manager = (InvoiceManager) billingManagers.get(object.getClass().getName());
        return manager.generateInvoice(object);
    }


    public CommonCrudRepository getCommonCrudRepository() {
        return commonCrudRepository;
    }

    @Resource
    public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
        this.commonCrudRepository = commonCrudRepository;
    }

    public Map<String, ? extends InvoiceManager> getBillingManagers() {
        return billingManagers;
    }

    public void setBillingManagers(Map<String, ? extends InvoiceManager> billingManagers) {
        this.billingManagers = billingManagers;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createInvoice(Invoice invoice) throws TransactionException {
        commonCrudRepository.save(invoice);
        saveInvoiceStatus(invoice, InvoiceStatusItem.READY);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly = false)
    public void saveInvoiceStatusAsWriteOff(Invoice invoice, InvoiceStatusItem invoiceStatusItem, InvoicePayment invoicePayment) {
        InvoiceStatus invoiceStatus = new InvoiceStatus(invoice, invoiceStatusItem, new Date());
        commonCrudService.save(invoice);
        commonCrudService.save(invoiceStatus);
        List<InvoicePayment> invoicePayments = new ArrayList<InvoicePayment>();
        invoicePayments.add(invoicePayment);
        AcctgTransaction acctgTrans = TransactionHelper.createAccountingTransactionEntryForWriteoff(invoice, invoicePayments, commonCrudService);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly = false)
    public void saveInvoiceStatusAtAdvance(Invoice invoice, InvoiceStatusItem invoiceStatusItem) {
        InvoiceStatus invoiceStatus = new InvoiceStatus(invoice, invoiceStatusItem, new Date());
        commonCrudService.save(invoice);
        commonCrudService.save(invoiceStatus);
        //       List<InvoicePayment> invoicePayments = new ArrayList<InvoicePayment>();
        //       invoicePayments.addAll(invoice.getInvoicePayments());
        //      AcctgTransaction acctgTrans = TransactionHelper.createAccountingTransactionEntryForAdvance(invoice, invoicePayments, commonCrudService);
    }


    @Override
    @Transactional(propagation = Propagation.NEVER, readOnly = false)
    public void saveInvoiceStatus(Invoice invoice, InvoiceStatusItem invoiceStatusItem) {
        InvoiceStatus invoiceStatus = new InvoiceStatus(invoice, invoiceStatusItem, new Date());
        commonCrudService.save(invoice);
        commonCrudService.save(invoiceStatus);
        if (invoiceStatusItem.equals(InvoiceStatusItem.RECEIVED)) {

            List<InvoicePayment> invoicePayments = new ArrayList<InvoicePayment>(invoice.getInvoicePayments());

            AcctgTransaction acctgTrans = TransactionHelper.createAccountingTransactionAndTransactionEntries(invoice, invoicePayments, commonCrudService);

            if (invoice.getItemType().equals(LabOrderRequest.class.getName())) {
                LabOrderRequest labOrderRequest = commonCrudService.getById(LabOrderRequest.class, new Long(invoice.getItemId()));
                for (PatientLabOrder labOrder : labOrderRequest.getPatientLabOrders()) {
                    if (PatientLabOrder.BILLINGSTATUS.INVOICED.equals(labOrder.getBillingStatus())) {
                        labOrder.setBillingStatus(PatientLabOrder.BILLINGSTATUS.PAID);
                        commonCrudService.save(labOrder);
                    }
                }
                labService.createLabRequisition(labOrderRequest, invoice);
            }

            if (UtilValidator.isNotEmpty(invoice.getItemType())) {
                if (invoice.getItemType().equals(LabOrderRequest.class.getName()))
                    saveLabOrderStatus(invoice);
            }
           /* if (UtilValidator.isNotEmpty(invoice.getItemType())) {
                if (invoice.getItemType().equals(PatientSoapNote.class.getName())) {
                    PatientSoapNote patientSoapNote = (PatientSoapNote) commonCrudService.getById(PatientSoapNote.class, new Long(invoice.getItemId()));
                    if (patientSoapNote != null) {
                        Schedule schedule = patientSoapNote.getSchedule();
                        schedule.setStatus(Schedule.STATUS.CHECKEDOUT);
                        commonCrudService.merge(schedule);
                    }
                }
            }*/
        }
    }

    @Override
    public InvoiceManager getManager(Invoice invoice) {
        return (InvoiceManager) billingManagers.get(invoice.getItemType());
    }

    @Override
    public List<Invoice> getInvoice(List<InvoiceStatusItem> status, Patient patient, Employee emp, Date fromDate, Date thruDate, String ipNumber) {
        return billingRepository.getInvoice(status, patient, emp, fromDate, thruDate, ipNumber);

    }

    @Override
    public List<LabOrderRequest> getSearchByLabOrder(List<LabOrderRequest.ORDERSTATUS> status, Patient patient,
                                                     Referral referral,Date fromDate,Date thruDate) {
        return billingRepository.getSearchByLabOrder(status, patient,referral,fromDate,thruDate);

    }

    @Override
    public ArrayList<InvoicePayment> getInvoicePaymentsByCriteria(Patient patient, Date fromDate, Date thruDate) {
        return billingRepository.getInvoicePaymentsByCriteria(patient,fromDate,thruDate);
    }

    @Override
    public void saveLabOrderStatus(Invoice invoice) {
        CommonCrudService ccService = Infrastructure.getSpringBean("commonCrudService");
        LabOrderRequest labOrderReq = ccService.getById(LabOrderRequest.class, Long.valueOf(invoice.getItemId()));
        /*  invoice.getInvoiceItems();
               if (UtilValidator.isEmpty(invoice.getInvoiceItems())) {
                    for (PatientLabOrder pLabOrder : labOrderReq.getPatientLabOrders()) {
                        for (InvoiceItem invoiceItem : invoice.getInvoiceItems())
                            if (invoiceItem.getItemId().equals(pLabOrder.getLabTestPanel().getId().toString()))
                                pLabOrder.setStatus(PatientLabOrder.STATUS.SENT);
                    }
                }
        */
        labOrderReq.setOrderStatus(LabOrderRequest.ORDERSTATUS.BILLING_DONE);
    }

   /* @Override
    public List<CptPrice> getContractCptPricesFor(Contract contract, Collection<Cpt> cpts) {
        return billingRepository.getContractCptPricesFor(contract, cpts);
    }

*/
    @Override
    public List<InsuranceProvider> getInsuranceProviderAttachedToContract() {
        return billingRepository.getInsuranceProviderAttachedToContract();
    }

    @Override
    public List<Contract> getContractForInsPro(InsuranceProvider insuPro) {
        return billingRepository.getContractForInsPro(insuPro);
    }

    @Override
    public List<Invoice> searchInvoiceBy(BillingSearchVO billingSearchVO, Date fromDate, Date thruDate) {
        return billingRepository.searchInvoiceBy(billingSearchVO, fromDate, thruDate);
    }

    @Override
    public Map<String, Set<Invoice>> getGroupedItems(String item, Set<Invoice> invoices) {
        if (InvoiceType.OPD_PROCEDURE.name().equals(item))
            return getGroupedItemsForCPT(invoices);
        return null;
    }

    private Map<String, Set<Invoice>> getGroupedItemsForCPT(Set<Invoice> invoices) {
        Map<String, Set<Invoice>> invoiceMap = new HashMap<String, Set<Invoice>>();
        for (Invoice invoice : invoices) {
            List<InvoiceItem> list = commonCrudService.findByEquality(InvoiceItem.class, new String[]{"itemType", "invoice"}, new Object[]{InvoiceType.OPD_PROCEDURE, invoice});
            for (InvoiceItem invoiceItem : list) {
                Set<Invoice> invoices2 = invoiceMap.get(invoiceItem.getItemId() + Constants.BLANK + invoiceItem.getDescription());
                if (UtilValidator.isEmpty(invoices2)) invoices2 = new HashSet<Invoice>();
                invoices2.add(invoice);
                invoiceMap.put(invoiceItem.getItemId() + Constants.BLANK + invoiceItem.getDescription(), invoices2);
                invoiceMap.put("Others", invoices);
            }
        }
        return invoiceMap;
    }

    @Override
    public Map<String, Set<InvoiceItem>> getCptGroupedItems(String item,
                                                            HashSet<Invoice> invoices) {
        //  if (InvoiceType.OPD_PROCEDURE.getDescription().equals(item)) {
        Map<String, Set<InvoiceItem>> invoiceMap = new HashMap<String, Set<InvoiceItem>>();
        for (Invoice invoice : invoices) {

            List<InvoiceItem> list = new ArrayList<InvoiceItem>();
            for (InvoiceItem invItem : invoice.getInvoiceItems()) {
                if (invItem.getItemType() != null && (invItem.getItemType().equals(InvoiceType.OPD_PROCEDURE)))
                    list.add(invItem);
            }

            for (InvoiceItem invoiceItem : list) {
                Set<InvoiceItem> invoicesItems2 = invoiceMap.get(invoiceItem.getItemId() + Constants.BLANK + invoiceItem.getDescription());
                if (UtilValidator.isEmpty(invoicesItems2)) invoicesItems2 = new HashSet<InvoiceItem>();
                invoicesItems2.add(invoiceItem);
                invoiceMap.put(invoiceItem.getItemId() + Constants.BLANK + invoiceItem.getDescription(), invoicesItems2);
            }
        }
        return invoiceMap;
        //    }

        //   return null;
    }


    @Resource
    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    @Override
    public void exportInPatientInvoicesInDetail(List<Invoice> invoices, String[] invoiceFields, String[] invoiceFieldsLabel, String[] invoiceItemFields, String[] invoiceItemLabels, String filename) {
        StringBuilder contentBuilder = new StringBuilder();

        for (Invoice invoice : invoices) {
            for (String label : invoiceFieldsLabel) {
                contentBuilder.append(label).append(COMMA);
            }
            contentBuilder.deleteCharAt(contentBuilder.length() - 1);
            contentBuilder.append(NEWLINE);
            for (String field : invoiceFields) {
                Object fieldValue = UtilReflection.getNestedFieldValue(invoice, field);
                contentBuilder.append(fieldValue == null ? "" : "\"" + fieldValue.toString() + "\"").append(COMMA);
            }
            contentBuilder.deleteCharAt(contentBuilder.length() - 1);
            contentBuilder.append(NEWLINE);
            if (invoice.getId() == null)
                contentBuilder.append("Bill Items of  OutStanding Amount ");
            else
                contentBuilder.append("Bill Items of Bill Number " + " " + "\"" + invoice.getId().toString() + "\"");
            contentBuilder.append(NEWLINE);
            for (String label : invoiceItemLabels) {
                contentBuilder.append(label).append(COMMA);
            }
            contentBuilder.deleteCharAt(contentBuilder.length() - 1);
            contentBuilder.append(NEWLINE);
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {

                for (String field : invoiceItemFields) {
                    Object fieldValue = UtilReflection.getNestedFieldValue(invoiceItem, field);
                    contentBuilder.append(fieldValue == null ? "" : "\"" + fieldValue.toString() + "\"").append(COMMA);
                }
                contentBuilder.deleteCharAt(contentBuilder.length() - 1);
                contentBuilder.append(NEWLINE);
            }
            contentBuilder.deleteCharAt(contentBuilder.length() - 1);
            contentBuilder.append(NEWLINE);
            contentBuilder.append(COMMA).append("Total Amount : ").append(invoice.getTotalAmount());
            contentBuilder.append(COMMA).append("Paid Amount: ").append(invoice.getCollectedAmount());
            contentBuilder.append(NEWLINE);
            contentBuilder.append(NEWLINE);
        }
        Filedownload.save(contentBuilder.toString().getBytes(), "text/csv", filename);
    }

    @Override
    public UnitOfMeasurement getConfiguredCurrency() {
        BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
        return billingDisplayConfig.getCurrency();
    }

    /*public Consultation getOutPatientConsultationCharge(Employee employee) {
        SoapNoteType visitType = (SoapNoteType) commonCrudService.getById(com.nzion.domain.SlotType.class, CONSULT_VISIT_ID);
        if (visitType == null) {
            List<SlotType> slotTypes = commonCrudService.findByEquality(SlotType.class, new String[]{"name"}, new String[]{"Consultation Visit"});
            if (UtilValidator.isNotEmpty(slotTypes)) {
                visitType = (SoapNoteType) slotTypes.get(0);
            }
        }
        return getConsultationChargeByProviderAndVisitType(employee, visitType);
    }
*/
    /*public Consultation getConsultationChargeByProviderAndVisitType(Employee employee, SoapNoteType soapNoteType) {
        List<Consultation> providerConsultations = billingRepository.getConsultationChargeFor(employee, soapNoteType);
        if (UtilValidator.isNotEmpty(providerConsultations) && !providerConsultations.get(0).getPrice().isZero())
            return providerConsultations.get(0);
        Provider provider = (Provider) employee;
        List<Speciality> specialities = new ArrayList<Speciality>(provider.getSpecialities());
        List<Consultation> specialityConsultations = billingRepository.getConsultationChargeFor(specialities.get(0), soapNoteType);
        if (UtilValidator.isNotEmpty(specialityConsultations) && !specialityConsultations.get(0).getPrice().isZero())
            return specialityConsultations.get(0);
        else {
            Consultation consultation = new Consultation();
            consultation.setPrice(new Money(soapNoteType.getDefaultPrice() != null ? soapNoteType.getDefaultPrice() : BigDecimal.ZERO));
            return consultation;
        }
    }*/


    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void doRegistrationTransaction(Patient patient) throws TransactionException {
       /* AcctgTransaction acctgTransaction = createTransactionForNewPatient(patient, new BigDecimal(0.00));
        billingRepository.save(acctgTransaction);
        */
    }

    @Override
    public List<Invoice> getFirstInvoice(Patient patient) {
        return billingRepository.getFirstInvoice(patient);
    }

    public Currency getCurrency() {
        BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
        String currency = billingDisplayConfig.getCurrency().getCode();
        Currency defaultCurrency = Currency.getInstance(currency);
        return defaultCurrency;
    }

    /*@Override
    public Invoice getBillingTransactionForSchedule(Schedule schedule) {
        List<Invoice> billingTransactions = billingRepository.getBillingTransactionForSchedule(schedule);
        if (UtilValidator.isNotEmpty(billingTransactions)) return billingTransactions.get(0);
        return new Invoice();
    }*/

    @Override
    public LabTestPanel getLabtestPanelByPanelName(String panelName) {
        return billingRepository.getLabtestPanelByPanelName(panelName);
    }


    public Map<AcctgTransTypeEnum, Map<String, List<AcctgTransactionEntry>>> getCollectionReport(AcctgTransTypeEnum chargeType, Date fromDate, Date toDate) {
        Map<AcctgTransTypeEnum, Map<String, List<AcctgTransactionEntry>>> collectionReportItemMap = new HashMap<AcctgTransTypeEnum, Map<String, List<AcctgTransactionEntry>>>();

        List<AcctgTransaction> acctgTransactions = billingRepository.searchAcctgTransactionBy(chargeType, fromDate, toDate);
        Map<AcctgTransTypeEnum, List<AcctgTransactionEntry>> acctgTransactionMap = new HashMap<AcctgTransTypeEnum, List<AcctgTransactionEntry>>();

        for (AcctgTransaction acctgTransaction : acctgTransactions) {
            acctgTransactionMap = groupByTransactionType(acctgTransaction.getAcctgTransactionEntries(), acctgTransactionMap);
        }

        for (AcctgTransTypeEnum acctgTransTypeEnum : acctgTransactionMap.keySet()) {
            Map<String, List<AcctgTransactionEntry>> groupedCollectionReportItemMap = groupByCollectionReportItems(acctgTransactionMap.get(acctgTransTypeEnum), acctgTransTypeEnum.getPropery(), acctgTransTypeEnum.getReportField());
            collectionReportItemMap.put(acctgTransTypeEnum, groupedCollectionReportItemMap);
        }

        return collectionReportItemMap;
    }


    public Map<AcctgTransTypeEnum, List<AcctgTransactionEntry>> groupByTransactionType(List<AcctgTransactionEntry> acctgTransactionEntryList, Map<AcctgTransTypeEnum, List<AcctgTransactionEntry>> acctgTransactionMap) {
        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntryList) {
            List<AcctgTransactionEntry> acctgTransactionEntries = acctgTransactionMap.get(acctgTransactionEntry.getTransactionType());
            acctgTransactionEntries = UtilValidator.isNotEmpty(acctgTransactionEntries) ? acctgTransactionEntries : new ArrayList<AcctgTransactionEntry>();
            acctgTransactionEntries.add(acctgTransactionEntry);
            acctgTransactionMap.put(acctgTransactionEntry.getTransactionType(), acctgTransactionEntries);
        }
        return acctgTransactionMap;
    }

    public Map<String, List<AcctgTransactionEntry>> groupByCollectionReportItems(List<AcctgTransactionEntry> acctgTransactionEntryList, String field, String reportField) {
        Map<String, List<AcctgTransactionEntry>> collectionReportItemMap = new HashMap<String, List<AcctgTransactionEntry>>();
        if (UtilValidator.isEmpty(field)) return collectionReportItemMap;
        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntryList) {
            String value = UtilReflection.getValue(acctgTransactionEntry, field).toString();
            String item = getItemValue(value, acctgTransactionEntry.getTransactionType(), reportField);
            List<AcctgTransactionEntry> acctgTransactionEntries = collectionReportItemMap.get(item);
            acctgTransactionEntries = UtilValidator.isNotEmpty(acctgTransactionEntries) ? acctgTransactionEntries : new ArrayList<AcctgTransactionEntry>();
            acctgTransactionEntries.add(acctgTransactionEntry);
            collectionReportItemMap.put(item, acctgTransactionEntries);

        }
        return collectionReportItemMap;
    }

    public String getItemValue(String value, AcctgTransTypeEnum acctgTransTypeEnum, String fieldName) {
        Object object = null;
        if (UtilValidator.isEmpty(fieldName))
            return value;
        /*if (!AcctgTransTypeEnum.IPD_PROCEDURE.equals(acctgTransTypeEnum))
            object = commonCrudService.getById(acctgTransTypeEnum.getClassName(), new Long(value));
        else*/
            object = commonCrudService.getById(acctgTransTypeEnum.getClassName(), value);
        Object fieldValue = UtilReflection.getNestedFieldValue(object, fieldName);
        String item = fieldValue != null ? fieldValue.toString() : "";
        return item;
    }

    public Map<String, Map<String, List<AcctgTransactionEntry>>> getLabCollectionReport(Date fromDate, Date toDate, Object chargeType, Object provider, Object referral) {
        Map<String, Map<String, List<AcctgTransactionEntry>>> collectionReportItemMap = new HashMap<String, Map<String, List<AcctgTransactionEntry>>>();
        List<AcctgTransactionEntry> acctgTransactionEntries = billingRepository.searchAcctgTransactionEntryForLabReport(fromDate, toDate, chargeType);

        Map<String, List<AcctgTransactionEntry>> acctgTransactionMap = new HashMap<String, List<AcctgTransactionEntry>>();
        if (UtilValidator.isNotEmpty(provider))
            acctgTransactionMap = groupLabReportByProvider(acctgTransactionEntries, acctgTransactionMap);
        else if (UtilValidator.isNotEmpty(referral))
            acctgTransactionMap = groupLabReportByReferral(acctgTransactionEntries, acctgTransactionMap);
        else
            acctgTransactionMap = groupLabReport(acctgTransactionEntries, acctgTransactionMap);

        for (String key : acctgTransactionMap.keySet()) {

            Map<String, List<AcctgTransactionEntry>> groupedCollectionReportItemMap = groupByCollectionReportItems(acctgTransactionMap.get(key), "description", null);
            collectionReportItemMap.put(key, groupedCollectionReportItemMap);
        }
        return collectionReportItemMap;
    }

    public Map<String, List<AcctgTransactionEntry>> groupLabReportByProvider(List<AcctgTransactionEntry> acctgTransactionEntryList, Map<String, List<AcctgTransactionEntry>> acctgTransactionMap) {

        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntryList) {

            Object value = UtilReflection.getValue(acctgTransactionEntry, "doctorId");
            if (value != null && UtilValidator.isNotEmpty(value.toString())) {

                Provider provider = commonCrudService.getById(Provider.class, new Long(value.toString()));
                Object firstNameFieldValue = UtilReflection.getNestedFieldValue(provider, "firstName");
                Object lastNameFieldValue = UtilReflection.getNestedFieldValue(provider, "lastName");

                String item = (firstNameFieldValue != null && lastNameFieldValue != null) ? firstNameFieldValue.toString() + " " + lastNameFieldValue.toString() : "";

                List<AcctgTransactionEntry> acctgTransactionEntries = acctgTransactionMap.get(item);
                acctgTransactionEntries = UtilValidator.isNotEmpty(acctgTransactionEntries) ? acctgTransactionEntries : new ArrayList<AcctgTransactionEntry>();
                acctgTransactionEntries.add(acctgTransactionEntry);
                acctgTransactionMap.put(item, acctgTransactionEntries);
            }
        }
        return acctgTransactionMap;
    }


    public Map<String, List<AcctgTransactionEntry>> groupLabReportByReferral(List<AcctgTransactionEntry> acctgTransactionEntryList, Map<String, List<AcctgTransactionEntry>> acctgTransactionMap) {

        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntryList) {

            Object value = UtilReflection.getValue(acctgTransactionEntry, "referralId");
            if (value != null && UtilValidator.isNotEmpty(value.toString())) {
                Referral referral = commonCrudService.getById(Referral.class, new Long(value.toString()));
                Object firstNameFieldValue = UtilReflection.getNestedFieldValue(referral, "firstName");
                Object lastNameFieldValue = UtilReflection.getNestedFieldValue(referral, "lastName");
                String item = (firstNameFieldValue != null && lastNameFieldValue != null) ? firstNameFieldValue.toString() + " " + lastNameFieldValue.toString() : "";

                List<AcctgTransactionEntry> acctgTransactionEntries = acctgTransactionMap.get(item);
                acctgTransactionEntries = UtilValidator.isNotEmpty(acctgTransactionEntries) ? acctgTransactionEntries : new ArrayList<AcctgTransactionEntry>();
                acctgTransactionEntries.add(acctgTransactionEntry);
                acctgTransactionMap.put(item, acctgTransactionEntries);
            }
        }
        return acctgTransactionMap;
    }

    public Map<String, List<AcctgTransactionEntry>> groupLabReport(List<AcctgTransactionEntry> acctgTransactionEntryList, Map<String, List<AcctgTransactionEntry>> acctgTransactionMap) {
        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntryList) {
            List<AcctgTransactionEntry> acctgTransactionEntries = acctgTransactionMap.get(acctgTransactionEntry.getTransactionType().getDescription());
            acctgTransactionEntries = UtilValidator.isNotEmpty(acctgTransactionEntries) ? acctgTransactionEntries : new ArrayList<AcctgTransactionEntry>();
            acctgTransactionEntries.add(acctgTransactionEntry);
            acctgTransactionMap.put(acctgTransactionEntry.getTransactionType().getDescription(), acctgTransactionEntries);
        }
        return acctgTransactionMap;
    }


}
