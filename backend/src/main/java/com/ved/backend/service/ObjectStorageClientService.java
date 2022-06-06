package com.ved.backend.service;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType;

import java.util.Date;

@AllArgsConstructor
@Service
public class ObjectStorageClientService {
  public ObjectStorageClient createClient() {
    try {
      ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
      AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
      return new ObjectStorageClient(provider);
    } catch (Exception e) {
      throw new RuntimeException("Invalid object storage config");
    }
  }

  public CreatePreauthenticatedRequestDetails createParDetails(String preauthenticatedRequestName,
                                                               String objectName,
                                                               AccessType accessType,
                                                               Long expiryTimer) {
    return CreatePreauthenticatedRequestDetails
        .builder()
        .name(preauthenticatedRequestName)
        .objectName(objectName)
        .accessType(accessType)
        .timeExpires(new Date(System.currentTimeMillis() + expiryTimer))
        .build();
  }

  public CreatePreauthenticatedRequestRequest createParRequest(String namespace,
                                                               String bucketName,
                                                               CreatePreauthenticatedRequestDetails createParDetails) {
    return CreatePreauthenticatedRequestRequest
        .builder()
        .namespaceName(namespace)
        .bucketName(bucketName)
        .createPreauthenticatedRequestDetails(createParDetails)
        .build();
  }

  public CreatePreauthenticatedRequestResponse createParResponse(CreatePreauthenticatedRequestRequest createParRequest,
                                                                 ObjectStorageClient client) {
    CreatePreauthenticatedRequestResponse response = client.createPreauthenticatedRequest(createParRequest);
    client.close();
    return response;
  }
}
