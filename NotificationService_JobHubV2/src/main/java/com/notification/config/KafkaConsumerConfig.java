
        package com.notification.config;

import com.notification.dto.ApplicationStatusUpdatedEventDto;
import com.notification.dto.ApplicationSubmittedEventDto;
import com.notification.dto.JobNotificationEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {


    // =====================================================
    // 1. APPLICATION SUBMITTED
    // =====================================================

    @Bean
    public ConsumerFactory<String, ApplicationSubmittedEventDto>
    applicationSubmittedConsumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "notification-service-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JacksonJsonDeserializer.class
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        JacksonJsonDeserializer<ApplicationSubmittedEventDto> deserializer =
                new JacksonJsonDeserializer<>(
                        ApplicationSubmittedEventDto.class
                ).ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }


    @Bean(name = "applicationSubmittedKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ApplicationSubmittedEventDto>
    applicationSubmittedKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ApplicationSubmittedEventDto>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                applicationSubmittedConsumerFactory()
        );

        return factory;
    }


    // =====================================================
    // 2. APPLICATION STATUS UPDATED
    // =====================================================

    @Bean
    public ConsumerFactory<String, ApplicationStatusUpdatedEventDto>
    applicationStatusConsumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "notification-status-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JacksonJsonDeserializer.class
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        JacksonJsonDeserializer<ApplicationStatusUpdatedEventDto> deserializer =
                new JacksonJsonDeserializer<>(
                        ApplicationStatusUpdatedEventDto.class
                ).ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }


    @Bean(name = "applicationStatusKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, ApplicationStatusUpdatedEventDto>
    applicationStatusKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ApplicationStatusUpdatedEventDto>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                applicationStatusConsumerFactory()
        );

        return factory;
    }


    // =====================================================
// 3. JOB NOTIFICATION
// =====================================================

    @Bean
    public ConsumerFactory<String, JobNotificationEvent>
    jobNotificationConsumerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "kafka:9092"
        );

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "notification-job-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JacksonJsonDeserializer.class
        );

        config.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest"
        );

        JacksonJsonDeserializer<JobNotificationEvent> deserializer =
                new JacksonJsonDeserializer<>(
                        JobNotificationEvent.class
                ).ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }


    @Bean(name = "jobNotificationKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, JobNotificationEvent>
    jobNotificationKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, JobNotificationEvent>
                factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(
                jobNotificationConsumerFactory()
        );

        return factory;
    }
}

