package com.dsinnovators.blog.services;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category, Long id) {
        Category oldCategory = categoryRepository.findById(id).get();

        oldCategory.setId(category.getId());
        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());

        return saveCategory(oldCategory);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).get();

        categoryRepository.delete(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> getCategories(int page) {
        return categoryRepository.findAll(PageRequest.of(page, 4));
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

}
