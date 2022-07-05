package com.ved.backend.objectStorage;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@AllArgsConstructor
@Component
public class PreauthenticatedRequestConfig {

  public CreatePreauthenticatedRequestDetails createParDetails(String preauthenticatedRequestName,
                                                               String objectName,
                                                               CreatePreauthenticatedRequestDetails.AccessType accessType,
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