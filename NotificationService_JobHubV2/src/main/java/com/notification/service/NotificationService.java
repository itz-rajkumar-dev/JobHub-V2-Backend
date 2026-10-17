package com.notification.service;

import com.notification.dto.InternalNotificationRequestDto;
import com.notification.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {

    NotificationResponseDto saveInternalNotification(InternalNotificationRequestDto requestDto);

    NotificationResponseDto saveKafkaNotification(InternalNotificationRequestDto requestDto);
    List<NotificationResponseDto> getAllNotification();

    NotificationResponseDto getByNotificationId(Long notificationId);

    void removeNotification(Long notificationId);

    long getUnreadCount();

    void markAsRead(Long notificationId);
    void markAllAsRead();
}