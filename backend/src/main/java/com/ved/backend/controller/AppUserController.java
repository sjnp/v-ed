package com.ved.backend.controller;

import com.ved.backend.model.AppUser;
import com.ved.backend.service.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(path = "/api/users")
public class AppUserController {
  private final AppUserService appUserService;

  @PostMapping("/register-new-student")
  public ResponseEntity<AppUser> registerStudent(@RequestBody AppUser appUser) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
    return ResponseEntity.created(uri).body(appUserService.registerStudent(appUser));
  }

  @PutMapping(path = "/u/change-to-instructor")
  public ResponseEntity<?> changeStudentIntoInstructor(Principal principal) {
    appUserService.changeRoleFromStudentIntoInstructor(principal.getName());
    return ResponseEntity.ok().build();
  }

  public AppUserController(final AppUserService appUserService) {
    this.appUserService = appUserService;
  }

}
