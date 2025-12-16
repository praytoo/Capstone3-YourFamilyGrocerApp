package org.yearup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yearup.models.Product;
import org.yearup.models.Profile;
import org.yearup.security.JwtAccessDeniedHandler;
import org.yearup.security.JwtAuthenticationEntryPoint;
import org.yearup.security.UserModelDetailsService;
import org.yearup.security.WebSecurityConfig;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.ProductService;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProfileController.class, excludeAutoConfiguration = WebSecurityConfig.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private UserService userService;

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

    private Integer userId;

    @DisplayName("Testing to see if getByUserId is wired correctly")
    @Test
    public void getByUserId() throws Exception {
        // Arrange
        Profile profile = new Profile();
        profile.setUserId(10);

        when(profileService.getByUserId(10)).thenReturn(profile);

        // Act & Assert
        mockMvc.perform(get("/profiles/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$.productId").value(1))
        //.andExpect(jsonPath("$.name").value("Organic Bananas"));
    }

    @DisplayName("Checking if getProfile is wired correctly")
    @Test
    public void getProfile() throws Exception{
        Profile profile = new Profile();

        when(profileService.getByUserId(userId)).thenReturn(profile);

        // Act & Assert
        mockMvc.perform(get("/profiles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$", hasSize(2)))
        //.andExpect(jsonPath("$[0].title").value("Task 1"))
        //.andExpect(jsonPath("$[1].title").value("Task 2"));
    }
}