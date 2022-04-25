package com.ved.backend.service;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.utility.FileExtensionStringHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

@Service
@Transactional
public class PublicObjectStorageServiceImpl implements PublicObjectStorageService {

  private final InstructorService instructorService;
  private final PublicObjectStorageConfigProperties publicObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public String createParToUploadCoursePicture(Long courseId, String fileName, String username) throws IOException {

    String pictureExtension = FileExtensionStringHandler.getViableExtension(fileName,
        publicObjectStorageConfigProperties.getViableImageExtensions());

    CourseRepo.CourseMaterials incompleteCourseMaterials = instructorService.getIncompleteCourse(courseId, username);

    ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
    AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
    ObjectStorageClient client = new ObjectStorageClient(provider);
    String pictureObjectName = "course_pic_" + incompleteCourseMaterials.getId() + "." + pictureExtension;

    CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails = CreatePreauthenticatedRequestDetails
        .builder()
        .name(username + "_upload_course_picture_" + incompleteCourseMaterials.getId())
        .objectName(pictureObjectName)
        .accessType(CreatePreauthenticatedRequestDetails.AccessType.AnyObjectReadWrite)
        .timeExpires(new Date(System.currentTimeMillis() + publicObjectStorageConfigProperties.getExpiryTimer()))
        .build();

    CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest = CreatePreauthenticatedRequestRequest
        .builder()
        .namespaceName(publicObjectStorageConfigProperties.getNamespace())
        .bucketName(publicObjectStorageConfigProperties.getBucketName())
        .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails).build();

    CreatePreauthenticatedRequestResponse response = client
        .createPreauthenticatedRequest(createPreauthenticatedRequestRequest);
    client.close();

    return publicObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        response.getPreauthenticatedRequest().getAccessUri() +
        pictureObjectName;
  }

  public PublicObjectStorageServiceImpl(InstructorService instructorService, PublicObjectStorageConfigProperties publicObjectStorageConfigProperties) {
    this.instructorService = instructorService;
    this.publicObjectStorageConfigProperties = publicObjectStorageConfigProperties;
  }
}
