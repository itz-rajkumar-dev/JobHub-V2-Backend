package com.authService.client;

import com.authService.dto.UserCreateRequestDto;
import com.authService.dto.UserCreateResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "UserService"

)
public interface UserServiceClient {

    @PostMapping("/users/internal")
    UserCreateResponseDto createUser(
            @RequestBody UserCreateRequestDto request
    );
}