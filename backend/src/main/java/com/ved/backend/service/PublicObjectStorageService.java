package com.ved.backend.service;

import java.io.IOException;

public interface PublicObjectStorageService {
  String createParToUploadCoursePicture(Long courseId, String fileName, String username) throws IOException;
}
