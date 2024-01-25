package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.services.CategoryService;
import com.dsinnovators.blog.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;
    private CategoryService categoryService;

    public PostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        List<Post> posts = postService.getAll();

        model.addAttribute("posts", posts);

        return "post/index";
    }

    @GetMapping("/post/{id}/view")
    public String view(Model model, @PathVariable Long id) {
        Post post = postService.find(id);

        model.addAttribute("post", post);

        return "post/view";
    }

    @GetMapping("/post/create")
    public String create(Model model) {
        List<Category> categories = categoryService.getAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);

        return "post/create";
    }

    @PostMapping("/post/create")
    public String create(@ModelAttribute Post post) {
        postService.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/post/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        Post post = postService.find(id);
        List<Category> categories = categoryService.getAll();

        model.addAttribute("post", post);
        model.addAttribute("categories", categories);

        return "post/update";
    }

    @PostMapping("/post/{id}/update")
    public String update(@ModelAttribute Post post, @PathVariable Long id) {
        postService.update(post, id);

        return "redirect:/posts";
    }


    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);

        return "redirect:/posts";
    }

}
