package com.nzion.service;

import com.nzion.domain.NotificationSetup;

import java.util.List;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
public interface NotificationService {

    boolean save(NotificationSetup notificationSetup);

    boolean merge(NotificationSetup notificationSetup);

    List<NotificationSetup> getAllNotifications();

    NotificationSetup findOneByCriteria(Class<NotificationSetup> persistentClass, String[] fields,Object[] values);
}
