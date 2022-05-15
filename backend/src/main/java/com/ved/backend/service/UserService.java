package com.ved.backend.service;

// import com.ved.backend.exception.RegisterException;

import com.ved.backend.exception.ConflictException;
import com.ved.backend.exception.NotFoundException;
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
      log.error(usernameAlreadyExist);
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
        .orElseThrow(() -> new NotFoundException("User with username: " + username + " does not exist"));
  }
}
