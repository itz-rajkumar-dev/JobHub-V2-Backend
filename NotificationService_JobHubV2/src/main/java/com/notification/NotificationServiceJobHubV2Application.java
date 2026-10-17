package com.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificationServiceJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceJobHubV2Application.class, args);
	}

}
