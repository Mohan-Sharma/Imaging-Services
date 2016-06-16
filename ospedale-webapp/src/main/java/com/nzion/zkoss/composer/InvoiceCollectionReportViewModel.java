package com.nzion.zkoss.composer;

import com.nzion.domain.Patient;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Window;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * Created by Saikiran Chepuri on 09-Jun-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class InvoiceCollectionReportViewModel {

    @WireVariable
    private CommonCrudService commonCrudService;

    @WireVariable
    private BillingService billingService;

    @Wire("#invoiceCollectionWin")
    private Window invoiceCollectionWin;

    @Wire("#searchResults")
    private Panel searchResults;


    private Patient patient;
    private Date fromDate;
    private Date thruDate;
    private List<Map<String, Object>> paymentList = new ArrayList<Map<String,Object>>();
    private BigDecimal totalBillableAmount = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    private BigDecimal totalPaidAmount = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
    private String totalDescriptionFooter1 = new String();

    private String totalDescriptionFooter2 = new String();


    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW)Component component){
        Selectors.wireComponents(component,this,true);
    }

    @Command("search")
    @NotifyChange({"paymentList","totalBillableAmount","totalPaidAmount","totalDescriptionFooter1","totalDescriptionFooter2"})
    public void search(){
        paymentList = new ArrayList<Map<String, Object>>();
        totalBillableAmount = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        totalPaidAmount = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalPatientAccount = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalCashCollection = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalDebitCardCollection = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalCreditCardCollection = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        BigDecimal totalCheckCollection = BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP);
        ArrayList<InvoicePayment> invoicePayments = billingService.getInvoicePaymentsByCriteria(patient,fromDate,thruDate);
        updateListByPatientCondition(invoicePayments);

        List<Map<String, Object>> listOfMap = buildListOfMap(invoicePayments);
        for(Map<String,Object> m : listOfMap) {
            if("OPD_ADVANCE_AMOUNT".equals(m.get("mode").toString())){
                totalPatientAccount = totalPatientAccount.add((BigDecimal) m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            }
            if("OPD_CASH".equals(m.get("mode").toString())){
                totalCashCollection = totalCashCollection.add((BigDecimal)m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            }
            if("OPD_DEBIT_CARD".equals(m.get("mode").toString())){
                totalDebitCardCollection = totalDebitCardCollection.add((BigDecimal)m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            }
            if("OPD_CREDIT_CARD".equals(m.get("mode").toString())){
                totalCreditCardCollection = totalCreditCardCollection.add((BigDecimal)m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            }
            if("OPD_PERSONAL_CHEQUE".equals(m.get("mode").toString())){
                totalCheckCollection = totalCheckCollection.add((BigDecimal)m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            }
            totalBillableAmount = totalBillableAmount.add((BigDecimal) m.get("billableAmount")).setScale(3, RoundingMode.HALF_UP);
            totalPaidAmount = totalPaidAmount.add((BigDecimal) m.get("paidAmount")).setScale(3, RoundingMode.HALF_UP);
            paymentList.add(m);
        }

        StringBuffer buffer1 = new StringBuffer();
        buffer1.append("Total Collectable = " + totalPaidAmount + " KD ");
      /*  buffer1.append("Payments from Patient Account = " + totalPatientAccount + " KD ");
        buffer1.append("Net Collection = " + totalPaidAmount.subtract(totalPatientAccount) + " KD ");*/

        StringBuffer buffer2 = new StringBuffer();
        //buffer2.append("Total Collection = " + totalPaidAmount.add(totalInsuranceAmount).add(totalCorporateAmount) + " KD ");
        buffer2.append("Cash Collected = " + totalCashCollection + " KD ");
        buffer2.append("Debit Card = " + totalDebitCardCollection + " KD ");
        buffer2.append("Credit Card = " + totalCreditCardCollection + " KD ");
        /*buffer2.append("Insurance Credit = " + totalInsuranceAmount + " KD ");
        buffer2.append("Corporate Credit = " + totalCorporateAmount + " KD ");*/

        buffer2.append("Check Collected = " + totalCheckCollection + " KD ");

        totalDescriptionFooter1 = buffer1.toString();
        totalDescriptionFooter2 = buffer2.toString();
        if(UtilValidator.isNotEmpty(paymentList))
            searchResults.setVisible(true);

    }

    private List<Map<String, Object>> buildListOfMap(ArrayList<InvoicePayment> invoicePayments){
        ArrayList<Map<String, Object>> listOfMap = new ArrayList<Map<String, Object>>();
       /* for(InvoicePayment invoicePayment : invoicePayments){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("receiptNumber",invoicePayment.getReceiptNumber());
            map.put("paymentDate",invoicePayment.getPaymentDate());
            map.put("afyaId",invoicePayment.getInvoice().getPatient().getAfyaId());
            map.put("civilId",invoicePayment.getInvoice().getPatient().getCivilId());
            map.put("patientName",invoicePayment.getInvoice().getPatient().getFirstName()+" "+invoicePayment.getInvoice().getPatient().getLastName());
            map.put("mode",invoicePayment.getPaymentType());


         listOfMap.add(map);
        }*/
        for(InvoicePayment invoicePayment : invoicePayments){
            Map<String, Object> map = new HashMap<String, Object>();
          //   map.put("receiptId", invoicePayment.getId());
            map.put("receiptNumber", invoicePayment.getReceiptNumber());
            map.put("paymentDate", invoicePayment.getPaymentDate());
            map.put("afyaId", invoicePayment.getInvoice().getPatient().getAfyaId());
            map.put("civilId", invoicePayment.getInvoice().getPatient().getCivilId());
            map.put("patientName", invoicePayment.getInvoice().getPatient().getFirstName() + " " + invoicePayment.getInvoice().getPatient().getLastName() );
            map.put("mode", invoicePayment.getPaymentType() );
            map.put("modeDescription", invoicePayment.getPaymentType().getDescription() );
            String tranNum = "";
            if (invoicePayment.getChequeOrDdNo() != null)
            {
                tranNum = invoicePayment.getChequeOrDdNo();
            }
            if (invoicePayment.getTransactionNumb() != null)
            {
                tranNum = tranNum + invoicePayment.getTransactionNumb();
            }
            map.put("transRefOrCheckNumber", tranNum);
            map.put("bankName", invoicePayment.getBankName());
            map.put("checkDate", invoicePayment.getChequeOrDdDate());

            map.put("invoiceId", invoicePayment.getInvoice().getId());
            map.put("billableAmount", invoicePayment.getInvoice().getTotalAmount().getAmount());
            map.put("paidAmount",BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
          /*  map.put("insuranceAmount",BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));
            map.put("corporateAmount",BigDecimal.ZERO.setScale(3, RoundingMode.HALF_UP));*/

            map.put("invoiceNumber", invoicePayment.getInvoice().getInvoiceNumber());
         /*   map.put("doctorName",invoicePayment.getInvoice().getConsultant().getFirstName()+""+invoicePayment.getInvoice().getConsultant().getLastName());
            map.put("fileNumber", invoicePayment.getInvoice().getPatient().getFileNo());*/
            if("OPD_CASH".equals(invoicePayment.getPaymentType().toString())){
                map.put("paidAmount", invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP) );
            }
            if("OPD_PERSONAL_CHEQUE".equals(invoicePayment.getPaymentType().toString())){
                map.put("paidAmount", invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP) );
            }
         /*   if("OPD_INSURANCE_AMOUNT".equals(invoicePayment.getPaymentType().toString())){
                map.put("insuranceAmount",invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP) );
            }else if("OPD_CORPORATE_AMOUNT".equals(invoicePayment.getPaymentType().toString())){
                map.put("corporateAmount",invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP) );
            }else{
                map.put("paidAmount", invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP) );
            }*/


            listOfMap.add(map);
        }
        return listOfMap;

    }
    private void updateListByPatientCondition(ArrayList<InvoicePayment> invoicePayments){
        ListIterator<InvoicePayment> invoicePaymentsIterator = invoicePayments.listIterator();
        while (invoicePaymentsIterator.hasNext()) {
            InvoicePayment invoicePayment = invoicePaymentsIterator.next();
            if(patient != null && !patient.equals(invoicePayment.getInvoice().getPatient()) ){
                invoicePaymentsIterator.remove();
                continue;
            }
           /* if(UtilValidator.isNotEmpty(patientType)){
                if( !patientType.equals(invoicePayment.getInvoice().getPatient().getPatientType()) ){
                    invoicePaymentsIterator.remove();
                    continue;
                }
            }
            if(selectedProvider != null && selectedProvider.getId() != null && !selectedProvider.equals(invoicePayment.getInvoice().getConsultant()) ){
                invoicePaymentsIterator.remove();
                continue;
            }*/
            if(invoicePayment.getAmount().getAmount().setScale(3, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) < 0){
                invoicePaymentsIterator.remove();
            }
        }
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public BillingService getBillingService() {
        return billingService;
    }

    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    public Window getInvoiceCollectionWin() {
        return invoiceCollectionWin;
    }

    public void setInvoiceCollectionWin(Window invoiceCollectionWin) {
        this.invoiceCollectionWin = invoiceCollectionWin;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }

    public List<Map<String, Object>> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Map<String, Object>> paymentList) {
        this.paymentList = paymentList;
    }

    public BigDecimal getTotalBillableAmount() {
        return totalBillableAmount;
    }

    public void setTotalBillableAmount(BigDecimal totalBillableAmount) {
        this.totalBillableAmount = totalBillableAmount;
    }

    public BigDecimal getTotalPaidAmount() {
        return totalPaidAmount;
    }

    public void setTotalPaidAmount(BigDecimal totalPaidAmount) {
        this.totalPaidAmount = totalPaidAmount;
    }

    public String getTotalDescriptionFooter1() {
        return totalDescriptionFooter1;
    }

    public void setTotalDescriptionFooter1(String totalDescriptionFooter1) {
        this.totalDescriptionFooter1 = totalDescriptionFooter1;
    }

    public String getTotalDescriptionFooter2() {
        return totalDescriptionFooter2;
    }

    public void setTotalDescriptionFooter2(String totalDescriptionFooter2) {
        this.totalDescriptionFooter2 = totalDescriptionFooter2;
    }
}
