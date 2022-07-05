package com.ved.backend.utility;

import com.ved.backend.exception.baseException.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class FileExtensionStringHandler {

    public String getViableExtension(String filename, List<String> viableExtensions) {
        String extension = Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElseThrow(() -> new BadRequestException("Invalid file type"));

        return viableExtensions.stream()
                .filter(extension::contains)
                .findAny()
                .orElseThrow(() -> new BadRequestException("Invalid file type"));
    }
}
