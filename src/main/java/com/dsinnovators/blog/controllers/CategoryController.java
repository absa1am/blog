package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String index(Model model) {
        List<Category> categories = categoryService.getAll();

        model.addAttribute("categories", categories);

        return "category/index";
    }

    @GetMapping("/category/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());

        return "category/create";
    }

    @PostMapping("/category/create")
    public String create(@ModelAttribute Category category) {
        categoryService.save(category);

        return "redirect:/categories";
    }

}
