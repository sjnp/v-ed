package com.ved.backend.configuration;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "omise-key")
public class OmiseConfigProperties {
    private String publicKey;
    private String secretKey;
    private String recipientUrl;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getRecipientUrl() {return recipientUrl;}

    public void setRecipientUrl(String recipientUrl) {this.recipientUrl = recipientUrl;}

    public String getBase64SecretKey() {
        String plainCreds = secretKey;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        return base64Creds;
    }

    public OmiseConfigProperties() {
    }

    public OmiseConfigProperties(String publicKey, String secretKey, String recipientUrl) {
        this.publicKey = publicKey;
        this.secretKey = secretKey;
        this.recipientUrl = recipientUrl;
    }
}
