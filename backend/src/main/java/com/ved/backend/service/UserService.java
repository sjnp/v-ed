package com.ved.backend.service;

// import com.ved.backend.exception.RegisterException;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;

import com.ved.backend.repo.AppRoleRepo;
import com.ved.backend.repo.AppUserRepo;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
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

@AllArgsConstructor
@Service
@Transactional
public class UserService implements UserDetailsService {

  private final AppUserRepo appUserRepo;
  private final AppRoleRepo appRoleRepo;
  private final PasswordEncoder passwordEncoder;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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

  public void registerStudent(AppUser appUser) {
    log.info("Register new student: {} to the database", appUser.getUsername());
    appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
    AppRole studentRole = appRoleRepo.findByName("STUDENT");
    appUser.getAppRoles().add(studentRole);
    appUserRepo.save(appUser);
  }

  public AppUser getAppUser(String username) {
    log.info("Fetching user: {}", username);
    return appUserRepo.findByUsername(username);
  }
}
