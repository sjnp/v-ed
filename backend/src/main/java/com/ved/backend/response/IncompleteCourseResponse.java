package com.ved.backend.response;

import com.ved.backend.model.Chapter;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncompleteCourseResponse {
  private Long id;
  private String name;
  private Long price;
  private String pictureUrl;
  private List<Chapter> chapters;
}
