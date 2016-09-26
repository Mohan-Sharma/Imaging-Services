package com.nzion.zkoss.composer.emr;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Listheader;

import com.nzion.domain.billing.AcctgTransTypeEnum;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.report.dto.CollectionReportDto;
import com.nzion.report.dto.CollectionReportItemDto;
import com.nzion.report.search.view.BillingSearchVO;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;
import com.nzion.view.component.StringAscendingComparator;
import com.nzion.view.component.StringDescendingComparator;

public class BillingSearchController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;

	private BillingService billingService;
	
	private CommonCrudService commonCrudService;
	
	private BillingSearchVO billingSearchVO;
	
	private BigDecimal collectionAmount = BigDecimal.ZERO;
	
	BigDecimal writeOffAmount = BigDecimal.ZERO;
	
	private BigDecimal totalCollectionAmount = BigDecimal.ZERO;
	
	private List<Invoice> invoices;

	private Map<String, Set<Invoice>> invoiceMap = new HashMap<String, Set<Invoice>>();
	
	private Map<String, Set<InvoiceItem>> invoiceCptItemMap =  new HashMap<String, Set<InvoiceItem>>();
	
	private StringAscendingComparator ascendingComparator = new StringAscendingComparator();
	
	private StringDescendingComparator descendingComparator = new StringDescendingComparator();
	
	public Map<String, Set<Invoice>> getInvoiceMap() {
		return invoiceMap;
	}

	public void setInvoiceMap(Map<String, Set<Invoice>> invoiceMap) {
		this.invoiceMap = invoiceMap;
	}	
	

	public Map<String, Set<InvoiceItem>> getInvoiceCptItemMap() {
		return invoiceCptItemMap;
	}

	public void setInvoiceCptItemMap(Map<String, Set<InvoiceItem>> invoiceCptItemMap) {
		this.invoiceCptItemMap = invoiceCptItemMap;
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	billingSearchVO = new BillingSearchVO();
	return super.doBeforeCompose(page, parent, compInfo);
	}
	
	public void setBillingService(BillingService billingService) {
	this.billingService = billingService;
	}
	
	public void  getGroupedItems(String item){
	if(UtilValidator.isEmpty(invoices))
		return;
	invoiceMap = billingService.getGroupedItems(item,new HashSet<Invoice>(invoices));
	}
	
	public void  getCptGroupedItems(String item){
		if(UtilValidator.isEmpty(invoices))
			return;
		invoiceCptItemMap = billingService.getCptGroupedItems(item, new HashSet<Invoice>(invoices));
	}

	public List<Invoice> searchInvoiceBy(Date fromDate,Date thruDate) {
	invoices =  billingService.searchInvoiceBy(billingSearchVO,fromDate,thruDate);
	return invoices;
	}

	public List<Invoice> searchReferralInvoiceBy(Date fromDate,Date thruDate) {
		invoices =  billingService.searchReferralInvoiceBy(billingSearchVO,fromDate,thruDate);
		return invoices;
	}

	public BillingSearchVO getBillingSearchVO() {
	return billingSearchVO;
	}
	

	public void setBillingSearchVO(BillingSearchVO billingSearchVO) {
	this.billingSearchVO = billingSearchVO;
	}

	public List<Invoice> getInvoices() {
	return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public StringAscendingComparator getAscendingComparator() {
		return ascendingComparator;
	}

	public void setAscendingComparator(StringAscendingComparator ascendingComparator) {
		this.ascendingComparator = ascendingComparator;
	}

	public StringDescendingComparator getDescendingComparator() {
		return descendingComparator;
	}

	public void setDescendingComparator(
			StringDescendingComparator descendingComparator) {
		this.descendingComparator = descendingComparator;
	}
	
	public BigDecimal getCollectionAmount() {
		return collectionAmount;
	}

	public void setCollectionAmount(BigDecimal collectionAmount) {
		this.collectionAmount = collectionAmount;
	}
	
	public BigDecimal getWriteOffAmount() {
		return writeOffAmount;
	}

	public void setWriteOffAmount(BigDecimal writeOffAmount) {
		this.writeOffAmount = writeOffAmount;
	}
	
	public BigDecimal getTotalCollectionAmount() {
		return totalCollectionAmount;
	}

	public void setTotalCollectionAmount(BigDecimal totalCollectionAmount) {
		this.totalCollectionAmount = totalCollectionAmount;
	}

	public void setAscendingComparator(Component component, String fieldName) {
		com.nzion.view.component.GenericAscendingComparator comparator = new com.nzion.view.component.GenericAscendingComparator(fieldName);
			((Listheader) component).setSortAscending(comparator);
	}
	
	public void setDescendingComparator(Component component, String fieldName) {
		com.nzion.view.component.GenericDescendingCompartor comparator = new com.nzion.view.component.GenericDescendingCompartor(fieldName);
			((Listheader) component).setSortDescending(comparator);
	}

	
	public Map<String, Set<InvoiceItem>> groupByInvoiceItems(){
		Map<String, Set<InvoiceItem>> InvoiceItemsMap = new HashMap<String, Set<InvoiceItem>>(); 
		if(UtilValidator.isEmpty(invoices))
			return InvoiceItemsMap;
		for(Invoice invObj : invoices){
			for(InvoiceItem invItem :  invObj.getInvoiceItems()){
				Set<InvoiceItem> grpItems = InvoiceItemsMap.get(invItem.getItemType().getDescription());
				if(UtilValidator.isEmpty(grpItems)) 
					grpItems = new HashSet();
				grpItems.add(invItem);
				InvoiceItemsMap.put(invItem.getItemType().getDescription(),grpItems);
			}
		}
			
		return InvoiceItemsMap;
	}
	
	public List<Map<String, Object>> groupByInvoiceItemsCollection(Map<String, Set<InvoiceItem>> InvoiceItemsMap){
		totalCollectionAmount = BigDecimal.ZERO;
		collectionAmount = BigDecimal.ZERO;
		writeOffAmount = BigDecimal.ZERO;
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		Set<InvoiceItem> consultationInvoiceItem = InvoiceItemsMap.get(InvoiceType.OPD_CONSULTATION.getDescription());
		Set<InvoiceItem> labTestInvoiceItem = InvoiceItemsMap.get(InvoiceType.OPD_LAB_CHARGES.getDescription());
		Set<InvoiceItem> casualtyInvoiceItem = InvoiceItemsMap.get(InvoiceType.CASUALTY.getDescription());
		if(consultationInvoiceItem != null)
			listMap.addAll(getFormattedConsultationInvoiceItem(consultationInvoiceItem));
		if(labTestInvoiceItem != null)
			listMap.addAll(getFormattedLabTestInvoiceItem(labTestInvoiceItem));
		if(casualtyInvoiceItem != null)
			listMap.addAll(getFormattedcasualtyInvoiceItem(casualtyInvoiceItem));
		for(Invoice inv : invoices){
			if(InvoiceType.OPD.equals(inv.getInvoiceType()))
			writeOffAmount = writeOffAmount.add(inv.getWrittenOffAmount().getAmount());
		}
		totalCollectionAmount = collectionAmount.subtract(writeOffAmount);
		return listMap;
	}
	
	private List<Map<String, Object>> getFormattedConsultationInvoiceItem(Set<InvoiceItem> consultationInvoiceItem){
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		for(InvoiceItem invItem : consultationInvoiceItem){
			Map<String, Object> map = new HashMap<String, Object>();
			int index = 0;
			int removeIndex = 0;
				for(Map m : listMap){
					if(m.get("itemName").equals(invItem.getInvoice().getConsultant())){
						map = m;
						removeIndex = index;
					}
					index++;
				}
			if(UtilValidator.isEmpty(map.get("itemName"))){
				map.put("itemName", invItem.getInvoice().getConsultant());
				map.put("itemCount", 1);
				map.put("itemValue", invItem.getPrice().getAmount());
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.add(map);
			}else{
				map.put("itemCount", ((Integer)   map.get("itemCount")) + 1);
				map.put("itemValue", ((BigDecimal)map.get("itemValue")).add(invItem.getPrice().getAmount()));
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.remove(removeIndex);
				listMap.add(map);
			}
			
		}
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		for(Map<String, Object> map : listMap){
			int val1 =  finalMap.get("itemCount") != null ? Integer.parseInt(finalMap.get("itemCount").toString()) : 0; 
			int val2 = ((Integer) map.get("itemCount")); 
			finalMap.put("itemCount", val1+ val2 );
			BigDecimal price1 = finalMap.get("itemValue") != null ? (BigDecimal) finalMap.get("itemValue") : BigDecimal.ZERO;
			BigDecimal price2 = (BigDecimal) map.get("itemValue");
			finalMap.put("itemValue", price1.add(price2));
		}
		finalMap.put("style", "font-weight:bold;");
		finalMap.put("style2", "font-weight:bold;");
		finalMap.put("itemName", "CONSULATION");
		this.collectionAmount = this.collectionAmount.add((BigDecimal) finalMap.get("itemValue"));
		listMap.add(0,finalMap);
		
		return listMap;
	}
	
	private List<Map<String, Object>> getFormattedLabTestInvoiceItem(Set<InvoiceItem> labTestInvoiceItem){
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		for(InvoiceItem invItem : labTestInvoiceItem){
			LabTestPanel labTestCategoryName = billingService.getLabtestPanelByPanelName(invItem.getDescription());
			Map<String, Object> map = new HashMap<String, Object>();
			int index = 0;
			int removeIndex = 0;
				for(Map m : listMap){
					/*if(m != null && labTestCategoryName != null && UtilValidator.isNotEmpty(m.get("itemName")) 
							&& UtilValidator.isNotEmpty(labTestCategoryName.getLabCategory().getName()) && 
							m.get("itemName").equals(labTestCategoryName.getLabCategory().getName())){
						map = m;
						removeIndex = index;
					}*/
					index++;
				}
			if(labTestCategoryName != null && UtilValidator.isEmpty(map.get("itemName"))){
				//map.put("itemName", labTestCategoryName.getLabCategory().getName());
				map.put("itemCount", 1);
				map.put("itemValue", invItem.getPrice().getAmount());
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.add(map);
			}else if(map != null && map.get("itemCount") != null){
				map.put("itemCount", ((Integer)   map.get("itemCount")) + 1);
				map.put("itemValue", ((BigDecimal)map.get("itemValue")).add(invItem.getPrice().getAmount()));
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.remove(removeIndex);
				listMap.add(map);
			}
			
		}
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		for(Map<String, Object> map : listMap){
			int val1 =  finalMap.get("itemCount") != null ? Integer.parseInt(finalMap.get("itemCount").toString()) : 0; 
			int val2 = ((Integer) map.get("itemCount")); 
			finalMap.put("itemCount", val1+ val2 );
			BigDecimal price1 = finalMap.get("itemValue") != null ? (BigDecimal) finalMap.get("itemValue") : BigDecimal.ZERO;
			BigDecimal price2 = (BigDecimal) map.get("itemValue");
			finalMap.put("itemValue", price1.add(price2));
		}
		finalMap.put("style", "font-weight:bold;");
		finalMap.put("style2", "font-weight:bold;");
		finalMap.put("itemName", "LAB TEST PANEL");
		this.collectionAmount = this.collectionAmount.add((BigDecimal) finalMap.get("itemValue"));
		listMap.add(0,finalMap);
		
		return listMap;
	}
	
	private List<Map<String, Object>> getFormattedcasualtyInvoiceItem(Set<InvoiceItem> casualtyInvoiceItem){
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		for(InvoiceItem invItem : casualtyInvoiceItem){
			Map<String, Object> map = new HashMap<String, Object>();
			int index = 0;
			int removeIndex = 0;
				for(Map m : listMap){
					if(m.get("itemName").equals(invItem.getDescription())){
						map = m;
						removeIndex = index;
					}
					index++;
				}
			if(UtilValidator.isEmpty(map.get("itemName"))){
				map.put("itemName", invItem.getDescription());
				map.put("itemCount", 1);
				map.put("itemValue", invItem.getPrice().getAmount());
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.add(map);
			}else{
				map.put("itemCount", ((Integer)   map.get("itemCount")) + 1);
				map.put("itemValue", ((BigDecimal)map.get("itemValue")).add(invItem.getPrice().getAmount()));
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.remove(removeIndex);
				listMap.add(map);
			}
			
		}
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		for(Map<String, Object> map : listMap){
			int val1 =  finalMap.get("itemCount") != null ? Integer.parseInt(finalMap.get("itemCount").toString()) : 0; 
			int val2 = ((Integer) map.get("itemCount")); 
			finalMap.put("itemCount", val1+ val2 );
			BigDecimal price1 = finalMap.get("itemValue") != null ? (BigDecimal) finalMap.get("itemValue") : BigDecimal.ZERO;
			BigDecimal price2 = (BigDecimal) map.get("itemValue");
			finalMap.put("itemValue", price1.add(price2));
		}
		finalMap.put("style", "font-weight:bold;");
		finalMap.put("style2", "font-weight:bold;");
		finalMap.put("itemName", "CASUALTY");
		this.collectionAmount = this.collectionAmount.add((BigDecimal) finalMap.get("itemValue"));
		returnMap.add(0,finalMap);
		
		return returnMap;
	}
	
	
	
	private List<Map<String, Object>> getFormattedRadiologyInvoiceItem(Set<InvoiceItem> radiologyInvoiceItem){
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		for(InvoiceItem invItem : radiologyInvoiceItem){
			Map<String, Object> map = new HashMap<String, Object>();
			int index = 0;
			int removeIndex = 0;
				for(Map m : listMap){
					if(m.get("itemName").equals(invItem.getDescription())){
						map = m;
						removeIndex = index;
					}
					index++;
				}
			if(UtilValidator.isEmpty(map.get("itemName"))){
				map.put("itemName", invItem.getDescription());
				map.put("itemCount", 1);
				map.put("itemValue", invItem.getPrice().getAmount());
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.add(map);
			}else{
				map.put("itemCount", ((Integer)   map.get("itemCount")) + 1);
				map.put("itemValue", ((BigDecimal)map.get("itemValue")).add(invItem.getPrice().getAmount()));
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.remove(removeIndex);
				listMap.add(map);
			}
		}
			
		Map<String, Object> finalMap = new HashMap<String, Object>();
		for(Map<String, Object> map : listMap){
			int val1 =  finalMap.get("itemCount") != null ? Integer.parseInt(finalMap.get("itemCount").toString()) : 0; 
			int val2 = ((Integer) map.get("itemCount")); 
			finalMap.put("itemCount", val1+ val2 );
			BigDecimal price1 = finalMap.get("itemValue") != null ? (BigDecimal) finalMap.get("itemValue") : BigDecimal.ZERO;
			BigDecimal price2 = (BigDecimal) map.get("itemValue");
			finalMap.put("itemValue", price1.add(price2));
		}
		finalMap.put("style", "font-weight:bold;");
		finalMap.put("style2", "font-weight:bold;");
		finalMap.put("itemName", "RADIOLOGY");
		this.collectionAmount = this.collectionAmount.add((BigDecimal) finalMap.get("itemValue"));
		//listMap.add(0,finalMap);
		//return listMap;
		returnMap.add(0,finalMap);
		return returnMap;
	}
	
	public Map<String, Set<InvoicePayment>> groupByInvoicePayment(){
		Map<String, Set<InvoicePayment>> InvoicePaymentMap = new HashMap<String, Set<InvoicePayment>>(); 
		if(UtilValidator.isEmpty(invoices))
			return InvoicePaymentMap;
	
		for(Invoice invObj : invoices){
		Invoice invoice = commonCrudService.getById(Invoice.class, invObj.getId());
			for(InvoicePayment invPayment :  invoice.getInvoicePayments()){
				Set<InvoicePayment> grpItems = InvoicePaymentMap.get(invPayment.getPaymentMethod().toString());
				if(UtilValidator.isEmpty(grpItems)) grpItems = new HashSet<InvoicePayment>();
				grpItems.add(invPayment);
				InvoicePaymentMap.put(invPayment.getPaymentMethod().getDescription(),grpItems);
			}
		}
			
		return InvoicePaymentMap;
	}
	
	private List<Map<String, Object>> getFormattedPharmacyInvoiceItem(Set<InvoiceItem> pharmacyInvoiceItem){
		List<Map<String, Object>> listMap = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> returnMap = new ArrayList<Map<String,Object>>();
		for(InvoiceItem invItem : pharmacyInvoiceItem){
			Map<String, Object> map = new HashMap<String, Object>();
			int index = 0;
			int removeIndex = 0;
				for(Map m : listMap){
					if(m.get("itemName").equals(invItem.getDescription())){
						map = m;
						removeIndex = index;
					}
					index++;
				}
			if(UtilValidator.isEmpty(map.get("itemName"))){
				map.put("itemName", invItem.getDescription());
				map.put("itemCount", 1);
				map.put("itemValue", invItem.getPrice().getAmount());
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.add(map);
			}else{
				map.put("itemCount", ((Integer)   map.get("itemCount")) + 1);
				map.put("itemValue", ((BigDecimal)map.get("itemValue")).add(invItem.getPrice().getAmount()));
				map.put("style", "font-weight:normal;margin-left:20px;");
				map.put("style2", "font-weight:normal;");
				listMap.remove(removeIndex);
				listMap.add(map);
			}
		}
			
		Map<String, Object> finalMap = new HashMap<String, Object>();
		for(Map<String, Object> map : listMap){
			int val1 =  finalMap.get("itemCount") != null ? Integer.parseInt(finalMap.get("itemCount").toString()) : 0; 
			int val2 = ((Integer) map.get("itemCount")); 
			finalMap.put("itemCount", val1+ val2 );
			BigDecimal price1 = finalMap.get("itemValue") != null ? (BigDecimal) finalMap.get("itemValue") : BigDecimal.ZERO;
			BigDecimal price2 = (BigDecimal) map.get("itemValue");
			finalMap.put("itemValue", price1.add(price2));
		}
		finalMap.put("style", "font-weight:bold;");
		finalMap.put("style2", "font-weight:bold;");
		finalMap.put("itemName", "PHARMACY");
		this.collectionAmount = this.collectionAmount.add((BigDecimal) finalMap.get("itemValue"));
		//listMap.add(0,finalMap);
		//return listMap;
		returnMap.add(0,finalMap);
		return returnMap;
		}
		
	
}
	
