package com.nzion.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.nzion.view.InsuranceProviderViewObject;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.repository.InsuranceProviderRepository;
import com.nzion.service.InsuranceProviderService;

@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
@Service("insuranceProviderService")
public class InsuranceProviderServiceImpl implements InsuranceProviderService {
	private InsuranceProviderRepository insuranceProviderRepository;

	public void saveOrUpdate(InsuranceProviderViewObject insuranceProviderViewObject) {
	InsuranceProvider insuranceProvider = insuranceProviderViewObject.getInsuranceProvider();
	insuranceProviderRepository.save(insuranceProvider);
	}
	
	@Resource
	@Required
	public void setInsuranceProviderRepository(InsuranceProviderRepository insuranceProviderRepository) {
	this.insuranceProviderRepository = insuranceProviderRepository;
	}

	public List<InsuranceProvider> findAllProviders() {
	return insuranceProviderRepository.getAll(InsuranceProvider.class,false);
	}

}
