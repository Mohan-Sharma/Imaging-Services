package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.lab.LabOrderRequest.ORDERSTATUS;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.domain.emr.soap.PatientLabOrder.STATUS;
import com.nzion.exception.TransactionException;
import com.nzion.service.billing.BillingService;
import com.nzion.service.billing.LabInvoiceManager;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.HashSet;
import java.util.Set;

@VariableResolver(DelegatingVariableResolver.class)
public class LabOrderInvoiceViewModel {

    @WireVariable("labInvoiceManager")
    @Qualifier("labInvoiceManager")
    private LabInvoiceManager labInvoiceManager;

    @WireVariable
    private BillingService billingService;

    @WireVariable
    private CommonCrudService commonCrudService;

    private Set<PatientLabOrder> labTestsOrdered;

    private Set<PatientLabOrder> selected;

    private LabOrderRequest labOrderRequest;

    private Invoice invoice;

    private int noOfCancelledLabOrder;

    public int getNoOfCancelledLabOrder() {
        return noOfCancelledLabOrder;
    }

    public void setNoOfCancelledLabOrder(int noOfCancelledLabOrder) {
        this.noOfCancelledLabOrder = noOfCancelledLabOrder;
    }

    private Boolean enableReceipt;
    private Boolean generateInvoice;
    private Boolean invoiceReady;

    private Boolean allLabOrderCancelled = Boolean.FALSE;

    @Init
    public void init(
            @ExecutionArgParam("labOrderRequest") LabOrderRequest labOrderRequest) {
        this.labOrderRequest = labOrderRequest;
        labTestsOrdered = new HashSet<PatientLabOrder>();
        selected =   new HashSet<PatientLabOrder>();
        for (PatientLabOrder patientLabOrder : labOrderRequest
                .getPatientLabOrders()) {
            if (PatientLabOrder.BILLINGSTATUS.INVOICED == patientLabOrder
                    .getBillingStatus()) {
                selected.add(patientLabOrder);
            }
            labTestsOrdered.add(patientLabOrder);
        }

        allLabOrderCancelled = isAllOrderCancelled();
        enableReceipt = Boolean.FALSE;
        generateInvoice = Boolean.TRUE;
        invoiceReady = Boolean.FALSE;
    }

    private Boolean isAllOrderCancelled() {
        for (PatientLabOrder patientLabOrder : labTestsOrdered) {
            if (STATUS.CANCELLED.equals(patientLabOrder.getStatus()))
                noOfCancelledLabOrder = noOfCancelledLabOrder + 1;
        }
        return noOfCancelledLabOrder == labTestsOrdered.size();
    }

    public Set<PatientLabOrder> getLabTestsOrdered() {
        return labTestsOrdered;
    }

    public void setLabTestsOrdered(Set<PatientLabOrder> labTestsOrdered) {
        this.labTestsOrdered = labTestsOrdered;
    }

    public void setSelected(Set<PatientLabOrder> selected) {
        this.selected = selected;
    }

    public Set<PatientLabOrder> getSelected() {
        return selected;
    }

    @Command("GenerateInvoice")
    @NotifyChange({"invoiceReady", "generateInvoice"})
    public void generateInvoice() {
        if (UtilValidator.isEmpty(selected)) {
            UtilMessagesAndPopups
                    .showError("Select at least one lab order item");
            return;
        }
        try {
            invoice = labInvoiceManager.generateInvoice(labOrderRequest,selected);
        } catch (TransactionException e) {
            e.printStackTrace();
        }

        generateInvoice = Boolean.FALSE;
        invoiceReady = Boolean.TRUE;
        UtilMessagesAndPopups.displaySuccess("Invoice Generated Successfully.");
    }

    @NotifyChange("enableReceipt")
    @Command("MarkInvoiceAsFinal")
    public void markInvoiceAsFinal() {
        try {
            if (labOrderRequest.getPatientLabOrders().size() == selected.size()) {
                labOrderRequest.setOrderStatus(LabOrderRequest.ORDERSTATUS.INVOICED);
                commonCrudService.save(labOrderRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        invoiceReady = Boolean.FALSE;
        enableReceipt = Boolean.TRUE;
    }

    @NotifyChange("enableReceipt")
    @Command(" CreateReceipt")
    public void CreateReceipt() {
        Executions.getCurrent().sendRedirect(
                "/billing/billingTxnItem.zul?invoiceId=" + invoice.getId(),
                "_BillSoapNote");
    }

    public Boolean getEnableReceipt() {
        return enableReceipt;
    }

    public void setEnableReceipt(Boolean enableReceipt) {
        this.enableReceipt = enableReceipt;
    }

    public Boolean getGenerateInvoice() {
        return generateInvoice;
    }

    public void setGenerateInvoice(Boolean generateInvoice) {
        this.generateInvoice = generateInvoice;
    }

    public Boolean getInvoiceReady() {
        return invoiceReady;
    }

    public void setInvoiceReady(Boolean invoiceReady) {
        this.invoiceReady = invoiceReady;
    }

    @Command
    @NotifyChange({"labTestsOrdered", "allLabOrderCancelled"})
    public void CancelOrder() {
        for (PatientLabOrder patientLabOrder : selected) {
            patientLabOrder.setStatus(STATUS.CANCELLED);
            commonCrudService.save(patientLabOrder);
        }
        if (labTestsOrdered.size() == selected.size() + noOfCancelledLabOrder) {
            labOrderRequest.setOrderStatus(ORDERSTATUS.CANCELLED);
            commonCrudService.save(labOrderRequest);
        }
        allLabOrderCancelled = isAllOrderCancelled();

    }

    @Command
    @NotifyChange("labTestsOrdered")
    public void cancelInvoice() throws TransactionException {
        invoice.setInvoiceStatus(InvoiceStatusItem.CANCELLED.toString());
        billingService.saveInvoiceStatus(invoice, InvoiceStatusItem.CANCELLED);
    }

    public Boolean getAllLabOrderCancelled() {
        return allLabOrderCancelled;
    }

    public void setAllLabOrderCancelled(Boolean allLabOrderCancelled) {
        this.allLabOrderCancelled = allLabOrderCancelled;
    }

}
