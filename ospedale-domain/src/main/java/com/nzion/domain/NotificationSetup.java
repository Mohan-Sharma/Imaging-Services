package com.nzion.domain;

import com.nzion.dto.NotificationSettingDto;

import javax.persistence.*;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"status"})})
public class NotificationSetup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private STATUS status;
    private boolean bySMS;
    private boolean byEmail;

    public enum STATUS{
        SCHEDULED{
            @Override
            public String toString(){ return "When appointment is given"; }
        },
        RESCHEDULED{

            public String toString() { return "When appointment is rescheduled"; }
        }
    }

    public void setPropertiesToEntity(NotificationSettingDto notificationSettingDto){
        this.bySMS = notificationSettingDto.isBySMS();
        this.byEmail = notificationSettingDto.isByEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
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
