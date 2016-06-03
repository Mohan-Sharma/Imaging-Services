package com.nzion.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nzion.domain.*;
import com.nzion.domain.Enumeration;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.billing.*;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabTest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.emr.lab.LabTestProfile;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.domain.product.common.Money;
import com.nzion.domain.screen.BillingDisplayConfig;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.repository.PracticeRepository;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.dto.LabOrderDto;
import com.nzion.service.dto.LabOrderItemDto;
import com.nzion.service.emr.lab.LabService;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.*;
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

public class PhlebotomistAvailableSlotsServlet extends HttpServlet{

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    CommonCrudService commonCrudService;
    @Autowired
    private BillingService billingService;
    @Autowired
    private FileBasedServiceImpl fileBasedServiceImpl;
    @Autowired
    PracticeRepository practiceRepository;
    @Autowired
    private LabService labService;

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

        Person person = null;

        String phlebotomistId = request.getParameter("phlebotomistId") != null ? request.getParameter("phlebotomistId").trim() :request.getParameter("phlebotomistId");
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

            person = commonCrudService.getById(Person.class,Long.parseLong(phlebotomistId));
            //List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"person.id"}, new Object[]{Long.valueOf(providerId)});
            List<CalendarResourceAssoc> calendarResourceAssocs = commonCrudService.findByEquality(CalendarResourceAssoc.class, new String[]{"location.id"}, new Object[]{Long.parseLong(locationId)});



            Iterator iterator = calendarResourceAssocs.iterator();
            while (iterator.hasNext()){
                CalendarResourceAssoc calendarResourceAssoc = (CalendarResourceAssoc)iterator.next();
                if ((calendarResourceAssoc.getPerson() == null) || (!calendarResourceAssoc.getPerson().getId().equals(Long.parseLong(phlebotomistId)))){
                    iterator.remove();
                }
            }

            Set<SlotAvailability> timeslots = null;
            if(calendarResourceAssocs.size() > 0){
                List<CalendarResourceAssoc> assocs = getCurrentCalendarResourceAssoc(calendarResourceAssocs, date);

                //***********Added code for available day of week start******
                String day = new SimpleDateFormat("EEE").format(date);
                Iterator iterator1 = assocs.iterator();
                while (iterator.hasNext()){
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
            timeslots = getAvailableSlots(person, locationId, appointmentDate);
            if(timeslots.size() > 0) {
                slots = getTimeSlot(timeslots);
            } else {
                slots = new HashSet<>();
            }

            RCMPreference rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
            SchedulingPreference schedulingPreferences = commonCrudService.findUniqueByEquality(SchedulingPreference.class, new String[]{"rcmPreference","visitType"}, new Object[]{rcmPreference, RCMPreference.RCMVisitType.HOME_PHLEBOTOMY});
            Set<CalendarIndividualSlot> calendarIndividualSlotsFilter = new HashSet<>();
            leadTime = schedulingPreferences.getLeadTimeAllowed();
            maxTime = schedulingPreferences.getMaxTimeAllowed();

            for(CalendarIndividualSlot calendarIndividualSlot : calendarIndividualSlots){
                Date slotDateTime = UtilDateTime.toDate(date.getMonth(), date.getDate(), date.getYear(), calendarIndividualSlot.getStartTime().getHours(),
                        calendarIndividualSlot.getStartTime().getMinutes(), calendarIndividualSlot.getStartTime().getSeconds());
                slotDateTime = UtilDateTime.getDayStart(slotDateTime);

                    BigDecimal hoursInterval = new BigDecimal(UtilDateTime.getIntervalInHours(UtilDateTime.getDayStart(new Date()), slotDateTime));
                    if(hoursInterval.compareTo(leadTime) >= 0 && hoursInterval.compareTo(maxTime) <= 0 ){
                        calendarIndividualSlotsFilter.add(calendarIndividualSlot);
                    }
            }

            if(!calendarIndividualSlotsFilter.isEmpty() && !slots.isEmpty()){
                furnishedSlots = createMappingOfSlotAndVisitType(calendarIndividualSlots , slots);
            }
        } catch (ParseException e) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, e.toString());
        }
        PrintWriter writer = response.getWriter();
        if(furnishedSlots != null)
            writer.print(furnishedSlots);
        else {
            if( UtilDateTime.addHrsToDate(new Date(), leadTime.intValue()).compareTo(UtilDateTime.getDayEnd(date)) <= 0  &&
                    UtilDateTime.addHrsToDate(new Date(), maxTime.intValue()).compareTo(UtilDateTime.getDayStart(date)) >= 0  ){
                writer.print( "On " + UtilDateTime.toDateString(date) + " the requested slots are not available, Please try alternate Slots" );
            }else{
                writer.print( "Appointments can be booked only between " +
                        UtilDateTime.toDateString(UtilDateTime.addHrsToDate(new Date(), leadTime.intValue())) + " to " +
                        UtilDateTime.toDateString(UtilDateTime.addHrsToDate(new Date(), maxTime.intValue()))  );
            }
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

        if(UtilValidator.isEmpty(labOrderDto.getRows())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "order details cannot be null");
            return;
        }

        if(validateDate(labOrderDto.getAppointmentStartDate()) || validateDate(labOrderDto.getAppointmentEndDate())) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid date, cannot place order from past");
            return;
        }
        Patient patient = checkIfPatientAlreadyExistOrPersist(labOrderDto);
        if(patient == null){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "patient does not exist with the given afya_id");
            return;
        }

        Map<String, Object> resultStatus = bookAppointment(response, patient, labOrderDto);

        String status = (String)resultStatus.get("status");
        PrintWriter writer = response.getWriter();
        if(status.equals("created")) {
            response.setStatus(HttpServletResponse.SC_OK, "order placed");

            String resultString = "{" +
                    "\"status\" : \"" + "SUCCESS" +
                    "\", \"orderId\" : \"" + resultStatus.get("labOrder") +
                    "\"}";
            writer.print(resultString);
        } else {
            response.setStatus(HttpServletResponse.SC_OK, "order creation failed");
            String resultString = "{" +
                    "\"status\" : \"" + "FAILED" +
                    "\", \"orderId\" : \"" + "null" +
                    "\"}";
            writer.print(resultString);
        }
        writer.close();
    }

    public Set<SlotAvailability> getAvailableSlots(Person person, String locationId, String appointmentDateInString) throws ParseException {
        Date appointmentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(appointmentDateInString);
        Location location = commonCrudService.getById(Location.class, Long.valueOf(locationId));
        Weekdays weekdays = Weekdays.allSelectedWeekdays();
        return scheduleService.searchAvailableSchedules(person, appointmentDate, weekdays, location);
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

    public Map<String, Object> bookAppointment(HttpServletResponse response, Patient patient, LabOrderDto labOrderDto) {
        //currentSchedule = new Schedule();
        LabOrderRequest labOrderRequest = new LabOrderRequest();
        List<LabOrderItemDto> labOrderItemDtoList = labOrderDto.getRows();

        Map<String, Object> result = new HashMap<>();

        try {
        Location location = commonCrudService.getById(Location.class, Long.parseLong(labOrderDto.getLocation().toString()));

        labOrderRequest.setLocation(location);
        labOrderRequest.setPatient(patient);
        labOrderRequest.setStartTime(convertGivenDate(labOrderDto.getAppointmentStartDate()));
        labOrderRequest.setEndTime(convertGivenDate(labOrderDto.getAppointmentEndDate()));
        labOrderRequest.setStartDate(com.nzion.util.UtilDateTime.getDayStart(labOrderDto.getAppointmentStartDate()));
        labOrderRequest.setFromMobileApp(labOrderDto.isFromMobileApp());
        labOrderRequest.setPaymentId(labOrderDto.getPaymentId());

        /*Referral referral = null;
        if (labOrderItemDto.getReferralClinicId() != null) {
            List<Referral> referrals = commonCrudService.findByEquality(Referral.class, new String[]{"tenantId"}, new Object[]{labOrderItemDto.getReferralClinicId()});
            if (UtilValidator.isNotEmpty(referrals)) {
                referral = referrals.get(0);
            }
        }
        labOrderRequest.setReferral(referral);*/
        //labOrderRequest.setReferralDoctorFirstName(labOrderDto.getReferralDoctorName());
        //labOrderRequest.setSequenceNum(0);
        labOrderRequest.setMobileOrPatinetPortal(labOrderDto.isFromMobileApp());

        for (LabOrderItemDto labOrderItemDto:labOrderItemDtoList) {

            String labTestProfile = "";
            String labTestPanel = "";
            String labTest = "";
            PatientLabOrder patientLabOrder = new PatientLabOrder();
            if (labOrderItemDto.getLabTestType().equals("LAB_TEST")){
                labTest = labOrderItemDto.getLabTestCode();
            }
            if (labOrderItemDto.getLabTestType().equals("LAB_PROFILE")){
                labTestProfile = labOrderItemDto.getLabTestCode();
            }
            if (labOrderItemDto.getLabTestType().equals("LAB_PANEL")){
                labTestPanel = labOrderItemDto.getLabTestCode();
            }

            if ((labTestProfile != null) && (labTestProfile != "")){
                LabTestProfile profile = commonCrudService.getByUniqueValue(LabTestProfile.class, "profileCode", labTestProfile);
                patientLabOrder.setLabTestProfile(profile);

                for(LabTest test: profile.getTests())
                    labOrderRequest.addLaboratories(test.getLaboratory());
            } if ((labTestPanel != null) && (labTestPanel != "")){
                LabTestPanel testPanel = commonCrudService.getByUniqueValue(LabTestPanel.class, "panelCode", labTestPanel);
                patientLabOrder.setLabTestPanel(testPanel);

                for(LabTest test: testPanel.getTests())
                    labOrderRequest.addLaboratories(test.getLaboratory());
            } if ((labTest != null) && (labTest != "")){
                LabTest test = commonCrudService.getByUniqueValue(LabTest.class, "testCode", labTest);
                patientLabOrder.setLabTest(test);

                labOrderRequest.addLaboratories(test.getLaboratory());
            }
            patientLabOrder.setHomeService(labOrderDto.isHomeService());
            labOrderRequest.addPatientLabOrder(patientLabOrder);
            patientLabOrder.setLabOrderRequest(labOrderRequest);
        }
        //labOrderRequest.setConsultationInvoiceGenerated(false);
        LabOrderRequest orderRequest = commonCrudService.save(labOrderRequest);

        try{
             Invoice invoice =  generateInvoice(labOrderRequest, labOrderDto);
        }catch (Exception e){
             e.printStackTrace();
        }

        result.put("labOrder", orderRequest.getId());
        result.put("status", "created");

        } catch (Exception e){
            result.put("labOrder", labOrderRequest);
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

    public Invoice generateInvoice(LabOrderRequest labOrderRequest, LabOrderDto labOrderDto){
        Long itemId = (System.currentTimeMillis()+labOrderRequest.getId());
        final Invoice invoice = new Invoice(itemId.toString(), LabOrderRequest.class.getName(), null,
                labOrderRequest.getPatient(), labOrderRequest.getLocation());
        invoice.setInvoiceType(InvoiceType.OPD);

        List<LabOrderItemDto> labOrderItems = labOrderDto.getRows();
        for (LabOrderItemDto labOrderItem : labOrderItems){
            String description = "";
            switch(labOrderItem.getLabTestType()){
                case "LAB_TEST" :
                    LabTest test = commonCrudService.getByUniqueValue(LabTest.class, "testCode", labOrderItem.getLabTestCode());
                    description = test.getTestDescription();
                    break;
                case "LAB_PROFILE" :
                    LabTestProfile profile = commonCrudService.getByUniqueValue(LabTestProfile.class, "profileCode", labOrderItem.getLabTestCode());
                    description = profile.getProfileName();
                    break;
                case "LAB_PANEL" :
                    LabTestPanel testPanel = commonCrudService.getByUniqueValue(LabTestPanel.class, "panelCode", labOrderItem.getLabTestCode());
                    description = testPanel.getPanelDescription();
                    break;
            }

            InvoiceItem invItem =   new InvoiceItem(invoice, labOrderRequest.getId().toString() , InvoiceType.OPD_LAB_CHARGES,
                    description, 1, null, LabOrderRequest.class.getName());
            invItem.init(new BigDecimal(labOrderItem.getHomeServiceCost().toString()).setScale(2), "KD", new Money(new BigDecimal(labOrderItem.getHomeServiceCost().toString()).setScale(2), convertTo()), 1);
            invoice.addInvoiceItem(invItem);
            if(invoice.getTotalAmount() != null && invoice.getTotalAmount().getAmount() != null)
                invoice.setTotalAmount(new com.nzion.domain.product.common.Money(invoice.getTotalAmount().getAmount().add(new BigDecimal(labOrderItem.getHomeServiceCost().toString()).setScale(2)),convertTo()));
            else
                invoice.setTotalAmount(new com.nzion.domain.product.common.Money(new BigDecimal(labOrderItem.getHomeServiceCost().toString()).setScale(2),convertTo()));
        }
        invoice.setLocation(labOrderRequest.getLocation());
        invoice.setCollectedAmount(invoice.getTotalAmount());
        invoice.setInvoiceDate(labOrderDto.getAppointmentEndDate());
        invoice.setInvoiceStatus(InvoiceStatusItem.RECEIVED.toString());

        Enumeration paymentMethod = commonCrudService.findUniqueByEquality(Enumeration.class, new String[]{"enumCode", "enumType"}, new Object[]{"ONLINE_PAYMENT", "PAYMENT_MODE"});
        InvoicePayment invoicePayment = new InvoicePayment(paymentMethod, invoice, invoice.getTotalAmount(), PaymentType.ONLINE_PAYMENT);
        invoice.addInvoicePayment(invoicePayment);

        Invoice inv = commonCrudService.save(invoice);
        //billingService.saveInvoiceStatus(invoice, InvoiceStatusItem.RECEIVED);
        labService.createLabRequisition(labOrderRequest, invoice);

        return inv;

    }

    public Currency convertTo(){
        BillingDisplayConfig billingDisplayConfig = commonCrudService.getByPractice(BillingDisplayConfig.class);
        String currency = billingDisplayConfig.getCurrency().getCode();
        Currency defaultCurrency = Currency.getInstance(currency);
        return defaultCurrency;
    }
}
