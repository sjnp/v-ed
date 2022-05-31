package com.ved.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VideoResponse {
 
    private String videoUrl;

    private String pictureUrl;

    private String chapterName;

    private String sectionName;

}