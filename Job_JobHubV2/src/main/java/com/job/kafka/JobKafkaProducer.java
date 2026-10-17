package com.job.kafka;

import com.job.event.JobSearchEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public JobKafkaProducer(
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendJobEvent(JobSearchEvent event) {

        kafkaTemplate.send(
                "job-events",
                event.getJobId().toString(),
                event
        );
    }
}