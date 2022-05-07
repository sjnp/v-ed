package com.ved.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ved.backend.exception.MyException;
import com.ved.backend.model.AppUser;
import com.ved.backend.model.Course;
import com.ved.backend.model.QuestionBoard;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.QuestionBoardRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.response.QuestionBoardResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QuestionBoardServiceImpl implements QuestionBoardService {

    private final QuestionBoardRepo questionBoardRepo;
    private final AppUserRepo appUserRepo;
    private final StudentCourseRepo studentCourseRepo;
    private final CourseRepo courseRepo;
    
 
    public QuestionBoardServiceImpl(
        QuestionBoardRepo questionBoardRepo, 
        AppUserRepo appUserRepo,
        StudentCourseRepo studentCourseRepo,
        CourseRepo courseRepo
    ) {
        this.questionBoardRepo = questionBoardRepo;
        this.appUserRepo = appUserRepo;
        this.studentCourseRepo = studentCourseRepo;
        this.courseRepo = courseRepo;
    }

    public QuestionBoardResponse create(Long courseId, String topic, String detail, String username) {

        if (Objects.isNull(courseId)) {
            throw new MyException("question.board.course.id.null", HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(topic) || topic.length() == 0) {
            throw new MyException("question.board.topic.null", HttpStatus.BAD_REQUEST);
        }

        if (Objects.isNull(detail) || detail.length() == 0) {
            throw new MyException("question.board.detail.null", HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = appUserRepo.findByUsername(username);
        Long studentId = appUser.getStudent().getId();
        StudentCourse studentCourse = studentCourseRepo.findByCourseIdAndStudentId(courseId, studentId);

        QuestionBoard questionBoard = new QuestionBoard();
        questionBoard.setTopic(topic);
        questionBoard.setDetail(detail);
        questionBoard.setCreateDateTime(LocalDateTime.now());
        questionBoard.setVisible(true);
        questionBoard.setCourse(studentCourse.getCourse());
        questionBoard.setStudentCourse(studentCourse);

        Course course = studentCourse.getCourse();
        course.getQuestionBoards().add(questionBoard);

        studentCourse.getQuestionBoards().add(questionBoard);

        questionBoardRepo.save(questionBoard);
        studentCourseRepo.save(studentCourse);
        courseRepo.save(course);

        QuestionBoardResponse response = new QuestionBoardResponse(questionBoard);
        return response;
    }

    public QuestionBoardResponse getQuestionBoardById(Long questionBoardId) {

        Optional<QuestionBoard> questionBoardOptional = questionBoardRepo.findById(questionBoardId);

        if (questionBoardOptional.isEmpty()) {
            throw new MyException("question.baord.id.not.found", HttpStatus.BAD_REQUEST);
        }

        QuestionBoard questionBoard = questionBoardOptional.get();
        QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(questionBoard);

        return questionBoardResponse;
    }

    public List<QuestionBoardResponse> getQuestionBoardByCourseId(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("question.baord.course.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Course course = courseOptional.get();
        List<QuestionBoard> questionBoards = course.getQuestionBoards();

        List<QuestionBoardResponse> response = new ArrayList<QuestionBoardResponse>();
        for (QuestionBoard questionBoard : questionBoards) {
            QuestionBoardResponse questionBoardResponse = new QuestionBoardResponse(questionBoard);
            response.add(questionBoardResponse);
        }

        return response;
    }

}