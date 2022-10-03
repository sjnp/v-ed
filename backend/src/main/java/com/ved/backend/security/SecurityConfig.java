package com.ved.backend.security;

import com.ved.backend.configuration.LoginProperties;
import com.ved.backend.filter.CustomAuthenticationFilter;
import com.ved.backend.filter.CustomAuthorizationFilter;
import com.ved.backend.utility.TokenUtil;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  private final TokenUtil tokenUtil;
  private final LoginProperties loginProperties;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter =
        new CustomAuthenticationFilter(authenticationManagerBean(), tokenUtil, loginProperties);
    customAuthenticationFilter.setFilterProcessesUrl("/api/login");
    http.cors();
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);

    http.authorizeRequests()
        .antMatchers(
            "/api/login",
            "/api/token/refresh/**",
            "/api/token/clear/**",
//            "/api/users/new-student",
            "/api/course-states/**",
            "/api/categories/**",
            "/api/overviews/category/**",
            "/api/overviews/courses/{\\d+}",
            "/api/overviews/courses/{\\d+}/card",
            "/api/overviews/video-example/**",
            "/api/students/reason-reports",
            "/api/search"
        )
        .permitAll();

    http.authorizeRequests()
        .antMatchers(GET, "/api/students/course-samples",
            "/api/students/courses",
            "/api/students/courses/{\\d+}",
            "/api/students/courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/video",
            "/api/students/courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/handout/{\\d+}",
            "/api/students/courses/{\\d+}/about",
            "/api/students/courses/{\\d+}/posts",
            "/api/students/courses/{\\d+}/posts/{\\d+}",
            "/api/students/courses/{\\d+}/reviews",
            "/api/students/courses/{\\d+}/reviews/{\\d+}",
            "/api/students/courses/{\\d+}/chapter/{\\d+}/answer",
            "/api/users/profile"
        )
        .hasAnyAuthority("STUDENT");

    http.authorizeRequests()
        .antMatchers(POST, "/api/students/course/free",
            "/api/students/course/buy",
            "/api/students/courses/answers/pre-authenticated-request",
            "/api/students/courses/{\\d+}/answer",
            "/api/students/courses/post",
            "/api/students/courses/{\\d+}/posts/{\\d+}/comment",
            "/api/students/courses/review",
            "/api/students/report",
            "/api/users/display",
            "/api/users/verify-password",
            "/api/students/active-instrustor"
        )
        .hasAnyAuthority("STUDENT");

    http.authorizeRequests()
        .antMatchers(PUT, "/api/students/instructor-feature/**",
            "/api/students/courses/reviews/{\\d+}",
            "/api/users/display",
            "/api/users/profile",
            "/api/users/change-password"
        )
        .hasAnyAuthority("STUDENT");

    http.authorizeRequests()
        .antMatchers(DELETE, "/api/students/courses/{\\d+}/answer/{\\d+}")
        .hasAnyAuthority("STUDENT");

    http.authorizeRequests()
        .antMatchers(GET, "/api/instructors/incomplete-courses/{\\d+}",
            "/api/instructors/incomplete-courses",
            "/api/instructors/pending-courses",
            "/api/instructors/approved-courses",
            "/api/instructors/rejected-courses",
            "/api/instructors/published-courses",
            "/api/instructors/incomplete-courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/video/**",
            "/api/instructors/courses/{\\d+}/reviews",
            "/api/instructors/courses/{\\d+}/posts",
            "/api/instructors/courses/{\\d+}/posts/{\\d+}"
        )
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(POST, "/api/instructors/course",
            "/api/instructors/incomplete-courses/{\\d+}/picture/pre-authenticated-request",
            "/api/instructors/incomplete-courses/{\\d+}/video/pre-authenticated-request",
            "/api/instructors/incomplete-courses/{\\d+}/handout/pre-authenticated-request",
            "/api/instructors/courses/{\\d+}/posts/{\\d+}/comment"
        )
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(PUT, "/api/instructors/incomplete-courses/{\\d+}/picture/**",
            "/api/instructors/incomplete-courses/{\\d+}/chapters",
            "/api/instructors/incomplete-courses/{\\d+}/state",
            "/api/instructors/approved-courses/{\\d+}",
            "/api/instructors/assignment/answer/comment",
            "/api/instructors/finance/updateAccount"
        )
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(DELETE, "/api/instructors/incomplete-courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/handout/**",

            "/api/instructors/incomplete-courses/{\\d+}/picture")
        .hasAnyAuthority("INSTRUCTOR");

    http.authorizeRequests()
        .antMatchers(GET, "/api/admins/pending-courses",
            "/api/admins/pending-courses/{\\d+}",
            "/api/admins/pending-courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/video",
            "/api/admins/pending-courses/{\\d+}/chapter/{\\d+}/section/{\\d+}/handout/**",
            "/api/admins/pending-reports/reviews",
            "/api/admins/pending-reports/posts",
            "/api/admins/pending-reports/comments",
            "/api/admins/report-reasons")
        .hasAnyAuthority("ADMIN");

    http.authorizeRequests()
        .antMatchers(PUT, "/api/admins/pending-courses/{\\d+}",
            "/api/admins/pending-reports/reviews/{\\d+}",
            "/api/admins/pending-reports/posts/{\\d+}",
            "/api/admins/pending-reports/comments/{\\d+}")
        .hasAnyAuthority("ADMIN");

    http.authorizeRequests().anyRequest().authenticated();

    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(tokenUtil), UsernamePasswordAuthenticationFilter.class);
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

}