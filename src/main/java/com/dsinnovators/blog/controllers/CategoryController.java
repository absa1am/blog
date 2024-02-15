package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String index(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getCategories();

        model.addAttribute("categories", categories);

        return "category/index";
    }

    @GetMapping("/category/create")
    public String create(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        model.addAttribute("category", new Category());

        return "category/create";
    }

    @PostMapping("/category/create")
    public String create(@Valid @ModelAttribute Category category, Errors errors) {
        if (errors.hasErrors()) {
            return "category/create";
        }

        categoryService.saveCategory(category);

        return "redirect:/categories";
    }

    @GetMapping("/category/{id}/update")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Optional<Category> category = categoryService.getCategoryById(id);

        if (!category.isPresent()) {
            return "error/index";
        }

        model.addAttribute("category", category.get());

        return "category/update";
    }

    @PostMapping("/category/{id}/update")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Category category, Errors errors) {
        if (errors.hasErrors()) {
            return "category/update";
        }

        categoryService.updateCategory(category, id);

        return "redirect:/categories";
    }

    @PostMapping("/category/{id}/delete")
    public String delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return "redirect:/categories";
    }

}
