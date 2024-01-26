package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "user/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, HttpSession session) {
        userService.saveUser(user);

        session.setAttribute("user", user.getEmail());

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/";
        }

        model.addAttribute("user", new User());

        return "user/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.findByEmail(email);

        if (user != null && password.equals(user.getPassword())) {
            session.setAttribute("user", user.getEmail());

            System.out.println("Email: " + email + ", Password: " + user.getPassword());

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
