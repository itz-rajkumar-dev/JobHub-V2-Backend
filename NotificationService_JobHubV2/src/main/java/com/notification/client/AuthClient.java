package com.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "AuthService")
public interface AuthClient {

    @GetMapping("/auth/internal/job-seeker-ids")
    List<Long> getJobSeekerUserIds();
}