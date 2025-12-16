package org.yearup.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;
import org.yearup.models.authentication.LoginDto;
import org.yearup.models.authentication.LoginResponseDto;
import org.yearup.models.authentication.RegisterUserDto;
import org.yearup.security.JwtAccessDeniedHandler;
import org.yearup.security.JwtAuthenticationEntryPoint;
import org.yearup.security.UserModelDetailsService;
import org.yearup.security.WebSecurityConfig;
import org.yearup.security.jwt.TokenProvider;
import org.yearup.service.ProductService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthenticationController.class, excludeAutoConfiguration = WebSecurityConfig.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginResponseDto loginResponseDto;

    @MockBean
    private LoginDto loginDto;

    @MockBean
    private RegisterUserDto registerUserDto;

    @MockBean
    private UserDao userDao;

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

    @MockBean
    private ProfileDao profileDao;

    private User user;

    @DisplayName("Making sure login is wired correctly")
    @Test
    public void login() throws Exception{
        LoginDto login = new LoginDto();

        when(loginDto.getUsername()).thenReturn(String.valueOf(login));

        // Act & Assert
        mockMvc.perform(get("/profiles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$", hasSize(2)))
        //.andExpect(jsonPath("$[0].title").value("Task 1"))
        //.andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @DisplayName("Making sure register is wired correctly")
    @Test
    public void register() throws Exception{
        RegisterUserDto register = new RegisterUserDto();

        when(registerUserDto.getUsername()).thenReturn(String.valueOf(register));

        // Act & Assert
        mockMvc.perform(get("/profiles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //.andExpect(jsonPath("$", hasSize(2)))
        //.andExpect(jsonPath("$[0].title").value("Task 1"))
        //.andExpect(jsonPath("$[1].title").value("Task 2"));
    }
}