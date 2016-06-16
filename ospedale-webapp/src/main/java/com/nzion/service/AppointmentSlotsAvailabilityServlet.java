package com.nzion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nzion.domain.*;
import com.nzion.domain.Enumeration;
import com.nzion.domain.RCMPreference.RCMVisitType;
import com.nzion.domain.Schedule.STATUS;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.billing.AcctgTransTypeEnum;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.domain.billing.DebitCreditEnum;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.billing.InvoiceType;
import com.nzion.domain.billing.PaymentType;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PatientRepository;
import com.nzion.repository.PersonRepository;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.ScheduleService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.EnumerationServiceImpl;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.*;
import org.joda.time.LocalDate;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppointmentSlotsAvailabilityServlet extends HttpServlet{
    private Location location;
    
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
    @Autowired
    EnumerationServiceImpl enumerationServiceImpl;
    @Autowired
    private BillingService billingService;
    @Autowired
    private FileBasedServiceImpl fileBasedServiceImpl;

    private SlotType slotType;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Set<String>> slotsMap = new LinkedHashMap<>();
        Map<String, String> resultMap = new LinkedHashMap<>();

        Map<String,Set<CalendarIndividualSlot>> calendarIndividualSlotsMap = new LinkedHashMap<>();
        List<Set<SlotAvailability>> slotAvailabilityList = new ArrayList<>();
        int noOfDays = 7;
        //List<SlotWithVisitType> furnishedSlots = null;
        List<Integer> furnishedSlots = new ArrayList<>();
        Date date = null;

        BigDecimal leadTime = null;
        BigDecimal maxTime = null;

        String labId = request.getParameter("labId") != null ? request.getParameter("labId").trim() :request.getParameter("labId");
        String phlebotomistId = request.getParameter("phlebotomistId") != null ? request.getParameter("phlebotomistId").trim() : request.getParameter("phlebotomistId");
        String appointmentDate = request.getParameter("appointmentDate") != null ? request.getParameter("appointmentDate").trim() : request.getParameter("appointmentDate");
        String locationId = request.getParameter("locationId") != null ? request.getParameter("locationId").trim() : request.getParameter("locationId");
        //String visitType = request.getParameter("visitType") != null ? request.getParameter("visitType").trim() : request.getParameter("visitType");

        if(labId == null || phlebotomistId == null || appointmentDate == null || locationId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing expected parameters, either one of the parameters is null");
            return;
        }
        if(!isInteger(phlebotomistId.trim())){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid phlebotomist id");
            return;
        }
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDate);
            if(validateDate(date)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date, cannot book appointment from past");
                return;
            }
            TenantIdHolder.setTenantId(labId);
            //List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"person.id"}, new Object[]{Long.valueOf(providerId)});
            //////////////

            Person person = commonCrudService.getById(Person.class,Long.parseLong(phlebotomistId));
            //List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"person.id"}, new Object[]{Long.valueOf(providerId)});
            List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"location.id"}, new Object[]{Long.parseLong(locationId)});

            Iterator iterator = calendarResourceAssocs.iterator();
            while (iterator.hasNext()){
                CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator.next();
                if ((calendarResourceAssoc.getPerson() == null) || (!calendarResourceAssoc.getPerson().getId().equals(Long.parseLong(phlebotomistId)))){
                    iterator.remove();
                }
            }

            ////////////
            //Set<SlotAvailability> timeslots = null;
            if(calendarResourceAssocs.size() > 0){
                for (int i= 0; i < noOfDays; i++) {
                    Date date1 = UtilDateTime.addDaysToDate(date, i);
                    List<CalendarResourceAssoc> assocs = getCurrentCalendarResourceAssoc(calendarResourceAssocs, date1);

                    //***********Added code for available day of week start******
                    String day = new SimpleDateFormat("EEE").format(date1);
                    Iterator iterator1 = assocs.iterator();
                    while (iterator1.hasNext()){
                        CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator1.next();
                        if (calendarResourceAssoc.getWeek() != null){
                            List<String> listOfDays = calendarResourceAssoc.getWeek().getSelectedDays();
                                if (!listOfDays.contains(day)){
                                    iterator1.remove();
                                    //break;
                                }
                        }

                    }
                    //******Added code for available day of week end ******

                    Set<CalendarIndividualSlot> calendarIndividualSlots = new HashSet<>();
                    for (CalendarResourceAssoc assoc : assocs){
                        /*for (CalendarIndividualSlot calendarIndividualSlot : assoc.getCalendarIndividualSlots()) {
                            if (visitType.equals(calendarIndividualSlot.getVisitTypeSoapModule().getSlotType().getName()))
                                calendarIndividualSlots.add(calendarIndividualSlot);
                        }*/
                        calendarIndividualSlots.addAll(assoc.getCalendarIndividualSlots());
                    }
                    //calendarIndividualSlotsList.add(calendarIndividualSlots);
                    calendarIndividualSlotsMap.put(UtilDateTime.toDateString(date1, "dd-MM-yyyy"), calendarIndividualSlots);
                }
            }
            for (int i= 0; i< noOfDays; i++) {
                Date date1 = UtilDateTime.addDaysToDate(date, i);
                Set<SlotAvailability> timeslots = null;
                timeslots = getAvailableSlots(person, locationId, date1);
                Set<String> slots = null;
                if(timeslots.size() > 0) {
                    slots = getTimeSlot(timeslots);
                } else {
                    slots = new HashSet<>();
                }
                //slotsList.add(slots);
                slotsMap.put(UtilDateTime.toDateString(date1, "dd-MM-yyyy"), slots);
            }

           /* slotType = commonCrudService.findUniqueByEquality(SlotType.class, new String[]{"name"}, new Object[]{visitType});
            if(slotType == null){
                slotType = commonCrudService.findUniqueByEquality(SlotType.class, new String[]{"name"}, new Object[]{"Consult Visit"});
            }
            RCMVisitType rcmVisitType = null;
            if(slotType.getName().equals("Premium Visit"))
                rcmVisitType = RCMVisitType.PREMIUM_APPOINTMENT;
            if(slotType.getName().equals("Home Visit"))
                rcmVisitType = RCMVisitType.HOME_VISIT_APPOINTMENT;
            if(slotType.getName().equals("Tele Consultation Visit"))
                rcmVisitType = RCMVisitType.TELE_CONSULT_APPOINTMENT;
            if(slotType.getName().equals("Consult Visit"))
                rcmVisitType = RCMVisitType.CONSULT_VISIT;*/
            RCMPreference rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
            SchedulingPreference schedulingPreferences = commonCrudService.findUniqueByEquality(SchedulingPreference.class, new String[]{"rcmPreference","visitType"}, new Object[]{rcmPreference, RCMPreference.RCMVisitType.HOME_PHLEBOTOMY});
            leadTime = schedulingPreferences.getLeadTimeAllowed();
            maxTime = schedulingPreferences.getMaxTimeAllowed();


            //for (Set<CalendarIndividualSlot> calendarIndividualSlots : calendarIndividualSlotsList) {
            for (Map.Entry<String, Set<CalendarIndividualSlot>> entry : calendarIndividualSlotsMap.entrySet()) {
                Set<CalendarIndividualSlot> calendarIndividualSlotsFilter = new HashSet<>();
                for (CalendarIndividualSlot calendarIndividualSlot : entry.getValue()) {
                    Date slotDateTime = UtilDateTime.toDate(date.getMonth(), date.getDate(), date.getYear(), calendarIndividualSlot.getStartTime().getHours(),
                            calendarIndividualSlot.getStartTime().getMinutes(), calendarIndividualSlot.getStartTime().getSeconds());
                    slotDateTime = UtilDateTime.getDayStart(slotDateTime);
                        BigDecimal hoursInterval = new BigDecimal(UtilDateTime.getIntervalInHours(UtilDateTime.getDayStart(new Date()), slotDateTime));
                        if (hoursInterval.compareTo(leadTime) >= 0 && hoursInterval.compareTo(maxTime) <= 0) {
                            calendarIndividualSlotsFilter.add(calendarIndividualSlot);
                        }
                }
                //calendarIndividualSlotsFilterList.add(calendarIndividualSlotsFilter);
                calendarIndividualSlotsMap.put(entry.getKey(), calendarIndividualSlotsFilter);
            }

            /*for (int i=0; i<7; i++){
                //Set<CalendarIndividualSlot> calendarIndividualSlots = calendarIndividualSlotsFilterList.get(i);
                //Set<String> slotsSet = slotsList.get(i);

                if(!calendarIndividualSlots.isEmpty() && !slotsSet.isEmpty()){
                    int j = createMappingOfSlotAndVisitType(calendarIndividualSlots , slotsSet).size();
                    furnishedSlots.add(j);
                }
            }*/

            Set<String> slotKey = slotsMap.keySet();
            for (String key : calendarIndividualSlotsMap.keySet()) {
                if (slotKey.contains(key)){
                    Set<CalendarIndividualSlot> calendarIndividualSlots = calendarIndividualSlotsMap.get(key);
                    Set<String> slotsSet = slotsMap.get(key);
                    int j = createMappingOfSlotAndVisitType(calendarIndividualSlots , slotsSet).size();
                    resultMap.put(key, j+"&"+calendarIndividualSlots.size());
                }
            }

        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.toString());
        }
        PrintWriter writer = response.getWriter();
        if(resultMap != null){
            String s=new JSONObject().toJSONString(resultMap);
            writer.print(s);
        }
        //writer.print(furnishedSlots);
        else {
            writer.print("");
        }
        writer.close();
    }

    public static void main(String args[]){
        System.out.println(UtilDateTime.addHrsToDate(new Date(), 720));
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

    public Set<SlotAvailability> getAvailableSlots(Person person, String locationId, Date appointmentDate) throws ParseException {
        //Date appointmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDateInString);
        Location location = commonCrudService.getById(Location.class, Long.valueOf(locationId));
        Weekdays weekdays = Weekdays.allSelectedWeekdays();
        return scheduleService.searchAvailableSchedules(person, appointmentDate, weekdays, location);
    }

    /*public Set<SlotAvailability> getAvailableSlotsFornext7Days(String providerId, Date appointmentDate) throws ParseException {
        //Date appointmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDateInString);
        Person provider = personRepository.getPersonById(Long.valueOf(providerId));
        ScheduleSearchValueObject searchObject = new ScheduleSearchValueObject(false);
        //location = (Location)Sessions.getCurrent().getAttribute("_location");
        Weekdays weekdays = Weekdays.allSelectedWeekdays();
        //searchObject.setLocation(location);
        Date date1 = UtilDateTime.addDaysToDate(appointmentDate, 6);

        searchObject.setPerson(provider);
        searchObject.setFromDate(appointmentDate);
        searchObject.setThruDate(date1);
        return scheduleService.searchAvailableSchedules(searchObject, weekdays);
    }*/

    public Set<String> getTimeSlot(Set<SlotAvailability> timeslots){
        Set<String> slots = new LinkedHashSet<>();
        StringBuilder buffer;
        if(timeslots.size() > 0) {
            for (SlotAvailability slotAvailability : timeslots) {
                buffer = new StringBuilder();
                CalendarSlot slot = slotAvailability.getSlot();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                try {
                    if (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(UtilDateTime.format(slotAvailability.getOn(), new SimpleDateFormat("yyyy-MM-dd")) + " " + sdf.format(slot.getStartTime())).before(new Date())) {
                    continue;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                buffer.append(sdf.format(slot.getStartTime()));
                buffer.append(" - ");
                buffer.append(sdf.format(slot.getEndTime()));
                slots.add(buffer.toString());
            }
        }
        return slots;
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
            return "{" + "\"timeSlot\" : \"" + time +"\" }";
        }
    }

}
