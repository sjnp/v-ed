package com.ved.backend.service;

import com.ved.backend.configuration.OmiseConfigProperties;
import com.ved.backend.request.ChargeDataRequest;
import com.ved.backend.request.FinanceDataRequest;
import com.ved.backend.response.ChargeResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Service
@Transactional
public class OmiseService {
    private final OmiseConfigProperties omiseKey;

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OmiseService.class);

    private String getBase64SecretKey() {
        String plainCreds = omiseKey.getSecretKey();
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        return base64Creds;
    }

    public String createRecipient(FinanceDataRequest finance) {
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getRecipientUrl();
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("bank_account[bank_code]", finance.getBankBrand());
            body.add("bank_account[name]", finance.getBankAccountName());
            body.add("bank_account[number]", finance.getBankAccountNumber());
            body.add("name", finance.getRecipientName());
            body.add("type", finance.getType());
            HttpEntity<?> request = new HttpEntity<Object>(body, headers);
            ResponseEntity<?> response = restTemplate.postForEntity(url, request, Map.class);
            HashMap<String, Object> responseHash = (HashMap<String, Object>) response.getBody();
            return responseHash.get("id").toString();
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public String verifyRecipient(String recipientId){
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getRecipientUrl() + '/' + recipientId + "/verify";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            HttpEntity<?> request = new HttpEntity<Object>(headers);
            HashMap<String, Object> response = restTemplate.patchForObject(url, request, HashMap.class);
            log.info(response.toString());
            return response.toString();
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }


    public String getRecipientData(String recipientId){
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getRecipientUrl() + '/' + recipientId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            HttpEntity<?> request = new HttpEntity<Object>(headers);
            HashMap<String, Object> response = restTemplate.patchForObject(url, request, HashMap.class);
            JSONObject responseJson = new JSONObject(response);
            String bankAccountData = responseJson.get("bank_account").toString();
            log.info(bankAccountData);
            return bankAccountData;
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public String updateRecipient(FinanceDataRequest finance, String recipientId){
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getRecipientUrl() + '/' + recipientId;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("bank_account[bank_code]", finance.getBankBrand());
            body.add("bank_account[name]", finance.getBankAccountName());
            body.add("bank_account[number]", finance.getBankAccountNumber());
            HttpEntity<?> request = new HttpEntity<Object>(body, headers);
            HashMap<String, Object> response = restTemplate.patchForObject(url, request, HashMap.class);
            log.info(response.toString());
            return response.toString();
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public String createPaymentSource (ChargeDataRequest chargeData) {
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getSourceUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("amount", chargeData.getAmount());
            body.add("currency", chargeData.getCurrency());
            body.add("type", chargeData.getType());
            HttpEntity<?> request = new HttpEntity<Object>(body, headers);
            HashMap<String, Object> response = restTemplate.postForObject(url, request, HashMap.class);
            JSONObject responseJson = new JSONObject(response);
            String sourceId = responseJson.get("id").toString();
            log.info(sourceId);
            return sourceId;
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public ChargeResponse createPaymentCharge (ChargeDataRequest chargeData , String source) {
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getChargeUrl();
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            body.add("amount", chargeData.getAmount());
            body.add("currency", chargeData.getCurrency());
            body.add("type", chargeData.getType());
            body.add("return_uri", chargeData.getReturnUri());
            body.add("source", source );
            HttpEntity<?> request = new HttpEntity<Object>(body, headers);
            HashMap<String, Object> response = restTemplate.postForObject(url, request, HashMap.class);
            JSONObject responseJson = new JSONObject(response);
            String authorizeUri = responseJson.get("authorize_uri").toString();
            String chargeId = responseJson.get("id").toString();
            ChargeResponse response = new ChargeResponse(chargeId,authorizeUri);
            response.setId("sdsdsds");
            ChargeResponse chargeResponse = ChargeResponse.builder()
                    .id(chargeId)
                    .authorizeUri(authorizeUri)
                    .build();
            log.info(authorizeUri);
            return chargeResponse;
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public String markChargeAsPaid(String chargeId) {
        try {
            String base64Creds = getBase64SecretKey();
            String url = omiseKey.getChargeUrl() + "/" + chargeId + "/mark_as_paid";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "Basic " + base64Creds);
            MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
            HttpEntity<?> request = new HttpEntity<Object>(body, headers);
            HashMap<String, Object> response = restTemplate.postForObject(url, request, HashMap.class);
            JSONObject responseJson = new JSONObject(response);
            String authorizeUri = responseJson.get("authorize_uri").toString();
            log.info(authorizeUri);
            return authorizeUri;
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }
}
