package com.nzion.zkoss.composer;

import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import com.nzion.domain.billing.Contract;
import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.domain.pms.Scheme;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;

public class ContractController extends OspedaleAutowirableComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BillingService billingService;

	private CommonCrudService commonCrudService;

	private BillingDisplayConfig billingDisplayConfig;
	
	
	
	private Contract contract;
	
	private Set<InsuranceProvider> insuranceProviders;
	
	private Set<Scheme> schemes;
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo){
	contract = (Contract) Executions.getCurrent().getArg().get("entity");
	contract = contract == null ? new Contract() : contract;
	return super.doBeforeCompose(page, parent, compInfo);
	}
	
	public void saveInsuContract(Listbox insuranceProviderList){	
		Set insuSelected = insuranceProviderList.getSelectedItems();
		for(Object item : insuSelected ){			
			com.nzion.domain.pms.InsuranceProvider insuPro = (com.nzion.domain.pms.InsuranceProvider)((Listitem)item).getValue();						
			contract.addInsuranceProvider(insuPro);  
			commonCrudService.save(contract);
		}		
	}
	
	public void saveSelfContract(){	  	
		 commonCrudService.save(contract);
	}
	
	public BillingDisplayConfig getBillingDisplayConfig() {
	return billingDisplayConfig;
	}

	public void setBillingDisplayConfig(BillingDisplayConfig billingDisplayConfig) {
	this.billingDisplayConfig = billingDisplayConfig;
	}


	public Contract getContract() {
	return contract;
	}

	public void setContract(Contract contract) {
	this.contract = contract;
	}

	public void setBillingService(BillingService billingService) {
	this.billingService = billingService;
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}	

	public Set<InsuranceProvider> getInsuranceProviders() {
	return insuranceProviders;
	}

	public void setInsuranceProviders(Set<InsuranceProvider> insuranceProviders) {
	this.insuranceProviders = insuranceProviders;
	}
	
	public Set<Scheme> getSchemes() {
	return schemes;
	}

	public void setSchemes(Set<Scheme> schemes) {
	this.schemes = schemes;
	}
	

}
