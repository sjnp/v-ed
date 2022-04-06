package com.ved.backend.utility;

import com.ved.backend.model.Chapter;

import javax.persistence.AttributeConverter;
import java.util.List;

public class ListConverter implements AttributeConverter<List<Chapter>, String> {

  @Override
  public String convertToDatabaseColumn(List<Chapter> chapters) {
    return null;
  }

  @Override
  public List<Chapter> convertToEntityAttribute(String dbData) {
    return null;
  }

}
