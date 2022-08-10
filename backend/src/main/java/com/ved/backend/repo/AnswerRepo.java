package com.ved.backend.repo;

import com.ved.backend.model.Answer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepo extends JpaRepository<Answer, Long> {
 
    interface AnswerInstructor {
        Long getAnswerId();
        Integer getChapterIndex();
        Integer getNoIndex();
        String getCommentInstructor();
        LocalDateTime getDatetime();
        String getFileName();
    }
    @Query(
        value =
        "SELECT " +
        "   a.id AS answerId, " +
        "   a.chapter_index AS chapterIndex, " +
        "   a.no_index AS noIndex, " +
        "   a.comment_instructor AS commentInstructor, " +
        "   a.datetime AS datetime, " +
        "   a.file_name AS fileName " +
        "FROM " +
        "   student_course sc " +
        "   INNER JOIN student_course_answer sca ON sca.student_course_id = sc.id " +
        "   INNER JOIN answer a ON a.id = sca.answer_id " +
        "WHERE " +
        "   sc.course_id = :courseId " +
        "   AND a.chapter_index = :chapterIndex " +
        "   AND a.no_index = :noIndex ",
        nativeQuery = true
    )
    List<AnswerInstructor> findByCourseIdAndChapterIndexAndNoindex(Long courseId, Integer chapterIndex, Integer noIndex);

}