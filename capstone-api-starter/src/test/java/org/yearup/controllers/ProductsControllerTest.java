package org.yearup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.yearup.data.mysql.BaseDaoTestClass;
import org.yearup.models.Product;
import org.yearup.security.JwtAccessDeniedHandler;
import org.yearup.security.JwtAuthenticationEntryPoint;
import org.yearup.security.UserModelDetailsService;
import org.yearup.security.WebSecurityConfig;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.ProductService;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductsController.class, excludeAutoConfiguration = WebSecurityConfig.class)
public class ProductsControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    private Integer categoryId;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String subCategory;



    @DisplayName("Check to see if search is wired correctly")
    @Test
    public void search() throws Exception{
        // Arrange
        List<Product> products = Arrays.asList(
                new Product(),
                new Product()
        );

        when(productService.search(categoryId, minPrice, maxPrice, subCategory)).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$", hasSize(2)))
                //.andExpect(jsonPath("$[0].title").value("Task 1"))
                //.andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @DisplayName("Proves getByID endpoint is wired correctly")
    @Test
    public void getById() throws Exception{
        // Arrange
        Product product = new Product();
        product.setProductId(1);
        product.setName("Organic Bananas");

        when(productService.getById(1)).thenReturn(product);

        // Act & Assert
        mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.productId").value(1))
                //.andExpect(jsonPath("$.name").value("Organic Bananas"));
    }

}