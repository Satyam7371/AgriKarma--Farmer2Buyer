package org.example.agrikarmabackend.request.dto;


import org.example.agrikarmabackend.common.enums.DealStatus;

import java.time.LocalDateTime;


// this response dto is valid for both farmer and buyer exposing only required fields
public record DealRequestResponse(

        Long requestId,
        Long listingId,
        String listingTitle,
        String buyerEmail,
        DealStatus status,
        String message,
        LocalDateTime createdAt
) {}

