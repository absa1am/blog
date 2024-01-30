package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserService userService;
    private HttpSession httpSession;

    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, Errors errors) {
        if (errors.hasErrors()) {
            return "user/register";
        }

        userService.saveUser(user);

        httpSession.setAttribute("user", user.getEmail());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute User user, Errors errors) {
        if (errors.hasErrors()) {
            return "user/login";
        }

        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            httpSession.setAttribute("user", user.getEmail());

            return "redirect:/";
        }

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

}
