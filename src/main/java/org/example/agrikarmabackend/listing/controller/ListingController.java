package org.example.agrikarmabackend.listing.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.listing.dto.CreateListingRequest;
import org.example.agrikarmabackend.listing.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    // Only FARMER can create listings
    @PostMapping
    @PreAuthorize("hasRole('FARMER')")           // this checks for role based access
    public ResponseEntity<?> createListing(
            @Valid @RequestBody CreateListingRequest request,
            Authentication authentication
    ) {
        listingService.createListing(request, authentication.getName());          // auth.getName() here email from JWT
        return ResponseEntity.ok("Listing created successfully");
    }
}

