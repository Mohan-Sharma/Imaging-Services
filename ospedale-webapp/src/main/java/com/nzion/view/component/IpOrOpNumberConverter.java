package com.nzion.view.component;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listitem;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.util.UtilValidator;

public class IpOrOpNumberConverter implements TypeConverter{

	 public Object coerceToBean(java.lang.Object val, org.zkoss.zk.ui.Component comp) {
		 return null;
		 }
	
		 public Object coerceToUi(java.lang.Object val,  final org.zkoss.zk.ui.Component comp) {
			String d = (String)val;
			if(UtilValidator.isEmpty(val)){
				Object obj =  ((Listitem)comp.getParent().getParent()).getValue();
				Invoice inv = null;
				if(obj instanceof InvoiceItem)
					inv = ((InvoiceItem)obj).getInvoice();
				else
					inv = (Invoice)obj;
				if(inv.getPatient()!=null)
				return inv.getPatient().getAccountNumber();
				
				}
				return d;
		}	
}
