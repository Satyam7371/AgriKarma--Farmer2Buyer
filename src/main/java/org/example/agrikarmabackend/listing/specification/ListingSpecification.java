package org.example.agrikarmabackend.listing.specification;


// here filter logic is implemented to filter listing accordingly
import org.example.agrikarmabackend.common.enums.WasteType;
import org.example.agrikarmabackend.listing.entity.Listing;
import org.springframework.data.jpa.domain.Specification;

public class ListingSpecification {

    public static Specification<Listing> hasWasteType(WasteType wasteType) {
        return (root, query, cb) ->
                wasteType == null ? null : cb.equal(root.get("wasteType"), wasteType);
    }

    public static Specification<Listing> hasDistrict(String district) {
        return (root, query, cb) ->
                district == null ? null : cb.equal(root.get("district"), district);
    }

    public static Specification<Listing> hasState(String state) {
        return (root, query, cb) ->
                state == null ? null : cb.equal(root.get("state"), state);
    }

    public static Specification<Listing> priceBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("pricePerTon"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("pricePerTon"), min);
            return cb.between(root.get("pricePerTon"), min, max);
        };
    }

    public static Specification<Listing> quantityBetween(Double min, Double max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("quantityTons"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("quantityTons"), min);
            return cb.between(root.get("quantityTons"), min, max);
        };
    }
}

