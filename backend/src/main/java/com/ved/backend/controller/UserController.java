package com.ved.backend.controller;

import com.ved.backend.model.AppUser;
import com.ved.backend.request.ProfileRequest;
import com.ved.backend.response.ProfileResponse;
import com.ved.backend.service.UserService;

import lombok.AllArgsConstructor;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/new-student")
  public ResponseEntity<?> registerStudent(@RequestBody AppUser appUser) {
    userService.registerStudent(appUser);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/profile")
  public ResponseEntity<ProfileResponse> getProfile(Principal principal) {
    ProfileResponse response = userService.getProfile(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/display")
  public ResponseEntity<String> createUploadDisplayUrl(Principal principal) {
    String response = userService.createUploadDisplayUrl(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/display")
  public ResponseEntity<String> updateDisplay(Principal principal) {
    String response = userService.updateDisplay(principal.getName());
    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/profile")
  public ResponseEntity<?> updateProfile(@RequestBody ProfileRequest profileRequest, Principal principal) {
    userService.updateProfile(profileRequest, principal.getName());
    return ResponseEntity.noContent().build();
  }

}