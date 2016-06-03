package com.nzion.service.billing;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.nzion.exception.TransactionException;
import com.nzion.domain.billing.Invoice;

public interface InvoiceManager {

	Invoice generateInvoice(Object object) throws TransactionException;
	
	Map<String,Object> viewInvoiceFor(Object object);
	
	Comparator<String> getItemTypeComparator();
	
	List<String> getItemTypeOrder();
}
