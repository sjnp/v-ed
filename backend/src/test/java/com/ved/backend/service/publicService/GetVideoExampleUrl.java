package com.ved.backend.service.publicService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.ved.backend.configuration.CourseStateProperties;
import com.ved.backend.service.CategoryService;
import com.ved.backend.service.CourseService;
import com.ved.backend.service.CourseStateService;
import com.ved.backend.service.PrivateObjectStorageService;
import com.ved.backend.service.PublicService;
import com.ved.backend.util.MockData;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class GetVideoExampleUrl {

    @Mock
    private CategoryService categoryService;

    @Mock
    private CourseStateService courseStateService;
    
    @Mock
    private CourseService courseService;

    @Mock
    private CourseStateProperties courseStateProperties;

    @Mock
    private PrivateObjectStorageService privateObjectStorageService;

    private PublicService publicServiceTest;
    private MockData mockData;

    @BeforeEach
    public void setUp() {
        publicServiceTest = new PublicService(
            categoryService, 
            courseStateService, 
            courseService, 
            privateObjectStorageService, 
            courseStateProperties
        );
        mockData = new MockData();
    }

    @Test
    @Order(1)
    public void givenCourseId_whenObjectStorageSuccess_thenReturnAccessUri() {
        Long courseId = 99L;
        String username = "public_overview";
        String fileName = String.format("course_vid_%s_c0_s0.mp4", courseId);
        String accessUri = "https://objectstorage.ap-singapore-1.oraclecloud.com/p/_amVQE_Yq3MtqIPclDV00Dv52CIhJTTkr8Eepo24GPl_gluTlPFVqomDN-ynaXPt/n/axjskktj5xlm/b/bucket-20220310-1506/o/" + fileName;
        // given
        given(privateObjectStorageService.readFile(fileName, username)).willReturn(accessUri);
        // when
        String actualResult = publicServiceTest.getVideoExampleUrl(courseId);
        // then
        assertEquals(accessUri, actualResult);
    }

    @Test
    @Order(2)
    public void givenCourseId_whenConfigFileError_thenThrowRuntimeException() {
        Long courseId = 99L;
        String username = "public_overview";
        String fileName = String.format("course_vid_%s_c0_s0.mp4", courseId);
        // given
        given(privateObjectStorageService.readFile(fileName, username)).willThrow(RuntimeException.class);
        // when & then
        assertThatThrownBy(() -> publicServiceTest.getVideoExampleUrl(courseId))
            .isInstanceOf(RuntimeException.class);
    }
    
}