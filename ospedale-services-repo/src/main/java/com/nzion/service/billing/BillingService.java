package com.nzion.service.billing;

import com.nzion.domain.*;
import com.nzion.domain.billing.*;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.exception.TransactionException;
import com.nzion.report.search.view.BillingSearchVO;
import com.nzion.view.ConsultationValueObject;

import java.math.BigDecimal;
import java.util.*;

public interface BillingService {

	


	List<InvoiceItem> getTransactionItemsFor(Invoice billingTransaction);

	void getAllConsultations(ConsultationValueObject vo);

	Invoice generateInvoiceFor(Object object) throws TransactionException;
	
	void createInvoice(Invoice invoice) throws TransactionException;

	void saveInvoiceStatus(Invoice invoice,InvoiceStatusItem invoiceStatusItem);
	
	void saveInvoiceStatusAsWriteOff(Invoice invoice, InvoiceStatusItem invoiceStatusItem,InvoicePayment invoicePayment);
	
	void saveInvoiceStatusAtAdvance(Invoice invoice, InvoiceStatusItem invoiceStatusItem);
	
	InvoiceManager getManager(Invoice invoice);
	
	List<Invoice> getInvoice(List<InvoiceStatusItem> status,Patient patient,Employee emp,Date fromDate,Date thruDate,String ipNumber);
	
	List<LabOrderRequest> getSearchByLabOrder(List<LabOrderRequest.ORDERSTATUS> status,Patient patient,Referral referral,Date fromDate,Date thruDate);

	ArrayList<InvoicePayment> getInvoicePaymentsByCriteria(Patient patient,Date fromDate,Date thruDate);
	
	void saveLabOrderStatus(Invoice invoice);
	
	List<Contract>  getContractForInsPro(InsuranceProvider insuPro);

	List<InsuranceProvider> getInsuranceProviderAttachedToContract();

	List<Invoice> searchInvoiceBy(BillingSearchVO billingSearchVO,Date fromDate,Date thruDate);

	Map<String, Set<Invoice>>  getGroupedItems(String item, Set<Invoice> invoices);
	
	void exportInPatientInvoicesInDetail(List<Invoice> invoices,
			String[] invoiceFields, String[] invoiceFieldsLabel,
			String[] invoiceItemFields, String[] invoiceItemLabels, String filename);	
	
	UnitOfMeasurement getConfiguredCurrency();
	
	Map<String, Set<InvoiceItem>> getCptGroupedItems(String item, HashSet<Invoice> hashSet);

    void doRegistrationTransaction(Patient patient) throws TransactionException;

    List<Invoice> getFirstInvoice(Patient patient);
    
	
	LabTestPanel getLabtestPanelByPanelName(String panelName);
	
	public Map<AcctgTransTypeEnum,Map<String,List<AcctgTransactionEntry>>> getCollectionReport(AcctgTransTypeEnum chargeType,Date fromDate,Date toDate);
	
}
