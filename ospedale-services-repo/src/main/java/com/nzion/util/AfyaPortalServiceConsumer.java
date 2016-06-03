package com.nzion.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nzion.dto.CivilUserDto;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: USER
 * Date: 4/10/15
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class AfyaPortalServiceConsumer {

    static String PORTAL_URL = null;
    static {
        Properties properties = new Properties();
        try {
            String profileName = System.getProperty("profile.name") != null ? System.getProperty("profile.name") : "dev";
            properties.load(AfyaPortalServiceConsumer.class.getClassLoader().getResourceAsStream("application-"+profileName+".properties"));
            PORTAL_URL = (String)properties.get("PORTAL_SERVER_URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CivilUserDto fetchUserByAfyaId(String afyaId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/fetchPatientByAfyaId?afyaId={afyaId}", HttpMethod.GET, requestEntity, String.class, afyaId);
        String repsonseJson = responseEntity.getBody();
        Gson gson = new GsonBuilder().serializeNulls().setDateFormat("yyyy-MM-dd").create();
        CivilUserDto civilUserDto = gson.fromJson(repsonseJson, CivilUserDto.class);
        return  civilUserDto;
    }

    public static Map<String, Object> getUserLoginByUserName(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(mediaTypes);
        HttpEntity<String> requestEntity = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(PORTAL_URL + "/anon/getUserLoginByName?userName={userName}", HttpMethod.GET, requestEntity, String.class, userName);
        String json = responseEntity.getBody();
        Map<String, Object> result = new HashMap<String, Object>();
        Gson gson = new GsonBuilder().serializeNulls().create();
        result = (Map<String, Object>) gson.fromJson(json, result.getClass());
        return result;
    }
    /*public static HttpComponentsClientHttpRequestFactory getHttpComponentsClientHttpRequestFactory(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        HttpClient httpclient=getCloseableHttpClient();
        factory.setHttpClient(httpclient);
        return factory;
    }
    private static CloseableHttpClient getCloseableHttpClient() {
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

    }*/
}
