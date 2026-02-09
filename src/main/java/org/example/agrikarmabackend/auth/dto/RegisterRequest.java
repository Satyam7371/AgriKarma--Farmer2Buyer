package org.example.agrikarmabackend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotNull
        String role
) {}
