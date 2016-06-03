package com.nzion.service;

import java.util.List;

import com.nzion.domain.pms.InsuranceProvider;
import com.nzion.view.InsuranceProviderViewObject;

public interface InsuranceProviderService {

	public void saveOrUpdate(InsuranceProviderViewObject insuranceProviderViewObject );
	
	public List<InsuranceProvider> findAllProviders();
}
