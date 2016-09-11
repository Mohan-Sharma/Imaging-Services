package com.nzion.repository.impl;

import com.nzion.domain.NotificationSetup;
import com.nzion.repository.NotificationRepository;

import java.util.List;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
public class NotificationRepositoryImpl extends HibernateBaseRepository implements NotificationRepository {

    public void saveNotification(NotificationSetup notificationSetup){
        save(notificationSetup);
    }

    public void mergeEntity(NotificationSetup notificationSetup){
        merge(notificationSetup);
    }

    public List<NotificationSetup> getAllNotifications(){
        return getAll(NotificationSetup.class);
    }

    public NotificationSetup findOneByCriteria(Class<NotificationSetup> persistentClass, String[] fields, Object[] values){
        return findUniqueByCriteria(persistentClass,fields,values);
    }
}
