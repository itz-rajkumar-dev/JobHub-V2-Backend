package com.engagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EngagementServiceJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(EngagementServiceJobHubV2Application.class, args);
	}

}
