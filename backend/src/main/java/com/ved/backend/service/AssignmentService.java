package com.ved.backend.service;

import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Answer;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AnswerRepo;
import com.ved.backend.response.AnswerResponse;
import com.ved.backend.response.AssignmentAnswerResponse;

import lombok.AllArgsConstructor;

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
    
    private final AnswerRepo answerRepo;

    private final StudentCourseService studentCourseService;

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    public List<AssignmentAnswerResponse> getAssignmentAnswerResponse(StudentCourse studentCourse, Integer chapterIndex) {
        return studentCourse
            .getCourse()
            .getChapters()
            .get(chapterIndex)
            .getAssignments()
            .stream()
            .map(asm -> AssignmentAnswerResponse.builder().assignment(asm.get("detail")).build())
            .collect(Collectors.toList());
    }

    public List<AssignmentAnswerResponse> getAssignmentAnswer(Long courseId, Integer chapterIndex, String username) {
        if (Objects.isNull(chapterIndex)) {
            throw new BadRequestException("Chapter index is required");
        }

        StudentCourse studentCourse = studentCourseService.auth(courseId, username);
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

    /////////////////////////////

    public Answer saveAnswer(Answer answer) {
        log.info("Save answer student id {} course id {}, chapter {}, no. {}", 
            answer.getStudentCourse().getStudent().getId(),
            answer.getStudentCourse().getCourse().getId(),
            answer.getChapterIndex(),
            answer.getNoIndex()
        );
        return answerRepo.save(answer);
    }

}