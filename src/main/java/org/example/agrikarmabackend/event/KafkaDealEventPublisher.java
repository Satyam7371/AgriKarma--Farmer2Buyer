package org.example.agrikarmabackend.event;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaDealEventPublisher implements DealEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "deal-events";

    @Override
    public void publish(DealEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}

