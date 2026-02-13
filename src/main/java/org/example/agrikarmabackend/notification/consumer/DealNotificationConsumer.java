package org.example.agrikarmabackend.notification.consumer;

import org.example.agrikarmabackend.common.enums.NotificationType;
import org.example.agrikarmabackend.event.DealEvent;
import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.notification.service.NotificationService;
import org.example.agrikarmabackend.user.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DealNotificationConsumer {

    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @KafkaListener(topics = "deal-events", groupId = "agrikarma-group")
    public void handleDealEvent(DealEvent event) {

        var farmer = userRepository.findByEmail(event.farmerEmail()).orElseThrow();
        var buyer = userRepository.findByEmail(event.buyerEmail()).orElseThrow();

        switch (event.status()) {
            case PENDING -> notificationService.createNotification(
                    farmer,
                    "New request received",
                    NotificationType.REQUEST_CREATED,
                    event.requestId()
            );

            case ACCEPTED -> notificationService.createNotification(
                    buyer,
                    "Your request was accepted",
                    NotificationType.REQUEST_ACCEPTED,
                    event.requestId()
            );

            case REJECTED -> notificationService.createNotification(
                    buyer,
                    "Your request was rejected",
                    NotificationType.REQUEST_REJECTED,
                    event.requestId()
            );
        }
    }
}

