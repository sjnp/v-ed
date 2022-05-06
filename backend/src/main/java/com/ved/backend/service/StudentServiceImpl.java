package com.ved.backend.service;

import com.ved.backend.configuration.OmiseConfigProperties;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.storeClass.Finance;
import org.apache.tomcat.util.codec.binary.Base64;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final StudentRepo studentRepo;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);



  @Override
  public Student getStudent(String username) {
    log.info("Fetching student: {}", username);
    return appUserRepo.findByUsername(username).getStudent();
  }

  @Override
  public void changeRoleFromStudentIntoInstructor(Instructor instructor, String username) {
    log.info("Changing role to student: {}", username);
    AppUser appUser = appUserRepo.findByUsername(username);
    List<String> appUserRoles = appUser.getAppRoles().stream()
        .map(AppRole::getName)
        .collect(Collectors.toList());
    if (appUserRoles.contains("INSTRUCTOR")) {
      log.info("Fail, user: {} already is an instructor", username);
    } else if (appUserRoles.contains("STUDENT")) {
      AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
      appUser.getAppRoles().add(instructorRole);
      Student student = appUser.getStudent();
      student.setInstructor(instructor);
      studentRepo.save(student);
      log.info("Success, user: {} is now an instructor", username);
    }
  }

  @Override
  public String activeInstructor(Finance finance, String username) {
    final OmiseConfigProperties omiseKey;
    try {
      // Add a recipient with a bank account
      String plainCreds = "skey_test_5rh9y2joz9zw4ilibsf";
      byte[] plainCredsBytes = plainCreds.getBytes();
      byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
      String base64Creds = new String(base64CredsBytes);
      String url = "https://api.omise.co/recipients";
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      headers.add("Authorization", "Basic " + base64Creds);
      MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
      body.add("bank_account[bank_code]", finance.getBankBrand());
      body.add("bank_account[name]", finance.getBankAccountName());
      body.add("bank_account[number]", finance.getBankAccountNumber());
      body.add("name", finance.getRecipientName());
      body.add("type", "individual");
      body.add("tax_id", finance.getTaxId());
      HttpEntity<?> request = new HttpEntity<Object>(body, headers);
      ResponseEntity<?> responseJson = restTemplate.postForEntity(url, request, Map.class);
      HashMap<String ,Object> response = (HashMap<String, Object>) responseJson.getBody();
//      System.out.println(response.get("id"));

      // Mark a recipient as verified
      String verifyUrl = "https://api.omise.co/recipients/"+response.get("id")+"/verify";
      RestTemplate patchRestTemplate = new RestTemplate();
      patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
      HttpHeaders verifyHeaders = new HttpHeaders();
      verifyHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      verifyHeaders.add("Authorization", "Basic " + base64Creds);
      HttpEntity<?> verifyRequest = new HttpEntity<Object>(verifyHeaders);
      HashMap<String ,Object> verifyResponseJson = patchRestTemplate.patchForObject( verifyUrl , verifyRequest , HashMap.class );
//      System.out.println(verifyResponseJson);


      String.valueOf(response.get("id"));
//  -----------------------------------------------------------------------------------


      AppUser appUser = appUserRepo.findByUsername(username);
      List<String> appUserRoles = appUser.getAppRoles().stream()
              .map(AppRole::getName)
              .collect(Collectors.toList());
      if (appUserRoles.contains("INSTRUCTOR")) {
        log.info("Fail, user: {} already is an instructor", username);
      } else if (appUserRoles.contains("STUDENT")) {
        AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
        appUser.getAppRoles().add(instructorRole);
        Student student = appUser.getStudent();
        Instructor instructor = new Instructor();
        instructor.setRecipientId(String.valueOf(response.get("id")));
        student.setInstructor(instructor);
        studentRepo.save(student);
        log.info("Success, user: {} is now an instructor", username);


      }

//  --------------------------------------------------------------------------------------
      return String.valueOf(response.get("id"));
    }
    catch (Exception error) {
      System.out.println(error.getMessage());
      return error.getMessage();
    }
  }

  public StudentServiceImpl(AppUserRepo appUserRepo, AppRoleRepo appRoleRepo, StudentRepo studentRepo) {
    this.appUserRepo = appUserRepo;
    this.appRoleRepo = appRoleRepo;
    this.studentRepo = studentRepo;
  }
}
