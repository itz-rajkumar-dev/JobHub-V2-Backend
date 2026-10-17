package com.notification.controller;

import com.notification.dto.InternalNotificationRequestDto;
import com.notification.dto.NotificationResponseDto;
import com.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/internal")
    public NotificationResponseDto saveInternalNotification(@Valid @RequestBody InternalNotificationRequestDto requestDto) {

        return notificationService.saveInternalNotification(requestDto);
    }

    @GetMapping("/me")
    public List<NotificationResponseDto> getAllNotification() {

        return notificationService.getAllNotification();
    }

    @GetMapping("/unread-count")
    public long getUnreadCount() {

        return notificationService.getUnreadCount();
    }

    @PutMapping("/{id}/read")

    public ResponseEntity<String> markAsRead(@PathVariable("id") Long notificationId) {

        notificationService.markAsRead(notificationId);

        return ResponseEntity.ok("Notification Marked As Read");
    }

    @GetMapping("/{id}")
    public NotificationResponseDto getByNotificationId(@PathVariable("id") Long notificationId) {

        return notificationService.getByNotificationId(notificationId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeNotification(
            @PathVariable("id") Long notificationId) {

        notificationService.removeNotification(notificationId);

        return ResponseEntity.ok("Notification Removed Successfully");
    }

    @PutMapping("/read-all")
    public ResponseEntity<String> markAllAsRead() {

        notificationService.markAllAsRead();

        return ResponseEntity.ok("All Notifications Marked As Read");
    }
}