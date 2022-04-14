package com.ved.backend.utility;

import io.vavr.collection.Array;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class FileExtensionStringHandler {

  public static String getViableExtension(String filename, List<String> viableExtensions) {
    String extension = Optional.of(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1)).orElseThrow(() -> new RuntimeException("Invalid file type"));

    return viableExtensions.stream().filter(extension::contains).findAny().orElseThrow(() -> new RuntimeException("Invalid file type"));
  }
}
