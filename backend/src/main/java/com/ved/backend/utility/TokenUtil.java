package com.ved.backend.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ved.backend.configuration.JsonWebTokenProperties;
import com.ved.backend.model.AppRole;
import com.ved.backend.model.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@AllArgsConstructor
@Component
public class TokenUtil {
  private JsonWebTokenProperties jwtProperties;

  public List<String> getUserRoles(User user) {
    return user.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  public List<String> getAppUserRoles(AppUser appUser) {
    return appUser.getAppRoles()
        .stream()
        .map(AppRole::getName)
        .collect(Collectors.toList());
  }

  public String generateAccessToken(User user, String issuer) {
    Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
    return JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiresAt()))
        .withIssuer(issuer)
        .withClaim("roles", getUserRoles(user))
        .sign(algorithm);
  }

  public String generateAccessToken(AppUser appUser, String issuer) {
    Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
    return JWT.create()
        .withSubject(appUser.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiresAt()))
        .withIssuer(issuer)
        .withClaim("roles", getAppUserRoles(appUser))
        .sign(algorithm);
  }

  public String generateRefreshToken(User user, String issuer) {
    Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
    return JWT.create()
        .withSubject(user.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiresAt()))
        .withIssuer(issuer)
        .sign(algorithm);
  }

  public DecodedJWT verify(String token) {
    Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecret().getBytes());
    JWTVerifier verifier = JWT.require(algorithm).build();
    return verifier.verify(token);
  }

  public Collection<SimpleGrantedAuthority> getAuthorities(Claim claim) {
    String[] roles = claim.asArray(String.class);
    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
    return authorities;
  }

  public void setAuthentication(String username, Collection<SimpleGrantedAuthority> authorities) {
    UsernamePasswordAuthenticationToken authenticationToken = new
        UsernamePasswordAuthenticationToken(username, null, authorities);
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
  }

}
