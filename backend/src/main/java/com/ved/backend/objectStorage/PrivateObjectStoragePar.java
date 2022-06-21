package com.ved.backend.objectStorage;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PrivateObjectStoragePar {
  private final ObjectStorageClientConfig objectStorageClientConfig;
  private final PreauthenticatedRequestConfig preauthenticatedRequestConfig;
  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  public String createPreauthenticatedRequest(String objectName,
                                               String preauthenticatedRequestName,
                                               CreatePreauthenticatedRequestDetails.AccessType accessType) {
    ObjectStorageClient client = objectStorageClientConfig.createClient();

    CreatePreauthenticatedRequestDetails parDetails = preauthenticatedRequestConfig
        .createParDetails(preauthenticatedRequestName,
            objectName,
            accessType,
            privateObjectStorageConfigProperties.getExpiryTimer());

    CreatePreauthenticatedRequestRequest parRequest = preauthenticatedRequestConfig
        .createParRequest(privateObjectStorageConfigProperties.getNamespace(),
            privateObjectStorageConfigProperties.getBucketName(),
            parDetails);

    CreatePreauthenticatedRequestResponse parResponse = preauthenticatedRequestConfig
        .createParResponse(parRequest, client);

    String regionStorageUri = privateObjectStorageConfigProperties.getRegionalObjectStorageUri();
    String accessUri = parResponse.getPreauthenticatedRequest().getAccessUri();

    return regionStorageUri + accessUri;
  }
}
