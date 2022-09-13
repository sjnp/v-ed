package com.ved.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ved.backend.configuration.CategoryProperties;
import com.ved.backend.repo.CourseRepo;
import com.ved.backend.response.CourseCardResponse;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SearchService {

    private final CourseRepo courseRepo;

    private final CategoryProperties categoryProperties;

    public List<CourseCardResponse> search(String courseName, String categoryName, Long minPrice, Long maxPrice, String rating) {
        List<String> categories = this.getCategoriesParam(categoryName);
        minPrice = this.getMinPriceParam(minPrice);
        maxPrice = this.getMaxPriceParam(maxPrice);
        Double stars = this.getRatingParam(rating);
        if (Objects.isNull(courseName)) {
            courseName = "";
        }
        return courseRepo.search(courseName, categories, minPrice, maxPrice, stars)
            .stream()
            .map(searchCourse -> new CourseCardResponse(searchCourse))
            .collect(Collectors.toList());
    }

    private List<String> getCategoriesParam(String categoryName) {
        List<String> categories = new ArrayList<>();
        if (Objects.isNull(categoryName) || categoryName.equals("all")) {
            categories.add(categoryProperties.getAcademic());
            categories.add(categoryProperties.getArt());
            categories.add(categoryProperties.getBusiness());
            categories.add(categoryProperties.getDesign());
            categories.add(categoryProperties.getProgramming());
        } else {
            switch (categoryName.toUpperCase()) {
                case "ACADEMIC": categories.add(categoryProperties.getAcademic()); break;
                case "ART": categories.add(categoryProperties.getArt()); break;
                case "BUSINESS": categories.add(categoryProperties.getBusiness()); break;
                case "DESIGN": categories.add(categoryProperties.getDesign()); break;
                case "PROGRAMMING": categories.add(categoryProperties.getProgramming()); break;
            }
        }
        return categories;
    }

    private Long getMinPriceParam(Long minPrice) {
        return Objects.isNull(minPrice) ? 0L : minPrice;
    }

    private Long getMaxPriceParam(Long maxPrice) {
        return Objects.isNull(maxPrice) ? 1000000L : maxPrice;
    }

    private Double getRatingParam(String rating) {
        if (Objects.isNull(rating) || rating.equals("all")) {
            return 0.0;
        } else {
            return Double.parseDouble(rating);
        }
    }

}