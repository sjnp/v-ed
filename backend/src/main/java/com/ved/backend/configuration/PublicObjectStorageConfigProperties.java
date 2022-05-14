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
@ConfigurationProperties(prefix = "public-object-storage")
public class PublicObjectStorageConfigProperties {

  private String namespace;
  private String bucketName;
  private Long expiryTimer;
  private List<String> viableImageExtensions;
  private String regionalObjectStorageUri;

}
