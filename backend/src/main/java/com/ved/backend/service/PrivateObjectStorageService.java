package com.ved.backend.service;

import java.io.IOException;

import com.ved.backend.request.AnswerRequest;

public interface PrivateObjectStorageService {
  
  String createParToUploadCourseVideo(Long courseId, Long chapterIndex, Long sectionIndex, String fileName, String username) throws IOException;

  String createParToUploadCourseHandout(Long courseId, Long chapterIndex, Long sectionIndex, String fileName, String username) throws IOException;

  void deleteHandout(Long courseId, String objectName, String name) throws IOException;

  public String getAccessURI(String fileName);

  public String getUploadFileURI(AnswerRequest answerRequest, String username);

}