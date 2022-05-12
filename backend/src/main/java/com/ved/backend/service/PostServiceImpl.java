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
import com.ved.backend.model.Post;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.AppUserRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.repo.StudentCourseRepo;
import com.ved.backend.response.PostResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final AppUserRepo appUserRepo;
    private final StudentCourseRepo studentCourseRepo;
    private final CourseRepo courseRepo;
    
 
    public PostServiceImpl(
        PostRepo postRepo,
        AppUserRepo appUserRepo,
        StudentCourseRepo studentCourseRepo,
        CourseRepo courseRepo
    ) {
        this.postRepo = postRepo;
        this.appUserRepo = appUserRepo;
        this.studentCourseRepo = studentCourseRepo;
        this.courseRepo = courseRepo;
    }

    public PostResponse create(Long courseId, String topic, String detail, String username) {

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

        Post post = new Post();
        post.setTopic(topic);
        post.setDetail(detail);
        post.setCreateDateTime(LocalDateTime.now());
        post.setVisible(true);
        post.setCourse(studentCourse.getCourse());
        post.setStudentCourse(studentCourse);

        Course course = studentCourse.getCourse();
        course.getPosts().add(post);

        studentCourse.getPosts().add(post);

        postRepo.save(post);
        studentCourseRepo.save(studentCourse);
        courseRepo.save(course);

        PostResponse response = new PostResponse(post);
        return response;
    }

    public PostResponse getPostById(Long questionBoardId) {

        Optional<Post> questionBoardOptional = postRepo.findById(questionBoardId);

        if (questionBoardOptional.isEmpty()) {
            throw new MyException("question.baord.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Post post = questionBoardOptional.get();
        PostResponse postResponse = new PostResponse(post);

        return postResponse;
    }

    public List<PostResponse> getPostByCourseId(Long courseId) {

        Optional<Course> courseOptional = courseRepo.findById(courseId);

        if (courseOptional.isEmpty()) {
            throw new MyException("question.baord.course.id.not.found", HttpStatus.BAD_REQUEST);
        }

        Course course = courseOptional.get();
        List<Post> posts = course.getPosts();

        List<PostResponse> response = new ArrayList<PostResponse>();
        for (Post post : posts) {
            PostResponse postResponse = new PostResponse(post);
            response.add(postResponse);
        }

        return response;
    }

}