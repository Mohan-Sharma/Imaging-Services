package com.nzion.superbill.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;

/**
 * Created with IntelliJ IDEA. User: Admin Date: 6/14/14 Time: 2:30 PM To change
 * this template use File | Settings | File Templates.
 */
public class InvoiceItemDto {
	public InvoiceType itemType;
	public String itemCode;
	public String itemName;
	public Integer quantity;
	public BigDecimal amount;

	public static List<InvoiceItem> createInvoiceItem(List<InvoiceItemDto> invoiceItemDtoList,Invoice invoice, CommonCrudService commonCrudService,Referral referral,Provider provider) {
		List<InvoiceItem> invoiceItemList = new ArrayList<InvoiceItem>();
		if(UtilValidator.isEmpty(invoiceItemDtoList)){
			return Collections.EMPTY_LIST;
		}
		BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
		for(InvoiceItemDto invoiceItemDto:invoiceItemDtoList){
			InvoiceType invoiceType = invoiceItemDto.itemType;
			InvoiceItem invoiceItem = new InvoiceItem(invoice, invoiceItemDto.itemCode,invoiceType, invoiceItemDto.itemName, invoiceItemDto.quantity,null, null);
			invoiceItem.init(invoiceItemDto.amount,billingDisplayConfig.getCurrency().getCode(), new Money(invoiceItemDto.amount,convertTo(billingDisplayConfig)), 0);
			if(referral!=null)
				invoiceItem.setReferralId(referral.getId());
			if(provider!=null)
				invoiceItem.setProviderId(provider.getId());
			invoiceItemList.add(invoiceItem);
			if(invoice.getTotalAmount() != null && invoice.getTotalAmount().getAmount() != null)
            	invoice.setTotalAmount(new Money(invoice.getTotalAmount().getAmount().add(invoiceItemDto.amount),convertTo(billingDisplayConfig)));
            else
            	invoice.setTotalAmount(new Money(invoiceItemDto.amount,convertTo(billingDisplayConfig)));
		}
		return invoiceItemList;
	}

	public static Currency convertTo(BillingDisplayConfig billingDisplayConfig) {
		String currency = billingDisplayConfig.getCurrency().getCode();
		Currency defaultCurrency = Currency.getInstance(currency);
		return defaultCurrency;
	}
}
