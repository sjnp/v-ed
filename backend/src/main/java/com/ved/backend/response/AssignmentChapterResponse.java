package com.ved.backend.response;

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
public class AssignmentChapterResponse {

    private Long courseId;
    
    private Integer chapterIndex;

    private Integer noIndex;

    private String detail;

    private Integer countNoti;

}