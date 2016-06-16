package com.nzion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.nzion.domain.*;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PatientRepository;
import com.nzion.repository.PersonRepository;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.EnumerationServiceImpl;
import com.nzion.service.dto.LabOrderDto;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.UtilValidator;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RescheduleLabOrderServlet extends HttpServlet{

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PracticeRepository practiceRepository;
    @Autowired
    CommonCrudService commonCrudService;
    /*@Autowired
    NotificationTaskExecutor notificationTaskExecutor;*/
    @Autowired
    EnumerationServiceImpl enumerationServiceImpl;
    @Autowired
    private BillingService billingService;
    @Autowired
    private FileBasedServiceImpl fileBasedServiceImpl;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext()); }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public static void main(String args[]){
        //System.out.println( UtilDateTime.addHrsToDate(new Date(), 720) );
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String tenantId = request.getParameter("labId");
        if(tenantId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "labId cannot be null");
            return;
        }
        TenantIdHolder.setTenantId(tenantId);
        //Map<String, Object> clinicDetails = RestServiceConsumer.getClinicDetailsByClinicId(tenantId);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        objectMapper.setDateFormat(df);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        LabOrderDto labOrderDto = objectMapper.readValue(request.getInputStream(), LabOrderDto.class);

        /*if(validateDate(labOrderDto.getAppointmentStartDate()) || validateDate(labOrderDto.getAppointmentEndDate())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date, cannot place order from past");
            return;
        }*/

        Map<String, Object> resultStatus = reschedule(response, labOrderDto);

        String status = (String)resultStatus.get("status");
        PrintWriter writer = response.getWriter();
        if(status.equals("updated")) {
            response.setStatus(HttpServletResponse.SC_OK, "order rescheduled");

            String resultString = "{" +
                    "\"status\" : \"" + "SUCCESS" +
                    "\", \"orderId\" : \"" + resultStatus.get("labOrder") +
                    "\"}";
            writer.print(resultString);
        } else {
            response.setStatus(HttpServletResponse.SC_OK, "failed");
            String resultString = "{" +
                    "\"status\" : \"" + "FAILED" +
                    "\", \"orderId\" : \"" + "null" +
                    "\"}";
            writer.print(resultString);
        }
        writer.close();
    }



    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        return true;
    }

    private boolean validateDate(Date appointmentDate){
        LocalDate localDate = new LocalDate(appointmentDate);
        localDate = new LocalDate(localDate.getYear(), localDate.getMonthOfYear(), localDate.getDayOfMonth());
        LocalDate currentDate = new LocalDate();
        currentDate = new LocalDate(currentDate.getYear(), currentDate.getMonthOfYear(), currentDate.getDayOfMonth());
        return localDate.isBefore(currentDate);
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public BillingService getBillingService() {
        return billingService;
    }

    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    public Map<String, Object> reschedule(HttpServletResponse response, LabOrderDto labOrderDto) {
        Map<String, Object> result = new HashMap<>();
        try {
            Person phlebotomist = commonCrudService.getById(Person.class,Long.parseLong(labOrderDto.getPhlebotomistId().toString()));
            LabOrderRequest existingLabOrderRequest = commonCrudService.getById(LabOrderRequest.class, Long.parseLong(labOrderDto.getScheduleId().toString()));

            existingLabOrderRequest.setPhlebotomistStartTime(convertGivenDate(labOrderDto.getAppointmentStartDate()));
            existingLabOrderRequest.setPhlebotomistEndTime(convertGivenDate(labOrderDto.getAppointmentEndDate()));
            existingLabOrderRequest.setPhlebotomistStartDate(com.nzion.util.UtilDateTime.getDayStart(labOrderDto.getAppointmentStartDate()));
            existingLabOrderRequest.setPhlebotomist(phlebotomist);

            LabOrderRequest updatedLabOrderRequest = commonCrudService.merge(existingLabOrderRequest);

        result.put("labOrder", updatedLabOrderRequest.getId());
        result.put("status", "updated");

    } catch (Exception e){
        result.put("status", "failed");
    }
    return result;
    }

    Date convertGivenDate(Date date){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(1970,0,1);
        return calendar.getTime();
    }


}
