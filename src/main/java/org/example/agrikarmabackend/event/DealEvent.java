package org.example.agrikarmabackend.event;


// This event will be published when request is created, accepted or rejected
import org.example.agrikarmabackend.common.enums.DealStatus;

public record DealEvent(
        Long requestId,
        String buyerEmail,
        String farmerEmail,
        DealStatus status,
        String message
) {}

