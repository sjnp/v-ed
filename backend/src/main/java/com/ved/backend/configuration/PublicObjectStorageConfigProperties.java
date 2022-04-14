package com.ved.backend.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "public-object-storage")
public class PublicObjectStorageConfigProperties {
  private String namespace;
  private String bucketName;
  private Long expiryTimer;
  private List<String> viableImageExtensions;
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

  public List<String> getViableImageExtensions() {
    return viableImageExtensions;
  }

  public void setViableImageExtensions(List<String> viableImageExtensions) {
    this.viableImageExtensions = viableImageExtensions;
  }

  public String getRegionalObjectStorageUri() {
    return regionalObjectStorageUri;
  }

  public void setRegionalObjectStorageUri(String regionalObjectStorageUri) {
    this.regionalObjectStorageUri = regionalObjectStorageUri;
  }

  public PublicObjectStorageConfigProperties() {
  }

  public PublicObjectStorageConfigProperties(String namespace, String bucketName, Long expiryTimer, List<String> viableImageExtensions, String regionalObjectStorageUri) {
    this.namespace = namespace;
    this.bucketName = bucketName;
    this.expiryTimer = expiryTimer;
    this.viableImageExtensions = viableImageExtensions;
    this.regionalObjectStorageUri = regionalObjectStorageUri;
  }
}
