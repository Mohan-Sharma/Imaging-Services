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
import com.nzion.repository.notifier.utility.SmsUtil;
import com.nzion.repository.notifier.utility.TemplateNames;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.EnumerationServiceImpl;
import com.nzion.service.dto.LabOrderDto;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.Infrastructure;
import com.nzion.util.RestServiceConsumer;
import com.nzion.util.UtilDateTime;
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
        LabOrderRequest oldLabOrderRequest = commonCrudService.getById(LabOrderRequest.class, Long.parseLong(labOrderDto.getScheduleId().toString()));
        String date = constructDate(oldLabOrderRequest.getStartTime(), oldLabOrderRequest.getStartDate());
        String time =  constructTime(oldLabOrderRequest.getStartTime(), oldLabOrderRequest.getStartDate());

        RCMPreference rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
        PatientReschedulingPreference patientReschedulingPreference = commonCrudService.findUniqueByEquality(PatientReschedulingPreference.class, new String[]{"rcmPreference", "visitType"}, new Object[]{rcmPreference, RCMPreference.RCMVisitType.HOME_PHLEBOTOMY});
        BigDecimal reScheduleTime = patientReschedulingPreference.getReschedulingTime() == null ? BigDecimal.ZERO : patientReschedulingPreference.getReschedulingTime();
        Date scheduleDateTime = UtilDateTime.toDate(oldLabOrderRequest.getStartDate().getMonth(), oldLabOrderRequest.getStartDate().getDate(), oldLabOrderRequest.getStartDate().getYear(),
                oldLabOrderRequest.getStartTime().getHours(), oldLabOrderRequest.getStartTime().getMinutes(), oldLabOrderRequest.getStartTime().getSeconds());
        BigDecimal hoursInterval = new BigDecimal(UtilDateTime.getIntervalInHours(new Date(), scheduleDateTime));

        PrintWriter writer = response.getWriter();

        if(hoursInterval.compareTo(reScheduleTime) < 0){
            //writer.print( "Appointment cannot be rescheduled within " + reScheduleTime + " hrs");
            writer.print( "Sorry, appointment cant be rescheduled , please check Afya Policy.");
            writer.close();
            return;
        }


        Map<String, Object> resultStatus = reschedule(response, labOrderDto);

        String status = (String)resultStatus.get("status");
        if(status.equals("updated")) {
            try {
                LabOrderRequest labOrderRequest = commonCrudService.getById(LabOrderRequest.class, Long.parseLong(resultStatus.get("labOrder").toString()));
                Map<String, Object> clinicDetails = RestServiceConsumer.getRadiologyDetByRadiologyId(tenantId);

                final Map adminUserLogin = RestServiceConsumer.getUserLoginByUserName(Infrastructure.getPractice().getAdminUserLogin().getUsername());
                Object languagePreference = adminUserLogin.get("languagePreference");
                clinicDetails.put("languagePreference", languagePreference);
                clinicDetails.put("oldDate", date);
                clinicDetails.put("oldTime", time);
                clinicDetails.put("mobileNumber", adminUserLogin.get("mobile_number"));
                clinicDetails.put("key", TemplateNames.RESCHEDULE_LAB_ORDER_SMS_TO_LAB_ADMIN.name());
                clinicDetails.put("forAdmin", new Boolean(true));

                clinicDetails.put("receipentType", "ADMIN");
                clinicDetails.put("referenceID", labOrderRequest.getId().toString());
                clinicDetails.put("referenceType", "ORDER");
                SmsUtil.sendStatusSms(labOrderRequest, clinicDetails);

                /*clinicDetails.put("key", TemplateNames.RESCHEDULE_LAB_ORDER_SMS_TO_PHLEBOTOMIST.name());
                clinicDetails.put("forDoctor", new Boolean(true));
                clinicDetails.put("forAdmin", new Boolean(false));
                SmsUtil.sendStatusSms(labOrderRequest, clinicDetails);*/

                //for admin
                if ((Infrastructure.getPractice().getIsNotificationSentToAdmin() != null) && (Infrastructure.getPractice().getIsNotificationSentToAdmin().equals("yes"))){
                    ArrayList<HashMap<String, Object>> adminList = RestServiceConsumer.getAllAdminByTenantId();
                    clinicDetails.put("key", TemplateNames.RESCHEDULE_LAB_ORDER_SMS_TO_LAB_ADMIN.name());
                    clinicDetails.put("forAdmin", new Boolean(true));
                    clinicDetails.put("isdCode", Infrastructure.getPractice().getAdminUserLogin().getPerson().getContacts().getIsdCode());
                    Iterator iterator = adminList.iterator();
                    while (iterator.hasNext()) {
                        Map map = (Map) iterator.next();
                        clinicDetails.put("mobileNumber", map.get("mobile_number"));
                        clinicDetails.put("languagePreference", map.get("languagePreference"));
                        clinicDetails.put("accountNumber", map.get("accountNumber") != null ? map.get("accountNumber").toString() : "");
                        clinicDetails.put("receipentType", "ADMIN");
                        clinicDetails.put("referenceID", labOrderRequest.getId().toString());
                        clinicDetails.put("referenceType", "ORDER");
                        SmsUtil.sendStatusSms(labOrderRequest, clinicDetails);
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
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

            existingLabOrderRequest.setStartTime(convertGivenDate(labOrderDto.getAppointmentStartDate()));
            existingLabOrderRequest.setEndTime(convertGivenDate(labOrderDto.getAppointmentEndDate()));
            existingLabOrderRequest.setStartDate(com.nzion.util.UtilDateTime.getDayStart(labOrderDto.getAppointmentStartDate()));
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

    private static String constructTime(Date date, Date scheduleDate){
        LocalDate localDate = new LocalDate(scheduleDate);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear());
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(furnishedDate);
    }

    private static String constructDate(Date date, Date scheduleDate){
        LocalDate localDate = new LocalDate(scheduleDate);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear()-1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        return dateFormat.format(furnishedDate);
    }
}
