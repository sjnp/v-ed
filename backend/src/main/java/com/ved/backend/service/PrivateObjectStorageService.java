package com.ved.backend.service;

import java.io.IOException;

import com.ved.backend.request.AnswerRequest;

public interface PrivateObjectStorageService {
  
  public String createParToUploadCourseVideo(Long courseId, Long chapterIndex, Long sectionIndex, String fileName, String username) throws IOException;

  public String createParToUploadCourseHandout(Long courseId, Long chapterIndex, Long sectionIndex, String fileName, String username) throws IOException;

  public String createParToReadFile(String fileUri, String username) throws IOException;

  public void deleteHandout(Long courseId, String objectName, String name) throws IOException;

  public String getAccessURI(String fileName);

  public String getAccessVideoURI(String courseId, String chapterNo, String sectionNo);

  public String getUploadFileURI(AnswerRequest answerRequest, String username);

}