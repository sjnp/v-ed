package com.ved.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ved.backend.exception.tempException.MyException;
import com.ved.backend.model.Course;
import com.ved.backend.model.Post;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.repo.PostRepo;
import com.ved.backend.response.PostResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class PostService {

    private final PostRepo postRepo;

    public Post savePost(Post post) {
        return postRepo.save(post);
    }

    // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

    private final CourseRepo courseRepo;

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