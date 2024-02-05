package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.services.CategoryService;
import com.dsinnovators.blog.services.PostService;
import com.dsinnovators.blog.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private UserService userService;

    @Test
    public void testPostPage() throws Exception {
        mockMvc.perform(get("/posts").sessionAttr("user", "salaam.mbstu@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/posts"));
    }

    @Test
    public void testPostAllPage() throws Exception {
        mockMvc.perform(get("/post/all").sessionAttr("user", "salaam.mbstu@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/index"));
    }

    @Test
    public void testPostCreatePage() throws Exception {
        mockMvc.perform(get("/post/create").sessionAttr("user", "salaam.mbstu@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/create"));
    }

}
