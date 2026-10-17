package com.savedjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SavedJobJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(SavedJobJobHubV2Application.class, args);
	}

}
