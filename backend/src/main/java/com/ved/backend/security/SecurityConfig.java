package com.ved.backend.security;

import com.ved.backend.filter.CustomAuthenticationFilter;
import com.ved.backend.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.cors();
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);

    http.authorizeRequests()
        .antMatchers("/api/login/**",
            "/api/token/refresh/**",
            "/api/token/clear/**",
            "/api/users/register-new-student/**",
            "/api/course-states/**",
            "/api/categories/**",
            "/api/overview/**",
            "/api/course/**",
            "/api/students/**",
            "/api/question-board/**",
            "/api/comment/**",
            "/api/review/**"
        )
        .permitAll();

    http.authorizeRequests()
        .antMatchers(PUT, "/api/students/instructor-feature/**")
        .hasAnyAuthority("STUDENT");

    http.authorizeRequests()
        .antMatchers(GET, "/api/instructors/incomplete-courses/**",
            "/api/instructors/pending-courses/**",
            "/api/instructors/approved-courses/**",
            "/api/instructors/rejected-courses/**")
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(POST, "/api/instructors/course/**",
            "/api/instructors/incomplete-courses/picture/pre-authenticated-request/**",
            "/api/instructors/incomplete-courses/video/pre-authenticated-request/**",
            "/api/instructors/incomplete-courses/handout/pre-authenticated-request/**")
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(PUT, "/api/instructors/incomplete-courses/picture/**",
            "/api/instructors/incomplete-courses/chapters/**",
            "/api/instructors/incomplete-courses/submission/**")
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(DELETE, "/api/instructors/incomplete-courses/picture/**",
            "/api/instructors/incomplete-courses/handout/**")
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(GET, "/api/admins/pending-courses/**",
            "/api/admins/pending-courses/video/**")
        .hasAnyAuthority("ADMIN");

    http.authorizeRequests()
        .antMatchers(PUT, "/api/admins/pending-courses/**")
        .hasAnyAuthority("ADMIN");

    http.authorizeRequests()
        .anyRequest()
        .authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }


  public SecurityConfig(final UserDetailsService userDetailsService, final BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userDetailsService = userDetailsService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

}
