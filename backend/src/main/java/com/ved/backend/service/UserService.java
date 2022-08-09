package com.ved.backend.service;

import com.ved.backend.configuration.PublicObjectStorageConfigProperties;

import com.ved.backend.exception.UserNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.exception.baseException.ConflictException;
import com.ved.backend.exception.baseException.UnauthorizedException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;

import com.ved.backend.model.Instructor;
import com.ved.backend.model.Student;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.InstructorRepo;
import com.ved.backend.repo.StudentRepo;
import com.ved.backend.request.ProfileRequest;
import com.ved.backend.response.ProfileResponse;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@AllArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final StudentRepo studentRepo;
  private final InstructorRepo instructorRepo;
  private final PasswordEncoder passwordEncoder;
  private final PublicObjectStorageService publicObjectStorageService;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = appUserRepo.findAppUserByUsername(username)
        .orElseThrow(() -> {
          String userDoesNotExist = "User with username: " + username + " does not exist";
          log.error(userDoesNotExist);
          return new UsernameNotFoundException(userDoesNotExist);
        });
    log.info("User with username: {} is found", username);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    appUser.getAppRoles()
        .forEach(appRole -> authorities.add(new SimpleGrantedAuthority(appRole.getName())));
    return new User(appUser.getUsername(), appUser.getPassword(), authorities);
  }

  public void registerStudent(AppUser appUser) {
    if (appUserRepo.existsByUsername(appUser.getUsername())) {
      String usernameAlreadyExist = "User with username: " + appUser.getUsername() + " already exists";
      throw new ConflictException(usernameAlreadyExist);
    }

    log.info("Register new student: {} to the database", appUser.getUsername());
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
    AppRole studentRole = appRoleRepo.findByName("STUDENT");
    appUser.getAppRoles().add(studentRole);
    appUserRepo.save(appUser);
  }

  public AppUser getAppUser(String username) {
    log.info("Fetching user: {}", username);
    return appUserRepo
        .findAppUserByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }

  public Student getStudent(String username) {
    AppUser appUser = getAppUser(username);
    log.info("Fetching student from user: {}", username);
    return studentRepo.findByAppUser(appUser)
        .orElseThrow(() -> {
          String userIsNotStudent = "User with username: " + username + " is not a student";
          log.error(userIsNotStudent);
          return new UnauthorizedException(userIsNotStudent);
        });
  }

  public Instructor getInstructor(String username) {
    Student student = getStudent(username);
    log.info("Fetching instructor from user: {}", username);
    return instructorRepo.findByStudent(student)
        .orElseThrow(() -> {
          String userIsNotInstructor = "User with username: " + username + " is not an instructor";
          log.error(userIsNotInstructor);
          return new UnauthorizedException(userIsNotInstructor);
        });
  }

  //---------------------------------------------------------------------------------

  public ProfileResponse getProfile(String username) {
    Student student = this.getStudent(username);
    return new ProfileResponse(student);
  }

  private String getFileNameDisplay(Student student) {
    if (Objects.isNull(student.getProfilePicUri())) {
      return "display_" + student.getId() + "_0" + ".jpg";
    } else {
      String splitStr = "display_" + student.getId() + "_";
      String[] arrStr = student.getProfilePicUri().split(splitStr);
      String count = arrStr[arrStr.length - 1].replace(".jpg", "");
      String next = count.equals("0") ? "1" : "0";
      return "display_" + student.getId() + "_" + next + ".jpg";
    }
  }

  public String createUploadDisplayUrl(String username) {
    Student student = this.getStudent(username);
    String fileName = this.getFileNameDisplay(student);
    return publicObjectStorageService.uploadFile(fileName, username);
  }

  public String updateDisplay(String username) {
    Student student = this.getStudent(username);
    String fileName = this.getFileNameDisplay(student);
    String pictureUrl = new StringBuilder()
      .append(publicObjectStorageConfigProperties.getRegionalObjectStorageUri())
      .append("/n/")
      .append(publicObjectStorageConfigProperties.getNamespace())
      .append("/b/")
      .append(publicObjectStorageConfigProperties.getBucketName())
      .append("/o/")
      .append(fileName)
      .toString();
    student.setProfilePicUri(pictureUrl);
    studentRepo.save(student);
    return pictureUrl;
  }

  public void updateProfile(ProfileRequest profileRequest, String username) {
    Student student = this.getStudent(username);

    if (Objects.nonNull(profileRequest.getFirstname())) {
      student.setFirstName(profileRequest.getFirstname());
    }

    if (Objects.nonNull(profileRequest.getLastname())) {
      student.setLastName(profileRequest.getLastname());
    }

    if (Objects.nonNull(profileRequest.getBiography())) {
      String value = profileRequest.getBiography().trim().equals("") ? null : profileRequest.getBiography();
      student.setBiography(value);
    }

    if (Objects.nonNull(profileRequest.getOccupation())) {
      String value = profileRequest.getOccupation().trim().equals("") ? null : profileRequest.getOccupation();
      student.setOccupation(value);
    }

    studentRepo.save(student);
  }

  private boolean isPasswordMatch(String rawPassword, String encodedPassword) {
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }

  public void verifyPassword(String username, String password) {
    AppUser appUser = this.getAppUser(username);

    if (Objects.isNull(password)) {
      throw new BadRequestException("Password is required");
    }

    boolean isMatch = this.isPasswordMatch(password, appUser.getPassword());
    if (isMatch == false) {
      throw new BadRequestException("Password Invalid");
    }
  }

  public void changePassword(String username, String newPassword) {
    AppUser appUser = this.getAppUser(username);

    if (Objects.isNull(newPassword)) {
      throw new BadRequestException("Password is required");
    }

    boolean isMatch = this.isPasswordMatch(newPassword, appUser.getPassword());
    if (isMatch == true) {
      throw new BadRequestException("The old password can't be used");
    }

    String newPasswordEncode = passwordEncoder.encode(newPassword);
    appUser.setPassword(newPasswordEncode);
    appUserRepo.save(appUser);
  }
  
}