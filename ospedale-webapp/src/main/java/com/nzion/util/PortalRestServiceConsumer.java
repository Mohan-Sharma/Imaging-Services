package com.nzion.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nzion.dto.PatientDto;
import com.nzion.dto.ReferralOrderDto;
import com.nzion.zkoss.dto.UserLoginDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Saikiran Chepuri on 12-May-16.
 */
public class PortalRestServiceConsumer {
    static String PORTAL_URL = null;
    static {
        Properties properties = new Properties();
        try {
            String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
            properties.load(RestServiceConsumer.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
            PORTAL_URL = (String)properties.get("PORTAL_SERVER_URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PatientDto fetchPatientByAfyaId(String afyaId){
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:7879/afya-portal/anon/fetchPatientByAfyaId?afyaId={afyaId}", HttpMethod.GET, requestEntity, String.class, afyaId);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        return  (PatientDto) gson.fromJson(json, PatientDto.class);
    }

    public static PatientDto fetchPatientByCivilId(String civilId){
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:7879/afya-portal/anon/fetchPatientByCivilId?civilId={civilId}", HttpMethod.GET, requestEntity, String.class, civilId);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        return  (PatientDto) gson.fromJson(json, PatientDto.class);
    }

    public static boolean checkIfTenantIsSubscribedToJoinInPackage(String tenantId){

        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:7879/afya-portal/anon/checkIfTenantIsSubscribedToJoinInPackage?tenantId={tenantId}", HttpMethod.GET, requestEntity, String.class,tenantId);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().create();
        Map<String, Object> result = gson.fromJson(json, Map.class);
        return getBooleanResultFromStringValue(result);
    }

    public static List<PatientDto> fetchPatientsByGivenCriteria(PatientDto patientDto){
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        String dateOfBirth = constructDate(patientDto.getDateOfBirth());
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:7879/afya-portal/anon/fetchPatientsByGivenCriteria?civilId={tenantId}&afyaId={afyaId}&firstName={firstName}&lastName={lastName}&mobileNumber={mobileNumber}&gender={gender}&dateOfBirth={dateOfBirth}", HttpMethod.GET, requestEntity, String.class, patientDto.getCivilId(), patientDto.getAfyaId(), patientDto.getFirstName(), patientDto.getLastName(), patientDto.getMobileNumber(), patientDto.getGender(), dateOfBirth);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().create();
        List<Map<String, Object>> resultList = gson.fromJson(json, List.class);
        return constructPatientDtoList(resultList);
    }

    private static String constructDate(Date dateOfBirth){
        if(UtilValidator.isNotEmpty(dateOfBirth)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.format(dateOfBirth);
        }
        return null;
    }

    private static List<PatientDto> constructPatientDtoList(List<Map<String, Object>> resultList){
        List<PatientDto> patientDtos = new ArrayList<>();
        PatientDto patientDto = null;
        for(Map<String, Object> resultMap : resultList){
            patientDto = new com.nzion.dto.PatientDto();
            patientDto.setPropertiesToPatientDtoFromMap(resultMap);
            patientDtos.add(patientDto);
        }
        return patientDtos;
    }

    private static HttpHeaders getHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static boolean getBooleanResultFromStringValue(Map<String, Object> result){
        if(UtilValidator.isEmpty(result))
            return Boolean.TRUE;
        return Boolean.valueOf(result.get("result").toString());
    }

    public static Map<String, Object> getUserLoginDetailsForUserName(String userName){
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL+"/anon/getUserLoginDetailsForUserName?userName={userName}", HttpMethod.GET, requestEntity, String.class, userName);
        String json = responseEntity.getBody();
        Map<String, Object> result = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (Map<String, Object>) gson.fromJson(json, result.getClass());
        return result;
    }

    public static String persistUserLogin(UserLoginDto userLoginDto) {
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        String userLoginJsonString = gson.toJson(userLoginDto);
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<String>(userLoginJsonString, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/persistUsersLoginFromTenant", HttpMethod.POST, requestEntity, String.class);
        return responseEntity.getBody();
    }

    public static String persistUserLoginFacilityAssociation(UserLoginDto userLoginDto) {
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders headers = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL+"/anon/persistUserLoginFacilityAssociation?userName={userName}&tenantId={tenantId}&facilityType={facilityType}", HttpMethod.POST, requestEntity, String.class, userLoginDto.getUsername(), userLoginDto.getTenantId(), "LABORATORY");
        return responseEntity.getBody();
    }
    public static List<ReferralOrderDto> fetchReferralOrder(String tenantId){
        RestTemplate restTemplate = new RestTemplate(RestServiceConsumer.getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/fetchReferralOrder?tenantId={tenantId}", HttpMethod.GET, requestEntity, String.class, tenantId);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().create();
        List<Map<String, Object>> resultList = gson.fromJson(json, List.class);
        return constructReferralOrderDtoList(resultList);
    }

    private static List<ReferralOrderDto> constructReferralOrderDtoList(List<Map<String, Object>> resultList){
        List<ReferralOrderDto> referralOrderDtos = new ArrayList<>();
        ReferralOrderDto referralOrderDto = null;
        for(Map<String, Object> resultMap : resultList){
            referralOrderDto = new com.nzion.dto.ReferralOrderDto();
            referralOrderDto.setPropertiesToReferralOrderDtoFromMap(resultMap);
            referralOrderDtos.add(referralOrderDto);
        }
        return referralOrderDtos;
    }
}
