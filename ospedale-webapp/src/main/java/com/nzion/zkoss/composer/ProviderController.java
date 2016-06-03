package com.nzion.zkoss.composer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;

import com.nzion.domain.Location;
import com.nzion.domain.Provider;
import com.nzion.domain.Speciality;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.ProviderService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilMessagesAndPopups;

/**
 * @author Sandeep Prusty
 * Apr 22, 2010
 */
public class ProviderController extends OspedaleAutowirableComposer {

	private static final long serialVersionUID = 1L;

	private ProviderService providerService;

	private CommonCrudService commonCrudService;

	private Speciality speciality;

	private Location location;

	private List<Speciality> specialities = new ArrayList<Speciality>();

	private String confirmPassword;

	private Provider provider;

	private boolean refresh;

	private CommonCrudRepository commonCrudRepository;

	public Provider getProvider() {
	return provider;
	}

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	public String getConfirmPassword() {
	return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
	this.confirmPassword = confirmPassword;
	}

	public ProviderController() {
	specialities = commonCrudService.getAll(Speciality.class);
	provider = null;
	}

	/*public ProviderController(Provider provider, boolean refresh) {
	this();
	this.provider = provider;
	if (provider != null) {
		List<SoapModuleQATemplate> templates = providerService.getSoapModuleQATemplates(provider);
		provider.setSoapModuleTemplates(new HashSet<SoapModuleQATemplate>(templates));
		if (templates.size() == 0) {
			Collection<SoapModule> soapModules = commonCrudRepository.getQASoapModules();
			for (SoapModule sm : soapModules) {
				com.nzion.domain.emr.SoapModuleQATemplate smqat = new com.nzion.domain.emr.SoapModuleQATemplate();
				smqat.setSoapModule(sm);
				smqat.setProvider(provider);
				provider.addSoapModuleTemplates(smqat);
			}
		}
	}
	if(provider != null && provider.getId()!=null)
		this.provider = commonCrudService.getById(Provider.class, provider.getId());
	this.refresh = refresh;
	}*/

	public List<Speciality> getSpecialities() {
	return specialities;
	}

	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}

	public void setProviderService(ProviderService providerService) {
	this.providerService = providerService;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
	super.doAfterCompose(comp);
	specialities.add(0, null);
	}

	public void onClick$saveOtherDeatils(Event event) {
	providerService.save(provider);
	UtilMessagesAndPopups.displaySuccess();
	}

	public void onClick$saveSignauture(Event event) {
	providerService.save(provider);
	UtilMessagesAndPopups.displaySuccess();
	}

	public void save() {
	providerService.save(provider);
	UtilMessagesAndPopups.displaySuccess();
	}

	public void addSpecialty() throws Exception {
	if (speciality == null) return;
	provider.addSpeciality(speciality);
	speciality = null;
	}

	public void addLocation() throws Exception {
	if (location == null) return;
	provider.addLocation(location);
	location = null;
	}

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	if (provider == null) {
		provider = new Provider();
		/*Collection<SoapModule> soapModules = commonCrudRepository.getQASoapModules();
		for (SoapModule sm : soapModules) {
			com.nzion.domain.emr.SoapModuleQATemplate smqat = new com.nzion.domain.emr.SoapModuleQATemplate();
			smqat.setSoapModule(sm);
			smqat.setProvider(provider);
			provider.addSoapModuleTemplates(smqat);
		}*/
		return compInfo;
	}
	if (!refresh) {
		return compInfo;
	}
	commonCrudService.refreshEntity(provider);
	return super.doBeforeCompose(page, parent, compInfo);
	}

	public CommonCrudRepository getCommonCrudRepository() {
	return commonCrudRepository;
	}

	public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
	this.commonCrudRepository = commonCrudRepository;
	}
}