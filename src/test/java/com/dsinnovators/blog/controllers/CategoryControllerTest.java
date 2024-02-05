package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testCategoryPage() throws Exception {
        mockMvc.perform(get("/categories").sessionAttr("user", "salaam.mbstu@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/index"));
    }

    @Test
    public void testCategoryCreatePage() throws Exception {
        mockMvc.perform(get("/category/create").sessionAttr("user", "salaam.mbstu@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/create"));
    }

//    @Test
//    public void testCategoryUpdatePage() throws Exception {
//        mockMvc.perform(get("/category/{id}/update").sessionAttr("user", "salaam.mbstu@gmail.com"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("category/update"));
//    }

}
