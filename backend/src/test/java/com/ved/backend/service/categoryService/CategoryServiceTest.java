package com.ved.backend.service.categoryService;

import com.ved.backend.exception.NotFoundException;
import com.ved.backend.model.Category;
import com.ved.backend.repo.CategoryRepo;
import com.ved.backend.service.CategoryService;
import com.ved.backend.util.MockData;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CategoryServiceTest {
    
    @Mock
    private CategoryRepo categoryRepo;

    private CategoryService categoryServiceTest;

    private MockData mockData;

    @BeforeEach
    public void setUp() {
        categoryServiceTest = new CategoryService(categoryRepo);
        mockData = new MockData();
    }

    @Test
    @Order(1)
    public void givenCategoryName_whenFindByNameFound_thenReturnCategory() {
        String categoryName = "ART";
        Category category = mockData.getCategory(categoryName);
        // given
        given(categoryRepo.findByName(categoryName)).willReturn(Optional.of(category));
        // when
        Category actualResult = categoryServiceTest.getByName(categoryName);
        // then
        assertEquals(category, actualResult);
        assertEquals(category.getName(), actualResult.getName());
    }

    @Test
    @Order(2)
    public void givenCategoryName_whenFindByNameNotFound_thenThrowCategoryNotFoundException() {
        String categoryName = "MOVIE";
        // given
        given(categoryRepo.findByName(categoryName)).willReturn(Optional.empty());
        // when & then
        assertThatThrownBy(() -> categoryServiceTest.getByName(categoryName))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining(String.format("Category %s not found.", categoryName));
    }

    @Test
    @Order(3)
    public void given_whenFindAllFound_thenReturnCategoryList() {
        List<Category> categories = Arrays.asList("academic", "art", "business", "design", "programming")
            .stream()
            .map((name) -> mockData.getCategory(name))
            .collect(Collectors.toList());
        // given
        given(categoryRepo.findAll()).willReturn(categories);
        // when
        List<Category> actualResult = categoryServiceTest.getAll();
        // then
        assertEquals(categories, actualResult);
    }

    @Test
    @Order(4)
    public void given_whenFindAllNotFound_thenReturnEmptyCategoryList() {
        List<Category> categories = Arrays.asList();
        // given
        given(categoryRepo.findAll()).willReturn(categories);
        // when
        List<Category> actualResult = categoryServiceTest.getAll();
        // then
        assertEquals(categories, actualResult);
    }

}
