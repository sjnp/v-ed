package com.ved.backend.objectStorage;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.ObjectWrite;

@AllArgsConstructor
@Component
public class PublicObjectStoragePar {
  private final ObjectStorageClientConfig objectStorageClientConfig;
  private final PreauthenticatedRequestConfig preauthenticatedRequestConfig;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  public String createPreauthenticatedRequest(String objectName,
                                              String preauthenticatedRequestName,
                                              CreatePreauthenticatedRequestDetails.AccessType accessType) {
    ObjectStorageClient client = objectStorageClientConfig.createClient();
    CreatePreauthenticatedRequestDetails parDetails = preauthenticatedRequestConfig
        .createParDetails(preauthenticatedRequestName,
            objectName,
            accessType,
            publicObjectStorageConfigProperties.getExpiryTimer());

    CreatePreauthenticatedRequestRequest parRequest = preauthenticatedRequestConfig
        .createParRequest(publicObjectStorageConfigProperties.getNamespace(),
            publicObjectStorageConfigProperties.getBucketName(),
            parDetails);

    CreatePreauthenticatedRequestResponse parResponse = preauthenticatedRequestConfig
        .createParResponse(parRequest, client);

    return publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        parResponse.getPreauthenticatedRequest().getAccessUri();
  }
}
