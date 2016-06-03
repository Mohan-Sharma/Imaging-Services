package com.nzion.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.nzion.domain.Patient;
import com.nzion.domain.PatientInsurance;
import com.nzion.dto.*;
import com.nzion.service.dto.ServiceMasterDto;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.lang.reflect.Type;

/**
 * Created by Nthdimenzion on 3/31/2015.
 */
public class RestServiceConsumer {
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

    public static String checkIfPatientExistInPortalAndCreateIfNotExist(Patient patient,String tenantId){
        PatientDto patientDto = new PatientDto();
        patientDto.setPropertiesToPatientDto(patient);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String patientJsonString = gson.toJson(patientDto);
        System.out.println(patientJsonString);
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.APPLICATION_JSON);
            headers.setAccept(mediaTypes);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<String>(patientJsonString, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/patient/retrieveAfyaId?tenantId={tenantId}&facilityType={facilityType}", HttpMethod.POST, requestEntity, String.class,tenantId,"LABORATORY");
            String afyaId = responseEntity.getBody();
            return afyaId;
            /*
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://localhost:7878/afya/saveOrUpdatePatient");
            StringEntity stringEntity = new StringEntity(patientJsonString);
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse response =  client.execute(httpPost);
            HttpEntity entity = response.getEntity();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CivilUserDto fetchUserByCivilId(String civilId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/fetchUserByCivilId?civilId={civilId}", HttpMethod.GET, requestEntity, String.class, civilId);
        String repsonseJson = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        CivilUserDto civilUserDto = gson.fromJson(repsonseJson, CivilUserDto.class);
        return  civilUserDto;
    }

    public static List<Map<String, Object>> getAllCities() {
        RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getAllCities", HttpMethod.GET, requestEntity, String.class);
        String json = responseEntity.getBody();
        List<Map<String, Object>> result = new ArrayList<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (List<Map<String, Object>>) gson.fromJson(json, result.getClass());
        return result;
    }

    public static Map<String, Object> getStateCountryBasedOnCity(String city) {
        RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getStateCountryBasedOnCity?city={city}", HttpMethod.GET, requestEntity, String.class, city);
        String json = responseEntity.getBody();
        Map<String, Object> result = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (Map<String, Object>) gson.fromJson(json, result.getClass());
        return result;
    }

    public static List<InsuranceCompanyDto> getAllInsuranceCompany(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/insuranceMaster/allInsuranceCompany", HttpMethod.GET, requestEntity, String.class);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type typeOfInsuranceCompanyDto = new TypeToken<List<InsuranceCompanyDto>>(){}.getType();
        List<InsuranceCompanyDto> insuranceCompanyDtos = gson.fromJson(json, typeOfInsuranceCompanyDto);
        return insuranceCompanyDtos;
    }

    public static List<InsurancePlanDto> getInsurancePlanByInsuranceCompany(String insuranceCompanyCode){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/insuranceMaster/allInsurancePlan?insuranceCompanyCode={insuranceCompanyCode}", HttpMethod.GET, requestEntity, String.class,insuranceCompanyCode);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type typeOfPatientInsurancePlanDto = new TypeToken<List<InsurancePlanDto>>(){}.getType();
        List<InsurancePlanDto> patientInsurancePlanDtos = gson.fromJson(json, typeOfPatientInsurancePlanDto);
        return patientInsurancePlanDtos;
    }

    public static void insertPatientInsuranceInPortal(Patient patient){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Map<String,Object> map = new HashMap<>();
        List<PatientInsuranceDto> patientInsuranceDtos = new ArrayList<PatientInsuranceDto>();
        for(PatientInsurance pi : patient.getPatientInsurances()){
            PatientInsuranceDto patientInsuranceDto = new PatientInsuranceDto(pi.getInsuranceProviderId(),pi.getPatientPlanId(),pi.getInsuranceName(), pi.getPlanName(),pi.getPatientPlanId(),pi.getPatientRegistrationNo());
            patientInsuranceDtos.add(patientInsuranceDto);
        }
        map.put("afyaId",patient.getAfyaId());
        map.put("insuredPatientDtos",patientInsuranceDtos);
        String patientJsonString = gson.toJson(map);
        System.out.println(patientJsonString);
        try{
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.APPLICATION_JSON);
            headers.setAccept(mediaTypes);
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<String>(patientJsonString, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/patient/retrieveAfyaId", HttpMethod.POST, requestEntity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ServiceMasterDto> getAllServiceMaster(int serviceMainGroupId){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getAllServiceMaster?serviceMainGroupId={serviceMainGroupId}", HttpMethod.GET, requestEntity, String.class,serviceMainGroupId);
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type typeOfServiceMaster = new TypeToken<List<ServiceMasterDto>>(){}.getType();
        List<ServiceMasterDto> doList = gson.fromJson(json, typeOfServiceMaster);
        return doList;

    }

    public static Map<String, Object> getNationalityByNationalityCode(String nationalityCode) {
        RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getNationalityByNationalityCode?nationalityCode={nationalityCode}", HttpMethod.GET, requestEntity, String.class, nationalityCode);
        String json = responseEntity.getBody();
        Map<String, Object> result = new HashMap<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (Map<String, Object>) gson.fromJson(json, result.getClass());
        return result;
    }

    public static List<Map<String, Object>> getAllNationality(){
        RestTemplate restTemplate = new RestTemplate(getHttpComponentsClientHttpRequestFactory());
        HttpHeaders httpHeaders = getHttpHeader();
        HttpEntity<String> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getAllNationality", HttpMethod.GET, requestEntity, String.class);
        String json = responseEntity.getBody();
        List<Map<String, Object>> result = new ArrayList<>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (List<Map<String, Object>>) gson.fromJson(json, result.getClass());
        return result;
    }

    public static HttpComponentsClientHttpRequestFactory getHttpComponentsClientHttpRequestFactory(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpclient=getCloseableHttpClient();
        factory.setHttpClient(httpclient);
        return factory;
    }

    private static CloseableHttpClient getCloseableHttpClient(){
        TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] certificate, String authType) {
                return true;
            }
        };
        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    private static HttpHeaders getHttpHeader(){
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        headers.setAccept(mediaTypes);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public static Map<String, Object> getPatientDetailsByAfyaId(String afyaId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        //ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:7879/afya-portal/anon/fetchUserByCivilId?civilId={civilId}", HttpMethod.GET, requestEntity, String.class, civilId);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getPatientDetailsByAfyaId?afyaId={afyaId}", HttpMethod.GET, requestEntity, String.class, afyaId);
        String repsonseJson = responseEntity.getBody();
        String json = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().create();
        return (Map<String, Object>) gson.fromJson(json, Map.class);
    }

}
