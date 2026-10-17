package com.notification.serviceImpl;

import com.notification.client.UserClient;
import com.notification.dto.InternalNotificationRequestDto;
import com.notification.dto.NotificationResponseDto;
import com.notification.entity.Notification;
import com.notification.exception.NotificationNotFoundException;
import com.notification.exception.UnauthorizedException;
import com.notification.mapper.NotificationMapper;
import com.notification.repository.NotificationRepository;
import com.notification.security.AuthenticatedUser;
import com.notification.service.NotificationService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final UserClient userClient;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(
            UserClient userClient,
            NotificationRepository notificationRepository) {

        this.userClient = userClient;
        this.notificationRepository = notificationRepository;
    }

    private AuthenticatedUser getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof AuthenticatedUser)) {

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }

    @Override
    public NotificationResponseDto saveInternalNotification(InternalNotificationRequestDto requestDto) {

        // Receiver exist karta hai ya nahi
        userClient.getByUserId(requestDto.getReceiverId());

        Notification notification = new Notification();

        notification.setUserId(requestDto.getReceiverId());
        notification.setType(requestDto.getType());
        notification.setTitle(requestDto.getTitle());
        notification.setMessage(requestDto.getMessage());
        notification.setRelatedEntityId(requestDto.getRelatedEntityId());
        notification.setRelatedEntityType(requestDto.getRelatedEntityType());
        notification.setCompanyId(requestDto.getCompanyId());
         notification.setCompanyName(requestDto.getCompanyName());
         notification.setCompanyLogo(requestDto.getCompanyLogo());


        notification.setIsRead(false);

        Notification savedNotification = notificationRepository.save(notification);

        return NotificationMapper.toResponseDto(savedNotification);
    }

    @Override
    public List<NotificationResponseDto> getAllNotification() {

        AuthenticatedUser currentUser = getCurrentUser();

        return notificationRepository
                .findByUserIdOrderByCreatedAtDesc(currentUser.getUserId())
                .stream()
                .map(NotificationMapper::toResponseDto)
                .toList();
    }

    @Override
    public NotificationResponseDto getByNotificationId(
            Long notificationId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Notification notification = notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new NotificationNotFoundException("Notification Not Found: " + notificationId));

        if (!notification.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException(
                    "You can only access your own notification"
            );
        }

        return NotificationMapper.toResponseDto(notification);
    }

    @Override
    public void removeNotification(Long notificationId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification Not Found: " + notificationId));

        if (!notification.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only delete your own notification");
        }

        notificationRepository.delete(notification);
    }

    @Override
    public long getUnreadCount() {

        AuthenticatedUser currentUser = getCurrentUser();

        return notificationRepository.countByUserIdAndIsRead(currentUser.getUserId(), false);
    }

    @Override
    public void markAsRead(Long notificationId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException("Notification Not Found: " + notificationId));

        if (!notification.getUserId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You can only update your own notification");
        }

        notification.setIsRead(true);

        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead() {
        AuthenticatedUser currentUser = getCurrentUser();

        List<Notification> notifications =
                notificationRepository.findAllByUserIdAndIsReadFalse(
                        currentUser.getUserId()
                );

        notifications.forEach(notification ->
                notification.setIsRead(true)
        );

        notificationRepository.saveAll(notifications);
    }

    @Override
    public NotificationResponseDto saveKafkaNotification(
            InternalNotificationRequestDto requestDto
    ) {

        Notification notification = new Notification();

        notification.setUserId(requestDto.getReceiverId());
        notification.setType(requestDto.getType());
        notification.setTitle(requestDto.getTitle());
        notification.setMessage(requestDto.getMessage());
        notification.setRelatedEntityId(requestDto.getRelatedEntityId());
        notification.setRelatedEntityType(requestDto.getRelatedEntityType());
        notification.setCompanyId(requestDto.getCompanyId());
        notification.setCompanyName(requestDto.getCompanyName());
        notification.setCompanyLogo(requestDto.getCompanyLogo());
        notification.setIsRead(false);

        Notification savedNotification = notificationRepository.save(notification);

        return NotificationMapper.toResponseDto(savedNotification);
    }
}