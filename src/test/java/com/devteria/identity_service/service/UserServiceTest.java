package com.devteria.identity_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import com.devteria.identity_service.dto.request.UserCreationRequest;
import com.devteria.identity_service.dto.response.UserResponse;
import com.devteria.identity_service.entity.User;
import com.devteria.identity_service.exception.AppException;
import com.devteria.identity_service.repository.UserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
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
        user = User.builder()
                .id("123123123")
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var res = userService.createUser(request);

        // THEN
        Assertions.assertThat(res.getId()).isEqualTo("123123123");
        Assertions.assertThat(res.getUsername()).isEqualTo("username");
        Assertions.assertThat(res.getFirstName()).isEqualTo("firstName");
        Assertions.assertThat(res.getLastName()).isEqualTo("lastName");
    }

    @Test
    void createUser_userExist_fail() {
        // GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
        var exception = assertThrows(
                AppException.class, () -> userService.createUser(request)); // assertThrows để ném ra loại exception

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "username")
    void getMyInfo_valid_success() {
        // GIVEN
        when(userRepository.findByUsername(any())).thenReturn(Optional.of((user)));

        // WHEN
        var res = userService.getMyInfor();

        // THEN
        Assertions.assertThat(res.getUsername()).isEqualTo("username");
        Assertions.assertThat(res.getId()).isEqualTo("123123123");
    }

    @Test
    @WithMockUser(username = "username")
    void getMyInfo_userNotFound_fail() {
        // GIVEN
        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(null));

        // WHEN
        var exception = assertThrows(
                AppException.class, () -> userService.getMyInfor()); // assertThrows để ném ra loại exception

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("User not existed");
    }
}
