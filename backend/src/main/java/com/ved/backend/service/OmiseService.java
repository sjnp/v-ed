package com.ved.backend.service;

import com.ved.backend.configuration.OmiseConfigProperties;
import com.ved.backend.storeClass.Finance;
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

    public String createRecipient(Finance finance) {
        try {
            String base64Creds = omiseKey.getBase64SecretKey();
            String recipientUrl = omiseKey.getRecipientUrl();
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
            ResponseEntity<?> responseJson = restTemplate.postForEntity(recipientUrl, request, Map.class);
            HashMap<String, Object> response = (HashMap<String, Object>) responseJson.getBody();
            return response.get("id").toString();
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }

    public String verifyRecipient(String recipientId){
        try {
            String base64Creds = omiseKey.getBase64SecretKey();
            String verifyUrl = omiseKey.getRecipientUrl() + '/' + recipientId + "/verify";
            RestTemplate patchRestTemplate = new RestTemplate();
            patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpHeaders verifyHeaders = new HttpHeaders();
            verifyHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            verifyHeaders.add("Authorization", "Basic " + base64Creds);
            HttpEntity<?> verifyRequest = new HttpEntity<Object>(verifyHeaders);
            HashMap<String, Object> verifyResponseJson = patchRestTemplate.patchForObject(verifyUrl, verifyRequest, HashMap.class);
            log.info(verifyResponseJson.toString());
            return verifyResponseJson.toString();
        }
        catch (Exception error) {
            System.out.println(error.getMessage());
            return error.getMessage();
        }
    }
}
