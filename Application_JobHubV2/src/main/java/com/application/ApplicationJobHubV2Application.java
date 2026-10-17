package com.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ApplicationJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationJobHubV2Application.class, args);
	}

}
