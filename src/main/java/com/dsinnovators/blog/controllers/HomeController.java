package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.Post;
import com.dsinnovators.blog.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Post> posts = postService.getAll();

        model.addAttribute("posts", posts);

        return "home/index";
    }

}
