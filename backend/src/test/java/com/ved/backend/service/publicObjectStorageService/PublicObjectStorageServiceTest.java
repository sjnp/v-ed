package com.ved.backend.service.publicObjectStorageService;

import com.ved.backend.objectStorage.PublicObjectStoragePar;
import com.ved.backend.service.PublicObjectStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.oracle.bmc.objectstorage.model.CreatePreauthenticatedRequestDetails.AccessType.ObjectWrite;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PublicObjectStorageServiceTest {
  @Mock
  private PublicObjectStoragePar publicObjectStoragePar;

  private PublicObjectStorageService underTest;

  @BeforeEach
  void setUp() {
    underTest = new PublicObjectStorageService(publicObjectStoragePar);
  }

  @Test
  void givenObjectAndUsername_whenUploadFile_thenReturnUrl() {
    //given
    String objectName = "test.jpg";
    String username = "user";
    String preauthenticatedRequestName = username + "_upload_" + objectName;
    String url = "test.com/" + objectName;

    given(publicObjectStoragePar.createPreauthenticatedRequest(objectName, preauthenticatedRequestName, ObjectWrite))
        .willReturn(url);

    //when
    String expected = underTest.uploadFile(objectName, username);

    //then
    assertThat(expected).isEqualTo(url);
  }
}
