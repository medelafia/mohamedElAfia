package com.bookshop.service;

import com.bookshop.dto.LoginRequest;
import com.bookshop.dto.LoginResponse;
import com.bookshop.entities.User;
import com.bookshop.repository.UserRepository;
import com.bookshop.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }
}