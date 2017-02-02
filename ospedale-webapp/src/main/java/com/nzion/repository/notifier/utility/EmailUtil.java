package com.nzion.repository.notifier.utility;

import com.nzion.domain.Patient;
import com.nzion.domain.Person;
import com.nzion.domain.Schedule;
import com.nzion.superbill.dto.CommunicationLogDto;
import com.nzion.util.Infrastructure;
import com.nzion.util.PortalRestServiceConsumer;
import com.nzion.util.RestServiceConsumer;
import com.nzion.util.UtilValidator;
import freemarker.template.Configuration;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.InputStreamSource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmailUtil {

    public static Session authenticateAndGetSession(final Properties prop){
        final Properties properties = new Properties();
        properties.put("mail.smtp.host", prop.getProperty("mail.smtp.relay.host").trim());
        properties.put("mail.smtp.port", prop.getProperty("mail.smtp.port").trim());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", prop.getProperty("mail.smtp.relay.host"));
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(prop.getProperty("mail.smtp.auth.user").trim(), prop.getProperty("mail.smtp.auth.password").trim());
            }
        };
        return Session.getInstance(properties, auth);
    }

    public static String sendMail(Session session, Properties properties, String emailId, String cc, String bcc, String bodyOfEmail, String subjectOfEmail) throws MessagingException {
        String stacktrace;
        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(properties.getProperty("mail.smtp.auth.user")));
        if(!UtilValidator.validateEmail(emailId))
            return StringUtils.EMPTY;
        InternetAddress[] toAddresses = { new InternetAddress(emailId) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        if(bcc != null) {
            InternetAddress[] bccs = {new InternetAddress(bcc)};
            msg.setRecipients(Message.RecipientType.BCC, bccs);
        }
        if(cc != null) {
            InternetAddress[] ccs = {new InternetAddress(cc)};
            msg.setRecipients(Message.RecipientType.CC, ccs);
        }
        msg.setSubject(subjectOfEmail);
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPartForBody= new MimeBodyPart();
        messageBodyPartForBody.setContent(bodyOfEmail, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPartForBody);
        msg.setContent(multipart);
        Transport.send(msg);
        stacktrace = "email sent";
        return stacktrace;
    }

    /*public static String sendNotificationEmailForFollowup(Patient patient, Provider provider) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.setBodyNotificationMail(patient, provider);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectForConsolidatedAttendanceRegisterAttachedMail());
        return response;
    }*/

    public static String sendAppointmentConfirmationMail(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        /*String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyAppointmentConfirmationEmail(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectForPatientAppointment());*/
        clinicDetails.put("baseUrl", PortalRestServiceConsumer.PORTAL_URL);
        String response = "";
        if (patient.getLanguage() != null) {
            clinicDetails.put("languagePreference", patient.getLanguage().getEnumCode());
        }
        clinicDetails.put("afyaId", patient.getAfyaId());
        clinicDetails.put("firstName", patient.getFirstName());
        clinicDetails.put("lastName", patient.getLastName());
        clinicDetails.put("subject", "Consult Visit Appointment Confirmation");
        clinicDetails.put("template", "CONSULT_VISIT_APPOINTMENT_CONFIRMATION_MAIL");
        clinicDetails.put("email", patient.getContacts().getEmail());
        clinicDetails.put("clinicName", clinicDetails.get("clinic_name"));
        clinicDetails.put("providerName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        String address = clinicDetails.get("address") != null ? clinicDetails.get("address").toString() : clinicDetails.get("city").toString();
        clinicDetails.put("clinicAddress", address);
        clinicDetails.put("clinicContact", clinicDetails.get("mobile"));
        clinicDetails.put("date", constructDate(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("time", constructTime(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("doctorName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        clinicDetails.put("patient", patient);
        try {
            response = sendNetworkContractStatusMail(clinicDetails);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /*public static String sendTeleConsultationAppointmentNonConfirmationMail(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyTeleConsultationAppointmentNonConfirmationEmail(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectOfTeleConsultationAppointmentReminderMail());
        return response;
    }

    public static String sendAppointmentRescheduledMail(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyAppointmentRescheduledEmail(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectOfAppointmentRescheduledMail());
        return response;
    }*/

    public static String sendAppointmentCancelledMail(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        /*String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyAppointmentCancelledMail(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectForPatientAppointmentCancelledMail());
        return response;*/
        clinicDetails.put("baseUrl", PortalRestServiceConsumer.PORTAL_URL);
        String response = "";
        if (patient.getLanguage() != null) {
            clinicDetails.put("languagePreference", patient.getLanguage().getEnumCode());
        }
        clinicDetails.put("afyaId", patient.getAfyaId());
        clinicDetails.put("firstName", patient.getFirstName());
        clinicDetails.put("lastName", patient.getLastName());
        clinicDetails.put("subject", "Consult Visit Appointment Cancelled");
        clinicDetails.put("template", "CONSULT_VISIT_APPOINTMENT_CANCELLED_MAIL");
        clinicDetails.put("email", patient.getContacts().getEmail());
        clinicDetails.put("clinicName", clinicDetails.get("clinic_name"));
        clinicDetails.put("providerName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        String address = clinicDetails.get("address") != null ? clinicDetails.get("address").toString() : clinicDetails.get("city").toString();
        clinicDetails.put("clinicAddress", address);
        clinicDetails.put("clinicContact", clinicDetails.get("mobile"));
        clinicDetails.put("date", constructDate(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("time", constructTime(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("doctorName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        clinicDetails.put("patient", patient);
        try {
            response = sendNetworkContractStatusMail(clinicDetails);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    //public static String sendAppointmentReminderMail(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        /*String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyOfAppointmentReminderMail(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,patient.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectOfAppointmentReminderMail());
        return response;*/
        /*String response = "";
        if (patient.getLanguage() != null) {
            clinicDetails.put("languagePreference", patient.getLanguage().getEnumCode());
        }
        clinicDetails.put("afyaId", patient.getAfyaId());
        clinicDetails.put("firstName", patient.getFirstName());
        clinicDetails.put("lastName", patient.getLastName());
        clinicDetails.put("subject", "Consult Visit Appointment Reminder");
        clinicDetails.put("template", "CONSULT_VISIT_APPOINTMENT_REMINDER_MAIL");
        clinicDetails.put("email", patient.getContacts().getEmail());
        clinicDetails.put("clinicName", clinicDetails.get("clinic_name"));
        clinicDetails.put("providerName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        String address = clinicDetails.get("address") != null ? clinicDetails.get("address").toString() : clinicDetails.get("city").toString();
        clinicDetails.put("clinicAddress", address);
        clinicDetails.put("clinicContact", clinicDetails.get("mobile"));
        clinicDetails.put("date", constructDate(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("time", constructTime(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("doctorName", schedule.getPerson().getFirstName()+" "+schedule.getPerson().getLastName());
        clinicDetails.put("patient", patient);
        try {
            response = sendNetworkContractStatusMail(clinicDetails);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public static String sendNotificationToProviderForHighPriorityPatient(Schedule schedule, Patient patient, Person provider, Map<String, Object> clinicDetails) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.setBodyNotificationMailToProviderForHighPriorityPatient(schedule, patient, provider, clinicDetails);
        response = sendMail(session, properties,provider.getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.setSubjectForHighPriorityAppointment());
        return  response;
    }*/
    public static String convertStackTraceToString(Throwable e){
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
   /* public static String sendRegistrationMailToDoctor(UserLogin user, String cc, String bcc) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyForDoctorRegistration(user);
        response = sendMail(session, properties,user.getPerson().getContacts().getEmail(), cc, bcc, bodyOfEmail, EmailContentConstructor.getSubjectOfRegistrationConfirmationMail());
        return response;
    }
    public static String sendRegistrationMailToPatient(PatientViewObject patientVO) throws IOException, MessagingException {
        String response;
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);
        String bodyOfEmail = EmailContentConstructor.getBodyForPatientRegistration(patientVO);
        response = sendMail(session, properties,patientVO.getPatient().getContacts().getEmail(),null,null, bodyOfEmail, EmailContentConstructor.getSubjectOfPatientRegistrationConfirmationMail());
        return response;
    }*/
    public static String sendNetworkContractStatusMail(Map<String, Object> details) throws Exception{
        MessageSource messageSource = Infrastructure.getSpringBean("messageSource");
        Configuration freemarkerConfiguration = Infrastructure.getSpringBean("freemarkerConfiguration");
        Locale locale = LocaleContextHolder.getLocale();
        details.put("baseUrl", PortalRestServiceConsumer.PORTAL_URL);
        String bcc = details.get("bcc") != null ? details.get("bcc").toString() : null;
        if((details.get("languagePreference") != null) && (details.get("languagePreference") != "")){
            locale = new Locale(details.get("languagePreference").toString());
        }

        String response = "success";
        Properties properties = new Properties();
        properties.load(EmailUtil.class.getClassLoader().getResourceAsStream("mailContent.properties"));
        Session session = authenticateAndGetSession(properties);

        details.put("subject", details.get("subject"));
        //List<String> emailList = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        messageSource.getMessage(details.get("template").toString(), null, locale);
        freemarkerConfiguration.setLocale(locale);

        String recipientType = details.get("receipentType") != null ? details.get("receipentType").toString() : "";
        String referenceID = details.get("referenceID") != null ? details.get("referenceID").toString() : "";
        String referenceType = details.get("referenceType") != null ? details.get("referenceType").toString() : "";

        if (details.get("patient") != null){
            Patient patient = (Patient)details.get("patient");
            if ((patient.getNotificationRequired() != null) && (patient.getNotificationRequired().equals("YES"))){
                mapList = RestServiceConsumer.getPatientContactsFromAfyaId(patient.getAfyaId());
                /*if (details.get("email") != null){
                    emailList.add(details.get("email").toString());
                }*/
                Iterator iterator = mapList.iterator();
                while (iterator.hasNext()){
                    Map<String, Object> map = (Map)iterator.next();
                    /*if (map.get("contactType").equals("EMAIL")){
                        emailList.add((String)map.get("contactValue"));
                    }*/
                    if ((!map.get("contactType").equals("EMAIL")) || ((map.get("contactType").equals("EMAIL")) && (map.get("contactValue") == null))){
                        iterator.remove();
                    }
                }
            } else {
                return "notification not required";
            }
        }

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate(messageSource.getMessage(details.get("template").toString(), null, locale), "UTF-8"), details);
        String messsage = UtilValidator.isNotEmpty(text) ? text.substring(text.indexOf("margin-bottom:9px;") + 20, text.indexOf("<p style", text.indexOf("margin-bottom:9px;"))) : null;
        if ((details.get("attachment") != null) && (((Boolean)details.get("attachment")).equals(true)) && (details.get("stream") != null)){
            if (mapList.size() > 0 ){
                Iterator iterator = mapList.iterator();
                while (iterator.hasNext()){
                    Map map = (Map)iterator.next();
                    String alternateEmail = map.get("contactValue") != null ? map.get("contactValue").toString() : "";
                    String patAccountNumber = map.get("accountNumber") != null ? String.valueOf(new Double(map.get("accountNumber").toString()).intValue()) : "";
                    try {
                        response = sendMailWithAttach(session, properties, alternateEmail, null, bcc, text, details.get("subject").toString(), (InputStreamSource)details.get("stream"));
                        persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, patAccountNumber, alternateEmail, "TO", null, null, null, null, response, details.get("template").toString());
                    } catch (Exception e){
                        e.printStackTrace();
                        persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, patAccountNumber, alternateEmail, "TO", null, null, null, null, e.getMessage(), details.get("template").toString());
                    }
                }
            } else {
                response = sendMailWithAttach(session, properties, details.get("email").toString(), null, bcc, text, details.get("subject").toString(), (InputStreamSource)details.get("stream"));
                persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, null, null, "TO", null, null, null, null, response, details.get("template").toString());
            }
        } else {
            if (mapList.size() > 0 ){
                Iterator iterator = mapList.iterator();
                while (iterator.hasNext()){
                    Map map = (Map)iterator.next();
                    String alternateEmail = map.get("contactValue") != null ? map.get("contactValue").toString() : "";
                    String patAccountNumber = map.get("accountNumber") != null ? String.valueOf(new Double(map.get("accountNumber").toString()).intValue()) : "";
                    try {
                        response = sendMail(session, properties, alternateEmail, null, bcc, text, details.get("subject").toString());
                        persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, patAccountNumber, alternateEmail, "TO", null, null, null, null, response, details.get("template").toString());
                    } catch (Exception e){
                        e.printStackTrace();
                        persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, patAccountNumber, alternateEmail, "TO", null, null, null, null, e.getMessage(), details.get("template").toString());
                    }
                }
            } else {
                try {
                    response = sendMail(session, properties, details.get("email").toString(), null, bcc, text, details.get("subject").toString());
                    persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, null, null, "TO", null, null, null, null, null, details.get("template").toString());
                } catch (Exception e){
                    e.printStackTrace();
                    persistCommunicationLog(recipientType, messsage, "EMAIL", "IMAGING", Infrastructure.getPractice().getTenantId(), referenceID, referenceType, null, null, "TO", null, null, null, null, e.getMessage(), details.get("template").toString());
                }
            }
        }

        return response;
    }
    public static String sendMailWithAttach(Session session, Properties properties, String emailId, String cc, String bcc, String bodyOfEmail, String subjectOfEmail, InputStreamSource inputStream) throws Exception {
        String stacktrace;
        Message msg=new MimeMessage(session);
        msg.setFrom(new InternetAddress(properties.getProperty("mail.smtp.auth.user")));
        if(!UtilValidator.validateEmail(emailId))
            return StringUtils.EMPTY;
        InternetAddress[] toAddresses = { new InternetAddress(emailId) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        if(bcc != null) {
            InternetAddress[] bccs = {new InternetAddress(bcc)};
            msg.setRecipients(Message.RecipientType.BCC, bccs);
        }
        if(cc != null) {
            InternetAddress[] ccs = {new InternetAddress(cc)};
            msg.setRecipients(Message.RecipientType.CC, ccs);
        }
        msg.setSubject(subjectOfEmail);
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPartForBody= new MimeBodyPart();
        /*ByteArrayDataSource ds = new ByteArrayDataSource(inputStream.getInputStream(), "application/pdf");
        messageBodyPartForBody.setDataHandler(new DataHandler(ds));
        messageBodyPartForBody.setFileName("Report.pdf");
*/
        messageBodyPartForBody.setContent(bodyOfEmail, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPartForBody);

        messageBodyPartForBody = new MimeBodyPart();
        ByteArrayDataSource ds = new ByteArrayDataSource(inputStream.getInputStream(), "application/pdf");
        messageBodyPartForBody.setDataHandler(new DataHandler(ds));
        messageBodyPartForBody.setFileName("Receipt.pdf");
        multipart.addBodyPart(messageBodyPartForBody);

        msg.setContent(multipart);
        Transport.send(msg);
        stacktrace = "email sent";
        return stacktrace;
    }
    private static String constructDate(Date date, Date scheduleDate){
        LocalDate localDate = new LocalDate(scheduleDate);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        return dateFormat.format(furnishedDate);
    }
    private static String constructTime(Date date, Date scheduleDate){
        LocalDate localDate = new LocalDate(scheduleDate);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear());
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
        return dateFormat.format(furnishedDate);
    }
    public static void persistCommunicationLog(String recipientType,String message, String messageChannel, String tenantType, String tenantId,
                                               String referenceID, String referenceType, String accountNumber, String sentToEmailId, String emailTarget,
                                               String sentToMobileNumber, String sentToDeviceId, String isDelivered, String request, String response, String eventName){
        CommunicationLogDto communicationLogDto = new CommunicationLogDto();
        communicationLogDto.setReceipentType(recipientType);
        communicationLogDto.setMessage(message);
        communicationLogDto.setMessageChannel(messageChannel);
        communicationLogDto.setTenantType(tenantType);
        communicationLogDto.setTenantId(tenantId);
        communicationLogDto.setReferenceId(referenceID);
        communicationLogDto.setReferenceType(referenceType);
        communicationLogDto.setAccountNumber(accountNumber);
        communicationLogDto.setSentToEmailId(sentToEmailId);
        communicationLogDto.setEmailTarget(emailTarget);
        communicationLogDto.setSentToMobileNumber(sentToMobileNumber);
        communicationLogDto.setSentToDeviceId(sentToDeviceId);
        communicationLogDto.setIsDelivered(isDelivered);
        communicationLogDto.setRequest(request);
        communicationLogDto.setResponse(response);
        communicationLogDto.setEventName(eventName);
        RestServiceConsumer.persistCommunicationLog(communicationLogDto);
    }
}
