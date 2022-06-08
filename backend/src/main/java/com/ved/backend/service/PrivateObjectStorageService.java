package com.ved.backend.service;

import java.io.IOException;
import java.util.Date;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.utility.FileExtensionStringHandler;
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.request.AnswerRequest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType;
import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.*;

@AllArgsConstructor
@Service
@Transactional
public class PrivateObjectStorageService {
  private final ObjectStorageClientService objectStorageClientService;

  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PrivateObjectStorageService.class);

  private String createPreauthenticatedRequest(String objectName,
                                               String preauthenticatedRequestName,
                                               AccessType accessType) {
    ObjectStorageClient client = objectStorageClientService.createClient();

    CreatePreauthenticatedRequestDetails parDetails = objectStorageClientService
        .createParDetails(preauthenticatedRequestName,
            objectName,
            accessType,
            privateObjectStorageConfigProperties.getExpiryTimer());

    CreatePreauthenticatedRequestRequest parRequest = objectStorageClientService
        .createParRequest(privateObjectStorageConfigProperties.getNamespace(),
            privateObjectStorageConfigProperties.getBucketName(),
            parDetails);

    CreatePreauthenticatedRequestResponse parResponse = objectStorageClientService
        .createParResponse(parRequest, client);

    String regionStorageUri = privateObjectStorageConfigProperties.getRegionalObjectStorageUri();
    String accessUri = parResponse.getPreauthenticatedRequest().getAccessUri();

    return regionStorageUri + accessUri;
  }

  public String uploadFile(String fileName, String username) {
    String preauthenticatedRequestName = username + "_upload_" + fileName;
    return createPreauthenticatedRequest(fileName, preauthenticatedRequestName, AnyObjectWrite) + fileName;
  }

  public String readFile(String fileName, String username) {
    String preauthenticatedRequestName = username + "_read_" + fileName;
    return createPreauthenticatedRequest(fileName, preauthenticatedRequestName, ObjectRead);
  }

  public void deleteFile(String fileName) {
    ObjectStorageClient client = objectStorageClientService.createClient();

    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
        .builder()
        .namespaceName(privateObjectStorageConfigProperties.getNamespace())
        .bucketName(privateObjectStorageConfigProperties.getBucketName())
        .objectName(fileName)
        .build();

    client.deleteObject(deleteObjectRequest);
    client.close();
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}