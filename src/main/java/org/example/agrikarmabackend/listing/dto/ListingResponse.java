package org.example.agrikarmabackend.listing.dto;


import org.example.agrikarmabackend.common.enums.WasteType;

import java.time.LocalDateTime;

public record ListingResponse(

        Long id,
        String title,
        WasteType wasteType,
        double quantityTons,
        double pricePerTon,
        String district,
        String state,
        LocalDateTime createdAt
) {}

