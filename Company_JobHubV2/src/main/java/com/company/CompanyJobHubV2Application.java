package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CompanyJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(CompanyJobHubV2Application.class, args);
	}

}
