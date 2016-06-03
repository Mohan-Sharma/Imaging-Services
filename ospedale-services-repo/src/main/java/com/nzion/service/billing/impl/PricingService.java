package com.nzion.service.billing.impl;


import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import com.nzion.domain.Location;
import com.nzion.util.UtilDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.nzion.domain.emr.soap.PatientLabOrder;

@Service
public class PricingService{
	
    
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    
    private String findPriceByLabTest = "SELECT \n" +
			"CASE WHEN 'true' = :homeService THEN l.home_service \n" +
			"WHEN 'false'= :homeService THEN l.billable_amount ELSE NULL END AS amount FROM lab_tariff l WHERE (l.location_id = :locationId AND l.tarif_category = :tarrifCategory AND l.lab_test = :labTest AND l.from_date <= :nowDate AND l.thru_date >= :nowDate) LIMIT 1";

    private String findPriceByLabTestPanel = "SELECT " +
			"CASE  WHEN 'true' = :homeService THEN l.home_service \n" +
			"WHEN 'false' = :homeService THEN l.billable_amount ELSE NULL END AS amount FROM lab_tariff l WHERE (l.tarif_category = :tarrifCategory AND  l.lab_panel = :labPanel AND l.location_id = :locationId AND l.from_date <= :nowDate AND l.thru_date >= :nowDate) LIMIT 1";


    private String findPriceByLabTestProfile = "SELECT " +
			"CASE WHEN 'true' = :homeService THEN l.home_service \n" +
			"WHEN 'false' = :homeService THEN l.billable_amount ELSE NULL END AS amount FROM lab_tariff l WHERE (l.tarif_category = :tarrifCategory AND l.lab_profile = :labProfile AND l.location_id = :locationId AND l.from_date <= :nowDate AND l.thru_date >= :nowDate) LIMIT 1";


   
   
	public BigDecimal getPriceForPatientLabOrder(String tarrifCategory, PatientLabOrder patientLabOrder, Long locationId, java.sql.Date nowDate){
		
		if((patientLabOrder.getLabTest() != null) && (locationId != null)) {
			return getPriceByLabTest(tarrifCategory, patientLabOrder.getLabTest().getTestCode(), locationId,nowDate, patientLabOrder.isHomeService());
		}
		else if((patientLabOrder.getLabTestProfile() != null) && (locationId != null) )
			return getPriceByLabTestProfile(tarrifCategory, patientLabOrder.getLabTestProfile().getProfileCode(),locationId,nowDate, patientLabOrder.isHomeService());
		else
			return getPriceByLabTestPanel(tarrifCategory, patientLabOrder.getLabTestPanel().getPanelCode(), locationId,nowDate, patientLabOrder.isHomeService());
	}




	private BigDecimal getPriceByLabTest(String tarrifCategory, String labTestId, Long id, java.sql.Date nowDate, Boolean homeService){
		BigDecimal price = null;
		SqlParameterSource namedParameters = new MapSqlParameterSource("tarrifCategory",tarrifCategory).addValue("labTest", labTestId).addValue("locationId", id).addValue("nowDate",nowDate).addValue("homeService",homeService.toString());
        try{
			price = (BigDecimal)namedParameterJdbcTemplate.queryForObject(findPriceByLabTest,namedParameters,new SingleColumnRowMapper());
        }catch (Exception e){}
        return price;
	}
	
	private BigDecimal getPriceByLabTestProfile(String tarrifCategory, String labTestProfileId, Long id, java.sql.Date nowDate, Boolean homeService){
		BigDecimal price = null;
		SqlParameterSource namedParameters = new MapSqlParameterSource("tarrifCategory",tarrifCategory).addValue("labProfile", labTestProfileId).addValue("locationId", id).addValue("nowDate",nowDate).addValue("homeService",homeService.toString());
        try{
        	price = (BigDecimal)namedParameterJdbcTemplate.queryForObject(findPriceByLabTestProfile,namedParameters,new SingleColumnRowMapper());
        }catch (Exception e){}
        return price;
	}
	
	
	private BigDecimal getPriceByLabTestPanel(String tarrifCategory, String labTestPanelId, Long id, java.sql.Date nowDate, Boolean homeService){
		BigDecimal price = null;
		SqlParameterSource namedParameters = new MapSqlParameterSource("tarrifCategory",tarrifCategory).addValue("labPanel",labTestPanelId).addValue("locationId", id).addValue("nowDate",nowDate).addValue("homeService",homeService.toString());
        try{
        	price = (BigDecimal)namedParameterJdbcTemplate.queryForObject(findPriceByLabTestPanel,namedParameters,new SingleColumnRowMapper());
        }catch (Exception e){}
        return price;
	}
}