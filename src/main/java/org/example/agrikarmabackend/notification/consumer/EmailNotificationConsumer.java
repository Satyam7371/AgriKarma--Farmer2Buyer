package org.example.agrikarmabackend.notification.consumer;


import org.example.agrikarmabackend.event.DealEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationConsumer {

    @KafkaListener(topics = "deal-events", groupId = "email-group")
    public void sendEmail(DealEvent event) {

        System.out.println("EMAIL SENT:");
        System.out.println("Deal ID: " + event.requestId());
        System.out.println("Status: " + event.status());
    }
}

