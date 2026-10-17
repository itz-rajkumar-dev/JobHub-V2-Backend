package com.notification.mapper;

import com.notification.dto.NotificationRequestDto;
import com.notification.dto.NotificationResponseDto;
import com.notification.entity.Notification;

public class NotificationMapper {

    public static Notification toEntity(NotificationRequestDto dto) {

        Notification notification = new Notification();

        notification.setType(dto.getType());
        notification.setMessage(dto.getMessage());
        notification.setTitle(dto.getTitle());
        notification.setRelatedEntityId(dto.getRelatedEntityId());
        notification.setRelatedEntityType(dto.getRelatedEntityType());

        return notification;
    }

    public static NotificationResponseDto toResponseDto(Notification notification) {

        NotificationResponseDto responseDto = new NotificationResponseDto();

        responseDto.setNotificationId(notification.getNotificationId());

        responseDto.setUserId(notification.getUserId());

        responseDto.setType(notification.getType());

        responseDto.setTitle(notification.getTitle());

        responseDto.setMessage(notification.getMessage());

        responseDto.setIsRead(notification.getIsRead());

        responseDto.setRelatedEntityId(notification.getRelatedEntityId());

        responseDto.setRelatedEntityType(notification.getRelatedEntityType());

        responseDto.setCreatedAt(notification.getCreatedAt());

        responseDto.setCompanyId(notification.getCompanyId());

        responseDto.setCompanyName(notification.getCompanyName());

        responseDto.setCompanyLogo(notification.getCompanyLogo());

        return responseDto;
    }
}