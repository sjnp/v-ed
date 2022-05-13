package com.ved.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter {

  private String name;

  private List<HashMap<String, Object>> sections;

  private List<HashMap<String, String>> assignments;

}
