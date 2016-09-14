package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.Person;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceStatusItem;
import com.nzion.domain.emr.lab.*;
import com.nzion.domain.emr.lab.LabOrderRequest.ORDERSTATUS;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.domain.emr.soap.PatientLabOrder.STATUS;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.exception.TransactionException;
import com.nzion.repository.notifier.utility.SmsUtil;
import com.nzion.repository.notifier.utility.TemplateNames;
import com.nzion.service.ScheduleService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.billing.LabInvoiceManager;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.RestServiceConsumer;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.text.SimpleDateFormat;
import java.util.*;

@VariableResolver(DelegatingVariableResolver.class)
public class RescheduleLabOrderViewModel {


    @WireVariable
    private BillingService billingService;

    @WireVariable
    private CommonCrudService commonCrudService;

    @WireVariable
    private ScheduleService scheduleService;

    private Set<PatientLabOrder> labTestsOrdered;

    private Set<PatientLabOrder> selected;

    private LabOrderRequest labOrderRequest;

    private Date appointmentDate;

    private int noOfCancelledLabOrder;

    @Init
    public void init(
            @ExecutionArgParam("labOrderRequest") LabOrderRequest labOrderRequest) {
        this.labOrderRequest = labOrderRequest;

    }

    private Boolean isAllOrderCancelled() {
        for (PatientLabOrder patientLabOrder : labTestsOrdered) {
            if (STATUS.CANCELLED.equals(patientLabOrder.getStatus()))
                noOfCancelledLabOrder = noOfCancelledLabOrder + 1;
        }
        return noOfCancelledLabOrder == labTestsOrdered.size();
    }

    public void setSelected(Set<PatientLabOrder> selected) {
        this.selected = selected;
    }

    public Set<PatientLabOrder> getSelected() {
        return selected;
    }


    public LabOrderRequest getLabOrderRequest() {
        return labOrderRequest;
    }

    public void setLabOrderRequest(LabOrderRequest labOrderRequest) {
        this.labOrderRequest = labOrderRequest;
    }

    /*@Command
    public void reschedule() throws TransactionException {
        System.out.println("hi");
    }*/

    public Set<SlotAvailability> searchSchedule(Person person, Date date, Weekdays weekdays) {
        return scheduleService.searchAvailableSchedules(person, date, weekdays, Infrastructure.getSelectedLocation());
    }

    public static void notifyRescheduleLabOrder(LabOrderRequest updatedLabOrderRequest, Date appointmentDate, Date startTimeDate){
        try {
            com.nzion.service.common.CommonCrudService commonCrudService = com.nzion.util.Infrastructure.getSpringBean("commonCrudService");

            Map<String, Object> clinicDetails = RestServiceConsumer.getRadiologyDetByRadiologyId(Infrastructure.getPractice().getTenantId());

            String date = constructDate(startTimeDate, appointmentDate);
            String time = constructTime(startTimeDate, appointmentDate);

            final Map adminUserLogin = RestServiceConsumer.getUserLoginByUserName(Infrastructure.getPractice().getAdminUserLogin().getUsername());
            Object languagePreference = adminUserLogin.get("languagePreference");
            clinicDetails.put("languagePreference", languagePreference);
            clinicDetails.put("oldDate", date);
            clinicDetails.put("oldTime", time);

            clinicDetails.put("key", TemplateNames.RESCHEDULE_LAB_ORDER_SMS_TO_PATIENT.name());
            clinicDetails.put("forDoctor", new Boolean(false));
            clinicDetails.put("forAdmin", new Boolean(false));
            clinicDetails.put("labName", Infrastructure.getPractice().getPracticeName());
            SmsUtil.sendStatusSms(updatedLabOrderRequest, clinicDetails);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        return dateFormat.format(furnishedDate);
    }

}
