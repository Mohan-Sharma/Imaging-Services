package com.nzion.enums;

import com.nzion.domain.Location;
import com.nzion.domain.olap.PatientStarSchema;
import com.nzion.domain.olap.ProviderStarSchema;

public enum SearchViewType {

	PATIENT(PatientStarSchema.class), PROVIDER(ProviderStarSchema.class), LOCATION(Location.class);

	private Class<?> searchResultClass;

	private SearchViewType(Class<?> klass) {
	this.searchResultClass = klass;
	}

	public Class<?> getSearchResultClass() {
	return searchResultClass;
	}
}