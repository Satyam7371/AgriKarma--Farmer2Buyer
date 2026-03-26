package org.example.agrikarmabackend.dashboard.dto;

public record FarmerDashboardResponse(
        long totalListings,
        long totalRequests,
        long acceptedRequests,
        long rejectedRequests,
        long pendingRequests
) {}
