package com.ved.backend.service;

import java.io.IOException;

import com.ved.backend.request.AnswerRequest;

public interface PrivateObjectStorageService {
  
  String createParToUploadCourseVideo(Long courseId, Long chapterIndex, Long sectionIndex, String fileName, String username) throws IOException;

  public String getAccessURI(String fileName);

  public String getUploadFileURI(AnswerRequest answerRequest, String username);

}