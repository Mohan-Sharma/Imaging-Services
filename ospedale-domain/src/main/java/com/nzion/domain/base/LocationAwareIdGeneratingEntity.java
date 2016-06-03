/*
 * header file
 */
package com.nzion.domain.base;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.nzion.domain.Location;

// TODO: Auto-generated Javadoc
/**
 * The Class TenantAwareIdGeneratingEntity.
 * 
 * @author Sandeep Prusty Apr 1, 2010
 */

@MappedSuperclass
public class LocationAwareIdGeneratingEntity extends IdGeneratingBaseEntity implements LocationAware {
	private static final long serialVersionUID = 613920910002828435L;
	
	private Location location;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LOCATION_ID")
	@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	@Override
	public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((location == null) ? 0 : location.hashCode());
	return result;
	}

	@Override
	public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	if (!super.equals(obj)) {
		return false;
	}
	if (getClass() != obj.getClass()) {
		return false;
	}
	LocationAwareIdGeneratingEntity other = (LocationAwareIdGeneratingEntity) obj;
	if (location == null) {
		if (other.location != null) {
			return false;
		}
	} else
		if (!location.equals(other.location)) {
			return false;
		}
	return true;
	}
}
