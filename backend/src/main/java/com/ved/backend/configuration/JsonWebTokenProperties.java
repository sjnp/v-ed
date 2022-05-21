package com.ved.backend.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "json-web-token")
public class JsonWebTokenProperties {
  private String secret;
  private Long accessTokenExpiresAt;
  private Long refreshTokenExpiresAt;
}
