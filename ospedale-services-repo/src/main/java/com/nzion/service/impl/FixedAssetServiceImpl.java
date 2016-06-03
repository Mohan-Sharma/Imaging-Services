package com.nzion.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nzion.domain.FixedAsset;
import com.nzion.domain.Location;
import com.nzion.repository.FixedAssetRepository;
import com.nzion.service.FixedAssetService;
@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
@Service("fixedAssetService")
public class FixedAssetServiceImpl implements FixedAssetService {

	private FixedAssetRepository fixedAssetRepository; 

	public FixedAsset getFixedAssetByName(String name) {
	return fixedAssetRepository.getFixedAssetByName(name);
	}

	@Resource
	@Required
	public void setFixedAssetRepository(FixedAssetRepository fixedAssetRepository) {
	this.fixedAssetRepository = fixedAssetRepository;
	}
	public List<FixedAsset> getAllFixedAsset() {
	return fixedAssetRepository .getAll(FixedAsset.class);
	}

	@Override
	public List<FixedAsset> getAllRoomsByLocation(Collection<Location> locations) {
	return fixedAssetRepository.getAllRoomsByLocation(locations);
	}
}
