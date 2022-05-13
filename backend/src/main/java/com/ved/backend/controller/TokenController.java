package com.ved.backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import com.ved.backend.service.UserService;
import org.springframework.http.MediaType;
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
import java.util.stream.Collectors;

//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/token")
public class TokenController {
  private final UserService userService;

  @GetMapping("/refresh")
  public void refreshToken(@CookieValue(name = "refresh_token", defaultValue = "") String refresh_token,
                           HttpServletRequest request,
                           HttpServletResponse response)
      throws IOException {
//    String authorizationHeader = request.getHeader(AUTHORIZATION);
//    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
    if (!refresh_token.equals("")) {
      try {
//        String refresh_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("TODO: Need to put this somewhere safe".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refresh_token);
        String username = decodedJWT.getSubject();
        AppUser appUser = userService.getAppUser(username);
        String access_token = JWT.create()
            .withSubject(appUser.getUsername())
            // Access token will expire within 20 seconds.
            .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .withIssuer(request.getRequestURL().toString())
            .withClaim("roles", appUser.getAppRoles()
                .stream()
                .map(AppRole::getName)
                .collect(Collectors.toList()))
            .sign(algorithm);
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        Cookie refreshTokenCookie = new Cookie("refresh_token", refresh_token);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(false);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
        Map<String, Object> jsonMessage = new HashMap<>();
        jsonMessage.put("roles",
            appUser.getAppRoles()
                .stream()
                .map(AppRole::getName)
                .collect(Collectors.toList()));
        jsonMessage.put("access_token", access_token);
        jsonMessage.put("username", username);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), jsonMessage);
      } catch (Exception exception) {
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> error = new HashMap<>();
        error.put("error_message", exception.getMessage());
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
      }
    } else {
      throw new RuntimeException("Refresh token is missing");
    }
  }

  @GetMapping("/clear")
  public void clearToken(@CookieValue(name = "refresh_token", defaultValue = "") String refresh_token,
                           HttpServletResponse response)
//      throws IOException {
  {
    if (!refresh_token.equals("")) {
      Cookie emptyRefreshToken = new Cookie("refresh_token", "");
      emptyRefreshToken.setHttpOnly(true);
      emptyRefreshToken.setSecure(false);
      emptyRefreshToken.setPath("/");
      response.addCookie(emptyRefreshToken);

    }
    response.setStatus(NO_CONTENT.value());
  }
  public TokenController(final UserService userService) {
    this.userService = userService;
  }

}
