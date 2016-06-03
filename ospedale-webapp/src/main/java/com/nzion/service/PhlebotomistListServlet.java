package com.nzion.service;

import com.google.gson.Gson;
import com.nzion.domain.*;
import com.nzion.domain.billing.Invoice;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PatientRepository;
import com.nzion.repository.PersonRepository;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.EnumerationServiceImpl;
import com.nzion.service.impl.FileBasedServiceImpl;
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

public class PhlebotomistListServlet extends HttpServlet{

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
        Set<String> slots = null;
        Set<CalendarIndividualSlot> calendarIndividualSlots = new HashSet<>();
        List<SlotWithVisitType> furnishedSlots = null;
        Date date = null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        String result = "";

        String labId = request.getParameter("labId") != null ? request.getParameter("labId").trim() :request.getParameter("labId");
        String locationId = request.getParameter("locationId") != null ? request.getParameter("locationId").trim() : request.getParameter("locationId");
        //String appointmentDate = request.getParameter("appointmentDate") != null ? request.getParameter("appointmentDate").trim() : request.getParameter("appointmentDate");
        //String visitType = request.getParameter("visitType") != null ? request.getParameter("visitType").trim() : request.getParameter("visitType");
        /*if(labId == null || locationId == null || appointmentDate == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing expected parameters, either one of the parameters is null");
            return;
        }*/
        if(!isInteger(locationId.trim())){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid location id");
            return;
        }
        try {
            //date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDate);
            TenantIdHolder.setTenantId(labId);

            List<Employee> phlebotomist = commonCrudService.findByEquality(Employee.class, new String[]{"phlebotomist"}, new Object[]{Boolean.TRUE});

            Iterator iterator = phlebotomist.iterator();
             while (iterator.hasNext()){
                 Employee employee = (Employee)iterator.next();

                 Map<String, Object> map = new HashMap<>();
                 map.put("id", employee.getId());
                 map.put("firstName", employee.getFirstName());
                 map.put("middleName", employee.getMiddleName());
                 map.put("lastName", employee.getLastName());
                 map.put("mobileNumber", employee.getContacts().getMobileNumber());
                 map.put("email", employee.getContacts().getEmail());
                 mapList.add(map);
             }

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.toString());
        }

        Gson gson = new Gson();
        result = gson.toJson(mapList);

        PrintWriter writer = response.getWriter();
        writer.print(result);
        writer.close();
    }

    public static void main(String args[]){
        //System.out.println( UtilDateTime.addHrsToDate(new Date(), 720) );
    }

    private List<SlotWithVisitType> createMappingOfSlotAndVisitType(Set<CalendarIndividualSlot> calendarIndividualSlots, Set<String> slots) {
        List<SlotWithVisitType> mapList = new ArrayList<>();
        for(String time : slots){
            StringBuffer buffer = null;
            SlotWithVisitType slotWithVisitType;
            for(CalendarIndividualSlot calendarIndividualSlot : calendarIndividualSlots){
                buffer = new StringBuffer();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                buffer.append(sdf.format(calendarIndividualSlot.getStartTime()));
                buffer.append(" - ");
                buffer.append(sdf.format(calendarIndividualSlot.getEndTime()));
                if(time.equals(buffer.toString())){
                    slotWithVisitType = new SlotWithVisitType(time);
                    mapList.add(slotWithVisitType);
                }
            }
        }
        return mapList;
    }

    private List<CalendarResourceAssoc> getCurrentCalendarResourceAssoc(List<CalendarResourceAssoc> calendarResourceAssocs, Date appointmentDate) throws ParseException {
        List<CalendarResourceAssoc> calAssocs = new ArrayList<CalendarResourceAssoc>();
        for(CalendarResourceAssoc calendarResourceAssoc :  calendarResourceAssocs){
            if(calendarResourceAssoc.getThruDate() == null && (appointmentDate.after(calendarResourceAssoc.getFromDate()) || appointmentDate.equals(calendarResourceAssoc.getFromDate())))
                calAssocs.add(calendarResourceAssoc);
            if(calendarResourceAssoc.getThruDate() != null && (appointmentDate.after(calendarResourceAssoc.getFromDate()) || appointmentDate.equals(calendarResourceAssoc.getFromDate())) && (appointmentDate.before(calendarResourceAssoc.getThruDate()) || appointmentDate.equals(calendarResourceAssoc.getThruDate())))
                calAssocs.add(calendarResourceAssoc);
        }
        return calAssocs;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

    private class SlotWithVisitType{
        private String time;
        //private String visitType;
        public SlotWithVisitType(String time){
            this.time = time;
            //this.visitType = visitType;
        }

        @Override
        public String toString() {
            return "{" + time + "}";
        }
    }


    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public void setPatientRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PracticeRepository getPracticeRepository() {
        return practiceRepository;
    }

    public void setPracticeRepository(PracticeRepository practiceRepository) {
        this.practiceRepository = practiceRepository;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    /*public NotificationTaskExecutor getNotificationTaskExecutor() {
        return notificationTaskExecutor;
    }

    public void setNotificationTaskExecutor(NotificationTaskExecutor notificationTaskExecutor) {
        this.notificationTaskExecutor = notificationTaskExecutor;
    }*/

    public EnumerationServiceImpl getEnumerationServiceImpl() {
        return enumerationServiceImpl;
    }

    public void setEnumerationServiceImpl(EnumerationServiceImpl enumerationServiceImpl) {
        this.enumerationServiceImpl = enumerationServiceImpl;
    }

    public BillingService getBillingService() {
        return billingService;
    }

    public void setBillingService(BillingService billingService) {
        this.billingService = billingService;
    }

    public FileBasedServiceImpl getFileBasedServiceImpl() {
        return fileBasedServiceImpl;
    }

    public void setFileBasedServiceImpl(FileBasedServiceImpl fileBasedServiceImpl) {
        this.fileBasedServiceImpl = fileBasedServiceImpl;
    }



}
