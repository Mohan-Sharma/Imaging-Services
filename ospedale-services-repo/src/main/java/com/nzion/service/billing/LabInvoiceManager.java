package com.nzion.service.billing;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.nzion.domain.Location;
import com.nzion.exception.TransactionException;
import com.nzion.domain.billing.*;
import com.nzion.domain.emr.UnitOfMeasurement;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.service.billing.impl.PricingService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;
import org.springframework.beans.factory.annotation.Autowired;

public class LabInvoiceManager extends AbstractInvoiceManager {

    @Override
    public Invoice generateInvoice(Object object) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Autowired
    private PricingService pricingService;

    private static final String LABTEST_ITEM_TYPE = LabOrderRequest.class.getName();
    private static final String TESTPANEL_ITEM_TYPE = "LAB TEST PANEL";

    public Invoice generateInvoice(LabOrderRequest labOrderRequest, Set<PatientLabOrder> patientLabOrders) throws TransactionException {
        BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
        List<PatientLabOrder> pLabOrderToUpd = new ArrayList<PatientLabOrder>();
        Invoice searchVO = new Invoice();
       // searchVO.setItemId(labOrderRequest.getId().toString());
       // List<Invoice> result = commonCrudService.findByEquality(Invoice.class, new String[]{"itemId"}, new Object[]{labOrderRequest.getId().toString()});
      //  Invoice invoice = null;
       // if (result.size() > 0) {
         //   invoice = result.get(0);
           // invoice.getInvoiceItems().clear();
       // } else
        	//invoice = new Invoice(labOrderRequest.getId().toString(), LABTEST_ITEM_TYPE, labOrderRequest.getProvider(), labOrderRequest.getPatient(), null);

      //  invoice.setInvoiceType(InvoiceType.OPD);
        searchVO = new Invoice(labOrderRequest.getId().toString(), LABTEST_ITEM_TYPE, labOrderRequest.getProvider(), labOrderRequest.getPatient(), null);
        searchVO.setInvoiceType(InvoiceType.OPD);
        if(labOrderRequest.getReferral()!=null)
        	searchVO.setReferralConsultantId(labOrderRequest.getReferral().getId());
        for (PatientLabOrder patientLabOrder : patientLabOrders) {
            if (patientLabOrder.getBillingStatus().equals(PatientLabOrder.BILLINGSTATUS.UNINVOICED)) {
            	String description = null;
            	if (patientLabOrder.getLabTest() != null)        		description = patientLabOrder.getLabTest().getTestDescription();
            	else if  (patientLabOrder.getLabTestProfile() != null)  description = patientLabOrder.getLabTestProfile().getProfileName();
            	else     								         		description =  patientLabOrder.getLabTestPanel().getPanelDescription();
            	 
                InvoiceItem invoiceItem = new InvoiceItem(searchVO, patientLabOrder.getId().toString(), InvoiceType.OPD_LAB_CHARGES,
                		description, 1, "",LabOrderRequest.class.getName());
                if(labOrderRequest.getProvider()!=null)
                	invoiceItem.setProviderId(labOrderRequest.getProvider().getId());
                else if(labOrderRequest.getReferral()!=null)
                	invoiceItem.setReferralId(labOrderRequest.getReferral().getId());
                /*Map<String, Object> pricingCtx = new HashMap<String, Object>();
                pricingCtx.put("productType", PricingService.PricingServiceProductType.LAB_CHARGES);
                pricingCtx.put("productId", patientLabOrder.getLabTest().getId());
                BigDecimal amount = pricingService.getPrice(pricingCtx);
                */
                String tarrifCategory = "01";
                java.sql.Date nowDate = new java.sql.Date(UtilDateTime.nowDate().getTime());
                Long locationId = Infrastructure.getSelectedLocation().getId();
                BigDecimal amount = pricingService.getPriceForPatientLabOrder(tarrifCategory, patientLabOrder, locationId,nowDate);
                UnitOfMeasurement billingDisplayUom = billingDisplayConfig.getCurrency();
                if (billingDisplayUom != null) {
                    Money price = new Money(amount, Currency.getInstance(billingDisplayUom.getCode()));
                    invoiceItem.init(amount, billingDisplayUom.getCode(), price, 1);
                    patientLabOrder.setBillingStatus(PatientLabOrder.BILLINGSTATUS.INVOICED);
                    searchVO.addInvoiceItem(invoiceItem);
                    if(UtilValidator.isEmpty(searchVO.getTotalAmount()))
                    	searchVO.setTotalAmount(price);
                    else
                    	searchVO.setTotalAmount(searchVO.getTotalAmount().plus( price));
                }
                pLabOrderToUpd.add(patientLabOrder);
            }
        }

        for(PatientLabOrder plo : labOrderRequest.getPatientLabOrders()){
                if(!patientLabOrders.contains(plo)){
                    plo.setBillingStatus(PatientLabOrder.BILLINGSTATUS.UNINVOICED);
                    patientLabOrders.add(plo);
                }
        }

        commonCrudService.save(patientLabOrders);
        if (UtilValidator.isEmpty(searchVO.getInvoiceItems()))
            return null;
        searchVO.setLabOrderId(labOrderRequest);
        createInvoice(searchVO);
        return searchVO;
    }

    @Override
    public List<String> getItemTypeOrder() {
        return Arrays.asList(InvoiceType.OPD_LAB_CHARGES.name(), InvoiceType.SERVICE_TAX.name());

    }

    @Override
    public Map<String, Object> viewInvoiceFor(Object object) {
        // TODO Auto-generated method stub
        return null;
    }
}