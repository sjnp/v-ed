package com.ved.backend.service;

import com.ved.backend.model.Answer;
import com.ved.backend.repo.AnswerRepo;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class AssignmentService {
    
    private final AnswerRepo answerRepo;

    private static final Logger log = LoggerFactory.getLogger(AssignmentService.class);

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