package com.ved.backend.response;

import java.time.LocalDateTime;

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
    
    private LocalDateTime createDateTime;
    
    private int commentCount;
    
    private boolean visible;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.topic = post.getTopic();
        this.createDateTime = post.getCreateDateTime();
        this.commentCount = post.getComments().size();
        this.visible = post.isVisible();
    }

}