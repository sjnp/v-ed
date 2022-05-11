package com.ved.backend.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ved.backend.model.Comment;
import com.ved.backend.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
 
    private Long id;
    private String topic;
    private String detail;
    private LocalDateTime createDateTime;
    private boolean visible;
    private List<CommentResponse> comments;
    private String firstname;
    private String lastname;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.topic = post.getTopic();
        this.detail = post.getDetail();
        this.createDateTime = post.getCreateDateTime();
        this.visible = post.isVisible();
        this.comments = this.getListComemnt(post.getComments());
        this.firstname = post.getStudentCourse().getStudent().getFirstName();
        this.lastname = post.getStudentCourse().getStudent().getLastName();
    }

    private List<CommentResponse> getListComemnt(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
        for (Comment comment : comments) {
            commentResponses.add(new CommentResponse(comment));
        }
        return commentResponses;
    }

}