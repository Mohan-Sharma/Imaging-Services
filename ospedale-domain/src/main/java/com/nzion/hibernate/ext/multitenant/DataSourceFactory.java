package com.nzion.hibernate.ext.multitenant;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.nzion.util.UtilValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceFactory extends NamedParameterJdbcDaoSupport implements ITenantAwareDataSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);
    Map<Object, Object> tenantIdToDataSourceMap = null;

    DataSource createDataSource(ITenantAware tenant) {
        logger.debug("Going to create a brand new data source for tenant " + tenant.getTenantId());
        MysqlDataSource dataSource = new MysqlDataSource();
        TenantCustomisationDetails customisationDetails = getCustomisationDetails(tenant);
        dataSource.setUrl(customisationDetails.getJdbcUrl());
        dataSource.setUser(customisationDetails.getJdbcUsername());
        dataSource.setPassword(customisationDetails.getJdbcPassword());
        return dataSource;
    }

    TenantCustomisationDetails getCustomisationDetails(ITenantAware tenant) {
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        Map<String, Object> tenantResult = jdbcTemplate.queryForMap("select * from tenant where tenant_id = ?", tenant.getTenantId());
        TenantCustomisationDetails tenantCustomisationDetails = new TenantCustomisationDetails((String) tenantResult.get("jdbc_url"), (String) tenantResult.get("jdbc_username"), (String) tenantResult.get("jdbc_password"));
        return tenantCustomisationDetails;
    }

    @Override
    public Map<Object, Object> initialiseConfiguredTenantDataSources() {
        Map<Object, Object> tenantIdToDataSourceMap = new HashMap();
        JdbcTemplate jdbcTemplate = getJdbcTemplate();
        List<Tenant> allConfiguredTenants = jdbcTemplate.query("select * from tenant", new RowMapper<Tenant>() {
            @Override
            public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
                Tenant tenant = new Tenant(rs.getString("tenant_id"), rs.getString("tenant_name"), rs.getBoolean("is_enabled"));
                return tenant;
            }
        });
        for (Tenant tenant : allConfiguredTenants) {
            tenantIdToDataSourceMap.put(tenant.getTenantId(), createDataSource(tenant));
        }
        this.tenantIdToDataSourceMap = tenantIdToDataSourceMap;
        return tenantIdToDataSourceMap;
    }

    @Override
    public Map<Object, Object> fetchConfiguredTenantDataSources() {
        if (UtilValidator.isNotEmpty(this.tenantIdToDataSourceMap)) {
            logger.debug("Picking data from cache");
            return tenantIdToDataSourceMap;
        } else {
            logger.debug("initialiseConfiguredTenantDataSources");
            return initialiseConfiguredTenantDataSources();
        }
    }


}
