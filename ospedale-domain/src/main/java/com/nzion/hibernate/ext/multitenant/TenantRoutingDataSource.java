package com.nzion.hibernate.ext.multitenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;
import java.util.Map;
import java.util.logging.Logger;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> tenantIdToDataSourceMap;

    public TenantRoutingDataSource(ITenantAwareDataSourceFactory tenantAwareDataSourceFactory) {
        tenantIdToDataSourceMap = tenantAwareDataSourceFactory.fetchConfiguredTenantDataSources();
        setTargetDataSources(tenantIdToDataSourceMap);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        logger.debug("Tenant Id for Datasource is " + TenantIdHolder.getTenantId());
        return TenantIdHolder.getTenantId();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
