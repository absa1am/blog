package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.services.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class CategoryController {

    private final String DEFAULT_PAGE_NO = "0";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public String index(Model model, HttpSession session, @RequestParam(defaultValue = DEFAULT_PAGE_NO) int page) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        if (page < 0) {
            return "error/index";
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("categories", categoryService.getCategories(page));

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
    public String create(@Valid @ModelAttribute Category category, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "category/create";
        }

        categoryService.saveCategory(category);

        redirectAttributes.addFlashAttribute("message", "Category created successfully!");

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
    public String update(@PathVariable Long id, @Valid @ModelAttribute Category category, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return "category/update";
        }

        categoryService.updateCategory(category, id);

        redirectAttributes.addFlashAttribute("message", "Category updated successfully!");

        return "redirect:/categories";
    }

    @PostMapping("/category/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        categoryService.deleteCategory(id);

        redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");

        return "redirect:/categories";
    }

}
