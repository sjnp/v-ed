package com.ved.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "private-object-storage")
public class PrivateObjectStorageConfigProperties {
  private String namespace;
  private String bucketName;
  private Long expiryTimer;
  private List<String> viableVideoExtensions;
  private String regionalObjectStorageUri;

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getBucketName() {
    return bucketName;
  }

  public void setBucketName(String bucketName) {
    this.bucketName = bucketName;
  }

  public Long getExpiryTimer() {
    return expiryTimer;
  }

  public void setExpiryTimer(Long expiryTimer) {
    this.expiryTimer = expiryTimer;
  }

  public List<String> getViableVideoExtensions() {
    return viableVideoExtensions;
  }

  public void setViableVideoExtensions(List<String> viableVideoExtensions) {
    this.viableVideoExtensions = viableVideoExtensions;
  }

  public String getRegionalObjectStorageUri() {
    return regionalObjectStorageUri;
  }

  public void setRegionalObjectStorageUri(String regionalObjectStorageUri) {
    this.regionalObjectStorageUri = regionalObjectStorageUri;
  }

  public PrivateObjectStorageConfigProperties() {
  }

  public PrivateObjectStorageConfigProperties(String namespace, String bucketName, Long expiryTimer, List<String> viableVideoExtensions, String regionalObjectStorageUri) {
    this.namespace = namespace;
    this.bucketName = bucketName;
    this.expiryTimer = expiryTimer;
    this.viableVideoExtensions = viableVideoExtensions;
    this.regionalObjectStorageUri = regionalObjectStorageUri;
  }
}
