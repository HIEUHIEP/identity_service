package com.devteria.identity_service.controller;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
        // Chạy trước mổi lần test.
    void initData() {
        dob = LocalDate.of(1990, 1, 1);
        request = UserCreationRequest.builder()
                .username("username")
                .password("password")
                .firstName("firstName")
                .lastName("lastName")
                .dob(dob)
                .build();
        userResponse = UserResponse.builder()
                .id("123123123")
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse); // giả lập kết quả trả về của service

        // WHEN => THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("123123123"))

        ;
    }

    @Test
    void createUser_usernameInvalidRequest_fail() throws Exception {
        // GIVEN
        request.setUsername("use");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);


        // WHEN => THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 4 character"))

        ;
    }
}
