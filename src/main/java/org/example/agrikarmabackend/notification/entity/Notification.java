package org.example.agrikarmabackend.notification.entity;


import jakarta.persistence.*;
import lombok.*;
import org.example.agrikarmabackend.common.enums.NotificationType;
import org.example.agrikarmabackend.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User who receives the notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    // Message shown to user
    @Column(nullable = false)
    private String message;

    // Type of event
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    // Reference to deal/request
    private Long referenceId;

    // Read/unread flag
    @Column(nullable = false)
    private boolean read;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }
}

