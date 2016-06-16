package com.nzion.hibernate.ext.multitenant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Re-Written by Raghu Bandi on 15-Dec-2015. The Class has been completely re-written to customize the behavior of the spring framework for
 * Multi-tenancy to ensure the newly created tenant database is available to the users immediately after its creation.
 */

public class TenantRoutingDataSource extends AbstractRoutingDataSource implements InitializingBean {

    private Map<Object, Object> tenantIdToDataSourceMap;
    private ITenantAwareDataSourceFactory tenantAwareDataSourceFactory;

    private Map<Object, Object> targetDataSources;
    private Object defaultTargetDataSource;
    private DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
    private Map<Object, DataSource> resolvedDataSources;
    private DataSource resolvedDefaultDataSource;


    public TenantRoutingDataSource(ITenantAwareDataSourceFactory tenantAwareDataSourceFactory) {
        this.tenantAwareDataSourceFactory = tenantAwareDataSourceFactory;
        tenantIdToDataSourceMap = tenantAwareDataSourceFactory.fetchConfiguredTenantDataSources();
        setTargetDataSources(tenantIdToDataSourceMap);
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    public void setDataSourceLookup(DataSourceLookup dataSourceLookup) {
        this.dataSourceLookup = (DataSourceLookup)(dataSourceLookup != null?dataSourceLookup:new JndiDataSourceLookup());
    }

    public void afterPropertiesSet() {
        this.resolvedDataSources = new HashMap(this.targetDataSources.size());
        Iterator dataSourceIterator = this.targetDataSources.entrySet().iterator();
        while(dataSourceIterator.hasNext()) {
            Map.Entry entry = (Map.Entry)dataSourceIterator.next();
            Object lookupKey = entry.getKey();
            DataSource dataSource = (DataSource)entry.getValue();
            this.resolvedDataSources.put(lookupKey, dataSource);
        }
        if(this.defaultTargetDataSource != null) {
            this.resolvedDefaultDataSource = (DataSource)this.defaultTargetDataSource;
        }
    }

/*    protected DataSource resolveSpecifiedDataSource(Object dataSource) throws IllegalArgumentException {
        if(dataSource instanceof DataSource) {
            return (DataSource)dataSource;
        } else if(dataSource instanceof String) {
            return this.dataSourceLookup.getDataSource((String)dataSource);
        } else {
            throw new IllegalArgumentException("Illegal data source value - only [javax.sql.DataSource] and String supported: " + dataSource);
        }
    }*/

    public Connection getConnection() throws SQLException {
        return this.determineTargetDataSource().getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return this.determineTargetDataSource().getConnection(username, password);
    }

    protected DataSource determineTargetDataSource() {
        Object lookupKey = this.determineCurrentLookupKey();
        DataSource dataSource = (DataSource)this.resolvedDataSources.get(lookupKey);
        if(dataSource == null) {
            dataSource = this.resolvedDefaultDataSource;
        }
        return dataSource;
    }

    protected Object resolveSpecifiedLookupKey(Object lookupKey) {
        return lookupKey;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return TenantIdHolder.getTenantId();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
    // This is the method that is invoked from the newly created servlet to refresh the DataSources.
    public void updateDataSourceMap(){
        tenantIdToDataSourceMap = tenantAwareDataSourceFactory.initialiseConfiguredTenantDataSources();
        setTargetDataSources(tenantIdToDataSourceMap);
        afterPropertiesSet();
    }
}
