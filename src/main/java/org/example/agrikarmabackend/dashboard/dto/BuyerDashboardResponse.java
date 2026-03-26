package org.example.agrikarmabackend.dashboard.dto;

public record BuyerDashboardResponse(
        long totalRequests,
        long acceptedRequests,
        long rejectedRequests,
        long pendingRequests
) {}
