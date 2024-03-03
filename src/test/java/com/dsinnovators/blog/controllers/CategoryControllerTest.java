package com.dsinnovators.blog.controllers;

import com.dsinnovators.blog.services.CategoryService;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    private final String user = "user";
    private final String email = "salaam.mbstu@gmail.com";

    @Test
    public void testCategoryPage() throws Exception {
        String url = "/categories";
        String viewName = "category/index";

        mockMvc.perform(get(url).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    public void testCategoryCreatePage() throws Exception {
        String url = "/category/create";
        String viewName = "category/create";

        mockMvc.perform(get(url).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }

    @Test
    public void simpleTest() {
        assertTrue(true);
    }

    // todo: will fix it later
    /*
    @ParameterizedTest
    @ValueSource(longs = { 1L })
    public void testCategoryUpdatePage(Long id) throws Exception {
        String url = "/category/{id}/update";
        String viewName = "category/update";

        mockMvc.perform(get(url, id).sessionAttr(user, email))
                .andExpect(status().isOk())
                .andExpect(view().name(viewName));
    }
     */

}
