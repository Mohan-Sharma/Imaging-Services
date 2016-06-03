package com.nzion.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.springframework.beans.factory.annotation.Configurable;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "FIXED_ASSET",uniqueConstraints={@UniqueConstraint(columnNames ={"FIXED_ASSET_NAME"})})
@Filters( { @Filter(name = "EnabledFilter", condition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)") })
@Configurable("fixedAsset")
public class FixedAsset extends IdGeneratingBaseEntity{

	private static final long serialVersionUID = 1L;
	private String fixedAssetName;
	private String description;
	private Location locatedAt;

	@Column(name = "description")
	public String getDescription() {
	return description;
	}

	public void setDescription(String description) {
	this.description = description;
	}

	@Column(name = "FIXED_ASSET_NAME")
	public String getFixedAssetName() {
	return fixedAssetName;
	}

	public void setFixedAssetName(String fixedAssetName) {
	this.fixedAssetName = fixedAssetName;
	}

	@OneToOne(targetEntity = Location.class)
	@JoinColumn(name = "LOCATION_ID")
	public Location getLocatedAt() {
	return locatedAt;
	}

	public void setLocatedAt(Location locatedAt) {
	this.locatedAt = locatedAt;
	}

	@Override
	public String toString() {
	return fixedAssetName;
	}
}