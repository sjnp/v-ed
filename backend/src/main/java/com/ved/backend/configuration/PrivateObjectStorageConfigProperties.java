package com.ved.backend.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "private-object-storage")
public class PrivateObjectStorageConfigProperties {

  private String namespace;
  private String bucketName;
  private Long expiryTimer;
  private List<String> viableVideoExtensions;
  private String regionalObjectStorageUri;

}
