package com.notification.kafka;

import com.notification.client.CompanyClient;
import com.notification.dto.ApplicationStatusUpdatedEventDto;
import com.notification.dto.ApplicationSubmittedEventDto;
import com.notification.dto.CompanyResponseDto;
import com.notification.dto.InternalNotificationRequestDto;
import com.notification.entity.NotificationType;
import com.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationKafkaConsumer {

    private final CompanyClient companyClient;
    private final NotificationService notificationService;

    public ApplicationKafkaConsumer(
            CompanyClient companyClient,
            NotificationService notificationService
    ) {
        this.companyClient = companyClient;
        this.notificationService = notificationService;
    }


    // =====================================================
    // 1. APPLICATION SUBMITTED
    // =====================================================

    @KafkaListener(
            topics = "application-events",
            groupId = "notification-service-group",
            containerFactory = "applicationSubmittedKafkaListenerContainerFactory"
    )
    public void consumeApplicationSubmittedEvent(
            ApplicationSubmittedEventDto event
    ) {

        System.out.println("========== KAFKA EVENT RECEIVED ==========");
        System.out.println("Application ID : " + event.getApplicationId());
        System.out.println("Job ID         : " + event.getJobId());
        System.out.println("Applicant ID   : " + event.getApplicantId());
        System.out.println("Company ID     : " + event.getCompanyId());
        System.out.println("Applicant Name : " + event.getApplicantName());
        System.out.println("Job Title      : " + event.getJobTitle());
        System.out.println("==========================================");


        // =====================================================
        // GET COMPANY DETAILS
        // =====================================================

        CompanyResponseDto company =
                companyClient.getCompanyById(event.getCompanyId());

        System.out.println("Company Name : " + company.getCompanyName());
        System.out.println("Company Logo : " + company.getLogoUrl());


        // =====================================================
        // 1. APPLICANT NOTIFICATION
        // =====================================================

        InternalNotificationRequestDto applicantNotification =
                InternalNotificationRequestDto.builder()
                        .receiverId(event.getApplicantId())
                        .type(NotificationType.APPLICATION_SUBMITTED)
                        .title("Application Submitted")
                        .message(
                                "Your application for "
                                        + event.getJobTitle()
                                        + " has been submitted successfully."
                        )
                        .relatedEntityId(event.getApplicationId())
                        .relatedEntityType("APPLICATION")

                        // Company details
                        .companyId(company.getCompanyId())
                        .companyName(company.getCompanyName())
                        .companyLogo(company.getLogoUrl())

                        .build();

        notificationService.saveKafkaNotification(
                applicantNotification
        );

        System.out.println(
                "Applicant Notification Sent to: "
                        + event.getApplicantId()
        );


        // =====================================================
        // 2. EMPLOYER NOTIFICATION
        // =====================================================

        try {

            Long ownerId = company.getOwnerId();

            InternalNotificationRequestDto employerNotification =
                    InternalNotificationRequestDto.builder()
                            .receiverId(ownerId)
                            .type(NotificationType.APPLICATION_SUBMITTED)
                            .title("New Application Received")
                            .message(
                                    event.getApplicantName()
                                            + " has applied for your job: "
                                            + event.getJobTitle()
                            )
                            .relatedEntityId(event.getApplicationId())
                            .relatedEntityType("APPLICATION")

                            // Company details
                            .companyId(company.getCompanyId())
                            .companyName(company.getCompanyName())
                            .companyLogo(company.getLogoUrl())

                            .build();

            notificationService.saveKafkaNotification(
                    employerNotification
            );

            System.out.println(
                    "Employer Notification Sent to: "
                            + ownerId
            );

        } catch (Exception e) {

            System.out.println(
                    "Employer notification failed: "
                            + e.getMessage()
            );
        }

        System.out.println("==========================================");
    }



// =====================================================
// 2. APPLICATION STATUS UPDATED
// =====================================================

    @KafkaListener(
            topics = "application-status-events",
            groupId = "notification-status-group",
            containerFactory = "applicationStatusKafkaListenerContainerFactory"
    )
    public void consumeApplicationStatusUpdatedEvent(
            ApplicationStatusUpdatedEventDto event
    ) {

        System.out.println("========== STATUS UPDATE EVENT ==========");
        System.out.println("Application ID : " + event.getApplicationId());
        System.out.println("Applicant ID   : " + event.getApplicantId());
        System.out.println("Company ID     : " + event.getCompanyId());
        System.out.println("Job ID         : " + event.getJobId());
        System.out.println("Job Title      : " + event.getJobTitle());
        System.out.println("Status         : " + event.getStatus());
        System.out.println("=========================================");


        // =====================================================
        // GET COMPANY DETAILS
        // =====================================================

        CompanyResponseDto company =
                companyClient.getCompanyById(event.getCompanyId());

        System.out.println("Company Name : " + company.getCompanyName());
        System.out.println("Company Logo : " + company.getLogoUrl());


        // =====================================================
        // STATUS → NOTIFICATION TYPE
        // =====================================================

        NotificationType notificationType;
        String title;
        String message;


        switch (event.getStatus().trim().toUpperCase()) {

            case "SHORTLISTED":

                notificationType =
                        NotificationType.APPLICATION_SHORTLISTED;

                title = "Application Shortlisted 🎉";

                message =
                        "Congratulations! Your application for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " has been shortlisted.";

                break;


            case "INTERVIEW_SCHEDULED":

                notificationType =
                        NotificationType.INTERVIEW_SCHEDULED;

                title = "Interview Scheduled 📅";

                message =
                        "Your interview for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " has been scheduled.";

                break;


            case "REJECTED":

                notificationType =
                        NotificationType.APPLICATION_REJECTED;

                title = "Application Update";

                message =
                        "Your application for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " was not selected.";

                break;


            case "UNDER_REVIEW":

                notificationType =
                        NotificationType.APPLICATION_UNDER_REVIEW;

                title = "Application Under Review";

                message =
                        "Your application for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " is now under review.";

                break;


            case "OFFERED":

                notificationType =
                        NotificationType.APPLICATION_OFFERED;

                title = "Job Offer Received 🎉";

                message =
                        "You have received a job offer for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + ".";

                break;


            case "HIRED":

                notificationType =
                        NotificationType.APPLICATION_HIRED;

                title = "Congratulations! 🎉";

                message =
                        "Congratulations! You have been hired for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + ".";

                break;


            case "INTERVIEW_COMPLETED":

                notificationType =
                        NotificationType.APPLICATION_STATUS_CHANGED;

                title = "Interview Completed";

                message =
                        "Your interview for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " has been completed.";

                break;


            case "WITHDRAWN":

                notificationType =
                        NotificationType.APPLICATION_STATUS_CHANGED;

                title = "Application Withdrawn";

                message =
                        "Your application for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " has been withdrawn.";

                break;


            default:

                notificationType =
                        NotificationType.APPLICATION_STATUS_CHANGED;

                title = "Application Status Updated";

                message =
                        "Your application status for "
                                + event.getJobTitle()
                                + " at "
                                + company.getCompanyName()
                                + " has been updated to "
                                + event.getStatus()
                                + ".";

                break;
        }

        // =====================================================
        // CREATE NOTIFICATION
        // =====================================================

        InternalNotificationRequestDto notification =
                InternalNotificationRequestDto.builder()

                        .receiverId(event.getApplicantId())

                        .type(notificationType)

                        .title(title)

                        .message(message)

                        .relatedEntityId(event.getApplicationId())

                        .relatedEntityType("APPLICATION")

                        // Company details
                        .companyId(company.getCompanyId())
                        .companyName(company.getCompanyName())
                        .companyLogo(company.getLogoUrl())

                        .build();


        // =====================================================
        // SAVE NOTIFICATION
        // =====================================================

        notificationService.saveKafkaNotification(
                notification
        );


        System.out.println(
                "Status notification sent to applicant: "
                        + event.getApplicantId()
                        + " | Type: "
                        + notificationType
        );

        System.out.println("=========================================");
    }
}