package com.nzion.repository.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.nzion.domain.Employee;
import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.Speciality;
import com.nzion.domain.billing.*;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.report.search.view.BillingSearchVO;
import com.nzion.repository.BaseRepository;

public interface BillingRepository extends BaseRepository {

	
	
	Contract findEffectiveContractFor(String name, Contract.CONTRACTTYPE cType, Date fromDate, Date thruDate);


	List<InvoiceItem> getTransactionItemsFor(Invoice billingTransaction);

	List<Consultation> getConsultationBySpeciality(Speciality speciality);

	List<Consultation> getConsultationByProvider(Person provider);
	
	
	
	List<Invoice> getInvoice(List<InvoiceStatusItem> status,Patient patient,Employee emp,Date fromDate,Date thruDate,String ipNumber);
	
	List<LabOrderRequest> getSearchByLabOrder(List<LabOrderRequest.ORDERSTATUS> status, Patient patient,Referral referral,Date fromDate,Date thruDate);

	ArrayList<InvoicePayment> getInvoicePaymentsByCriteria(Patient patient,Date fromDate,Date thruDate);
	
	List<InsuranceProvider> getInsuranceProviderAttachedToContract();
	
	List<Contract>  getContractForInsPro(InsuranceProvider insuPro);

	List<Invoice> searchInvoiceBy(BillingSearchVO billingSearchVO,Date fromDate,Date thruDate);
	
	List<AcctgTransaction> searchAcctgTransactionBy(AcctgTransTypeEnum chargeType,Date fromDate,Date thruDate);
	
    BigDecimal[] getInvoiceTotal(Invoice invoice);

    List<Invoice> getFirstInvoice(Patient patient);
    
    
   /* List<Consultation> getConsultationChargeForPatientCheckedIn(Speciality speciality,SlotType slotType);*/
    
	
    LabTestPanel getLabtestPanelByPanelName(String panelName);
    
    List<AcctgTransactionEntry> searchAcctgTransactionEntryForLabReport(Date fromDate,Date thruDate,Object chargeType);
	
    List<AcctgTransactionEntry> searchAcctgTransactionEntryForLabReportExport(Date fromDate,Date thruDate,Object chargeType,Object doctor,Object referral);

}
