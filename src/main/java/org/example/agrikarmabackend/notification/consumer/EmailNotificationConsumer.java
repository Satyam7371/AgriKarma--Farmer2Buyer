package org.example.agrikarmabackend.notification.consumer;


import lombok.RequiredArgsConstructor;
import org.example.agrikarmabackend.event.DealEvent;
import org.example.agrikarmabackend.notification.service.EmailService;
import org.example.agrikarmabackend.notification.service.EmailTemplateBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailNotificationConsumer {

    private final EmailService emailService;
    private final EmailTemplateBuilder templateBuilder;

    @KafkaListener(topics = "deal-events", groupId = "email-group")
    public void sendEmail(DealEvent event) {

        String subject = "AgriKarma Update";
        String message;
        String title;

        switch (event.status()) {

            case PENDING -> {
                title = "New Request Received";
                message = "You have received a new deal request.";
            }

            case ACCEPTED -> {
                title = "Request Accepted";
                message = "Your request has been accepted by the farmer.";
            }

            case REJECTED -> {
                title = "Request Rejected";
                message = "Your request has been rejected.";
            }

            default -> {
                title = "Update";
                message = "There is an update on your request.";
            }
        }

        String recipient = switch (event.status()) {
            case PENDING -> event.farmerEmail();
            case ACCEPTED, REJECTED -> event.buyerEmail();
        };

        String html = templateBuilder.buildTemplate(
                title,
                message,
                event.status().name()
        );

        emailService.sendHtmlEmail(recipient, subject, html);
    }
}

