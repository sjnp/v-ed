package com.ved.backend.controller;

import com.ved.backend.model.AppUser;
import com.ved.backend.service.AppUserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {
  private final AppUserService appUserService;

  @PostMapping("/new-student")
  public ResponseEntity<?> registerStudent(@RequestBody AppUser appUser) {
    appUserService.registerStudent(appUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
