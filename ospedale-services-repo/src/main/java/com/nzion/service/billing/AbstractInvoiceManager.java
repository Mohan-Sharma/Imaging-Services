package com.nzion.service.billing;

import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;

import com.nzion.domain.billing.Invoice;
import com.nzion.exception.TransactionException;
import com.nzion.service.common.CommonCrudService;

public abstract class AbstractInvoiceManager implements InvoiceManager {

	protected BillingService billingService;

	protected CommonCrudService commonCrudService;
	
	public static final String TAX_ITEM_TYPE = "TAX";

	Comparator<String> INVOICE_ITEM_TYPE_COMPARATOR = new Comparator<String>() {
		List<String> itemTypeOrder = getItemTypeOrder();

		@Override
		public int compare(String o1, String o2) {
		if(itemTypeOrder.indexOf(o1)==-1 || itemTypeOrder.indexOf(o2)==-1){
			return 1;				
		}
		return itemTypeOrder.indexOf(o1) - itemTypeOrder.indexOf(o2);
		}
	};
	
	protected void createInvoice(Invoice invoice) throws TransactionException {
	billingService.createInvoice(invoice);
	}

	@Override
	public Comparator<String> getItemTypeComparator() {
	return INVOICE_ITEM_TYPE_COMPARATOR;
	}

	public BillingService getBillingService() {
	return billingService;
	}

	@Resource
	@Required
	public void setBillingService(BillingService billingService) {
	this.billingService = billingService;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	@Resource
	@Required
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}
}