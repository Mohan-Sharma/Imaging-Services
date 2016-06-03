package com.nzion.zkoss.composer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import com.nzion.domain.Provider;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.exception.TransactionException;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.view.QuickBillItemValueObject;

@VariableResolver(DelegatingVariableResolver.class)
public class MiscellaneousChargesController {
	
	private static final long serialVersionUID = 1L;

	@WireVariable
	private CommonCrudService commonCrudService;
	
	private List<QuickBillItemValueObject> invoiceItems = new ArrayList<QuickBillItemValueObject>();
	
	private List<Provider> providers;
	
	private Invoice invoice;
	

	public static final String CASUALTY_TYPE = "CASUALTY";

	@Wire("#newMiscellaneousWin")
	private Window newMiscellaneousWin;
	
	
	@Init
	public void init(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, true);
		invoiceItems.add(new QuickBillItemValueObject());
	    providers = commonCrudService.getAll(com.nzion.domain.Provider.class);
	    this.invoice = new Invoice();
	   }
	
	@Command("MarkSave")
	@NotifyChange("invoice")
	public void save(){
		 BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
		 boolean isValidData = validateData(invoiceItems);
		 if(!isValidData){
			 return;
		 }
		 if(invoice.getId()==null){
		 invoice.setLocation(Infrastructure.getSelectedLocation());
		 invoice.setInvoiceType(InvoiceType.CASUALTY);
		 invoice.setItemType(InvoiceType.CASUALTY.name());
		 invoice.setInvoiceDate(new Date());
		 invoice.setInvoiceStatus(InvoiceStatusItem.READY.name());
		 Money totalMiscellaneousPrice=new Money();
		 for(QuickBillItemValueObject item : invoiceItems){
	     InvoiceItem invoiceItem = new InvoiceItem(invoice, null,InvoiceType.CASUALTY ,item.getMiscellaneousChargeName(),item.getQuantity(), null,InvoiceType.CASUALTY.name());
         Money miscellaneousPrice = new Money(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())), convertAndCurrencyWithBillingConfig());
         invoiceItem.init(item.getPrice(), billingDisplayConfig.getCurrency().getCode(), miscellaneousPrice, 0);
         totalMiscellaneousPrice = totalMiscellaneousPrice.plus(miscellaneousPrice);
         invoice.addInvoiceItem(invoiceItem);
         
		 }
		 invoice.setTotalAmount(totalMiscellaneousPrice);
		 invoice = commonCrudService.save(invoice);
		 UtilMessagesAndPopups.displaySuccess();
		 }
		 
	}
	
	private boolean validateData(List<QuickBillItemValueObject> invoiceItems){
		if(UtilValidator.isEmpty(invoice.getPatient())){
			 com.nzion.util.UtilMessagesAndPopups.showError("Select a Patient to proceed");
			 return false;
		 }
		for(QuickBillItemValueObject item : invoiceItems){
			 if(item.getMiscellaneousChargeName()==null){
			  com.nzion.util.UtilMessagesAndPopups.showError("Enter an item description to proceed");
			  return false;
			 }
			 else if(item.getQuantity()==0 || UtilValidator.isEmpty(item.getQuantity())){
				 com.nzion.util.UtilMessagesAndPopups.showError("Enter quantity to proceed");
				 return false;
			 }
			 else if(item.getPrice()==null){
				  com.nzion.util.UtilMessagesAndPopups.showError("Enter price to proceed");
				  return false;
			 }
		}
		return true;
	}
	
	
	@Command("receivePayment")
	public void receivePayment() throws TransactionException {
		String invoicePaymentUrl = "/billing/miscellaneousBillingTxnItem.zul?invoiceId=";
		invoicePaymentUrl = invoicePaymentUrl+invoice.getId();
		Executions.getCurrent().sendRedirect(invoicePaymentUrl ,"_Blank");
	}
	
	@Command("addInvoiceItems")
	@NotifyChange("invoiceItems")
	public void addInvoiceItems() {
		invoiceItems.add(new QuickBillItemValueObject());
	}
	
	@Command("computeTotal")
	@NotifyChange("invoiceItems")
	public void computeTotal() {
	}
	
	@Command("removeInvoiceItem")
	@NotifyChange("invoiceItems")
	public void removeInvoiceItem(@BindingParam("invoiceItemObj") QuickBillItemValueObject invoiceItem) {
		invoiceItems.remove(invoiceItem);
	}
	
	 private  Currency convertAndCurrencyWithBillingConfig(){
	  BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
	  String currency = billingDisplayConfig.getCurrency().getCode();
	  Currency defaultCurrency = Currency.getInstance(currency);
	  return defaultCurrency;
     }

	public List<Provider> getProviders() {
		return providers;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public List<QuickBillItemValueObject> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<QuickBillItemValueObject> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}
	
}
