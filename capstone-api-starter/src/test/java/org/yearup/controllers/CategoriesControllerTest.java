package org.yearup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.security.JwtAccessDeniedHandler;
import org.yearup.security.JwtAuthenticationEntryPoint;
import org.yearup.security.UserModelDetailsService;
import org.yearup.security.WebSecurityConfig;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoriesController.class, excludeAutoConfiguration = WebSecurityConfig.class)
public class CategoriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @MockBean
    private UserModelDetailsService userModelDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test to see if getById is wired correctly")
    @Test
    public void getById() throws Exception{
        // Arrange
        Category category = new Category();
        Integer categoryId = 1;

        when(categoryService.getById(categoryId)).thenReturn(category);

        // Act & Assert
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$.categoryId").value(1))
    }

    @DisplayName("Testing getProductsByCategoryId wiring")
    @Test
    public void getProductsByCategoryId() throws Exception {
        // Arrange
        Integer categoryId = 1;
        List<Product> product = new ArrayList<>();

        when(productService.listByCategoryId(categoryId)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$.categoryId").value(1))
    }
}