package com.ved.backend.service;

import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AppUserServiceImpl.class);
  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final PasswordEncoder passwordEncoder;



  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = appUserRepo.findByUsername(username);
    if (appUser == null) {
      String userNotFound = "User: " + username + " not found in the database";
      log.error(userNotFound);
      throw new UsernameNotFoundException(userNotFound);
    } else {
      log.info("User: {} found in the database", username);
      Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
      appUser.getAppRoles().forEach(appRole -> authorities.add(new SimpleGrantedAuthority(appRole.getName())));
      return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }
  }

  @Override
  public AppUser registerStudent(AppUser appUser)  {
    log.info("Register new student: {} to the database", appUser.getUsername());
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
    AppRole studentRole = appRoleRepo.findByName("STUDENT");
    appUser.getAppRoles().add(studentRole);
    return appUserRepo.save(appUser);
  }

  @Override
  public AppUser getAppUser(String username) {
    log.info("Fetching user: {}", username);
    return appUserRepo.findByUsername(username);
  }

  @Override
  public void changeRoleFromStudentIntoInstructor(String username) {
    log.info("Changing role to user: {}", username);
    AppUser appUser = appUserRepo.findByUsername(username);
    List<String> appUserRoles = appUser.getAppRoles().stream()
        .map(AppRole::getName)
        .collect(Collectors.toList());
    if (appUserRoles.contains("INSTRUCTOR")) {
      log.info("Fail, user: {} already is INSTRUCTOR", username);
    } else if (appUserRoles.contains("STUDENT")) {
      AppRole instructorRole = appRoleRepo.findByName("INSTRUCTOR");
      appUser.getAppRoles().add(instructorRole);
      appUserRepo.save(appUser);
      log.info("Success, user: {} is now INSTRUCTOR", username);
    }
  }

  public AppUserServiceImpl(final AppUserRepo appUserRepo, final AppRoleRepo appRoleRepo, final PasswordEncoder passwordEncoder) {
    this.appUserRepo = appUserRepo;
    this.appRoleRepo = appRoleRepo;
    this.passwordEncoder = passwordEncoder;
  }
}
