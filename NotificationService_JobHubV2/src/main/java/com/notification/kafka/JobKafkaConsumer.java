package com.notification.kafka;

import com.notification.client.AuthClient;
import com.notification.client.UserClient;
import com.notification.dto.InternalNotificationRequestDto;
import com.notification.dto.JobNotificationEvent;
import com.notification.dto.UserResponseDto;
import com.notification.entity.NotificationType;
import com.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JobKafkaConsumer {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private AuthClient authClient;

    @KafkaListener(
            topics = "job-events",
            groupId = "notification-job-group",
            containerFactory = "jobNotificationKafkaListenerContainerFactory"
    )
    public void consumeJobEvent(JobNotificationEvent event) {

        System.out.println("========== JOB EVENT ==========");

        System.out.println("Job ID     : " + event.getJobId());
        System.out.println("Title      : " + event.getTitle());
        System.out.println("Company    : " + event.getCompanyName());
        System.out.println("Location   : " + event.getLocation());

        System.out.println("================================");


        // Get all users from User Service
        List<UserResponseDto> users = userClient.getAllUsers();


        Set<Long> jobSeekerIds =
                new HashSet<>(authClient.getJobSeekerUserIds());


        // Check every user's location
        for (UserResponseDto user : users) {

            if (jobSeekerIds.contains(user.getUserId())
                    && user.getLocation() != null
                    && event.getLocation() != null
                    && user.getLocation().equalsIgnoreCase(event.getLocation())) {


                InternalNotificationRequestDto notification =
                        InternalNotificationRequestDto.builder()

                                .receiverId(user.getUserId())

                                .type(NotificationType.JOB_LOCATION)

                                .title("New Job Near You")

                                .message(
                                        "A new " + event.getTitle()
                                                + " job has been posted in "
                                                + event.getLocation()
                                                + " by "
                                                + event.getCompanyName()
                                )

                                .companyId(event.getCompanyId())
                                .companyName(event.getCompanyName())
                                .companyLogo(event.getCompanyLogo())

                                .relatedEntityId(event.getJobId())
                                .relatedEntityType("JOB")

                                .build();


                notificationService
                        .saveInternalNotification(notification);


                System.out.println(
                        "JOB NOTIFICATION SENT TO USER : "
                                + user.getUserId()
                );
            }
        }
    }
}