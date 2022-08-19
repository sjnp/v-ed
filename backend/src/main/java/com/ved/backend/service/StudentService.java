package com.ved.backend.service;

import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.StudentRepo;

import com.ved.backend.request.FinanceDataRequest;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class StudentService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final StudentRepo studentRepo;
  private final OmiseService omiseService;

  private static final Logger log = LoggerFactory.getLogger(StudentService.class);
  
  /* ************************************************************************************************** */

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

  public String activeInstructor(FinanceDataRequest finance, String username) {
    try {
      AppUser appUser = appUserRepo.findByUsername(username);
      log.info(username);
      List<String> appUserRoles = appUser.getAppRoles().stream()
              .map(AppRole::getName)
              .collect(Collectors.toList());
      if (appUserRoles.contains("INSTRUCTOR")) {
        log.info("Fail, user: {} already is an instructor", username);
        return "Fail, user: {} already is an instructor" + username;
      } else if (appUserRoles.contains("STUDENT")) {

        String recipientId = omiseService.createRecipient(finance); // Add a recipient with a bank account
        omiseService.verifyRecipient(recipientId); // Mark a recipient as verified

        // Add INSTRUCTOR role to User
        AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
        appUser.getAppRoles().add(instructorRole);
        Student student = appUser.getStudent();
        Instructor instructor = new Instructor();
        instructor.setRecipientId(recipientId);
        student.setInstructor(instructor);
        studentRepo.save(student);
        log.info("Success, user: {} is now an instructor", username);
     }

      return String.valueOf("OK");
    }
    catch (Exception error) {
      System.out.println(error.getMessage());
      return error.getMessage();
    }
  }

}