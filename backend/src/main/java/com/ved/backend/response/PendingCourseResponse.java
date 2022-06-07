package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PendingCourseResponse {
  private Long id;
  private String name;
  private String instructorName;
}
