package com.bookshop.controller;

import com.bookshop.dto.LoginRequest;
import com.bookshop.dto.LoginResponse;
import com.bookshop.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@bookshop.com");
        request.setPassword("password");

        LoginResponse response = new LoginResponse("fake-token");
        when(authService.login(any(LoginRequest.class))).thenReturn(response);

        ResponseEntity<LoginResponse> result = authController.login(request);

        assertEquals(200, result.getStatusCode().value());
        assertEquals("fake-token", result.getBody().getToken());
    }

    @Test
    void login_ServiceThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@bookshop.com");
        request.setPassword("wrong");

        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Email ou mot de passe incorrect"));

        assertThrows(RuntimeException.class, () -> authController.login(request));
    }
}