package com.nzion.domain.olap;

import java.util.Map;

public interface StarSchemaObject {

	Map<String, Object> getFields();

	StarSchemaId getStarSchemaId();
}