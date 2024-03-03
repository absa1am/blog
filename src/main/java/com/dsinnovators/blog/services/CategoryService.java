package com.dsinnovators.blog.services;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final int DEFAULT_PAGE_SIZE = 4;
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        LocalDateTime currentTimestamp = LocalDateTime.now();

        category.setCreatedAt(currentTimestamp);
        category.setUpdatedAt(currentTimestamp);
        category.setIsDeleted(false);

        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category, Long id) {
        Category oldCategory = categoryRepository.findById(id).get();

        oldCategory.setId(category.getId());
        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());
        oldCategory.setUpdatedAt(LocalDateTime.now());

        return categoryRepository.save(oldCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();

        category.setIsDeleted(true);

        updateCategory(category, id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAllByIsDeletedIsFalse();
    }

    public Page<Category> getCategories(int page) {
        return categoryRepository.findAllByIsDeletedIsFalse(PageRequest.of(page, DEFAULT_PAGE_SIZE));
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findByIdAndIsDeletedIsFalse(id);
    }

}
