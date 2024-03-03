package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.dto.LoginDTO;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final HttpSession httpSession;

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
    public String register(@Valid @ModelAttribute("user") User user, BindingResult errors) {
        if (errors.hasErrors()) {
            return "user/register";
        }

        try {
            userService.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            errors.rejectValue("email", "error.email", "User account already exists");
            logger.error(e.getMessage());

            return "user/register";
        }

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
    public String login(@Valid @ModelAttribute("user") LoginDTO user, BindingResult errors) {
        if (errors.hasErrors()) {
            return "user/login";
        }

        User existingUser = userService.findByEmail(user.getEmail());

        if (userService.isCredentialMatched(user, existingUser)) {
            httpSession.setAttribute("user", user.getEmail());

            return "redirect:/";
        }

        errors.rejectValue("email", "error.email", "The email or password is wrong");

        return "user/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }

}
