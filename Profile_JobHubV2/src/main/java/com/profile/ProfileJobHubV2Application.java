package com.profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProfileJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ProfileJobHubV2Application.class, args);
	}

}
