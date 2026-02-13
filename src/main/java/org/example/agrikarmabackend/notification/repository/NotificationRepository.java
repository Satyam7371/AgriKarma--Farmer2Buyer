package org.example.agrikarmabackend.notification.repository;


import org.example.agrikarmabackend.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipient_EmailOrderByCreatedAtDesc(String email);

    long countByRecipient_EmailAndReadFalse(String email);
}

