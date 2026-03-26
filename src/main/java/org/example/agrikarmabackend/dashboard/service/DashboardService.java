package org.example.agrikarmabackend.dashboard.service;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.common.enums.DealStatus;
import org.example.agrikarmabackend.dashboard.dto.FarmerDashboardResponse;
import org.example.agrikarmabackend.listing.repository.ListingRepository;
import org.example.agrikarmabackend.request.repository.DealRequestRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ListingRepository listingRepository;
    private final DealRequestRepository dealRequestRepository;

    /*
     * Returns dashboard data for farmer.
     */
    public FarmerDashboardResponse getFarmerDashboard(String email) {

        long totalListings = listingRepository.countByCreatedBy_Email(email);

        long totalRequests = dealRequestRepository
                .countByListing_CreatedBy_Email(email);

        long accepted = dealRequestRepository
                .countByListing_CreatedBy_EmailAndStatus(email, DealStatus.ACCEPTED);

        long rejected = dealRequestRepository
                .countByListing_CreatedBy_EmailAndStatus(email, DealStatus.REJECTED);

        long pending = dealRequestRepository
                .countByListing_CreatedBy_EmailAndStatus(email, DealStatus.PENDING);

        return new FarmerDashboardResponse(
                totalListings,
                totalRequests,
                accepted,
                rejected,
                pending
        );
    }
}
