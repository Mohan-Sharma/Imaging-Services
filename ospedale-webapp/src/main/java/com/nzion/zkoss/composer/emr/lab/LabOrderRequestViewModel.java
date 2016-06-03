package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.Person;
import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.Weekdays;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.util.SlotAvailability;
import com.nzion.repository.UserLoginRepository;
import com.nzion.service.ScheduleService;
import com.nzion.service.billing.BillingService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import org.springframework.beans.factory.annotation.Qualifier;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.*;

@VariableResolver(DelegatingVariableResolver.class)
public class LabOrderRequestViewModel {

    @WireVariable
    private CommonCrudService commonCrudService;

    @WireVariable
    private ScheduleService scheduleService;

    @WireVariable
    private UserLoginRepository userLoginRepository;

    private LabOrderRequest labOrderRequest;

    @Init
    public void init() {
        this.labOrderRequest = new LabOrderRequest();
    }

    public LabOrderRequest getLabOrderRequest() {
        return labOrderRequest;
    }

    public void setLabOrderRequest(LabOrderRequest labOrderRequest) {
        this.labOrderRequest = labOrderRequest;
    }

    @Command("SaveLabOrderRequest")
    public void saveLabOrderRequest(){
        commonCrudService.save(labOrderRequest);
    }

    public Set<SlotAvailability> searchSchedule(Person person, Date date, Weekdays weekdays) {
        return scheduleService.searchAvailableSchedules(person, date, weekdays, Infrastructure.getSelectedLocation());
    }
}
