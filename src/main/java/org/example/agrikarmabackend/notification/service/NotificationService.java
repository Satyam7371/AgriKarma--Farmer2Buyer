package org.example.agrikarmabackend.notification.service;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.common.enums.NotificationType;
import org.example.agrikarmabackend.notification.entity.Notification;
import org.example.agrikarmabackend.notification.repository.NotificationRepository;
import org.example.agrikarmabackend.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;


     // Create a notification entry in DB
    public void createNotification(
            User recipient,
            String message,
            NotificationType type,
            Long referenceId
    ) {

        Notification notification = Notification.builder()
                .recipient(recipient)
                .message(message)
                .type(type)
                .referenceId(referenceId)
                .build();

        notificationRepository.save(notification);
    }


     // fetch notifications for user
    public List<Notification> getUserNotifications(String email) {
        return notificationRepository
                .findByRecipient_EmailOrderByCreatedAtDesc(email);
    }


     // count unread notifications.

    public long getUnreadCount(String email) {
        return notificationRepository
                .countByRecipient_EmailAndReadFalse(email);
    }


     // mark notification as read.
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setRead(true);
        notificationRepository.save(notification);
    }
}

