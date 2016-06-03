package com.nzion.service.common;

import java.util.Map;

import com.nzion.domain.UserLogin;
import com.nzion.report.search.view.ReminderMailVo;

public interface MailService {
	
	void sendMail(Map<String,Object> messageProperties);
	
	void sendMail(UserLogin userLogin);
	
	void sendUserLoginCredentials(UserLogin userLogin);
	
	void sendPatientReminder(ReminderMailVo mailVo);
}
