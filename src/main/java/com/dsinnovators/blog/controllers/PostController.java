package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.dto.PostDTO;
import com.dsinnovators.blog.models.Category;
import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.services.CategoryService;
import com.dsinnovators.blog.services.PostService;
import com.dsinnovators.blog.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private PostService postService;
    private CategoryService categoryService;
    private UserService userService;
    private HttpSession httpSession;

    public PostController(PostService postService, CategoryService categoryService, UserService userService, HttpSession httpSession) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @GetMapping("/posts")
    public String index(Model model) {
        List<Post> posts = postService.getAll();

        model.addAttribute("posts", posts);

        return "post/posts";
    }

    @GetMapping("/post/all")
    public String posts(Model model) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

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
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getAll();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);

        return "post/create";
    }

    @PostMapping("/post/create")
    public String create(@Valid @ModelAttribute("post") PostDTO post, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());

            return "post/create";
        }

        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(httpSession.getAttribute("user").toString());
        postService.save(post, user);

        return "redirect:/posts";
    }

    @GetMapping("/post/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Post post = postService.find(id);
        List<Category> categories = categoryService.getAll();

        model.addAttribute("post", post);
        model.addAttribute("categories", categories);

        return "post/update";
    }

    @PostMapping("/post/{id}/update")
    public String update(@Valid @ModelAttribute Post post, Errors errors, Model model, @PathVariable Long id) {
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());

            return "post/update";
        }

        postService.update(post, id);

        return "redirect:/posts";
    }

    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id) {
        postService.delete(id);

        return "redirect:/posts";
    }

}
