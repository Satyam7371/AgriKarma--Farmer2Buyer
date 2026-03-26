package org.example.agrikarmabackend.listing.repository;


import org.example.agrikarmabackend.listing.entity.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ListingRepository extends JpaRepository<Listing, Long>, JpaSpecificationExecutor<Listing> {      // used jpaSpecsExec for pagination anf filter of listing

    long countByCreatedBy_Email(String email);
}

