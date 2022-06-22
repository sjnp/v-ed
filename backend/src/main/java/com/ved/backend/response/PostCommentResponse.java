package com.ved.backend.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.ved.backend.model.Post;

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
public class PostCommentResponse {
    
    private Long id;

    private String topic;

    private String detail;

    private String profilePictureUrl;

    private String firstname;

    private String lastname;

    private LocalDateTime createDateTime;

    private boolean visible;

    private List<CommentResponse> comments;

    public PostCommentResponse(Post post) {
        this.id = post.getId();
        this.topic = post.getTopic();
        this.detail = post.getDetail();
        this.profilePictureUrl = post.getStudentCourse().getStudent().getProfilePicUri();
        this.firstname = post.getStudentCourse().getStudent().getFirstName();
        this.lastname = post.getStudentCourse().getStudent().getLastName();
        this.createDateTime = post.getCreateDateTime();
        this.visible = post.isVisible();
        this.comments = post.getComments()
            .stream()
            .map(comment -> new CommentResponse(comment))
            .collect(Collectors.toList());
    }

}