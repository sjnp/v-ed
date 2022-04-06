package com.ved.backend.model;

import java.util.HashMap;
import java.util.List;

public class Chapter {

  private String name;

  private List<HashMap<String, String>> sections;

  private List<HashMap<String, String>> assignments;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<HashMap<String, String>> getSections() {
    return sections;
  }

  public void setSections(List<HashMap<String, String>> sections) {
    this.sections = sections;
  }

  public List<HashMap<String, String>> getAssignments() {
    return assignments;
  }

  public void setAssignments(List<HashMap<String, String>> assignments) {
    this.assignments = assignments;
  }

  public Chapter() {
  }

  public Chapter(String name, List<HashMap<String, String>> sections, List<HashMap<String, String>> assignments) {
    this.name = name;
    this.sections = sections;
    this.assignments = assignments;
  }
}
