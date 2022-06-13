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
@ConfigurationProperties(prefix = "omise-key")
public class OmiseConfigProperties {

    private String publicKey;
    private String secretKey;
    private String recipientUrl;
    private String sourceUrl;
    private String chargeUrl;
}
