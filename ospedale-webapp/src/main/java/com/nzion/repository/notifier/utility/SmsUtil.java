package com.nzion.repository.notifier.utility;

import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.Provider;
import com.nzion.domain.emr.lab.LabOrderRequest;
import com.nzion.util.Infrastructure;
import com.nzion.util.RestServiceConsumer;
import com.nzion.util.UtilValidator;
import org.hibernate.classic.Session;
import org.joda.time.LocalDate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import static com.nzion.repository.notifier.utility.NotificationTask.STATUS;

public class SmsUtil {

    static String SMS_SERVER_URL = null;
    static String SMS_SENDER = null;
    static String SMS_UID = null;
    static String SMS_PASSWORD = null;
    static String defaultLanguage = "L";
    static Locale locale = null;
    static {
        Properties properties = new Properties();
        try {
            String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
            properties.load(SmsUtil.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
            SMS_SERVER_URL = (String)properties.get("SMS_SERVER_URL");
            SMS_SENDER = (String)properties.get("SMS_SENDER");
            SMS_UID = (String)properties.get("SMS_UID");
            SMS_PASSWORD = (String)properties.get("SMS_PASSWORD");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getSenderNameForGivenTenant(String tenantId) {
        Map<String, Object> senderNameMap = RestServiceConsumer.getSMSSenderNameForGivenTenant(tenantId);
        if(UtilValidator.isNotEmpty(senderNameMap) && (Boolean) senderNameMap.get("sms_sender_name_verified")) {
            return senderNameMap.get("sms_sender_name").toString();
        }
        else {
            return SMS_SENDER;
        }
    }

    private static boolean checkIfSmsAvailableForTenant(String tenantId){
        return RestServiceConsumer.checkIfSmsAvailableForTenant(tenantId);
    }

    private static HttpHeaders getHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static String constructPhoneNumber(LabOrderRequest schedule) {
        //return schedule.getPatient() != null ? schedule.getPatient().getContacts() != null ? schedule.getPatient().getContacts().getIsdCode()+schedule.getPatient().getContacts().getMobileNumber() : null : null;
        return schedule.getPatient() != null ? schedule.getPatient().getContacts() != null ? schedule.getPatient().getContacts().getMobileNumber() : null : null;
    }

    private static String getClinicName(Map<String, Object> clinicDetails){
        String clinicName = "";
        if(!clinicDetails.isEmpty()) {
            clinicName = (String)clinicDetails.get("clinic_name");
        }
        return clinicName;
    }

    public static String constructName(Object object){
        StringBuilder stringBuilder = new StringBuilder();
        String name=null;
        if(object instanceof Patient) {
            Patient patient = (Patient)object;
            if(patient.getSalutation() != null)
                stringBuilder.append(patient.getSalutation()+". ");
            if(patient.getFirstName() != null)
                stringBuilder.append(patient.getFirstName()+" ");
            if(patient.getLastName() != null)
                stringBuilder.append(patient.getLastName());
            name = stringBuilder.toString();
        }

        if(object instanceof Provider) {
            Provider provider = (Provider)object;
            if(provider.getSalutation() != null)
                stringBuilder.append(provider.getSalutation()+". ");
            if(provider.getFirstName() != null)
                stringBuilder.append(provider.getFirstName()+" ");
            if(provider.getLastName() != null)
                stringBuilder.append(provider.getLastName());
            name =  stringBuilder.toString();
        }
        return name;
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

    private static String constructDate(Date date, Date scheduleDate){
        LocalDate localDate = new LocalDate(scheduleDate);
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthOfYear()-1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        Date furnishedDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMMM yyyy");
        return dateFormat.format(furnishedDate);
    }

    public static void sendStatusSms(LabOrderRequest schedule, Map<String, Object> clinicDetails){

        if((clinicDetails.get("forAdmin") == null) || ((Boolean)clinicDetails.get("forAdmin")).equals(false)){
            String preferredLanguage = schedule.getPatient().getLanguage() != null ? schedule.getPatient().getLanguage().getEnumCode() : null;
            if(preferredLanguage != null) {
                clinicDetails.put("languagePreference", preferredLanguage);
            }
        }

        locale = LocaleContextHolder.getLocale();
        if(clinicDetails.get("languagePreference") != null){
            locale = new Locale((String)clinicDetails.get("languagePreference"));
        }

        String language = defaultLanguage;

        clinicDetails.put("patientName", schedule.getPatient().getFirstName()+" "+schedule.getPatient().getLastName());

        /*String drSalutation = schedule.getPhlebotomist().getSalutation() != null ? schedule.getPhlebotomist().getSalutation() + ". " : "";
        String drMiddleName = schedule.getPhlebotomist().getMiddleName() != null ? " " + schedule.getPhlebotomist().getMiddleName() : "";
        String doctorNameWithSalutation = drSalutation + schedule.getPhlebotomist().getFirstName() + drMiddleName + " " + schedule.getPhlebotomist().getLastName();
        clinicDetails.put("doctorNameWithSalutation", doctorNameWithSalutation);
        clinicDetails.put("doctorName", schedule.getPhlebotomist().getFirstName() + " " + schedule.getPhlebotomist().getLastName());*/
        clinicDetails.put("date", constructDate(schedule.getStartTime(), schedule.getStartDate()));
        clinicDetails.put("time", constructTime(schedule.getStartTime(), schedule.getStartDate()));

        String patientMobNumber = schedule.getPatient().getContacts().getMobileNumber() != null ? "("+ schedule.getPatient().getContacts().getMobileNumber() +")" : "";
        clinicDetails.put("patientMobNumber", patientMobNumber);

        List<String> mobileList = new ArrayList<>();

        ResponseEntity<String> responseEntity = null;
        String message= null;
        try {

            String tenantId = clinicDetails.get("tenant_id") != null ? clinicDetails.get("tenant_id").toString() : null;
            if (tenantId == null){
                tenantId = getTenantId();
                if (tenantId == null){
                    return;
                }
            }

            String senderName = SMS_SENDER;
            boolean updateSmsCount = false;
            if (!schedule.isMobileOrPatinetPortal()) {
                senderName = getSenderNameForGivenTenant(tenantId);
                updateSmsCount = true;
            }

            RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = getHttpHeader();
            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
            String phoneNumber = "";
            if ((clinicDetails.get("forAdmin") != null) && ((Boolean)clinicDetails.get("forAdmin")).equals(true)){
                String isdCode = clinicDetails.get("isdCode") != null ? clinicDetails.get("isdCode").toString() : "965";
                String mobileNumber = clinicDetails.get("mobileNumber") != null ? clinicDetails.get("mobileNumber").toString() : null;
                //phoneNumber = mobileNumber != null ? isdCode+mobileNumber : null;
                phoneNumber = mobileNumber != null ? mobileNumber : null;
                if(phoneNumber == null || !phoneNumber.matches("\\d+"))
                    return;
            } else {
                if ((schedule.getPatient().getNotificationRequired() == null) || (schedule.getPatient().getNotificationRequired().equals("NO"))){
                    return;
                }
                //List<Map<String, Object>> mapList = RestServiceConsumer.getPatientContactsFromAfyaId(schedule.getPatient().getAfyaId());
                List<Map<String, Object>> mapList = RestServiceConsumer.getPatientContactsFromAfyaIdAndType(schedule.getPatient().getAfyaId(), null);
                Iterator iterator = mapList.iterator();
                while (iterator.hasNext()){
                    Map<String, Object> map = (Map)iterator.next();
                    if ((map.get("contactType").equals("MOBILE")) && (map.get("contactValue") != null)){
                        mobileList.add((String)map.get("contactValue"));
                    }
                }

               /* phoneNumber = constructPhoneNumber(schedule);
                if(phoneNumber == null || !phoneNumber.matches("\\d+"))
                    return;*/

            }

            if (locale.getDisplayLanguage().equals("Arabic")) {
                language = "A";
            }

            //message = constructOTPMessage(detail);
            //The above methos was commented out and replaced by the below method to make use of
            // ResourceBundle created by Raghu
            message = constructMessage(schedule, clinicDetails);
            if(message != null) {
                if((clinicDetails.get("forAdmin") == null) || ((Boolean)clinicDetails.get("forAdmin")).equals(false)){
                    Iterator iterator = mobileList.iterator();
                    while (iterator.hasNext()){
                        //String alternateMobile = "965" + iterator.next();
                        String alternateMobile = iterator.next().toString();
                        if((updateSmsCount) && (checkIfSmsAvailableForTenant(tenantId))) {
                            responseEntity = restTemplate.exchange(SMS_SERVER_URL, HttpMethod.POST, requestEntity, String.class, SMS_UID, SMS_PASSWORD, senderName, language, alternateMobile, message);
                            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                                RestServiceConsumer.updateSMSCountForGivenTenant(tenantId);
                            }
                        } else if (!updateSmsCount){
                            responseEntity = restTemplate.exchange(SMS_SERVER_URL, HttpMethod.POST, requestEntity, String.class, SMS_UID, SMS_PASSWORD, senderName, language, alternateMobile, message);
                        }
                    }
                } else {
                    if((updateSmsCount) && (checkIfSmsAvailableForTenant(tenantId))){
                        responseEntity = restTemplate.exchange(SMS_SERVER_URL, HttpMethod.POST, requestEntity, String.class, SMS_UID, SMS_PASSWORD, senderName, language, phoneNumber, message);
                        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                            RestServiceConsumer.updateSMSCountForGivenTenant(tenantId);
                        }
                    } else if (!updateSmsCount){
                        responseEntity = restTemplate.exchange(SMS_SERVER_URL, HttpMethod.POST, requestEntity, String.class, SMS_UID, SMS_PASSWORD, senderName, language, phoneNumber, message);
                    }
                }
            }
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String constructPhoneNumberForDoctor(LabOrderRequest schedule) {
        //return schedule.getPhlebotomist() != null ? schedule.getPhlebotomist().getContacts() != null ? schedule.getPhlebotomist().getContacts().getIsdCode()+schedule.getPhlebotomist().getContacts().getMobileNumber() : null : null;
        return schedule.getPhlebotomist() != null ? schedule.getPhlebotomist().getContacts() != null ? schedule.getPhlebotomist().getContacts().getMobileNumber() : null : null;
    }
    private static String constructMessage(LabOrderRequest schedule, Map<String, Object> detail) {
        MessageSource messageSource = Infrastructure.getSpringBean("messageSource");
        locale = LocaleContextHolder.getLocale();
        if(detail.get("languagePreference") != null){
            locale = new Locale((String)detail.get("languagePreference"));
        }

        StringBuilder builder = new StringBuilder();
        String message = null;

        if(detail.get("key").toString().equals("PLACE_LAB_ORDER_SMS_TO_LAB_ADMIN")){
            Object[] arguments = {detail.get("patientName"), detail.get("patientMobNumber"), detail.get("date"), detail.get("time")};

            message = messageSource.getMessage((String) detail.get("key"), null, locale);
            message = MessageFormat.format(message, arguments);
            message = message.substring(1, message.length() - 1);
        } else if (detail.get("key").toString().equals("PLACE_LAB_ORDER_SMS_TO_PATIENT")) {
            Object[] arguments = {detail.get("patientName"), detail.get("collectedAmount")};

            message = messageSource.getMessage((String) detail.get("key"), null, locale);
            message = MessageFormat.format(message, arguments);
            message = message.substring(1, message.length() - 1);
        } else if (detail.get("key").toString().equals("RESCHEDULE_LAB_ORDER_SMS_TO_LAB_ADMIN")) {
            Object[] arguments = {detail.get("patientName"), detail.get("patientMobNumber"), detail.get("oldDate"), detail.get("oldTime"), detail.get("date"), detail.get("time")};

            message = messageSource.getMessage((String) detail.get("key"), null, locale);
            message = MessageFormat.format(message, arguments);
            message = message.substring(1, message.length() - 1);
        } else if (detail.get("key").toString().equals("RESCHEDULE_LAB_ORDER_SMS_TO_PHLEBOTOMIST")) {
            Object[] arguments = {detail.get("doctorNameWithSalutation"), detail.get("patientName"), detail.get("patientMobNumber"), detail.get("oldDate"), detail.get("oldTime"), detail.get("date"), detail.get("time")};

            message = messageSource.getMessage((String) detail.get("key"), null, locale);
            message = MessageFormat.format(message, arguments);
            message = message.substring(1, message.length() - 1);
        } else if (detail.get("key").toString().equals("RESCHEDULE_LAB_ORDER_SMS_TO_PATIENT")) {
            Object[] arguments = {detail.get("patientName"), detail.get("labName"), detail.get("oldDate"), detail.get("oldTime"), detail.get("date"), detail.get("time")};

            message = messageSource.getMessage((String) detail.get("key"), null, locale);
            message = MessageFormat.format(message, arguments);
            message = message.substring(1, message.length() - 1);
        }
        builder.append(message);
        //    builder.append("Thanks for registering with Afyaarabia. OTP for completing your registration process is " + detail.get("token") + ".\nThanks\nCommunity Care\nAfyaarabia");
        return builder.toString();
    }

    private static String constructPhoneNumberForReferral(Map<String, Object> detail) {
        String  isdCode = "965"; // To Do: This should be sent in detail
        return detail.get("mobile") != null ? (isdCode + detail.get("mobile").toString()) : null;
    }

    public static String getTenantId(){
        Session session = null;
        //Boolean newSession = false;
        String tenantId = null;
        try {
             /*session = Infrastructure.getSessionFactory().getCurrentSession();
            if (session == null){*/
                session = Infrastructure.getSessionFactory().openSession();
                //newSession = true;
            //}
            List <Practice> results = session.createQuery("from Practice order by id DESC").list();
            Map<String, Object> details = null;
            if (results.size() > 0) {
                tenantId = ((Practice) results.get(0)).getTenantId();
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (session != null){
                session.close();
            }
        }
        return tenantId;
    }
}
