package com.nzion.service.impl;

import com.nzion.domain.NotificationSetup;
import com.nzion.repository.NotificationRepository;
import com.nzion.service.NotificationService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Nthdimenzion on 11-Sep-16.
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    NotificationRepository notificationRepository;

    @Resource(name = "notificationRepository")
    @Required
    public void setNotificationRepository(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public boolean save(NotificationSetup notificationSetup){
        notificationRepository.saveNotification(notificationSetup);
        return true;
    }

    @Transactional
    public boolean merge(NotificationSetup notificationSetup){
        notificationRepository.mergeEntity(notificationSetup);
        return true;
    }

    public List<NotificationSetup> getAllNotifications(){
        return notificationRepository.getAllNotifications();
    }

    public NotificationSetup findOneByCriteria(Class<NotificationSetup> persistentClass, String[] fields, Object[] values){
        return notificationRepository.findOneByCriteria(persistentClass,fields,values);
    }

    public NotificationRepository getNotificationRepository() {
        return notificationRepository;
    }
}
