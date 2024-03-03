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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    private final String DEFAULT_PAGE_NO = "0";

    private final PostService postService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final HttpSession httpSession;

    public PostController(PostService postService, CategoryService categoryService, UserService userService, HttpSession httpSession) {
        this.postService = postService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @GetMapping("/posts")
    public String index(Model model, @RequestParam(defaultValue = DEFAULT_PAGE_NO) int page) {
        if (page < 0) {
            return "error/index";
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("posts", postService.getPosts(page));

        return "post/posts";
    }

    @GetMapping("/post/all")
    public String posts(Model model, @RequestParam(defaultValue = DEFAULT_PAGE_NO) int page) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        if (page < 0) {
            return "error/index";
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("posts", postService.getPosts(page));

        return "post/index";
    }

    @GetMapping("/post/{id}/view")
    public String view(Model model, @PathVariable Long id) {
        Optional<Post> post = postService.getPost(id);

        if (!post.isPresent()) {
            return "error/index";
        }

        model.addAttribute("post", post.get());

        return "post/view";
    }

    @GetMapping("/post/create")
    public String create(Model model) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        List<Category> categories = categoryService.getCategories();

        model.addAttribute("post", new Post());
        model.addAttribute("categories", categories);

        return "post/create";
    }

    @PostMapping("/post/create")
    public String create(@Valid @ModelAttribute("post") PostDTO post, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());

            return "post/create";
        }

        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(httpSession.getAttribute("user").toString());
        postService.savePost(post, user);

        redirectAttributes.addFlashAttribute("message", "Post created successfully!");

        return "redirect:/post/all";
    }

    @GetMapping("/post/{id}/update")
    public String update(Model model, @PathVariable Long id) {
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/login";
        }

        Optional<Post> post = postService.getPost(id);
        List<Category> categories = categoryService.getCategories();

        if (!post.isPresent()) {
            return "error/index";
        }

        model.addAttribute("post", post.get());
        model.addAttribute("categories", categories);

        return "post/update";
    }

    @PostMapping("/post/{id}/update")
    public String update(@Valid @ModelAttribute("post") PostDTO post, Errors errors, Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategories());

            return "post/update";
        }

        postService.updatePost(post, id);

        redirectAttributes.addFlashAttribute("message", "Post updated successfully!");

        return "redirect:/post/all";
    }

    @PostMapping("/post/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        postService.deletePost(id);

        redirectAttributes.addFlashAttribute("message", "Category deleted successfully.");

        return "redirect:/posts";
    }

}
