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
public class AnswerRequest {
 
    private Long courseId;

    private Integer chapterIndex;

    private Integer noIndex;

    private String fileName;

}