package com.nzion.dto;

import com.nzion.domain.NotificationSetup;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
public class NotificationSettingDto {

    private boolean bySMS;
    private boolean byEmail;

    public void setPropertiesToNotificationDto(NotificationSetup notificationSetup){
        this.setByEmail(notificationSetup.isByEmail());
        this.setBySMS(notificationSetup.isBySMS());
    }

    public boolean isBySMS() {
        return bySMS;
    }

    public void setBySMS(boolean bySMS) {
        this.bySMS = bySMS;
    }

    public boolean isByEmail() {
        return byEmail;
    }

    public void setByEmail(boolean byEmail) {
        this.byEmail = byEmail;
    }
}

