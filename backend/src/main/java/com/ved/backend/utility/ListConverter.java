package com.ved.backend.utility;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ved.backend.model.Chapter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Converter(autoApply = true)
public class ListConverter implements AttributeConverter<List<Chapter>, String> {

  private static final Logger logger = LoggerFactory.getLogger(ListConverter.class);

  @Override
  public String convertToDatabaseColumn(List<Chapter> chapters) {
    String chaptersJson = null;

    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      new ObjectMapper().writeValue(out, chapters);
      chaptersJson = out.toString();
    } catch (IOException e) {
      logger.error("JSON writing error", e);
    }

    return chaptersJson;
  }

  @Override
  public List<Chapter> convertToEntityAttribute(String chaptersJson) {
    List<Chapter> chapters = null;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      TypeFactory typeFactory = objectMapper.getTypeFactory();
      chapters = objectMapper.readValue(chaptersJson, typeFactory.constructCollectionType(List.class, Chapter.class));
    } catch (IOException e) {
      logger.error("JSON reading error", e);
    }

    return chapters;
  }

}
