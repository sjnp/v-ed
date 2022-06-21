package com.ved.backend.service.tokenService;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ved.backend.model.AppUser;
import com.ved.backend.service.TokenService;
import com.ved.backend.service.UserService;
import com.ved.backend.utility.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

  @Mock
  private TokenUtil tokenUtil;

  @Mock
  private UserService userService;

  private TokenService underTest;

  @BeforeEach
  void setUp() {
    underTest = new TokenService(tokenUtil, userService);
  }

  @Test
  void givenEmptyRefreshToken_whenRefreshAccessToken_thenThrow() {
    //given
    String refresh_token = "";
    String issuer = "";

    //when
    //then
    assertThatThrownBy(() -> underTest.refreshAccessToken(refresh_token, issuer))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Refresh token is missing");
  }

  @Test
  void givenRefreshToken_whenRefreshAccessToken_thenReturnCredentials() {
    //given
    String valid_refresh_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdC9hcGkvbG9naW4iLCJleHAiOjE2NTU2NTE2ODB9.RD8nnpelF7zi8SsZwXghRHMJcxot62-xALeo4cUp7oY";
    String issuer = "token.com";
    AppUser appUser = new AppUser();
    String access_token = "valid_access_token";
    DecodedJWT decodedJWT = JWT.decode(valid_refresh_token);
    List<String> roles = new ArrayList<>();

    given(tokenUtil.verify(valid_refresh_token)).willReturn(decodedJWT);
    given(userService.getAppUser(decodedJWT.getSubject())).willReturn(appUser);
    given(tokenUtil.generateAccessToken(appUser, issuer)).willReturn(access_token);
    given(tokenUtil.getAppUserRoles(appUser)).willReturn(roles);

    //when
    Map<String, Object> expected = underTest.refreshAccessToken(valid_refresh_token, issuer);

    //then
    assertThat(expected.get("access_token")).isEqualTo(access_token);
    assertThat(expected.get("username")).isEqualTo(decodedJWT.getSubject());
    assertThat(expected.get("roles")).isEqualTo(roles);
  }

  @Test
  void givenInvalidRefreshToken_whenRefreshAccessToken_thenThrow() {
    //given
    String invalid_refresh_token = "invalid";
    String issuer = "token.com";

    //when
    //then
    assertThatThrownBy(() -> underTest.refreshAccessToken(invalid_refresh_token, issuer))
        .isInstanceOf(ResponseStatusException.class);
  }
}
