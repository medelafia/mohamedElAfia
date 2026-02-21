package com.bookshop.service;

import com.bookshop.dto.LoginRequest;
import com.bookshop.dto.LoginResponse;
import com.bookshop.entities.User;
import com.bookshop.exception.UnauthorizedException;
import com.bookshop.repository.UserRepository;
import com.bookshop.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthService authService;

    private User user;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("admin@bookshop.com");
        user.setPasswordHash("hashedPassword");
        user.setRole("ADMIN");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@bookshop.com");
        loginRequest.setPassword("password");
    }

    @Test
    void login_Success() {
        when(userRepository.findByEmail("admin@bookshop.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("admin@bookshop.com", "ADMIN")).thenReturn("fake-token");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("fake-token", response.getToken());
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        when(userRepository.findByEmail("admin@bookshop.com")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_WrongPassword_ThrowsException() {
        when(userRepository.findByEmail("admin@bookshop.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> authService.login(loginRequest));
    }
}