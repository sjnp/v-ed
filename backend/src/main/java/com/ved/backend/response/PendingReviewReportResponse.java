package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingReviewReportResponse {
  private Long id;
  private String reviewComment;
  private String reportReason;
  private Long studentId;
  private String reporterName;
}
