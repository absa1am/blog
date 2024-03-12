package com.dsinnovators.blog.services;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private CategoryService categoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @ParameterizedTest
    @MethodSource("categoryProvider")
    void saveCategory(Category category) {
        when(categoryRepository.save(category)).thenReturn(category);

        var savedCategory = categoryService.saveCategory(category);

        assertAll("category",
                () -> assertEquals(category.getId(), savedCategory.getId()),
                () -> assertEquals(category.getName(), savedCategory.getName()),
                () -> assertEquals(category.getDescription(), savedCategory.getDescription()),
                () -> assertEquals(category.getCreatedAt(), savedCategory.getCreatedAt()),
                () -> assertEquals(category.getUpdatedAt(), savedCategory.getUpdatedAt()),
                () -> assertEquals(category.getIsDeleted(), savedCategory.getIsDeleted()));

        verify(categoryRepository).save(category);
    }

    @ParameterizedTest
    @MethodSource("categoryAndCategoryIdProvider")
    void updateCategory(Category category, Long categoryId) {
        when(categoryRepository.findById(categoryId))
                .thenReturn(Optional.of(categoryProvider()
                        .stream()
                        .filter(c -> c.getId() == categoryId)
                        .toList()
                        .getFirst()));

        when(categoryRepository.save(any())).thenReturn(category);

        var updatedCategory = categoryService.updateCategory(category, categoryId);

        assertAll("category",
                () -> assertEquals(category.getId(), updatedCategory.getId()),
                () -> assertEquals(category.getName(), updatedCategory.getName()),
                () -> assertEquals(category.getDescription(), updatedCategory.getDescription()),
                () -> assertEquals(category.getCreatedAt(), updatedCategory.getCreatedAt()),
                () -> assertEquals(category.getUpdatedAt(), updatedCategory.getUpdatedAt()),
                () -> assertEquals(category.getIsDeleted(), updatedCategory.getIsDeleted()));

        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).save(any());
    }

    static List<Category> categoryProvider() {
        Category category;
        List<Category> categories = new ArrayList<>();

        category = new Category();

        category.setId(1L);
        category.setName("Java");
        category.setDescription("Java related articles");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setIsDeleted(false);

        categories.add(category);

        category = new Category();

        category.setId(2L);
        category.setName("C#");
        category.setDescription("C# related articles");
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        category.setIsDeleted(false);

        categories.add(category);

        return categories;
    }

    static Stream<Arguments> categoryAndCategoryIdProvider() {
        return Stream.of(
                arguments(categoryProvider().getFirst(), 1L),
                arguments(categoryProvider().getLast(), 2L)
        );
    }

}
