package com.dashboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DashboardServiceJobHubV2Application {

	public static void main(String[] args) {
		SpringApplication.run(DashboardServiceJobHubV2Application.class, args);
	}

}
