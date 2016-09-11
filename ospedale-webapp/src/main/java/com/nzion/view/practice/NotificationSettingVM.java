package com.nzion.view.practice;

import com.nzion.domain.NotificationSetup;
import com.nzion.dto.NotificationSettingDto;
import com.nzion.service.NotificationService;
import com.nzion.util.UtilMessagesAndPopups;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.List;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class NotificationSettingVM {

    private NotificationSettingDto appointmentGiven = new NotificationSettingDto();
    private NotificationSettingDto appointmentRescheduled = new NotificationSettingDto();

    @WireVariable
    NotificationService notificationService;

    @AfterCompose
    public void afterCompose(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, true);
        populateNotification();
    }

    public void populateNotification(){

        List<NotificationSetup> notificationSetupList = notificationService.getAllNotifications();
        for(NotificationSetup notificationSetup : notificationSetupList){
            if(notificationSetup.getStatus().equals(NotificationSetup.STATUS.SCHEDULED))
                appointmentGiven.setPropertiesToNotificationDto(notificationSetup);
            if(notificationSetup.getStatus().equals(NotificationSetup.STATUS.RESCHEDULED))
                appointmentRescheduled.setPropertiesToNotificationDto(notificationSetup);
        }

    }

    @Command
    public void saveNotification(){

        NotificationSetup scheduledNotification = getExistingNotificationSet(NotificationSetup.STATUS.SCHEDULED);
        setPropertiesAndSave(appointmentGiven,scheduledNotification);
        NotificationSetup rescheduledNotification = getExistingNotificationSet(NotificationSetup.STATUS.RESCHEDULED);
        setPropertiesAndSave(appointmentRescheduled,rescheduledNotification);
        UtilMessagesAndPopups.showSuccess("Operation Successful");

    }

    public void setPropertiesAndSave(NotificationSettingDto notificationSettingDto,NotificationSetup notificationSetup){
        notificationSetup.setPropertiesToEntity(notificationSettingDto);
        notificationService.save(notificationSetup);
    }

    private NotificationSetup getExistingNotificationSet(NotificationSetup.STATUS status){
        NotificationSetup notificationSetup = notificationService.findOneByCriteria(NotificationSetup.class, new String[]{"status"}, new Object[]{status});
        if(notificationSetup == null) {
            notificationSetup = new NotificationSetup();
            notificationSetup.setStatus(status);
        }
        return notificationSetup;
    }

    public NotificationSettingDto getAppointmentGiven() {
        return appointmentGiven;
    }

    public void setAppointmentGiven(NotificationSettingDto appointmentGiven) {
        this.appointmentGiven = appointmentGiven;
    }

    public NotificationSettingDto getAppointmentRescheduled() {
        return appointmentRescheduled;
    }

    public void setAppointmentRescheduled(NotificationSettingDto appointmentRescheduled) {
        this.appointmentRescheduled = appointmentRescheduled;
    }

}

