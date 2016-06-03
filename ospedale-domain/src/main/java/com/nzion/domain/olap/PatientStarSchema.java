package com.nzion.domain.olap;

import java.util.HashMap;
import java.util.Map;

public class PatientStarSchema implements StarSchemaObject {

	private Map<String, Object> fields = new HashMap<String, Object>();
	private StarSchemaId starSchemaId;

	public Map<String, Object> getFields() {
	return fields;
	}

	public void setFields(Map<String, Object> fields) {
	this.fields = fields;
	}

	@Override
	public StarSchemaId getStarSchemaId() {
	return starSchemaId;
	}

	public void setStarSchemaId(StarSchemaId starSchemaId) {
	this.starSchemaId = starSchemaId;
	}
}