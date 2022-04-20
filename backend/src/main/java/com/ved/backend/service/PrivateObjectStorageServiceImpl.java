package com.ved.backend.service;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.ConfigFileAuthenticationDetailsProvider;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails;
import com.oracle.bmc.objectstorage.requests.CreatePreauthenticatedRequestRequest;
import com.oracle.bmc.objectstorage.responses.CreatePreauthenticatedRequestResponse;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.configuration.PublicObjectStorageConfigProperties;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.utility.FileExtensionStringHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

@Service
@Transactional
public class PrivateObjectStorageServiceImpl implements PrivateObjectStorageService {

  private final InstructorService instructorService;
  private final PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(InstructorServiceImpl.class);

  @Override
  public String createParToUploadCourseVideo(Long courseId,
                                             Long chapterIndex,
                                             Long sectionIndex,
                                             String fileName,
                                             String username) throws IOException {
    String videoExtension = FileExtensionStringHandler.getViableExtension(fileName,
        privateObjectStorageConfigProperties.getViableVideoExtensions());

    CourseRepo.CourseMaterials incompleteCourseMaterials = instructorService.getIncompleteCourse(courseId, username);

    ConfigFileReader.ConfigFile configFile = ConfigFileReader.parseDefault();
    AuthenticationDetailsProvider provider = new ConfigFileAuthenticationDetailsProvider(configFile);
    ObjectStorageClient client = new ObjectStorageClient(provider);
    String videoObjectName = "course_vid_" +
        incompleteCourseMaterials.getId() +
        "_c" + chapterIndex +
        "_s" + sectionIndex +
        "." + videoExtension;

    CreatePreauthenticatedRequestDetails createPreauthenticatedRequestDetails = CreatePreauthenticatedRequestDetails
        .builder()
        .name(username + "_upload_" + videoObjectName)
        .objectName(videoObjectName)
        .accessType(CreatePreauthenticatedRequestDetails.AccessType.AnyObjectReadWrite)
        .timeExpires(new Date(System.currentTimeMillis() + privateObjectStorageConfigProperties.getExpiryTimer()))
        .build();

    CreatePreauthenticatedRequestRequest createPreauthenticatedRequestRequest = CreatePreauthenticatedRequestRequest
        .builder()
        .namespaceName(privateObjectStorageConfigProperties.getNamespace())
        .bucketName(privateObjectStorageConfigProperties.getBucketName())
        .createPreauthenticatedRequestDetails(createPreauthenticatedRequestDetails)
        .build();

    CreatePreauthenticatedRequestResponse response = client
        .createPreauthenticatedRequest(createPreauthenticatedRequestRequest);


    return privateObjectStorageConfigProperties.getRegionalObjectStorageUri() +
        response.getPreauthenticatedRequest().getAccessUri() +
        videoObjectName;
  }

  public PrivateObjectStorageServiceImpl(InstructorService instructorService, PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties) {
    this.instructorService = instructorService;
    this.privateObjectStorageConfigProperties = privateObjectStorageConfigProperties;
  }

}
