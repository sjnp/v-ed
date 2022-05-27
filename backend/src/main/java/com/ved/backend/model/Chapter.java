package com.ved.backend.model;

import lombok.*;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

  private String name;

  private List<HashMap<String, Object>> sections;

  private List<HashMap<String, String>> assignments;

}
