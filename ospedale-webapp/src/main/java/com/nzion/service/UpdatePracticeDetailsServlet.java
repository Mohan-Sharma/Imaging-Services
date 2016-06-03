package com.nzion.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nzion.domain.ContactFields;
import com.nzion.domain.Person;
import com.nzion.domain.Practice;
import com.nzion.dto.PracticeDto;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.impl.PracticeServiceImpl;
import com.nzion.util.UtilValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class UpdatePracticeDetailsServlet extends HttpServlet {

    @Autowired
    CommonCrudService commonCrudService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        objectMapper.setDateFormat(df);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        PracticeDto practiceDto = objectMapper.readValue(request.getInputStream(), PracticeDto.class);
        String tenantId = practiceDto.getTenantId();
        if(UtilValidator.isEmpty(tenantId)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "tenantId cannot be null");
            return;
        }
        TenantIdHolder.setTenantId(tenantId);
        List<Practice> practices = commonCrudService.findByEquality(Practice.class, new String[]{"tenantId"}, new Object[]{tenantId});
        if(UtilValidator.isNotEmpty(practices)) {
            Practice practice = practices.get(0);
            practice = practiceDto.setPropertiesToPractice(practice);
            commonCrudService.merge(practice);
        }
        List<Person> persons = commonCrudService.getAll(Person.class);
        if(UtilValidator.isNotEmpty(persons)) {
            Person person = persons.get(0);
            ContactFields contactFields = person.getContacts();
            contactFields.setEmail(practiceDto.getEmail());
            contactFields.setMobileNumber(practiceDto.getMobileNumber());
            commonCrudService.merge(person);
        }
        response.setStatus(HttpServletResponse.SC_OK, "successfully updated");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
