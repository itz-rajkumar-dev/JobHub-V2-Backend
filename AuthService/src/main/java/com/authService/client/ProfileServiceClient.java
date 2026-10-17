package com.authService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ProfileService")
public interface ProfileServiceClient {

    @PostMapping("/profile/internal")
    void createProfile(@RequestParam("userId") Long userId , @RequestParam String role);
}