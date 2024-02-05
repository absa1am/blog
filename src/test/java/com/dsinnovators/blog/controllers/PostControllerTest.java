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

    private final String user = "user";
    private final String email = "salaam.mbstu@gmail.com";

    @Test
    public void testPostPage() throws Exception {
        String url = "/posts";
        String viewName = "post/posts";

        mockMvc.perform(get(url).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    public void testPostAllPage() throws Exception {
        String url = "/post/all";
        String viewName = "post/index";

        mockMvc.perform(get(url).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    public void testPostCreatePage() throws Exception {
        String url = "/post/create";
        String viewName = "post/create";

        mockMvc.perform(get(url).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

}
