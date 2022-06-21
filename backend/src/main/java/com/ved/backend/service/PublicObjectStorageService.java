package com.ved.backend.service;

import com.ved.backend.objectStorage.PublicObjectStoragePar;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.ObjectWrite;

@AllArgsConstructor
@Service
@Transactional
public class PublicObjectStorageService {

  private final PublicObjectStoragePar publicObjectStoragePar;
  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PublicObjectStorageService.class);

  public String uploadFile(String objectName, String username) {
    log.info("Create PAR to upload file from user: {}", username);
    String preauthenticatedRequestName = username + "_upload_" + objectName;
    return publicObjectStoragePar.createPreauthenticatedRequest(objectName, preauthenticatedRequestName, ObjectWrite);
  }
}
