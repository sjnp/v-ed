package com.ved.backend.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ved.backend.utility.TokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CustomAuthorizationFilter.class);

  private final TokenUtil tokenUtil;
  @Override
  protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
    if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh")) {
      filterChain.doFilter(request, response);
    } else {
      String authorizationHeader = request.getHeader(AUTHORIZATION);
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        try {
          String token = authorizationHeader.substring("Bearer ".length());
          DecodedJWT decodedJWT = tokenUtil.verify(token);
          String username = decodedJWT.getSubject();
          Collection<SimpleGrantedAuthority> authorities = tokenUtil
              .getAuthorities(decodedJWT.getClaim("roles"));
          tokenUtil.setAuthentication(username, authorities);
          filterChain.doFilter(request, response);
        } catch (Exception exception) {
          log.error("Invalid access_token");
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, exception.getMessage());
        }
      } else {
        filterChain.doFilter(request, response);
      }
    }
  }
}
