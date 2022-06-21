package com.ved.backend.service;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.objectStorage.ObjectStorageClientConfig;

import com.ved.backend.objectStorage.PrivateObjectStoragePar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType;
import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.*;

@AllArgsConstructor
@Service
@Transactional
public class PrivateObjectStorageService {

  private final PrivateObjectStoragePar privateObjectStoragePar;
  private final ObjectStorageClientConfig objectStorageClientConfig;
  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PrivateObjectStorageService.class);

  public String uploadFile(String fileName, String username) {
    String preauthenticatedRequestName = username + "_upload_" + fileName;
    return privateObjectStoragePar.createPreauthenticatedRequest(fileName, preauthenticatedRequestName, AnyObjectWrite) + fileName;
  }

  public String readFile(String fileName, String username) {
    String preauthenticatedRequestName = username + "_read_" + fileName;
    return privateObjectStoragePar.createPreauthenticatedRequest(fileName, preauthenticatedRequestName, ObjectRead);
  }

  public void deleteFile(String fileName) {
    ObjectStorageClient client = objectStorageClientConfig.createClient();

    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
        .builder()
        .namespaceName(privateObjectStorageConfigProperties.getNamespace())
        .bucketName(privateObjectStorageConfigProperties.getBucketName())
        .objectName(fileName)
        .build();

    client.deleteObject(deleteObjectRequest);
    client.close();
  }
}