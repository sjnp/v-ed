package com.ved.backend.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class CommentAnswerRequest {

    private Long courseId;

    private Long answerId;

    private String comment;

}