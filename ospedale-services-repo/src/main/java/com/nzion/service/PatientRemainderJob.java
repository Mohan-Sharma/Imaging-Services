package com.nzion.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.nzion.domain.EmailMessageContent;
import com.nzion.domain.Patient;
import com.nzion.domain.PatientRemainder;
import com.nzion.domain.Person;
import com.nzion.domain.Practice;
import com.nzion.domain.Provider;
import com.nzion.enums.CommunicationPreference;
import com.nzion.enums.EmailContentType;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.common.MailService;
import com.nzion.util.Constants;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilValidator;

public class PatientRemainderJob extends QuartzJobBean {

	private MailService mailService;

	private SessionFactory sessionFactory;

	private static final Map<String, EmailContentType> mapping = new HashMap<String, EmailContentType>();
	static {
		//mapping.put(RecommendationSection.BMI_PLAN, EmailContentType.BMI_PLAN);
		//mapping.put(RecommendationSection.TOBACCO_PLAN, EmailContentType.TOBACCO_PLAN);
	}

	private void sendNotification(Session session, String followUpType, Patient patient, Provider provider, Date dueDate) {
	String providerName = provider.getFirstName() + provider.getLastName() + "," + provider.getQualifications();
	/*Practice practice = provider.getPractice();
	if (CommunicationPreference.EMAIL.equals(patient.getRemainderPreference())) {
		EmailMessageContent mailContent = (EmailMessageContent) session.createCriteria(EmailMessageContent.class).add(
				Restrictions.eq("contentType", mapping.get(followUpType))).uniqueResult();
		StringBuilder buffer = new StringBuilder();
		buffer.append("Hi").append(" ").append(patient.getLastName()).append(patient.getFirstName()).append(",").append("\r\n")
				.append("\r\n");
		buffer.append(mailContent != null ? mailContent.getMessageBody() : Constants.BLANK);
		buffer.append("\r\n");
		buffer.append("Please phone the office at "+" "+practice.getContacts().getOfficePhone() +" "+"to schedule your appointment with"+" " +providerName);
		buffer.append("\r\n");
		buffer.append("\r\n");
		buffer.append("Best Regards,\r\n");
		Person person = practice.getAdminUserLogin().getPerson();
		buffer.append(person.getLastName()).append(" ").append(person.getFirstName()).append("\r\n");
		buffer.append(practice.getPracticeName());
		Map<String, Object> messageProperties = new HashMap<String, Object>();
		messageProperties.put("mailContent", buffer);
		messageProperties.put("toEmail", patient.getContacts().getEmail());
		if("BMI management plan".equalsIgnoreCase(followUpType))
		messageProperties.put("subject", "BMI Follow Up Remainder Alert");
		else if("Tobacco Cessation".equalsIgnoreCase(followUpType))
			messageProperties.put("subject", "Tobacco  Follow Up Remainder Alert");
		else
			messageProperties.put("subject", "Follow Up Remainder");
		mailService.sendMail(messageProperties);
	} else
		if (CommunicationPreference.PATIENT_PORTAL.equals(patient.getRemainderPreference())) {
			PatientRemainder remainder = new PatientRemainder();
			remainder.setPatient(patient);
			remainder.setExpectedFollowUpDate(dueDate);
			remainder.setRemainderText("Your next " + followUpType + " Follow Up with " + providerName + " is due on "
					+ UtilDateTime.format(dueDate) + ".");
			Transaction transaction = session.beginTransaction();
			transaction.begin();
			session.save(remainder);
			transaction.commit();

		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	/*Session session = sessionFactory.openSession();
	List<Object[]> plans = session.createCriteria(SOAPPlan.class).add(Restrictions.eq("remainderSent", Boolean.FALSE))
			.add(Restrictions.gt("followUp.expectedDate", new Date())).setProjection(
					Projections.projectionList().add(Projections.property("followUp")).add(
							Projections.property("followUpFor")).add(Projections.property("patient")).add(
							Projections.property("provider")).add(Projections.property("id"))).list();
	if (UtilValidator.isEmpty(plans)){
		session.close();
		return;
	}	
	for (Object[] plan : plans) {
		Integer daysBefore = ((FollowUp) plan[0]).getAlertBefore();
		Date sendDate = UtilDateTime.getDayStart(UtilDateTime.addDaysToDate(((FollowUp) plan[0]).getExpectedDate(),
				daysBefore * -1));
		if (UtilDateTime.getIntervalInDays(sendDate, new Date()) < 1) {
			String followUpType = plan[1].toString();
			Patient patient = (Patient) plan[2];
			if (UtilValidator.isNotEmpty(patient.getContacts().getEmail()) && patient.getRemainderPreference()!=null) {
				Provider provider = (Provider) plan[3];
				sendNotification(session, followUpType, patient, provider, ((FollowUp) plan[0]).getExpectedDate());
				Long soapPlanId = (Long) plan[4];
				if (soapPlanId != null) {
					CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
					SOAPPlan soapPlan = commonCrudService.getById(SOAPPlan.class, soapPlanId);
					if (soapPlan != null) {
						soapPlan.setRemainderSent(Boolean.TRUE);
						soapPlan.setRemainderSentOn(new Date());
						commonCrudService.save(soapPlan);
					}
				}

			}
		}
	}*/
	//session.close();
	}

	public MailService getMailService() {
	return mailService;
	}

	@Resource
	public void setMailService(MailService mailService) {
	this.mailService = mailService;
	}

	public SessionFactory getSessionFactory() {
	return sessionFactory;
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
	}
}
