package com.nzion.hibernate.ext.multitenant;

import java.util.Map;

public interface ITenantAwareDataSourceFactory {

    Map<Object,Object> initialiseConfiguredTenantDataSources();

    Map<Object, Object>  fetchConfiguredTenantDataSources();
}
