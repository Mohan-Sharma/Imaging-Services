package com.nzion.service.common.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.nzion.domain.EmailMessageContent;
import com.nzion.domain.Party.PartyType;
import com.nzion.domain.Person;
import com.nzion.domain.PostalAddressFields;
import com.nzion.domain.UserLogin;
import com.nzion.enums.EmailContentType;
import com.nzion.report.search.view.ReminderMailVo;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.MailService;
import com.nzion.util.Constants;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

public class MailServiceImpl implements MailService {
	private SimpleMailMessage templateMessage;
	private JavaMailSender javaMailSender;
	private CommonCrudService commonCrudService;

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
	this.templateMessage = templateMessage;
	}

	public void sendUserLoginCredentials(UserLogin userLogin) {
	Map<String, Object> messageProperties = new HashMap<String, Object>();
	String email = userLogin.getPerson().getContacts().getEmail();
	if (UtilValidator.isEmpty(email)) {
		throw new RuntimeException("Email not specified for " + commonCrudService.getFormattedName(userLogin.getPerson()));
	}
	StringBuilder buffer = new StringBuilder();
	Person loggedInPerson = userLogin.getPerson();
	buffer.append("Hi ").append( userLogin.getPerson().getFirstName() + " " + userLogin.getPerson().getLastName())
			.append(",").append("\r\n").append("\r\n");
	buffer.append("We have received new password request for your account").append(Constants.BLANK).append(
			userLogin.getUsername()).append("\n");
	buffer.append("Here is the new password details:").append("\r\n").append("\r\n");
	Properties properties = new Properties();
	InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
			"mailContent.properties");
	try {
		properties.load(inputStream);
	} catch (IOException e) {
		e.printStackTrace();
	}
	String applicationUrl = (String) properties.get("applicationUrl");
	buffer.append("Url:").append("\t").append(applicationUrl);
	buffer.append("\r\n UserName:").append(userLogin.getUsername());
	buffer.append(" \r\n Password:").append(userLogin.getPassword());
	buffer.append("\r\n");
	buffer.append("\r\n");
	if (Infrastructure.getPractice() != null)
		buffer.append("The system will ask you to choose a new password when you login next time.").append("\r\n")
				.append("\r\n");
	buffer.append("Best Regards\r\n");
	buffer.append(loggedInPerson.getLastName());
	buffer.append(loggedInPerson.getFirstName()).append("\r\n");
	buffer.append(loggedInPerson.getContacts().getOfficePhone());
	messageProperties.put("subject", "User Login details");
	messageProperties.put("mailContent", buffer);
	messageProperties.put("toEmail", email);
	messageProperties.put("password", userLogin.getPassword());
	messageProperties.put("username", userLogin.getUsername());
	sendMail(messageProperties);
	}

	public void sendMail(UserLogin userLogin) {
	Map<String, Object> messageProperties = new HashMap<String, Object>();
	String email = userLogin.getPerson().getContacts().getEmail();
	if (UtilValidator.isEmpty(email)) {
		throw new RuntimeException("Email not specified for " + commonCrudService.getFormattedName(userLogin.getPerson()));
	} 
	EmailContentType emailType = null;
	if (userLogin.getPatient() != null) {
		emailType = EmailContentType.EMERGENCY_LOGIN;
	} else
		if (PartyType.PATIENT.equals(userLogin.getPerson().getPartyType())) {
			emailType = EmailContentType.PATIENT_LOGIN;
		} else {
			emailType = EmailContentType.USER_LOGIN;
		}
	EmailMessageContent mailContent = new EmailMessageContent();
	mailContent.setContentType(emailType);

	List<EmailMessageContent> messages = commonCrudService.searchByExample(mailContent);
	if (UtilValidator.isNotEmpty(messages)) {
		mailContent = messages.get(0);
	}
	StringBuilder buffer = new StringBuilder();
	buffer.append("Hi").append(" ").append(commonCrudService.getFormattedName(userLogin.getPerson())).append(",")
			.append("\r\n").append("\r\n");
	if (UtilValidator.isNotEmpty(mailContent.getMessageBody()))
		buffer.append(mailContent.getMessageBody()).append("\r\n").append("\r\n");
	switch (emailType) {

	case USER_LOGIN:
		messageProperties.put("subject", "User Login details");
		break;
	case PATIENT_LOGIN:
		messageProperties.put("subject", "Patient Login details");
		PostalAddressFields pf = userLogin.getPerson().getContacts().getPostalAddress();
		buffer.append("Your Account Information \r\n");
		buffer.append("Last Name \t\t").append(userLogin.getPerson().getLastName()).append("\r\n");
		buffer.append("First Name\t\t").append(userLogin.getPerson().getFirstName()).append("\r\n");
		buffer.append("Gender\t\t").append(userLogin.getPerson().getGender().getDescription()).append("\r\n");
		buffer.append("DOB\t\t\t").append(UtilDateTime.format(userLogin.getPerson().getDateOfBirth())).append("\r\n");
		buffer.append("Address\t\t").append(pf.getAddress1());
		if (UtilValidator.isNotEmpty(pf.getAddress2())) buffer.append(", ").append(pf.getAddress2());
		if (UtilValidator.isNotEmpty(pf.getAddress2())) buffer.append(", ").append(pf.getCity());
		if (UtilValidator.isNotEmpty(pf.getStateProvinceGeo())) buffer.append(", ").append(pf.getStateProvinceGeo());
		if (UtilValidator.isNotEmpty(pf.getPostalCode())) buffer.append(", ").append(pf.getPostalCode());

		buffer.append("\r\n");
		buffer.append("Home Phone No\t").append(userLogin.getPerson().getContacts().getHomePhone()).append("\r\n");
		buffer.append("Mobile No \t\t").append(userLogin.getPerson().getContacts().getMobileNumber()).append("\r\n");
		break;
	case EMERGENCY_LOGIN:
		messageProperties.put("subject", "Emergency Access login details");
		pf = userLogin.getPatient().getContacts().getPostalAddress();
		buffer.append("Emergency Access Patient Information \r\n");
		buffer.append("Last Name \t\t").append(userLogin.getPatient().getLastName()).append("\r\n");
		buffer.append("First Name\t\t").append(userLogin.getPatient().getFirstName()).append("\r\n");
		buffer.append("Gender\t\t").append(userLogin.getPatient().getGender().getDescription()).append("\r\n");
		buffer.append("DOB\t\t\t").append(UtilDateTime.format(userLogin.getPatient().getDateOfBirth())).append("\r\n");
		buffer.append("Address\t\t").append(pf.getAddress1());
		if (UtilValidator.isNotEmpty(pf.getAddress2())) buffer.append(", ").append(pf.getAddress2());
		if (UtilValidator.isNotEmpty(pf.getAddress2())) buffer.append(", ").append(pf.getCity());
		if (UtilValidator.isNotEmpty(pf.getStateProvinceGeo())) buffer.append(", ").append(pf.getStateProvinceGeo());
		if (UtilValidator.isNotEmpty(pf.getPostalCode())) buffer.append(", ").append(pf.getPostalCode());

		buffer.append("\r\n");
		buffer.append("Home Phone No\t").append(userLogin.getPatient().getContacts().getHomePhone()).append("\r\n");
		buffer.append("Mobile No \t\t").append(userLogin.getPatient().getContacts().getMobileNumber());
		break;
	}

	buffer.append("\r\n");
	buffer.append("You can now login into the application using the following details").append("\r\n").append("\r\n");
	Properties properties = new Properties();
	InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(
			"mailContent.properties");
	try {
		properties.load(inputStream);
	} catch (IOException e) {
		e.printStackTrace();
	}
	String applicationUrl = (String) properties.get("applicationUrl");
	buffer.append("Url").append("\t").append(applicationUrl).append("\r\n").append("\r\n");
	buffer.append("UserName").append("\t").append(userLogin.getUsername()).append("\r\n");
	buffer.append("Password").append("\t").append(userLogin.getPassword()).append("\r\n");
	buffer.append("\n");
	buffer.append("Best Regards").append("\r\n");
	buffer.append("admin").append("\r\n");
	/*buffer.append(Infrastructure.getPractice().getAdminUserLogin().getPerson().getLastName()).append(" ").append(
			Infrastructure.getPractice().getAdminUserLogin().getPerson().getFirstName()).append("\r\n");
	buffer.append(Infrastructure.getPracticeName()).append("\r\n");
	buffer.append(Infrastructure.getPractice().getContacts().getOfficePhone());*/
	messageProperties.put("mailContent", buffer);
	messageProperties.put("toEmail", email);
	sendMail(messageProperties);
	}

	public void sendMail(Map<String, Object> messageProperties) {
	String subject = (String) messageProperties.get("subject");
	if (UtilValidator.isNotEmpty(subject)) this.templateMessage.setSubject(subject);
	SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
	msg.setTo((String) messageProperties.get("toEmail"));
	msg.setText(messageProperties.get("mailContent").toString());
	// msg.setText("UserName " + (String) messageProperties.get("username") + " Password "
	// + (String) messageProperties.get("password"));	
	try {
		javaMailSender.send(msg);
	} catch (MailException ex) {		
		//throw new RuntimeException("Could Not Send Mail. Might be connection problem.");
	}
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
	this.javaMailSender = javaMailSender;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	@Resource
	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}

	@Override
	public void sendPatientReminder(ReminderMailVo mailVo) {
		this.templateMessage.setSubject(mailVo.getSubject());
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(mailVo.getMailingPatient().getContacts().getEmail());
		StringBuffer buffer = new StringBuffer();
		buffer.append("Hi ").append(commonCrudService.getFormattedName(mailVo.getMailingPatient()))
		.append(",").append("\r\n").append("\r\n");
		buffer.append(mailVo.getMailContent()).append("\r\n").append("\r\n");
		buildMailFooter(buffer);
		msg.setText(buffer.toString());
		try {
			javaMailSender.send(msg);
		} catch (MailException e) {
			//throw new RuntimeException("Could Not Send Mail. Might be connection problem.");
		}
	}

	private void buildMailFooter(StringBuffer buffer) {
	buffer.append("Best Regards").append("\r\n");
	/*buffer.append(Infrastructure.getPractice().getAdminUserLogin().getPerson().getLastName()).append(" ").append(
			Infrastructure.getPractice().getAdminUserLogin().getPerson().getFirstName()).append("\r\n");
	buffer.append(Infrastructure.getPracticeName()).append("\r\n");
	buffer.append(Infrastructure.getPractice().getContacts().getOfficePhone());*/
	}
}
