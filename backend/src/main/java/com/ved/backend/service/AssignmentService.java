package com.ved.backend.service;

import com.ved.backend.exception.AnswerNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Answer;
import com.ved.backend.model.Course;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.repo.AnswerRepo.AnswerInstructor;
import com.ved.backend.repo.AnswerRepo.AnswerNoti;
import com.ved.backend.request.AnswerRequest;
import com.ved.backend.request.CommentAnswerRequest;
import com.ved.backend.response.AnswerResponse;
import com.ved.backend.response.AssignmentAnswerResponse;
import com.ved.backend.response.AssignmentChapterResponse;
import com.ved.backend.response.AssignmentCourseResponse;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public List<AssignmentAnswerResponse> getAssignmentAnswer(Long courseId, Integer chapterIndex, String username) {
        log.info("Get assignment answer course id: {} by username: {}", courseId, username);
        
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

    public String getUploadAnswerUrl(Long courseId, int chapterIndex, int noIndex, String clientFileName, String username) {
        log.info("Get upload answer url, course id: {} by username: {}", courseId, username);
        StudentCourse studentCourse = authService.authorized(username, courseId);
        String fileName = "answer_sid_" + studentCourse.getStudent().getId() + "_cid_" + courseId + "_c" + chapterIndex + "_no." + noIndex + "_" + clientFileName;
        return privateObjectStorageService.uploadFile(fileName, username);
    }

    public void createAnswer(AnswerRequest answerRequest, String username) {
        log.info("Create answer course id: {} by username: {}", answerRequest.getCourseId(), username);

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

    //////////////////////////////////////////////////////////////////////////////////

    public List<AssignmentCourseResponse> getAssignmentCourse(Long courseId, String username) {
        Course course = authService.authorizedInstructor(username, courseId);
        List<AssignmentCourseResponse> assignmentCourseResponses = new ArrayList<AssignmentCourseResponse>();
        int size = course.getChapters().size();
        for (int i = 0; i < size; ++i) {
            List<AnswerNoti> answerNotis = answerRepo.findInstructorNotCommentAnswerByCourseIdAndChapterIndex(courseId, i);
            AssignmentCourseResponse assignmentCourseResponse = AssignmentCourseResponse.builder()
                .chapterIndex(i)
                .chapterNo(i + 1)
                .countNoti(answerNotis.size())
                .build();
            assignmentCourseResponses.add(assignmentCourseResponse);
        }
        return assignmentCourseResponses;
    }

    public List<AssignmentChapterResponse> getAssignmentChapter(Long courseId, int chapterIndex, String username) {
        Course course = authService.authorizedInstructor(username, courseId);
        List<AssignmentChapterResponse> assignments = course
            .getChapters()
            .get(chapterIndex)
            .getAssignments()
            .stream()
            .map(asm -> new AssignmentChapterResponse(courseId, chapterIndex, null, asm.get("detail")))
            .collect(Collectors.toList());

        for (int i = 0; i < assignments.size(); ++i) {
            assignments.get(i).setNoIndex(i);
        }

        return assignments;
    }

    public List<AnswerInstructor> getAssignmentAnswer(Long courseId, int chapterIndex, int noIndex, String username) {
        authService.authorizedInstructor(username, courseId);
        return answerRepo.findByCourseIdAndChapterIndexAndNoindex(courseId, chapterIndex, noIndex);
    }

    ////////////////////////////////

    public void updateCommentInstructor(CommentAnswerRequest commentAnswerRequest, String username) {
        authService.authorizedInstructor(username, commentAnswerRequest.getCourseId());
        Answer answer = answerRepo.findById(commentAnswerRequest.getAnswerId())
            .orElseThrow(() -> new AnswerNotFoundException(commentAnswerRequest.getAnswerId()));
        answer.setCommentInstructor(commentAnswerRequest.getComment());
        answerRepo.save(answer);
    }

    public String getAnswerUrl(Long courseId, Long answerId, String username) {
        authService.authorizedInstructor(username, courseId);
        Answer answer = answerRepo.findById(answerId)
            .orElseThrow(() -> new AnswerNotFoundException(answerId));
        String fileName = new StringBuilder()
            .append("answer_sid_")
            .append(answer.getStudentCourse().getStudent().getId())
            .append("_cid_")
            .append(courseId)
            .append("_c")
            .append(answer.getChapterIndex())
            .append("_no.")
            .append(answer.getNoIndex())
            .append("_")
            .append(answer.getFileName())
            .toString();
        return privateObjectStorageService.readFile(fileName, username);
    }

}