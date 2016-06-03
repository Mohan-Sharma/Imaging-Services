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
import com.nzion.service.ScheduleService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.billing.LabInvoiceManager;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

}
