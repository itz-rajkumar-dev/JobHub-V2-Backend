package com.application.kafka;

import com.application.dto.ApplicationStatusUpdatedEventDto;
import com.application.dto.ApplicationSubmittedEventDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApplicationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ApplicationKafkaProducer(
            KafkaTemplate<String, Object> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendApplicationSubmittedEvent(
            ApplicationSubmittedEventDto event) {

        kafkaTemplate.send(
                "application-events",
                event.getApplicationId().toString(),
                event
        );
    }

    public void sendApplicationStatusUpdatedEvent(
            ApplicationStatusUpdatedEventDto event) {

        kafkaTemplate.send(
                "application-status-events",
                event.getApplicationId().toString(),
                event
        );
    }
}