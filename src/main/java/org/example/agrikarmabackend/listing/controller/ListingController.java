package org.example.agrikarmabackend.listing.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.listing.dto.CreateListingRequest;
import org.example.agrikarmabackend.listing.dto.ListingResponse;
import org.example.agrikarmabackend.listing.service.ListingService;
import org.springframework.data.domain.Page;
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


    // this for filtering the listings based on search
    @GetMapping
    public ResponseEntity<Page<ListingResponse>> searchListings(
            @RequestParam(required = false) String wasteType,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minQuantity,
            @RequestParam(required = false) Double maxQuantity,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sortBy
    ) {

        return ResponseEntity.ok(
                listingService.searchListings(
                        wasteType,
                        district,
                        state,
                        minPrice,
                        maxPrice,
                        minQuantity,
                        maxQuantity,
                        page,
                        size,
                        sortBy
                )
        );
    }
}

