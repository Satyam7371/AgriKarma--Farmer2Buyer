package org.example.agrikarmabackend.notification.consumer;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.event.DealEvent;
import org.example.agrikarmabackend.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailNotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "deal-events", groupId = "email-group")
    public void sendEmail(DealEvent event) {

        String subject = "AgriKarma Notification";
        String body;

        switch (event.status()) {
            case PENDING -> body = "A new request has been created.";
            case ACCEPTED -> body = "Your request has been accepted.";
            case REJECTED -> body = "Your request has been rejected.";
            default -> body = "Update on your request.";
        }

        // Decide recipient
        String recipient = switch (event.status()) {
            case PENDING -> event.farmerEmail();
            case ACCEPTED, REJECTED -> event.buyerEmail();
        };

        emailService.sendEmail(recipient, subject, body);
    }
}

