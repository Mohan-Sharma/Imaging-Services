package com.nzion.service;

import com.nzion.domain.*;
import com.nzion.domain.Enumeration;
//import com.nzion.domain.Schedule.Tentative;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PatientRepository;
import com.nzion.repository.PersonRepository;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.impl.EnumerationServiceImpl;
import com.nzion.service.dto.LabOrderDto;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

@Service
public class LabBookAppointmentServlet extends HttpServlet{
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
    /*@Autowired
    NotificationTaskExecutor notificationTaskExecutor;*/
    @Autowired
    EnumerationServiceImpl enumerationServiceImpl;
    @Autowired
    private BillingService billingService;
    @Autowired
    private FileBasedServiceImpl fileBasedServiceImpl;

    private SlotType slotType;

    private BigDecimal consultationCharges;

    private BigDecimal convenienceFee;

    private BigDecimal registrationCharges;

    private BigDecimal totalAmount;

    private BigDecimal totalAdvAmount;

    private boolean displayConvenienceFee;

    Schedule currentSchedule;

    private Invoice invoice;

    private String url;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext()); }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Set<String> slots = null;
        Set<CalendarIndividualSlot> calendarIndividualSlots = new HashSet<>();
        List<SlotWithVisitType> furnishedSlots = null;
        Date date = null;

        BigDecimal leadTime = null;
        BigDecimal maxTime = null;

        String labId = request.getParameter("labId") != null ? request.getParameter("labId").trim() :request.getParameter("labId");
        String locationId = request.getParameter("locationId") != null ? request.getParameter("locationId").trim() : request.getParameter("locationId");
        String appointmentDate = request.getParameter("appointmentDate") != null ? request.getParameter("appointmentDate").trim() : request.getParameter("appointmentDate");
        //String visitType = request.getParameter("visitType") != null ? request.getParameter("visitType").trim() : request.getParameter("visitType");
        if(labId == null || locationId == null || appointmentDate == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing expected parameters, either one of the parameters is null");
            return;
        }
        if(!isInteger(locationId.trim())){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid location id");
            return;
        }
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDate);
            /*if(validateDate(date)) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date, cannot book appointment from past");
                return;
            }*/
            TenantIdHolder.setTenantId(labId);
            //List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"person.id"}, new Object[]{Long.valueOf(providerId)});
            List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"location.id"}, new Object[]{Long.parseLong(locationId)});

            Iterator iterator = calendarResourceAssocs.iterator();
            while (iterator.hasNext()){
                CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator.next();
                if (calendarResourceAssoc.getPerson() != null){
                    iterator.remove();
                }
            }

            Set<SlotAvailability> timeslots = null;
            if(calendarResourceAssocs.size() > 0){
                List<CalendarResourceAssoc> assocs = getCurrentCalendarResourceAssoc(calendarResourceAssocs, date);

                //***********Added code for available day of week start******
                String day = new SimpleDateFormat("EEE").format(date);
                Iterator iterator1 = assocs.iterator();
                while (iterator1.hasNext()){
                    CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator1.next();
                    if (calendarResourceAssoc.getWeek() != null){
                        List<String> listOfDays = calendarResourceAssoc.getWeek().getSelectedDays();
                        if (!listOfDays.contains(day)){
                            iterator1.remove();
                        }
                    }
                }
                //******Added code for available day of week end ******

                for(CalendarResourceAssoc assoc : assocs)
                    for(CalendarIndividualSlot calendarIndividualSlot : assoc.getCalendarIndividualSlots() ){
                        calendarIndividualSlots.add(calendarIndividualSlot);
                    }
            }
            timeslots = getAvailableSlots(locationId, appointmentDate);
            if(timeslots.size() > 0) {
                slots = getTimeSlot(timeslots);
            } else {
                slots = new HashSet<>();
            }

             /*slotType = commonCrudService.findUniqueByEquality(SlotType.class, new String[]{"name"}, new Object[]{visitType});
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
                rcmVisitType = RCMVisitType.CONSULT_VISIT;
            RCMPreference rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
            SchedulingPreference schedulingPreferences = commonCrudService.findUniqueByEquality(SchedulingPreference.class, new String[]{"rcmPreference","visitType"}, new Object[]{rcmPreference,rcmVisitType});
            Set<CalendarIndividualSlot> calendarIndividualSlotsFilter = new HashSet<>();
            leadTime = schedulingPreferences.getLeadTimeAllowed();
            maxTime = schedulingPreferences.getMaxTimeAllowed();
            for(CalendarIndividualSlot calendarIndividualSlot : calendarIndividualSlots){
                Date slotDateTime = UtilDateTime.toDate(date.getMonth(), date.getDate(), date.getYear(), calendarIndividualSlot.getStartTime().getHours(), calendarIndividualSlot.getStartTime().getMinutes(), calendarIndividualSlot.getStartTime().getSeconds());
                slotDateTime = UtilDateTime.getDayStart(slotDateTime);
                String calendarVisitType = calendarIndividualSlot.getVisitTypeSoapModule().getSlotType().toString();
                if(calendarVisitType.equals("Premium Visit") || calendarVisitType.equals("Home Visit") ||
                        calendarVisitType.equals("Tele Consultation Visit") || calendarVisitType.equals("Consult Visit") ){

                    BigDecimal hoursInterval = new BigDecimal(UtilDateTime.getIntervalInHours(UtilDateTime.getDayStart(new Date()), slotDateTime));
                    if(hoursInterval.compareTo(leadTime) >= 0 && hoursInterval.compareTo(maxTime) <= 0 ){
                        calendarIndividualSlotsFilter.add(calendarIndividualSlot);
                    }
                }
            } */


            if(!calendarIndividualSlots.isEmpty() && !slots.isEmpty()){
                furnishedSlots = createMappingOfSlotAndVisitType(calendarIndividualSlots , slots);
            }
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.toString());
        }
        PrintWriter writer = response.getWriter();
        if(furnishedSlots != null)
            writer.print(furnishedSlots);
        else {
            writer.print( "On " + UtilDateTime.toDateString(date) + " the requested slots are not available, Please try alternate Slots" );
            /*if( UtilDateTime.addHrsToDate(new Date(), leadTime.intValue()).compareTo(UtilDateTime.getDayEnd(date)) <= 0  &&
                    UtilDateTime.addHrsToDate(new Date(), maxTime.intValue()).compareTo(UtilDateTime.getDayStart(date)) >= 0  ){
                writer.print( "On " + UtilDateTime.toDateString(date) + " the requested slots are not available, Please try alternate Slots" );
            }else{
                writer.print( "Appointments can be booked only between " +
                        UtilDateTime.toDateString(UtilDateTime.addHrsToDate(new Date(), leadTime.intValue())) + " to " +
                        UtilDateTime.toDateString(UtilDateTime.addHrsToDate(new Date(), maxTime.intValue()))  );
            }
*/
        }
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

    public Set<SlotAvailability> getAvailableSlots(String locationId, String appointmentDateInString) throws ParseException {
        Date appointmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDateInString);
        //Person provider = personRepository.getPersonById(Long.valueOf(providerId));
        //Location location = personRepository.getPersonById(Long.valueOf(providerId));
        Location location = commonCrudService.getById(Location.class, Long.valueOf(locationId));
        //ScheduleSearchValueObject searchObject = new ScheduleSearchValueObject(false);
        //location = (Location)Sessions.getCurrent().getAttribute("_location");
        Weekdays weekdays = Weekdays.allSelectedWeekdays();
        /*searchObject.setLocation(location);
        searchObject.setPerson(provider);
        searchObject.setFromDate(appointmentDate);
        searchObject.setThruDate(appointmentDate);*/
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

    Date convertGivenDate(Date date){
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(1970,0,1);
        return calendar.getTime();
    }

    public Patient checkIfPatientAlreadyExistOrPersist(LabOrderDto labOrderDto) throws IOException {
        Patient oldPatientList = commonCrudService.getByUniqueValue(Patient.class, "afyaId", labOrderDto.getAfyaId());
        //List<Patient> patientList = commonCrudService.findByEquality(Patient.class, new String[]{"firstName", "lastName", "contacts.mobileNumber", "dateOfBirth"}, new Object[]{labOrderDto.getFirstName(), labOrderDto.getLastName(), labOrderDto.getMobileNumber(), labOrderDto.getDateOfBirth()});
        /*if(patientList.size() > 0)
            oldPatientList = patientList.get(0);*/
        if(oldPatientList != null){
            /*if(oldPatientList.getContacts() != null) {
                oldPatientList.getContacts().setEmail(labOrderDto.getEmailId());
            }
            if ((oldPatientList.getLanguage() == null) || (!oldPatientList.getLanguage().getEnumCode().equals(labOrderDto.getPreferredLanguage()))){
                Enumeration enumeration = commonCrudService.findUniqueByEquality(Enumeration.class, new String[]{"enumType", "enumCode"}, new Object[]{"LANGUAGE", labOrderDto.getPreferredLanguage()});
                oldPatientList.setLanguage(enumeration);
                patientRepository.merge(oldPatientList);
            }*/
            return oldPatientList;
        } else {
            /*Enumeration gender = getGenderEnumerationForPatient(labOrderDto.getGender());
            if(gender == null){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "gender cannot be null");
                return null;
            }*/

            Map<String, Object> patientDetailsFromPortal = RestServiceConsumer.getPatientDetailsByAfyaId(labOrderDto.getAfyaId());

            Patient patient = new Patient();
            patient.setFirstName(patientDetailsFromPortal.get("firstName").toString());
            patient.setLastName(patientDetailsFromPortal.get("firstName").toString());
            //patient.setPatientType("CASH PAYING");
            //patient.setDateOfBirth(labOrderDto.getDateOfBirth());
            ContactFields contactFields = new ContactFields();
            contactFields.setMobileNumber(patientDetailsFromPortal.get("firstName").toString());
            contactFields.setEmail(patientDetailsFromPortal.get("firstName").toString());
            patient.setContacts(contactFields);
            //patient.setGender(gender);
            //String afyaId = RestServiceConsumer.checkIfPatientExistInPortalAndCreateIfNotExist(patient, tenantId);
            patient.setAfyaId(labOrderDto.getAfyaId());
            //patient.setCivilId(labOrderDto.getCivilId());
            //patient.setNotificationRequired("YES");

            //Enumeration enumeration = commonCrudService.findUniqueByEquality(Enumeration.class, new String[]{"enumType", "enumCode"}, new Object[]{"LANGUAGE", labOrderDto.getPreferredLanguage()});

            //patient.setLanguage(enumeration);
            patient = commonCrudService.save(patient);
            fileBasedServiceImpl.createDefaultFolderStructure(patient);
            return patient;
        }
    }

    private Enumeration getGenderEnumerationForPatient(String gender) {
        List<Enumeration> emEnumerations = enumerationServiceImpl.getGeneralEnumerationsByType("GENDER");
        for(Enumeration enumeration : emEnumerations){
            if(enumeration.getDescription().equals(gender))
                return enumeration;
        }
        return null;
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
