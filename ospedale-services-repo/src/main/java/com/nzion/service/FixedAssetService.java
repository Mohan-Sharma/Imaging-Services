package com.nzion.service;

import java.util.Collection;
import java.util.List;

import com.nzion.domain.FixedAsset;
import com.nzion.domain.Location;
public interface FixedAssetService {

	FixedAsset getFixedAssetByName(String name);
	
	List<FixedAsset> getAllFixedAsset();
	
	List<FixedAsset> getAllRoomsByLocation(Collection<Location> locations);
}
