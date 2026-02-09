package org.example.agrikarmabackend.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.auth.dto.LoginRequest;
import org.example.agrikarmabackend.auth.dto.RegisterRequest;
import org.example.agrikarmabackend.common.enums.Role;
import org.example.agrikarmabackend.user.entity.User;
import org.example.agrikarmabackend.user.repository.UserRepository;
import org.example.agrikarmabackend.auth.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        Role role = Role.valueOf(request.role().toUpperCase());

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .active(true)
                .build();

        userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // On successful login, JWT is generated, role is seen inside token
        // then token return to client
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }

}

