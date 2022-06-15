package com.ved.backend.response;

import com.ved.backend.model.Chapter;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FullPendingCourseInfoDto {
  private String name;
  private Long price;
  private String pictureUrl;
  private InstructorInfoDto instructorInfo;
  private String category;
  private String requirement;
  private String overview;
  private List<Chapter> chapters;
}
