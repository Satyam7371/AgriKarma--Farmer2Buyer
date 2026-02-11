package org.example.agrikarmabackend.request.service;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.common.enums.DealStatus;
import org.example.agrikarmabackend.listing.entity.Listing;
import org.example.agrikarmabackend.listing.repository.ListingRepository;
import org.example.agrikarmabackend.request.dto.CreateDealRequest;
import org.example.agrikarmabackend.request.dto.DealRequestResponse;
import org.example.agrikarmabackend.request.entity.DealRequest;
import org.example.agrikarmabackend.request.repository.DealRequestRepository;
import org.example.agrikarmabackend.user.entity.User;
import org.example.agrikarmabackend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealRequestService {

    private final DealRequestRepository dealRequestRepository;
    private final ListingRepository listingRepository;
    private final UserRepository userRepository;


     // buyer sends a request on a listing
     public void createRequest(CreateDealRequest request, String buyerEmail) {

         User buyer = userRepository.findByEmail(buyerEmail)
                 .orElseThrow(() -> new RuntimeException("Buyer not found"));

         Listing listing = listingRepository.findById(request.listingId())
                 .orElseThrow(() -> new RuntimeException("Listing not found"));

         // Prevent buyer from requesting their own listing
         if (listing.getCreatedBy().getEmail().equals(buyerEmail)) {
             throw new RuntimeException("You cannot request your own listing");
         }

         // Prevent duplicate requests
         boolean alreadyRequested = dealRequestRepository
                 .existsByBuyer_EmailAndListing_Id(buyerEmail, request.listingId());

         if (alreadyRequested) {
             throw new RuntimeException("You have already requested this listing");
         }

         DealRequest dealRequest = DealRequest.builder()
                 .buyer(buyer)
                 .listing(listing)
                 .message(request.message())
                 .build();

         dealRequestRepository.save(dealRequest);
     }



    // farmer accepts or rejects a request on their own listing
    public void actOnRequest(Long requestId, String farmerEmail, String action) {

        DealRequest dealRequest = dealRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        String ownerEmail = dealRequest.getListing()
                .getCreatedBy()
                .getEmail();

        if (!ownerEmail.equals(farmerEmail)) {
            throw new RuntimeException("Unauthorized action");
        }

        // Prevent action if already done
        if (dealRequest.getStatus() != DealStatus.PENDING) {
            throw new RuntimeException("Request already finalized");
        }

        if ("ACCEPT".equalsIgnoreCase(action)) {
            dealRequest.setStatus(DealStatus.ACCEPTED);
        } else if ("REJECT".equalsIgnoreCase(action)) {
            dealRequest.setStatus(DealStatus.REJECTED);
        } else {
            throw new RuntimeException("Invalid action");
        }

        dealRequestRepository.save(dealRequest);
    }



    // this is basically for dashboard showing listings requests for bot hbuyer and farmer

     // returns all requests created by the logged in buyer
    public List<DealRequestResponse> getRequestsByBuyer(String buyerEmail) {

        return dealRequestRepository.findByBuyer_Email(buyerEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }


     // returns all incoming requests for listings owned by the logged in farmer
    public List<DealRequestResponse> getRequestsForFarmer(String farmerEmail) {

        return dealRequestRepository.findByListing_CreatedBy_Email(farmerEmail)
                .stream()
                .map(this::toResponse)
                .toList();
    }


     // maps DealRequest entity to response DTO
    private DealRequestResponse toResponse(DealRequest request) {

        return new DealRequestResponse(
                request.getId(),
                request.getListing().getId(),
                request.getListing().getTitle(),
                request.getBuyer().getEmail(),
                request.getStatus(),
                request.getMessage(),
                request.getCreatedAt()
        );
    }

}

