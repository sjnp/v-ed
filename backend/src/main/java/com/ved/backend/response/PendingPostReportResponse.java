package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingPostReportResponse {
  private Long id;
  private String postTopic;
  private String postDetail;
  private String reportReason;
  private Long studentId;
  private String reporterName;
}
