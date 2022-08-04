package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingCommentReportResponse {
  private Long id;
  private String reportReason;
  private Long studentId;
  private String reporterName;
}
