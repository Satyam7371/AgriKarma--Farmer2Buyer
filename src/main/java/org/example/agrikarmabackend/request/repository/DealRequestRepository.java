package org.example.agrikarmabackend.request.repository;


import org.example.agrikarmabackend.request.entity.DealRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealRequestRepository extends JpaRepository<DealRequest, Long> {

    // All requests made by a buyer
    List<DealRequest> findByBuyer_Email(String email);

    // All requests for listings owned by a farmer
    List<DealRequest> findByListing_CreatedBy_Email(String email);

    boolean existsByBuyer_EmailAndListing_Id(String email, Long listingId);

}

