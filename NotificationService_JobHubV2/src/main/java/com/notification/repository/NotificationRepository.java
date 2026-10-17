package com.notification.repository;

import com.notification.entity.Notification;
import com.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdAndIsRead(Long userId, Boolean isRead);

    long countByUserIdAndIsRead(Long userId, Boolean isRead);

    List<Notification> findByUserIdAndType(Long userId, NotificationType type);

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Notification> findAllByUserIdAndIsReadFalse(Long userId);
}