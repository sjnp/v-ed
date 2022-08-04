package com.ved.backend.response;

import com.ved.backend.model.Comment;
import com.ved.backend.model.Post;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostWithReportedCommentResponse {
  private Long reportId;
  private Long reportedCommentId;
  private String reportReason;
  private Long studentId;
  private String reporterName;
  private Post post;
  private List<Comment> commentList;
}
