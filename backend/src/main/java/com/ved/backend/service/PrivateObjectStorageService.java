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
import com.ved.backend.model.AppUser;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.utility.FileExtensionStringHandler;
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

  private final InstructorService instructorService;
  private final ObjectStorageClientService objectStorageClientService;

  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;
  private final AppUserRepo appUserRepo;

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
    return createPreauthenticatedRequest(fileName, preauthenticatedRequestName, ObjectWrite);
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
  // TODO: this
  public String createParToReadFile(String fileUri, String username) throws IOException {
    ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
    AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
    ObjectStorageClient client = new ObjectStorageClient(provider);
    CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails =
        CreatePreauthenticatedRequestDetails.builder()
            .name(username + "_read_" + fileUri)
            .objectName(fileUri)
            .accessType(ObjectRead)
            .timeExpires(new Date(System.currentTimeMillis() + privateObjectStorageConfigProperties.getExpiryTimer()))
            .build();

    CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest =
        CreatePreauthenticatedRequestRequest.builder()
            .namespaceName(privateObjectStorageConfigProperties.getNamespace())
            .bucketName(privateObjectStorageConfigProperties.getBucketName())
            .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails)
            .build();

    CreatePreauthenticatedRequestResponse response = client
        .createPreauthenticatedRequest(createPreauthenticatedRequestRequest);
    client.close();

    return privateObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        response.getPreauthenticatedRequest().getAccessUri();
  }

  public String getAccessURI(String fileName) {
    try {

      ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
      AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
      ObjectStorageClient client = new ObjectStorageClient(provider);

      CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails = CreatePreauthenticatedRequestDetails
          .builder()
          .name(fileName)
          .objectName(fileName)
          .accessType(ObjectRead)
          .timeExpires(new Date(System.currentTimeMillis() + privateObjectStorageConfigProperties.getExpiryTimer()))
          .build();

      CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest = CreatePreauthenticatedRequestRequest
          .builder()
          .namespaceName(privateObjectStorageConfigProperties.getNamespace())
          .bucketName(privateObjectStorageConfigProperties.getBucketName())
          .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails)
          .build();

      CreatePreauthenticatedRequestResponse response;
      response = client.createPreauthenticatedRequest(createPreauthenticatedRequestRequest);
      client.close();

      String regionObjectStorage = privateObjectStorageConfigProperties.getRegionalObjectStorageUri();
      String accessURI = response.getPreauthenticatedRequest().getAccessUri();

      log.info("Preauthenticate uri example video successful");
      return regionObjectStorage + accessURI;

    } catch (Exception e) {
      log.error("Preauthenticate uri example video fail, response error {}", e.getMessage());
      throw new MyException("pre.authentication.fail", HttpStatus.UNAUTHORIZED);
    }
  }

  public String getAccessVideoURI(String courseId, String chapterNo, String sectionNo) {
    String fileName = "course_vid_" + courseId + "_c" + chapterNo + "_s" + sectionNo + ".mp4";
    return this.getAccessURI(fileName);
  }

  public String getUploadFileURI(AnswerRequest answerRequest, String username) {
    try {

      ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
      AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
      ObjectStorageClient client = new ObjectStorageClient(provider);

      AppUser appUser = appUserRepo.findByUsername(username);
      String studentId = "_sid" + appUser.getStudent().getId();
      String courseId = "_cid" + answerRequest.getCourseId();
      String chapterNo = "_c" + answerRequest.getChapterNo();
      String no = "_no" + answerRequest.getNo();
      String fileType = "." + answerRequest.getFileName().substring(answerRequest.getFileName().indexOf(".") + 1);
      String fileName = "answer" + studentId + courseId + chapterNo + no + fileType;

      CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails = CreatePreauthenticatedRequestDetails.builder().name(username + "_upload_" + fileName).objectName(fileName).accessType(AnyObjectReadWrite).timeExpires(new Date(System.currentTimeMillis() + privateObjectStorageConfigProperties.getExpiryTimer())).build();

      CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest = CreatePreauthenticatedRequestRequest.builder().namespaceName(privateObjectStorageConfigProperties.getNamespace()).bucketName(privateObjectStorageConfigProperties.getBucketName()).createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails).build();

      CreatePreauthenticatedRequestResponse response = client.createPreauthenticatedRequest(createPreauthenticatedRequestRequest);
      client.close();

      String regionObjectStorage = privateObjectStorageConfigProperties.getRegionalObjectStorageUri();
      String accessURI = response.getPreauthenticatedRequest().getAccessUri();

      return regionObjectStorage + accessURI + fileName;

    } catch (Exception e) {
      log.error("Preauthenticate uri example video fail, response error {}", e.getMessage());
      throw new MyException("pre.authentication.fail", HttpStatus.UNAUTHORIZED);
    }
  }

}