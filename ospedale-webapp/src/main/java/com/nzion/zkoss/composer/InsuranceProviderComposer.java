package com.nzion.zkoss.composer;

import com.nzion.view.InsuranceProviderViewObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.DataBinder;

import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.service.InsuranceProviderService;
import com.nzion.util.UtilMessagesAndPopups;

public class InsuranceProviderComposer extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;
	private InsuranceProviderViewObject insuranceProviderViewObject;
	private InsuranceProviderService insuranceProviderService;
	private InsuranceProvider insuranceProvider;

	public InsuranceProviderService getInsuranceProviderService() {
	return insuranceProviderService;
	}
	public void setInsuranceProviderService(InsuranceProviderService insuranceProviderService) {
	this.insuranceProviderService = insuranceProviderService;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
	super.doAfterCompose(comp);
	InsuranceProvider provider = (InsuranceProvider) Executions.getCurrent().getArg().get("entity");
	if(provider!=null){
		insuranceProviderViewObject = new InsuranceProviderViewObject(provider);
	}
	else{
		insuranceProviderViewObject = new InsuranceProviderViewObject();
	}
	comp.setAttribute("vo", insuranceProviderViewObject);
	DataBinder binder = new AnnotateDataBinder(comp);
	binder.loadAll();
	}

	public void onClick$Save(Event event){
	saveInsuranceProvider(insuranceProviderViewObject);
	UtilMessagesAndPopups.displaySuccess();
	if(event.getTarget().getFellowIfAny("InsuranceProvider") != null)
		event.getTarget().getFellowIfAny("InsuranceProvider").detach();
	}
	public void saveInsuranceProvider(InsuranceProviderViewObject insuranceProviderViewObject) {
	insuranceProviderService.saveOrUpdate(insuranceProviderViewObject);
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	return super.doBeforeCompose(page, parent, compInfo);
	}
	public InsuranceProviderViewObject getInsuranceProviderViewObject() {
	return insuranceProviderViewObject;
	}
	public void setInsuranceProviderViewObject(InsuranceProviderViewObject insuranceProviderViewObject) {
	this.insuranceProviderViewObject = insuranceProviderViewObject;
	}
}
