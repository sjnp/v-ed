package com.ved.backend.service.privateObjectStorageService;

import com.oracle.bmc.objectstorage.ObjectStorageClient;
import com.oracle.bmc.objectstorage.requests.DeleteObjectRequest;
import com.ved.backend.configuration.PrivateObjectStorageConfigProperties;
import com.ved.backend.objectStorage.ObjectStorageClientConfig;
import com.ved.backend.objectStorage.PrivateObjectStoragePar;
import com.ved.backend.service.PrivateObjectStorageService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.AnyObjectWrite;
import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.ObjectRead;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PrivateObjectStorageServiceTest {

  @Mock
  private PrivateObjectStoragePar privateObjectStoragePar;

  @Mock
  private ObjectStorageClientConfig objectStorageClientConfig;

  @Mock
  private PrivateObjectStorageConfigProperties privateObjectStorageConfigProperties;

  private PrivateObjectStorageService underTest;

  @BeforeEach
  void setUp() {
    underTest = new PrivateObjectStorageService(privateObjectStoragePar,
        objectStorageClientConfig,
        privateObjectStorageConfigProperties);
  }

  @Test
  void givenFileNameAndUsername_whenUploadFile_thenReturnUrl() {
    //given
    String fileName = "test.jpg";
    String username = "user";
    String preauthenticatedRequestName = username + "_upload_" + fileName;
    String url = "test.com/";

    given(privateObjectStoragePar.createPreauthenticatedRequest(fileName, preauthenticatedRequestName, AnyObjectWrite))
        .willReturn(url);

    //when
    String expected = underTest.uploadFile(fileName, username);

    //then
    assertThat(expected).isEqualTo(url + fileName);
  }

  @Test
  void givenFileNameAndUsername_whenReadFile_thenReturnUrl() {
    //given
    String fileName = "test.jpg";
    String username = "user";
    String preauthenticatedRequestName = username + "_read_" + fileName;
    String url = "test.com/" + fileName;

    given(privateObjectStoragePar.createPreauthenticatedRequest(fileName, preauthenticatedRequestName, ObjectRead))
        .willReturn(url);

    //when
    String expected = underTest.readFile(fileName, username);

    //then
    assertThat(expected).isEqualTo(url);
  }


}
