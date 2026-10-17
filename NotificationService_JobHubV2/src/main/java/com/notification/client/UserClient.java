package com.notification.client;

import com.notification.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/users/internal/{userId}")
    UserResponseDto getByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/users/internal/all")
    List<UserResponseDto> getAllUsers();
}