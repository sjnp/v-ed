package com.ved.backend.controller;

import com.auth0.jwt.interfaces.DecodedJWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppUser;
import com.ved.backend.service.TokenService;
import com.ved.backend.utility.TokenUtil;
import com.ved.backend.service.UserService;
import com.ved.backend.utility.RefreshTokenCookieBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@AllArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenController {
  private final UserService userService;
  private final TokenUtil tokenUtil;
  private final TokenService tokenService;

  @GetMapping("/refresh")
  public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", defaultValue = "") String refresh_token,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
    Map<String, Object> credentials = tokenService.refreshAccessToken(refresh_token,
        request.getRequestURL().toString());
    Cookie refreshTokenCookie = new RefreshTokenCookieBuilder(refresh_token).build();
    response.addCookie(refreshTokenCookie);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    return ResponseEntity.ok().body(credentials);
  }

  @GetMapping("/clear")
  public void clearToken(@CookieValue(name = "refresh_token", defaultValue = "") String refresh_token,
                         HttpServletResponse response) {
    if (!refresh_token.equals("")) {
      Cookie emptyRefreshTokenCookie = new RefreshTokenCookieBuilder("").build();
      response.addCookie(emptyRefreshTokenCookie);
    }
    response.setStatus(NO_CONTENT.value());
  }
}
