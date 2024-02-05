package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void testRegisterPage() throws Exception {
        String url = "/register";
        String viewName = "user/register";

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    public void testLoginPage() throws Exception {
        String url = "/login";
        String viewName = "user/login";

        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

}
