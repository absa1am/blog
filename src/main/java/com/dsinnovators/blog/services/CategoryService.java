package com.dsinnovators.blog.services;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Category category, Long id) {
        Category oldCategory = categoryRepository.findById(id).get();

        oldCategory.setId(category.getId());
        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());

        return save(oldCategory);
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).get();

        categoryRepository.delete(category);
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

}
