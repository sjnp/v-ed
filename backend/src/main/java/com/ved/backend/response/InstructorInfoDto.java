package com.ved.backend.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorInfoDto {
  private String firstName;
  private String lastName;
  private String biography;
  private String occupation;
  private String profilePicUri;
}
