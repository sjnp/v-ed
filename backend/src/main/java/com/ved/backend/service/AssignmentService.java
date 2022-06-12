package com.ved.backend.service;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Answer;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.response.AnswerResponse;
import com.ved.backend.response.AssignmentAnswerResponse;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class AssignmentService {

    private final AuthService authService;
    private final PrivateObjectStorageService privateObjectStorageService;

    private final AnswerRepo answerRepo;

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    public List<AssignmentAnswerResponse> getAssignmentAnswerNew(Long courseId, Integer chapterIndex, String username) {
        if (Objects.isNull(courseId)) {
            throw new BadRequestException("Course id is required");
        }

        if (Objects.isNull(chapterIndex)) {
            throw new BadRequestException("Chapter index is required");
        }

        StudentCourse studentCourse = authService.authorized(username, courseId);
        List<AssignmentAnswerResponse> assignmentAnswerResponses = studentCourse
            .getCourse()
            .getChapters()
            .get(chapterIndex)
            .getAssignments()
            .stream()
            .map(asm -> AssignmentAnswerResponse.builder().assignment(asm.get("detail")).build())
            .collect(Collectors.toList());
        List<Answer> answers = studentCourse
            .getAnswers()
            .stream()
            .filter(answer -> answer.getChapterIndex() == chapterIndex)
            .collect(Collectors.toList());
        
        for(int i = 0; i < assignmentAnswerResponses.size(); ++i) {
            for(Answer answer : answers) {
                if (answer.getNoIndex() == i) {
                    assignmentAnswerResponses.get(i).setAnswer(new AnswerResponse(answer));
                    break;
                }
            }
        }
        return assignmentAnswerResponses;
    }

    public String getUploadAnswerUrlNew(Long courseId, int chapterIndex, int noIndex, String clientFileName, String username) {
        StudentCourse studentCourse = authService.authorized(username, courseId);
        String fileName = "answer_sid_" + studentCourse.getStudent().getId() + "_cid_" + courseId + "_c" + chapterIndex + "_no." + noIndex + "_" + clientFileName;
        return privateObjectStorageService.uploadFile(fileName, username);
    }

    public void createAnswerNew(AnswerRequest answerRequest, String username) {
        if (Objects.isNull(answerRequest.getChapterIndex())) {
            throw new BadRequestException("Chapter index is required");
        }
      
        if (Objects.isNull(answerRequest.getNoIndex())) {
            throw new BadRequestException("No index is required");
        }
    
        if (Objects.isNull(answerRequest.getFileName())) {
            throw new BadRequestException("File name is required");
        }

        StudentCourse studentCourse = authService.authorized(username, answerRequest.getCourseId());
        Answer answer = Answer.builder()
            .chapterIndex(answerRequest.getChapterIndex())
            .noIndex(answerRequest.getNoIndex())
            .fileName(answerRequest.getFileName())
            .datetime(LocalDateTime.now())
            .studentCourse(studentCourse)
            .build();
        answerRepo.save(answer);
    }

}