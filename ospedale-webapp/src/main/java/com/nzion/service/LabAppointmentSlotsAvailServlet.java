package com.nzion.service;

import com.nzion.domain.*;
import com.nzion.domain.RCMPreference.RCMVisitType;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PersonRepository;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilDateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.zkoss.json.JSONObject;

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

public class LabAppointmentSlotsAvailServlet extends HttpServlet{
    private Location location;
    
    @Autowired
    PersonRepository personRepository;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    CommonCrudService commonCrudService;

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
        Date date = null;

        BigDecimal leadTime = null;
        BigDecimal maxTime = null;

        String labId = request.getParameter("labId") != null ? request.getParameter("labId").trim() :request.getParameter("labId");
        String appointmentDate = request.getParameter("appointmentDate") != null ? request.getParameter("appointmentDate").trim() : request.getParameter("appointmentDate");
        String locationId = request.getParameter("locationId") != null ? request.getParameter("locationId").trim() : request.getParameter("locationId");

        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDate);
            if(validateDate(date)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date, cannot book appointment from past");
                return;
            }
            TenantIdHolder.setTenantId(labId);

            CalendarResourceAssoc calendarResourceAssoc1 = new CalendarResourceAssoc();
            calendarResourceAssoc1.setLocation(commonCrudService.getById(Location.class, Long.parseLong(locationId)));
            calendarResourceAssoc1.setPerson(null);

            List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.searchByExample(calendarResourceAssoc1);

            Iterator iterator = calendarResourceAssocs.iterator();
            while (iterator.hasNext()){
                CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator.next();
                if (calendarResourceAssoc.getPerson() != null){
                    iterator.remove();
                }
            }

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
                        calendarIndividualSlots.addAll(assoc.getCalendarIndividualSlots());
                    }
                    calendarIndividualSlotsMap.put(UtilDateTime.toDateString(date1, "dd-MM-yyyy"), calendarIndividualSlots);
                }
            }
            for (int i= 0; i< noOfDays; i++) {
                Date date1 = UtilDateTime.addDaysToDate(date, i);
                Set<SlotAvailability> timeslots = null;
                timeslots = getAvailableSlots(locationId, date1);
                Set<String> slots = null;
                if(timeslots.size() > 0) {
                    slots = getTimeSlot(timeslots);
                } else {
                    slots = new HashSet<>();
                }
                slotsMap.put(UtilDateTime.toDateString(date1, "dd-MM-yyyy"), slots);
            }

            RCMPreference rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
            SchedulingPreference schedulingPreferences = commonCrudService.findUniqueByEquality(SchedulingPreference.class, new String[]{"rcmPreference","visitType"}, new Object[]{rcmPreference, RCMVisitType.HOME_PHLEBOTOMY});
            leadTime = schedulingPreferences.getLeadTimeAllowed();
            maxTime = schedulingPreferences.getMaxTimeAllowed();


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
                calendarIndividualSlotsMap.put(entry.getKey(), calendarIndividualSlotsFilter);
            }

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

    public Set<SlotAvailability> getAvailableSlots(String locationId, Date appointmentDate) throws ParseException {
        Location location = commonCrudService.getById(Location.class, Long.valueOf(locationId));
        Weekdays weekdays = Weekdays.allSelectedWeekdays();
        return scheduleService.searchAvailableSchedules(null, appointmentDate, weekdays, location);
    }

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
