package org.example.agrikarmabackend.listing.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.agrikarmabackend.common.enums.WasteType;
import org.example.agrikarmabackend.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Title shown on frontend card
    @Column(nullable = false)
    private String title;

    // Waste type (enum)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WasteType wasteType;

    // Quantity in tons
    @Column(nullable = false)
    private double quantityTons;

    // Price per ton
    @Column(nullable = false)
    private double pricePerTon;

    // Location details
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String state;

    // Listing owner (farmer)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // Timestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

