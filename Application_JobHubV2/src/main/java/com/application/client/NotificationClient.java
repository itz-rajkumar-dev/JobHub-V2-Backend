package com.application.client;

import com.application.dto.InternalNotificationRequestDto;
import com.application.dto.NotificationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NotificationService")
public interface NotificationClient {

    @PostMapping("/notification/internal")
    NotificationResponseDto sendNotification(
            @RequestBody InternalNotificationRequestDto requestDto
    );
}