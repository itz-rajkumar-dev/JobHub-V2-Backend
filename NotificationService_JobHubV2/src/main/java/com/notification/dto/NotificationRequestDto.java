package com.notification.dto;

import com.notification.entity.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequestDto {

    @NotNull(message = "Notification type is required")
    private NotificationType type;

    @NotBlank(message = "Notification title is required")
    @Size(max = 200, message = "Notification title cannot exceed 200 characters")
    private String title;

    @NotBlank(message = "Notification message is required")
    private String message;

    private Long relatedEntityId;

    @Size(max = 50, message = "Related entity type cannot exceed 50 characters")
    private String relatedEntityType;
}