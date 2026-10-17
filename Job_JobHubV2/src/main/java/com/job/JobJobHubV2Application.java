package com.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JobJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(JobJobHubV2Application.class, args);
	}

}
