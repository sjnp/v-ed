package com.ved.backend.objectStorage;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ObjectStorageClientConfig {

  public ObjectStorageClient createClient() {
    try {
      ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
      AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
      return new ObjectStorageClient(provider);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}
