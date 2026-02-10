package org.example.agrikarmabackend.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDealRequest(

        @NotNull
        Long listingId,

        @NotBlank
        String message
) {}

