package com.profile.client;

import com.profile.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/users/{userId}")
    UserResponseDto getByUserId(@PathVariable("userId") Long userId);
}