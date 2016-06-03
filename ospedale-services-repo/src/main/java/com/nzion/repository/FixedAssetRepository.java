package com.nzion.repository;

import java.util.Collection;
import java.util.List;

import com.nzion.domain.FixedAsset;
import com.nzion.domain.Location;

public interface FixedAssetRepository extends BaseRepository {
	
	FixedAsset getFixedAssetByName(String name);
	
	List<FixedAsset> getAllRoomsByLocation(Collection<Location> locations);
}
