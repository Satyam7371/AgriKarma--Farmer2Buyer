package org.example.agrikarmabackend.listing.service;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.common.enums.WasteType;
import org.example.agrikarmabackend.listing.dto.CreateListingRequest;
import org.example.agrikarmabackend.listing.dto.ListingResponse;
import org.example.agrikarmabackend.listing.entity.Listing;
import org.example.agrikarmabackend.listing.repository.ListingRepository;
import org.example.agrikarmabackend.listing.specification.ListingSpecification;
import org.example.agrikarmabackend.user.entity.User;
import org.example.agrikarmabackend.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserRepository userRepository;

    // create new listing, can only be done by farmer
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



    // search listings using filters, pagination andd sort
    public Page<ListingResponse> searchListings(
            String wasteType,
            String district,
            String state,
            Double minPrice,
            Double maxPrice,
            Double minQuantity,
            Double maxQuantity,
            int page,
            int size,
            String sortBy
    ) {

        Specification<Listing> spec = Specification
                .where(ListingSpecification.hasWasteType(
                        wasteType == null ? null : WasteType.valueOf(wasteType.toUpperCase())))
                .and(ListingSpecification.hasDistrict(district))
                .and(ListingSpecification.hasState(state))
                .and(ListingSpecification.priceBetween(minPrice, maxPrice))
                .and(ListingSpecification.quantityBetween(minQuantity, maxQuantity));

        Sort sort = switch (sortBy) {
            case "price" -> Sort.by("pricePerTon").ascending();
            case "quantity" -> Sort.by("quantityTons").descending();
            default -> Sort.by("createdAt").descending();
        };

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Listing> listings = listingRepository.findAll(spec, pageable);

        return listings.map(this::toResponse);
    }


     // this converts Listing entity to ListingResponse DTO.
     // thsi prevents entity leakage to API layer.
    private ListingResponse toResponse(Listing listing) {
        return new ListingResponse(
                listing.getId(),
                listing.getTitle(),
                listing.getWasteType(),
                listing.getQuantityTons(),
                listing.getPricePerTon(),
                listing.getDistrict(),
                listing.getState(),
                listing.getCreatedAt()
        );
    }
}

