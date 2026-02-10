package org.example.agrikarmabackend.listing.service;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.common.enums.WasteType;
import org.example.agrikarmabackend.listing.dto.CreateListingRequest;
import org.example.agrikarmabackend.listing.entity.Listing;
import org.example.agrikarmabackend.listing.repository.ListingRepository;
import org.example.agrikarmabackend.user.entity.User;
import org.example.agrikarmabackend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    public void createListing(CreateListingRequest request, String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Listing listing = Listing.builder()
                .title(request.title())
                .wasteType(WasteType.valueOf(request.wasteType().toUpperCase()))
                .quantityTons(request.quantityTons())
                .pricePerTon(request.pricePerTon())
                .address(request.address())
                .district(request.district())
                .state(request.state())
                .createdBy(user)
                .build();

        listingRepository.save(listing);
    }
}

