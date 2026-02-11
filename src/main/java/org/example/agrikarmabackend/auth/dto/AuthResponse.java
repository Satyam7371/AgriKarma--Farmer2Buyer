package org.example.agrikarmabackend.auth.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}

