package com.ved.backend.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ved.backend.model.AppUser;
import com.ved.backend.utility.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class TokenService {
  private final TokenUtil tokenUtil;
  private final UserService userService;

  public Map<String, Object> refreshAccessToken(String refresh_token, String issuer) {
    if (refresh_token.equals("")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Refresh token is missing");
    }

    try {
      DecodedJWT decodedJWT = tokenUtil.verify(refresh_token);
      String username = decodedJWT.getSubject();
      AppUser appUser = userService.getAppUser(username);
      String access_token = tokenUtil.generateAccessToken(appUser, issuer);
      Map<String, Object> credentials = new HashMap<>();
      credentials.put("roles", tokenUtil.getAppUserRoles(appUser));
      credentials.put("access_token", access_token);
      credentials.put("username", username);
      return credentials;
    } catch (Exception exception) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage());
    }
  }
}
