package com.nzion.zkoss.composer;

import com.nzion.domain.UserLogin;
import com.nzion.domain.base.IdGeneratingBaseEntity;
import com.nzion.domain.billing.*;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.exception.TransactionException;
import com.nzion.service.billing.BillingService;
import com.nzion.service.billing.InvoiceManager;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.util.JasperReportUtil;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Required;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class BillingController extends OspedaleAutowirableComposer {

    private BillingService billingService;

    private CommonCrudService commonCrudService;

    private BillingDisplayConfig billingDisplayConfig;

    private Invoice invoice;

    private boolean finalizeInvoice;

    private InvoicePayment invoicePayment;

    private boolean paymentReceived;

    private Map<String, List<InvoiceItem>> invoiceItemMap;

    private Map<String, List<InvoiceItem>> modifyInvoiceItemMap;

    private Map<String, List<InvoiceItem>> invPrevBalanceItemMap;

    private Map<String, List<InvoiceItem>> invoiceDeductibleItemMap;

    private Map<String, List<InvoiceItem>> invoicePrevAdvanceItemMap;

    private List<LabOrderInvoice> labInvoiceItemList;

    private BigDecimal totalAmtTillDate;

    private BigDecimal overallBalance;

    private BigDecimal remainingAmount;

    private BigDecimal writeOffAmount;

    private BigDecimal totalGrossAmount;

    public BigDecimal getTotalGrossAmount() {
        totalGrossAmount = BigDecimal.ZERO;
        for(InvoiceItem li : invoice.getInvoiceItems()){
            totalGrossAmount = totalGrossAmount.add(li.getPrice().getAmount().setScale(3, RoundingMode.HALF_UP));
        }
        return totalGrossAmount.setScale(3, RoundingMode.HALF_UP);
    }

    public void setTotalGrossAmount(BigDecimal totalGrossAmount) {
        this.totalGrossAmount = totalGrossAmount;
    }

    private Money labItemTotalAmount = new Money();

    private TreeMap<String, List<InvoiceItem>> quickBillinvoiceItemMap;


    public TreeMap<String, List<InvoiceItem>> getQuickBillinvoiceItemMap() {
        return quickBillinvoiceItemMap;
    }

    public BillingController(Invoice invoice){
        this.invoice = invoice;
    }

    public void setQuickBillinvoiceItemMap(
            TreeMap<String, List<InvoiceItem>> quickBillinvoiceItemMap) {
        this.quickBillinvoiceItemMap = quickBillinvoiceItemMap;
    }

    public BillingController() throws ParseException {
        invoice = (Invoice) Executions.getCurrent().getArg().get("invoiceObj");
        String invoiceId = null;
        if (invoice != null)
            invoice = (Invoice) Executions.getCurrent().getArg().get("invoiceObj");
        else {
            invoiceId = Executions.getCurrent().getParameter("invoiceId");
            if (UtilValidator.isNotEmpty(invoiceId)) {
                NumberFormat nf = NumberFormat.getInstance();
                Number number = nf.parse(invoiceId);
                invoice = commonCrudService.getById(Invoice.class, number.longValue());

            }
        }
        if (invoice != null)
            extractInvoiceToDisplay(invoice);
        if (invoicePayment.getAmount().getAmount() != null)
            buildRemainingAmount();

        if (UtilValidator.isNotEmpty(invoice.getWrittenOffAmount()))
            writeOffAmount = invoice.getWrittenOffAmount().getAmount();

    }

    public void extractInvoiceToDisplay(Invoice inv) {
        invoice = inv;
        InvoiceManager manager = billingService.getManager(invoice);
        if (manager != null) {
            invoiceItemMap = new TreeMap<String, List<InvoiceItem>>(manager.getItemTypeComparator());
            invoiceDeductibleItemMap = new TreeMap<String, List<InvoiceItem>>(manager.getItemTypeComparator());
            invoicePrevAdvanceItemMap = new TreeMap<String, List<InvoiceItem>>(manager.getItemTypeComparator());
            invPrevBalanceItemMap = new TreeMap<String, List<InvoiceItem>>(manager.getItemTypeComparator());
            invoicePayment = new InvoicePayment();
            billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
            paymentReceived = (InvoiceStatusItem.RECEIVED.toString().equals(invoice.getInvoiceStatus()) || InvoiceStatusItem.WRITEOFF.toString().equals(invoice.getInvoiceStatus()));
            List result = commonCrudService.findByEquality(AcctgTransaction.class, new String[]{"invoiceId"}, new Object[]{invoice.getId()});
            if (result.size() > 0) {
                finalizeInvoice = false;
            }
            remainingAmount = invoice.getTotalAmount().getAmount().subtract(invoice.getCollectedAmount().getAmount());
            builItemMap();
        } else {
            invoiceItemMap = new TreeMap<String, List<InvoiceItem>>();
            quickBillinvoiceItemMap = new TreeMap<String, List<InvoiceItem>>();
            invoicePayment = new InvoicePayment();
            billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
            paymentReceived = (InvoiceStatusItem.RECEIVED.toString().equals(invoice.getInvoiceStatus()) || InvoiceStatusItem.WRITEOFF.toString().equals(invoice.getInvoiceStatus()));
            List result = commonCrudService.findByEquality(AcctgTransaction.class, new String[]{"invoiceId"}, new Object[]{invoice.getId()});
            if (result.size() > 0) {
                finalizeInvoice = false;
            }
            remainingAmount = invoice.getTotalAmount().getAmount().subtract(invoice.getCollectedAmount().getAmount());
            builItemMap();
        }
    }


    public boolean isFinalizeInvoice() {
        return finalizeInvoice;
    }

    public void setFinalizeInvoice(boolean finalizeInvoice) {
        this.finalizeInvoice = finalizeInvoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


    @SuppressWarnings("null")
    public void builItemMap() {
        List<InvoiceItem> nonDeductibleItems = null;
        List<InvoiceItem> deductibleItems = null;
        List<InvoiceItem> advanceItems = null;
        List<InvoiceItem> invPrevBalanceItems = null;
        List<InvoiceItem> invQuickBillItems = null;
        if (invoice.getInvoiceItems() != null) {
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                if (invoiceItem.getItemType().name().equals("PREVIOUSBALANCE")) {
                    invPrevBalanceItems = UtilValidator.isEmpty(invoicePrevAdvanceItemMap.get(invoiceItem.getItemType().name())) ? new ArrayList<InvoiceItem>() : invoicePrevAdvanceItemMap.get(invoiceItem.getItemType().name());
                    invPrevBalanceItems.add(invoiceItem);
                    invPrevBalanceItemMap.put(invoiceItem.getItemType().name(), invPrevBalanceItems);
                } else {
                    nonDeductibleItems = UtilValidator.isEmpty(invoiceItemMap.get(invoiceItem.getItemType().name())) ? new ArrayList<InvoiceItem>() : invoiceItemMap.get(invoiceItem.getItemType().name());
                    nonDeductibleItems.add(invoiceItem);
                    invoiceItemMap.put(invoiceItem.getItemType().name(), nonDeductibleItems);
                }
            }
        }

    }

    public BigDecimal buildRemainingAmount() {
        Set<InvoicePayment> invoicePayments = invoice.getInvoicePayments();
        BigDecimal allPaidAmonut = BigDecimal.ZERO;
        for (InvoicePayment invoicePayment : invoicePayments) {
            allPaidAmonut = allPaidAmonut.add(invoicePayment.getAmount().getAmount());
        }
        remainingAmount = invoice.getTotalAmount().getAmount().subtract(allPaidAmonut);
        if (UtilValidator.isNotEmpty(invoice.getWrittenOffAmount()))
            remainingAmount = remainingAmount.subtract(invoice.getWrittenOffAmount().getAmount());
        return remainingAmount;
    }


    public void addTxnPaymentItem() throws InterruptedException{
        if (invoicePayment.getAmount().getAmount().compareTo(remainingAmount) == 1) {
            invoicePayment = new InvoicePayment(invoicePayment.getPaymentMethod(), invoicePayment.getInvoice(), invoicePayment.getAmount()
                    , invoicePayment.getPaymentType());
            UtilMessagesAndPopups.showError("Collected amount cannot be more than amount to be paid");
            return;
        }

        String enumCode = invoicePayment.getPaymentMethod().getEnumCode();

        if (invoice.getInvoiceType().name().equals(InvoiceType.OPD.name())) {
            if (enumCode.equals("CASH"))
                invoicePayment.setPaymentType(PaymentType.OPD_CASH);
            if (enumCode.equals("DEBIT_CARD"))
                invoicePayment.setPaymentType(PaymentType.OPD_DEBIT_CARD);
            if (enumCode.equals("CREDIT_CARD"))
                invoicePayment.setPaymentType(PaymentType.OPD_CREDIT_CARD);
            if (enumCode.equals("PERSONAL_CHEQUE"))
                invoicePayment.setPaymentType(PaymentType.OPD_PERSONAL_CHEQUE);
            if (enumCode.equals("INSURANCE_CARD"))
                invoicePayment.setPaymentType(PaymentType.OPD_INSURANCE_CARD);
        } else {
            if (enumCode.equals("CASH"))
                invoicePayment.setPaymentType(PaymentType.CASUALTY_CASH);
            if (enumCode.equals("DEBIT_CARD"))
                invoicePayment.setPaymentType(PaymentType.CASUALTY_DEBIT_CARD);
            if (enumCode.equals("CREDIT_CARD"))
                invoicePayment.setPaymentType(PaymentType.CASUALTY_CREDIT_CARD);
            if (enumCode.equals("PERSONAL_CHEQUE"))
                invoicePayment.setPaymentType(PaymentType.CASUALTY_PERSONAL_CHEQUE);
            if (enumCode.equals("INSURANCE_CARD"))
                invoicePayment.setPaymentType(PaymentType.CASUALTY_INSURANCE_CARD);
        }
        invoice.addInvoicePayment(invoicePayment);
        invoice.getCollectedAmount().setAmount(invoice.getCollectedAmount().getAmount().add(invoicePayment.getAmount().getAmount()));
        remainingAmount = remainingAmount.subtract(invoicePayment.getAmount().getAmount());

        invoicePayment = new InvoicePayment();
        UtilMessagesAndPopups.showSuccess();
       // Executions.sendRedirect(null);
    }

    public void writeOffBillAmt() throws InterruptedException {
        if (UtilValidator.isNotEmpty(invoice.getCollectedAmount().getAmount())) {
            Money tmpMoney = new Money(invoice.getTotalAmount().getAmount().subtract(invoice.getCollectedAmount().getAmount()), invoice.getTotalAmount().getCurrency());
            invoice.setWrittenOffAmount(tmpMoney);
            invoice.setWriteOffDate(new Date());
            invoice.setWriteOffBy(Infrastructure.getUserName());
            invoice.setInvoiceStatus(InvoiceStatusItem.WRITEOFF.toString());
            remainingAmount = BigDecimal.ZERO;
            InvoicePayment invoicePayment = new InvoicePayment();
            if (tmpMoney.gt(new Money(BigDecimal.ZERO))) {

                invoicePayment.setAmount(tmpMoney);
                if (invoice.getInvoiceType().name().equals(InvoiceType.OPD.name()))
                    invoicePayment.setPaymentType(PaymentType.OPD_WRITE_OFF);
                else
                    invoicePayment.setPaymentType(PaymentType.CASUALTY_WRITE_OFF);

            }
            billingService.saveInvoiceStatusAsWriteOff(invoice, InvoiceStatusItem.WRITEOFF, invoicePayment);
            Executions.sendRedirect(null);


            UtilMessagesAndPopups.showSuccess();
        }
    }

    public void saveInvoice() {
        if (UtilValidator.isNotEmpty(invoice.getCollectedAmount().getAmount())) {
            if (invoice.getCollectedAmount().getAmount().equals(BigDecimal.ZERO)) {
                UtilMessagesAndPopups.showError("Collected Amount cannot be zero");
                return;
            }
        }
        UtilMessagesAndPopups.showSuccess();
        invoice.setInvoiceStatus(InvoiceStatusItem.INPROCESS.toString());
        billingService.saveInvoiceStatus(invoice, InvoiceStatusItem.INPROCESS);
    }

    public boolean displayWriteOff() {
        return (remainingAmount.compareTo(BigDecimal.ZERO) > 0 && paymentReceived && !(InvoiceStatusItem.WRITEOFF.toString().equals(invoice.getInvoiceStatus())));
    }

    public boolean isWriteOff() {
        return InvoiceStatusItem.WRITEOFF.toString().equals(invoice.getInvoiceStatus());
    }

    public void receivePayment() throws InterruptedException {
        String msg = "";
        if (UtilValidator.isNotEmpty(invoice.getCollectedAmount().getAmount())) {
            if ((invoice.getCollectedAmount().getAmount().compareTo(invoice.getTotalAmount().getAmount()) == 1)) {
                UtilMessagesAndPopups.showError("Cannot be completed the billing : as amount collected is more than amount to be paid.");
                return;
            }
            if (((invoice.getCollectedAmount().getAmount().compareTo(invoice.getTotalAmount().getAmount()) == -1)))
                msg = "Collected amount is less than amount be to paid. ";
        }
        Messagebox.show(msg + "Once processed cannot be modified.", "Payment Confirm ?", Messagebox.YES | Messagebox.NO,
                Messagebox.QUESTION, new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        if ("onYes".equalsIgnoreCase(event.getName())) {
                            invoice.setInvoiceStatus(InvoiceStatusItem.RECEIVED.toString());
                            UserLogin login = Infrastructure.getUserLogin();
                            invoice.setCollectedByUser(login.getUsername());
                            billingService.saveInvoiceStatus(invoice, InvoiceStatusItem.RECEIVED);
                            Executions.sendRedirect(null);

                        }
                    }
                });
    }

    public void printBill() throws JRException {
        Map<String, Object> reportParameterMap = new HashMap<String, Object>();
        reportParameterMap.put("patientAddress", invoice.getPatient().getContacts().getPostalAddress().toString());
        reportParameterMap.put("practiceAddress", Infrastructure.getPractice().getContacts().getPostalAddress().toString());
        reportParameterMap.put("patientFormattedName", commonCrudService.getFormattedName(invoice.getPatient()));
        JasperReportUtil.buildPdfReport("patientBilling.xml", reportParameterMap, invoice.getInvoiceItems(), "invoice.pdf");
    }

    public void billPrint() throws JRException {
        Map<String, String> reportParameterMap = new HashMap<String, String>();
        reportParameterMap.put("patientAddress", invoice.getPatient().getContacts().getPostalAddress().toString());
        reportParameterMap.put("practiceAddress", Infrastructure.getPractice().getContacts().getPostalAddress().toString());
        reportParameterMap.put("patientFormattedName", commonCrudService.getFormattedName(invoice.getPatient()));
        Executions.getCurrent().sendRedirect("/billing/billingTxnItemPrint.zul?invoiceId=" + invoice.getId(), "_BillSoapNote");
    }

    public void removeTxnPaymentItem(InvoicePayment txnPayment) {

        if (UtilValidator.isNotEmpty(invoice.getCollectedAmount().getAmount()))
            invoice.getInvoicePayments().remove(txnPayment);
        invoice.getCollectedAmount().setAmount(invoice.getCollectedAmount().getAmount().subtract(txnPayment.getAmount().getAmount()));
        remainingAmount = remainingAmount.add(txnPayment.getAmount().getAmount());
    }

    @Resource
    @Required
    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    @Resource
    @Required
    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public BillingDisplayConfig getBillingDisplayConfig() {
        return billingDisplayConfig;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public Map<String, List<InvoiceItem>> getInvoiceItemMap() {
        return invoiceItemMap;
    }

    public InvoicePayment getInvoicePayment() {
        return invoicePayment;
    }

    public void setInvoicePayment(InvoicePayment invoicePayment) {
        this.invoicePayment = invoicePayment;
    }

    public boolean isPaymentReceived() {
        return paymentReceived;
    }

    public Map<String, List<InvoiceItem>> getInvoiceDeductibleItemMap() {
        return invoiceDeductibleItemMap;
    }

    public Map<String, List<InvoiceItem>> getInvoicePrevAdvanceItemMap() {
        return invoicePrevAdvanceItemMap;
    }

    public void setInvoicePrevAdvanceItemMap(
            Map<String, List<InvoiceItem>> invoicePrevAdvanceItemMap) {
        this.invoicePrevAdvanceItemMap = invoicePrevAdvanceItemMap;
    }

    public BigDecimal getTotalAmtTillDate() {
        return totalAmtTillDate;
    }

    public BigDecimal getOverallBalance() {
        return overallBalance;
    }

    public Invoice genInvoiceForSelectedItems(Invoice invObj, List<InvoiceItem> selectedInvItems, Map<String, Collection<? extends IdGeneratingBaseEntity>> allItemsInSelectedInv) {
        invObj.getTotalAmount().setAmount(BigDecimal.ZERO);
        invObj.getInvoiceItems().clear();
        Set<? extends IdGeneratingBaseEntity> itemTypeEntities;
        Set<Object> enitiesToUpd = new HashSet<Object>();
        for (InvoiceItem invItem : selectedInvItems) {
            itemTypeEntities = (Set<? extends IdGeneratingBaseEntity>) allItemsInSelectedInv.get(invItem.getItemType());
            if (itemTypeEntities != null) {
                for (Object obj : itemTypeEntities) {
                    IdGeneratingBaseEntity be = (IdGeneratingBaseEntity) obj;
                    if (be.getId().toString().equals(invItem.getItemId()))
                        enitiesToUpd.add(be);
                }
            }
            invObj.addInvoiceItem(invItem);
        }
        commonCrudService.save(enitiesToUpd);
        try {
            billingService.createInvoice(invObj);
        } catch (TransactionException e) {
            UtilMessagesAndPopups.showError(e.getMessage());
        }
        return invObj;
    }

    public Map<String, List<InvoiceItem>> getInvPrevBalanceItemMap() {
        return invPrevBalanceItemMap;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public BigDecimal getWriteOffAmount() {
        return writeOffAmount;
    }

    public BillingService getBillingService() {
        return billingService;
    }


    public class LabOrderInvoice {
        private String description;
        private BigDecimal factor = BigDecimal.ONE;
        private BigDecimal quantity = BigDecimal.ONE;
        private BigDecimal price = BigDecimal.ZERO;


        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getFactor() {
            return factor;
        }

        public void setFactor(BigDecimal factor) {
            this.factor = factor;
        }

        public BigDecimal getQuantity() {
            return quantity;
        }

        public void setQuantity(BigDecimal quantity) {
            this.quantity = quantity;
        }


    }

    private class NurseObservationComparator implements Comparator<InvoiceItem> {

        @Override
        public int compare(InvoiceItem o1, InvoiceItem o2) {
            return o1.getItemId().compareTo(o2.getItemId());
        }

    }

    private static final long serialVersionUID = 1L;
}
