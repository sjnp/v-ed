package com.ved.backend.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.utility.TokenUtil;
import com.ved.backend.utility.RefreshTokenCookieBuilder;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomAuthenticationFilter.class);
  private final AuthenticationManager authenticationManager;

  private final TokenUtil tokenUtil;

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
    log.info("Log in username : {}", username);
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
    String access_token = tokenUtil.generateAccessToken(user, request.getRequestURL().toString());
    String refresh_token = tokenUtil.generateRefreshToken(user, request.getRequestURL().toString());
    Cookie refreshTokenCookie = new RefreshTokenCookieBuilder(refresh_token).build();
    response.addCookie(refreshTokenCookie);
    Map<String, Object> jsonMessage = new HashMap<>();
    jsonMessage.put("roles", tokenUtil.getUserRoles(user));
    jsonMessage.put("access_token", access_token);
    response.setContentType(APPLICATION_JSON_VALUE);
    new ObjectMapper().writeValue(response.getOutputStream(), jsonMessage);
  }
}
