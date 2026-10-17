package com.notification.dto;

import com.notification.entity.NotificationType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDto {

    private Long notificationId;

    private Long userId;

    private NotificationType type;

    private String title;

    private String message;

    private Boolean isRead;

    private Long companyId;

    private String companyName;

    private String companyLogo;

    private Long relatedEntityId;

    private String relatedEntityType;

    private LocalDateTime createdAt;
}
