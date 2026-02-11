package org.example.agrikarmabackend.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.agrikarmabackend.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Token string
    @Column(nullable = false, unique = true)
    private String token;

    // Token owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Expiration timestamp
    @Column(nullable = false)
    private LocalDateTime expiryDate;
}

