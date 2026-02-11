package org.example.agrikarmabackend.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.auth.dto.AuthResponse;
import org.example.agrikarmabackend.auth.dto.LoginRequest;
import org.example.agrikarmabackend.auth.dto.RegisterRequest;
import org.example.agrikarmabackend.auth.entity.RefreshToken;
import org.example.agrikarmabackend.auth.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

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

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate short-lived access token
        String accessToken = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // Generate refresh token (random UUID)
        String refreshTokenValue = java.util.UUID.randomUUID().toString();

        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .user(user)
                .expiryDate(java.time.LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(refreshToken);

        return new AuthResponse(accessToken, refreshTokenValue);
    }


    public String refreshAccessToken(String refreshTokenValue) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }

    // this is for logout and delete refreshaccess token from db
    public void logout(String email) {
        refreshTokenRepository.deleteByUser_Email(email);
    }




}

