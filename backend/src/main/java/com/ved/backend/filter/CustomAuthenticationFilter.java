package com.ved.backend.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomAuthenticationFilter.class);
  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException {
    String username, password;
    try {
      Map<String, String> requestMap = new ObjectMapper().readValue(request.getInputStream(), Map.class);
      username = requestMap.get("username").toLowerCase();
      password = requestMap.get("password");
    } catch (IOException exception) {
      throw new AuthenticationServiceException(exception.getMessage(), exception);
    }
    log.info("Username is: {}", username);
    log.info("Password is: {}", password);
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authentication)
      throws IOException, ServletException {
    User user = (User) authentication.getPrincipal();
    Algorithm algorithm = Algorithm.HMAC256("TODO: Need to put this somewhere safe".getBytes());
    String access_token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .withIssuer(request.getRequestURL().toString())
        .withClaim("roles",
            user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .sign(algorithm);
    String refresh_token = JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .withIssuer(request.getRequestURL().toString())
        .sign(algorithm);
//    response.setHeader("access_token", access_token);
//    response.setHeader("refresh_token", refresh_token);
    Cookie refreshTokenCookie = new Cookie("refresh_token", refresh_token);
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setSecure(false);
    refreshTokenCookie.setPath("/");
    response.addCookie(refreshTokenCookie);
    Map<String, Object> jsonMessage = new HashMap<>();
    jsonMessage.put("roles",
        user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()));
    jsonMessage.put("access_token", access_token);
//    jsonMessage.put("refresh_token", refresh_token);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), jsonMessage);
  }

  public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }
}
