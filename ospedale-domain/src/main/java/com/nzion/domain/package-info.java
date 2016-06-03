@org.hibernate.annotations.FilterDefs(value = {
		@org.hibernate.annotations.FilterDef(name = "LocationFilter", defaultCondition = ":locationId=LOCATION_ID", parameters = {@org.hibernate.annotations.ParamDef(type = "long", name = "locationId")}),
		@org.hibernate.annotations.FilterDef(name = "DateFilterDef", defaultCondition = "THRU_DATETIME IS NULL"),
		@org.hibernate.annotations.FilterDef(name = "EnabledFilter", defaultCondition = "(IS_ACTIVE=1 OR IS_ACTIVE IS NULL)"),
		@org.hibernate.annotations.FilterDef(name = "ICD9Filter", defaultCondition = ":ICD-9-CM=ICD_VERSION") })
@org.hibernate.annotations.TypeDefs(value = { @TypeDef(defaultForType = Character.class, typeClass = EmptyCharacterType.class) })
package com.nzion.domain;

import org.hibernate.annotations.TypeDef;

import com.nzion.hibernate.ext.EmptyCharacterType;

