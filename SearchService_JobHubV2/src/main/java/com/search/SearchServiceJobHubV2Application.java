package com.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SearchServiceJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceJobHubV2Application.class, args);
	}

}
