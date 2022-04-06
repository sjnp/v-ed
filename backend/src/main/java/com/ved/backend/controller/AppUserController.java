package com.ved.backend.controller;

import com.ved.backend.exception.RegisterException;
import com.ved.backend.model.AppUser;
import com.ved.backend.service.AppUserService;

// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(path = "/api/users")
public class AppUserController {
  private final AppUserService appUserService;

  @PostMapping("/register-new-student")
  public ResponseEntity<?> registerStudent(@RequestBody AppUser appUser) throws RegisterException {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
    appUserService.registerStudent(appUser);
    return ResponseEntity.created(uri).body(null);
  }

  public AppUserController(final AppUserService appUserService) {
    this.appUserService = appUserService;
  }

}
