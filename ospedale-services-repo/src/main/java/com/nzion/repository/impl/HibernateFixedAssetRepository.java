package com.nzion.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.nzion.domain.FixedAsset;
import com.nzion.domain.Location;
import com.nzion.repository.FixedAssetRepository;

public class HibernateFixedAssetRepository extends HibernateBaseRepository implements FixedAssetRepository {

	public FixedAsset getFixedAssetByName(String name) {
	return findUniqueByCriteria(FixedAsset.class, new String[]{"fixedAssetName"}, new Object[]{name});
	}
	
	public List<FixedAsset> getAllRoomsByLocation(Collection<Location> locations){
	Criteria criteria = getSession().createCriteria(FixedAsset.class);
	criteria.add(Restrictions.in("locatedAt", locations));
	return criteria.list();
	}
}
