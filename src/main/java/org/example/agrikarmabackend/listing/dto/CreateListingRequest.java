package org.example.agrikarmabackend.listing.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateListingRequest(

        @NotBlank
        String title,

        @NotNull
        String wasteType,

        double quantityTons,

        double pricePerTon,

        @NotBlank
        String address,

        @NotBlank
        String district,

        @NotBlank
        String state
) {}

