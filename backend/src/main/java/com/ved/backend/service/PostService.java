package com.ved.backend.service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ved.backend.configuration.CommentStateProperties;
import com.ved.backend.exception.CourseNotFoundException;
import com.ved.backend.exception.PostNotFoundException;
import com.ved.backend.exception.baseException.BadRequestException;
import com.ved.backend.model.Comment;
import com.ved.backend.model.CommentState;
import com.ved.backend.model.Course;
import com.ved.backend.model.Post;
import com.ved.backend.model.StudentCourse;
import com.ved.backend.repo.CommentStateRepo;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.request.CommentRequest;
import com.ved.backend.request.PostRequest;
import com.ved.backend.response.CommentResponse;
import com.ved.backend.response.CreatePostResponse;
import com.ved.backend.response.PostCardResponse;
import com.ved.backend.response.PostCommentResponse;

import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class PostService {

    private final AuthService authService;
    
    private final PostRepo postRepo;
    private final CommentStateRepo commentStateRepo;
    private final CourseRepo courseRepo;

    private final CommentStateProperties commentStateProperties;

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    public CreatePostResponse createPost(PostRequest postRequest, String username) {
        log.info("Create post in course id: {} by username: {}", postRequest.getCourseId(), username);

        if (Objects.isNull(postRequest.getCourseId())) {
            throw new BadRequestException("Course id is required");
        }
      
        if (Objects.isNull(postRequest.getTopic())) {
            throw new BadRequestException("Topic is required");
        }
      
        if (Objects.isNull(postRequest.getDetail())) {
            throw new BadRequestException("Detail is required");
        }

        StudentCourse studentCourse = authService.authorized(username, postRequest.getCourseId());
        Post post = Post.builder()
            .course(studentCourse.getCourse())
            .studentCourse(studentCourse)
            .topic(postRequest.getTopic())
            .detail(postRequest.getDetail())
            .createDateTime(LocalDateTime.now())
            .visible(true)
            .build();
            
        Post resultPost = postRepo.save(post);
        return CreatePostResponse.builder()
            .postId(resultPost.getId())
            .build();
    }

    public List<PostCardResponse> getAllPostsCourse(Long courseId) {
        Course course = courseRepo
            .findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        return course
            .getPosts()
            .stream()
            .filter(post -> post.isVisible() == true)
            .map(post -> new PostCardResponse(post))
            .collect(Collectors.toList());
    }

    public List<PostCardResponse> getAllPostsCourseByStudent(Long courseId, String username) {
        log.info("Get post course id: {} by username: {}", courseId, username);
        authService.authorized(username, courseId);
        return this.getAllPostsCourse(courseId);
    }

    public List<PostCardResponse> getAllPostsCourseByInstructor(Long courseId, String username) {
        log.info("Get post course id: {} by username: {}", courseId, username);
        authService.authorizedInstructor(username, courseId);
        return this.getAllPostsCourse(courseId);
    }

    public PostCommentResponse getPost(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
        return new PostCommentResponse(post);
    }

    public PostCommentResponse getPostByStudent(String username, Long courseId, Long postId) {
        log.info("Get post id: {} in course id: {} by username: {}", postId, courseId, username);
        authService.authorized(username, courseId);
        return this.getPost(postId);
    }

    public PostCommentResponse getPostByInstructor(String username, Long courseId, Long postId) {
        log.info("Get post id: {} in course id: {} by username: {}", postId, courseId, username);
        authService.authorizedInstructor(username, courseId);
        return this.getPost(postId);
    }

    public List<CommentResponse> createCommentByStudent(String username, Long courseId, Long postId, CommentRequest commentRequest) {
        log.info("Create comment post id: {} in course id: {} by username: {}", postId, courseId, username);

        if (Objects.isNull(postId)) {
            throw new BadRequestException("Post id is required");
        }

        if (Objects.isNull(commentRequest.getComment())) {
            throw new BadRequestException("Comment is required");
        }

        if (commentRequest.getComment().length() > 1000) {
            throw new BadRequestException("Comments are more than 1000 characters");
        }

        StudentCourse studentCourse = authService.authorized(username, courseId);
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));

        String commentStateName;
        if (username.equals(post.getStudentCourse().getStudent().getAppUser().getUsername())) {
            commentStateName = commentStateProperties.getOwner();
        } else {
            commentStateName = commentStateProperties.getStudent();
        }
        CommentState commentState = commentStateRepo.findByName(commentStateName);
        Comment comment = Comment.builder()
            .post(post)
            .comment(commentRequest.getComment())
            .commentDateTime(LocalDateTime.now())
            .visible(true)
            .commentState(commentState)
            .student(studentCourse.getStudent())
            .build();
        post.getComments().add(comment);
        Post postResponse = postRepo.save(post);
        
        return postResponse
            .getComments()
            .stream()
            .filter(cm -> cm.isVisible() == true)
            .map(cm -> new CommentResponse(cm))
            .collect(Collectors.toList());
    }

    public List<CommentResponse> createCommentByInstructor(String username, Long courseId, Long postId, CommentRequest commentRequest) {
        log.info("Create comment post id: {} in course id: {} by username: {}", postId, courseId, username);

        if (Objects.isNull(postId)) {
            throw new BadRequestException("Post id is required");
        }

        if (Objects.isNull(commentRequest.getComment())) {
            throw new BadRequestException("Comment is required");
        }

        if (commentRequest.getComment().length() > 1000) {
            throw new BadRequestException("Comments are more than 1000 characters");
        }
        
        Course course = authService.authorizedInstructor(username, courseId);
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
        String commentStateName = commentStateProperties.getInstructor();
        CommentState commentState = commentStateRepo.findByName(commentStateName);
        Comment comment = Comment.builder()
            .post(post)
            .comment(commentRequest.getComment())
            .commentDateTime(LocalDateTime.now())
            .visible(true)
            .commentState(commentState)
            .student(course.getInstructor().getStudent())
            .build();
        post.getComments().add(comment);
        Post postResponse = postRepo.save(post);
        
        return postResponse
            .getComments()
            .stream()
            .filter(cm -> cm.isVisible() == true)
            .map(cm -> new CommentResponse(cm))
            .collect(Collectors.toList());
    }

}