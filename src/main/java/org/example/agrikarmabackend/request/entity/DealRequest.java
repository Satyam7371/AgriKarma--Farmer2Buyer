package org.example.agrikarmabackend.request.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.agrikarmabackend.common.enums.DealStatus;
import org.example.agrikarmabackend.listing.entity.Listing;
import org.example.agrikarmabackend.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "deal_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DealRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Listing on which request is made
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    private Listing listing;

    // Buyer who raised the request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;

    // Current status of the request
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DealStatus status;

    // Optional message from buyer
    @Column(length = 500)
    private String message;

    // Timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = DealStatus.PENDING;
    }
}

