package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishedCourseInfoResponse {
  private Long id;
  private String name;
  private String pictureUrl;
  private Long price;
  private Double rating;
  private Long reviewTotal;
}
