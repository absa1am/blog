package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final String defaultPageNo = "0";

    private PostService postService;

    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = defaultPageNo) int page) {
        if (page < 0) {
            return "error/index";
        }

        model.addAttribute("currentPage", page);
        model.addAttribute("posts", postService.getPosts(page));

        return "home/index";
    }

}
