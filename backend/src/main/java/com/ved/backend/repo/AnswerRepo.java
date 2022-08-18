package com.ved.backend.repo;

import com.ved.backend.model.Answer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepo extends JpaRepository<Answer, Long> {

    interface AnswerNoti {
        Long getAnswerId();
    }
    @Query(
        value =
        "SELECT " +
        "   a.id AS answerId " +
        "FROM " +
        "   student_course sc " +
        "   INNER JOIN student_course_answer sca ON sca.student_course_id = sc.id " +
        "   INNER JOIN answer a ON a.id = sca.answer_id " +
        "WHERE " +
        "   sc.course_id = :courseId " +
        "   AND a.chapter_index = :chapterIndex " +
        "   AND a.comment_instructor IS NULL " ,
        nativeQuery = true
    )
    List<AnswerNoti> findInstructorNotCommentAnswerByCourseIdAndChapterIndex(Long courseId, int chapterIndex);
 
    @Query(
        value =
        "SELECT " +
        "   a.id AS answerId " +
        "FROM " +
        "   student_course sc " +
        "   INNER JOIN student_course_answer sca ON sca.student_course_id = sc.id " +
        "   INNER JOIN answer a ON a.id = sca.answer_id " +
        "WHERE " +
        "   sc.course_id = :courseId " +
        "   AND a.chapter_index = :chapterIndex " +
        "   AND a.no_index = :noIndex " +
        "   AND a.comment_instructor IS NULL " ,
        nativeQuery = true
    )
    List<AnswerNoti> findInstructorNotCommentAnswerByCourseIdAndChapterIndexAndNoIndex(Long courseId, int chapterIndex, int noIndex);

    interface AnswerInstructor {
        Long getAnswerId();
        Integer getChapterIndex();
        Integer getNoIndex();
        String getCommentInstructor();
        LocalDateTime getDatetime();
        String getFileName();
        String getStudentName();
    }
    @Query(
        value =
        "SELECT " +
        "   a.id AS answerId, " +
        "   a.chapter_index AS chapterIndex, " +
        "   a.no_index AS noIndex, " +
        "   a.comment_instructor AS commentInstructor, " +
        "   a.datetime AS datetime, " +
        "   a.file_name AS fileName, " +
        "   CONCAT(s.first_name, ' ', s.last_name) AS studentName " +
        "FROM " +
        "   student_course sc " +
        "   INNER JOIN student_course_answer sca ON sca.student_course_id = sc.id " +
        "   INNER JOIN answer a ON a.id = sca.answer_id " +
        "   INNER JOIN student s ON s.id = sc.student_id " +
        "WHERE " +
        "   sc.course_id = :courseId " +
        "   AND a.chapter_index = :chapterIndex " +
        "   AND a.no_index = :noIndex ",
        nativeQuery = true
    )
    List<AnswerInstructor> findByCourseIdAndChapterIndexAndNoindex(Long courseId, Integer chapterIndex, Integer noIndex);

}