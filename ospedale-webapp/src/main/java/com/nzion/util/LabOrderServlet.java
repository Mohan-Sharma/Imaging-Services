package com.nzion.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nzion.external.LabOrderDto;
import com.nzion.service.emr.lab.LabService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;


public class LabOrderServlet extends HttpServlet{
	
	@Autowired
    private LabService labService;
    
	
	public void init(ServletConfig config) throws ServletException{
		super.init(config);
	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext()); }
	
	public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        System.out.println("\n\n\n\nEnter post \n\n\n\n");
		try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd")); // 1.8 and above
		    LabOrderDto labOrderDto = mapper.readValue(httpServletRequest.getInputStream(), LabOrderDto.class);
		    labService.createLabOrderRequest(labOrderDto);
		}catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("\n\n\n\nEnter post \n\n\n\n");
	}

}
