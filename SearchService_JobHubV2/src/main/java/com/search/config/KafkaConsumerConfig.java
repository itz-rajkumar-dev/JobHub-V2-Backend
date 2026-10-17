package com.search.config;

import com.search.event.JobSearchEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${spring.kafka.bootstrap-servers:kafka:9092}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, JobSearchEvent> consumerFactory() {


        Map<String, Object> config = new HashMap<>();


        config.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers
        );
        System.out.println("KAFKA SERVER FROM CONFIG = " + bootstrapServers);
        System.out.println("KAFKA SERVER IN MAP = " + config.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));

        config.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                "search-service-group"
        );

        config.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class
        );

        config.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JacksonJsonDeserializer.class
        );

        JacksonJsonDeserializer<JobSearchEvent> deserializer =
                new JacksonJsonDeserializer<>(JobSearchEvent.class);

        deserializer.ignoreTypeHeaders();
        deserializer.addTrustedPackages(
                "com.job.event",
                "com.search.event"
        );

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobSearchEvent>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, JobSearchEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}