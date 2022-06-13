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

    public CreatePostResponse createPost(PostRequest postRequest, String username) {
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

    public List<PostCardResponse> getPostsByCourseIdNew(String username, Long courseId) {
        authService.authorized(username, courseId);
        Course course = courseRepo.findById(courseId)
            .orElseThrow(() -> new CourseNotFoundException(courseId));
        return course
            .getPosts()
            .stream()
            .map(post -> new PostCardResponse(post))
            .collect(Collectors.toList());
    }

    public PostCommentResponse getPostById(String username, Long courseId, Long postId) {
        authService.authorized(username, courseId);
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
        return new PostCommentResponse(post);
    }

    public CommentResponse createComment(String username, Long courseId, CommentRequest commentRequest) {
        if (Objects.isNull(commentRequest.getPostId())) {
            throw new BadRequestException("Post id is required");
        }

        if (Objects.isNull(commentRequest.getComment())) {
            throw new BadRequestException("Comment is required");
        }

        StudentCourse studentCourse = authService.authorized(username, courseId);
        Post post = postRepo.findById(commentRequest.getPostId())
            .orElseThrow(() -> new PostNotFoundException(commentRequest.getPostId()));

        String commentStateName;
        if (username.equals(post.getStudentCourse().getStudent().getAppUser().getUsername())) {
            commentStateName = commentStateProperties.getOwner();
        } else if (username.equals(studentCourse.getCourse().getInstructor().getStudent().getAppUser().getUsername())) {
            commentStateName = commentStateProperties.getInstructor();
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
        postRepo.save(post);
        
        return new CommentResponse(comment);
    }

}