package com.search.config;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchTestConfig {

    @Bean
    CommandLineRunner testOpenSearch(OpenSearchClient client) {

        return args -> {

            var response = client.count(c -> c.index("jobs"));

            System.out.println("=================================");
            System.out.println("OpenSearch Connected Successfully");
            System.out.println("Jobs Count: " + response.count());
            System.out.println("=================================");
        };
    }
}