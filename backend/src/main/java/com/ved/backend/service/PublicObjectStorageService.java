package com.ved.backend.service;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.ObjectWrite;

@AllArgsConstructor
@Service
@Transactional
public class PublicObjectStorageService {

  private final ObjectStorageClientService objectStorageClientService;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PublicObjectStorageService.class);

  private String createPreauthenticatedRequestToWrite(String objectName,
                                               String preauthenticatedRequestName) {
    ObjectStorageClient client = objectStorageClientService.createClient();
    CreatePreauthenticatedRequestDetails parDetails = objectStorageClientService
        .createParDetails(preauthenticatedRequestName,
            objectName,
            ObjectWrite,
            publicObjectStorageConfigProperties.getExpiryTimer());

    CreatePreauthenticatedRequestRequest parRequest = objectStorageClientService
        .createParRequest(publicObjectStorageConfigProperties.getNamespace(),
            publicObjectStorageConfigProperties.getBucketName(),
            parDetails);

    CreatePreauthenticatedRequestResponse parResponse = objectStorageClientService
        .createParResponse(parRequest, client);

    return publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        parResponse.getPreauthenticatedRequest().getAccessUri();
  }

  public String uploadFile(String objectName, String username) {
    log.info("Create PAR to upload file from user: {}", username);
    String preauthenticatedRequestName = username + "_upload_" + objectName;
    return createPreauthenticatedRequestToWrite(objectName, preauthenticatedRequestName);
  }
}
