package com.ved.backend.response;

import java.util.List;

import com.ved.backend.model.Chapter;
import com.ved.backend.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
 
    private Long courseId;
    private List<Chapter> content;

    public CourseResponse(Course course) {
        this.courseId = course.getId();
        this.content = course.getChapters();
    }

}