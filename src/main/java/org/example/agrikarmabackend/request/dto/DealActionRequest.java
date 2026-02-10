package org.example.agrikarmabackend.request.dto;


import jakarta.validation.constraints.NotBlank;

public record DealActionRequest(

        @NotBlank
        String action   // ACCEPT or REJECT
) {}

