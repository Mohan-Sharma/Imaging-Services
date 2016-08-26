package com.nzion.service.utility;

import com.nzion.util.UtilValidator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Nthdimenzion on 26-Aug-16.
 */
public class UtilityFinder {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final String GET_ALL_LAB_TARIFF ="SELECT * FROM lab_tariff";

    @Resource
    public void setDataSource(DataSource dataSource){
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Map<String, Object>> getAllLabTariff(){

        List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(GET_ALL_LAB_TARIFF, new MapSqlParameterSource());
        if(UtilValidator.isNotEmpty(result)){
            return result;
        }
        return Collections.EMPTY_LIST;
    }
}
