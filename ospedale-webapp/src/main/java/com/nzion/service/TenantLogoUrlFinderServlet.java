package com.nzion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nzion.domain.Practice;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TenantLogoUrlFinderServlet extends HttpServlet {

    @Autowired
    private CommonCrudService commonCrudService;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> url = new HashMap<>();
        StringBuilder br = new StringBuilder();
        String pathOfImage = StringUtils.EMPTY;
        String tenantId = request.getParameter("labId");
        if(UtilValidator.isEmpty(tenantId)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "TenantId cannot be empty");
        }
        TenantIdHolder.setTenantId(tenantId);
        List<Practice> practices = commonCrudService.findByEquality(Practice.class, new String[]{"tenantId"}, new Object[]{tenantId});
        if(UtilValidator.isNotEmpty(practices)) {
            Practice practice = practices.get(0);
            if(UtilValidator.isNotEmpty(practice)){
                br.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort()).append(getServletContext().getContextPath()).append(practice.getImageUrl());
                pathOfImage = br.toString();
            }
            url.put("practiceName", practice.getPracticeName());
            url.put("tenantId", practice.getTenantId());
            url.put("logoUrl", pathOfImage);
            url.put("logoWithAddress", practice.isLogoWithAddress());
        }
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        objectMapper.setDateFormat(df);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.writeValue(writer, url);
        writer.close();
    }
}
