package com.nzion.view.component;

import java.math.BigDecimal;
import java.util.Currency;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;

import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.enums.ROUNDING_MODE;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;

public class BillingAmountConverter implements TypeConverter {
	CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	 BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
	@Override
	public Object coerceToBean(Object val, Component comp) {
	return null;
	}

	@Override
	public Object coerceToUi(Object object, Component comp) {
	BigDecimal amount = null;
	UnitOfMeasurement uom = null;
	if(object instanceof Invoice){
		Invoice txn = ((Invoice)object);
		amount = txn.getTotalAmount().getAmount();
		uom = billingDisplayConfig.getCurrency();
		//uom = txn.getTotalAmount().getCurrency();
		
		
	}
	else if(object instanceof InvoiceItem){
		InvoiceItem txnItem = ((InvoiceItem)object);
		amount = txnItem.getPrice().getAmount();
		uom = billingDisplayConfig.getCurrency();
	//	uom = txnItem.getPrice().getCurrency();
	}	
	if(amount == null)
		amount = BigDecimal.ZERO;
	CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
	Money money = null;
	if (ROUNDING_MODE.CEIL.equals(billingDisplayConfig.getRoundingMODE()))
		money =new Money(amount.setScale(billingDisplayConfig.getDecimalPoints(), amount.ROUND_CEILING),Currency.getInstance(uom.getCode()) );
	else
		if (ROUNDING_MODE.FLOOR.equals(billingDisplayConfig.getRoundingMODE()))
			money=new Money(amount.setScale(billingDisplayConfig.getDecimalPoints(), amount.ROUND_FLOOR), Currency.getInstance(uom.getCode()) );
	if(comp instanceof Label){
		Label label = (Label)comp;
		label.setValue(money == null ? "" : money.toString());
	}
	else if(comp instanceof Decimalbox){
		String formatter = "0.";
		Decimalbox decimalbox = (Decimalbox)comp;
		for(int i=0;i<billingDisplayConfig.getDecimalPoints();i++)
			formatter = formatter+"0";
		decimalbox.setFormat(formatter);
	}
	return null;
	}

}
