package com.ved.backend.response;

import java.util.ArrayList;

import com.ved.backend.model.Chapter;

public class StudentCourseResponse {
 
    private Long studentId;
    private ArrayList<Chapter> chapterList;

    public StudentCourseResponse() {}
    
    public StudentCourseResponse(Long studentId, ArrayList<Chapter> chapterList) {
        this.studentId = studentId;
        this.chapterList = chapterList;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public ArrayList<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(ArrayList<Chapter> chapterList) {
        this.chapterList = chapterList;
    }
    
}