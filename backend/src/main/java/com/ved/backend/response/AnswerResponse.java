package com.ved.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.ved.backend.model.Answer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AnswerResponse {
    
    private Long id;

    private Integer chapterIndex;

    private Integer noIndex;

    private LocalDateTime datetime;

    private String fileName;

    private String commentInstructor;

    public AnswerResponse(Answer answer) {
        this.id = answer.getId();
        this.chapterIndex = answer.getChapterIndex();
        this.noIndex = answer.getNoIndex();
        this.datetime = answer.getDatetime();
        this.fileName = answer.getFileName();
        this.commentInstructor = answer.getCommentInstructor();
    }

}