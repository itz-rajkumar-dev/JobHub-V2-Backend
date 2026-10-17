package com.search.kafka;

import com.search.event.JobSearchEvent;
import com.search.dto.JobSearchResponseDto;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobSearchEventConsumer {

    private final OpenSearchClient openSearchClient;

    public JobSearchEventConsumer(OpenSearchClient openSearchClient) {
        this.openSearchClient = openSearchClient;
    }

    @KafkaListener(
            topics = "job-events",
            groupId = "search-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeJobEvent(JobSearchEvent event) {

        try {

            System.out.println("=================================");
            System.out.println("Job Event Received");
            System.out.println("Event Type: " + event.getEventType());
            System.out.println("Job ID: " + event.getJobId());
            System.out.println("Job Title: " + event.getTitle());
            System.out.println("Company: " + event.getCompanyName());
            System.out.println("Category: " + event.getCategoryName());

            if ("JOB_CREATED".equals(event.getEventType())
                    || "JOB_UPDATED".equals(event.getEventType())) {

                JobSearchResponseDto job = JobSearchResponseDto.builder()
                        .jobId(event.getJobId())
                        .title(event.getTitle())
                        .description(event.getDescription())
                        .companyId(event.getCompanyId())
                        .companyLogo(event.getCompanyLogo())
                        .categoryId(event.getCategoryId())
                        .salaryMin(event.getSalaryMin())
                        .salaryMax(event.getSalaryMax())
                        .experienceMin(event.getExperienceMin())
                        .experienceMax(event.getExperienceMax())
                        .jobType(event.getJobType())
                        .workMode(event.getWorkMode())
                        .location(event.getLocation())
                        .skills(event.getSkills())
                        .vacancies(event.getVacancies())
                        .viewsCount(event.getViewsCount())
                        .applicationsCount(event.getApplicationsCount())
                        .featured(event.getFeatured())
                        .deadline(event.getDeadline())
                        .status(event.getStatus())
                        .companyName(event.getCompanyName())
                        .categoryName(event.getCategoryName())
                        .createdAt(event.getCreatedAt())
                        .build();

                openSearchClient.index(index -> index
                        .index("jobs")
                        .id(event.getJobId().toString())
                        .document(job)
                );

                System.out.println("Job indexed successfully in OpenSearch");
                System.out.println("OpenSearch Job ID: " + event.getJobId());
            }

            else if ("JOB_DELETED".equals(event.getEventType())) {

                openSearchClient.delete(delete -> delete
                        .index("jobs")
                        .id(event.getJobId().toString())
                );

                System.out.println("Job deleted from OpenSearch");
                System.out.println("OpenSearch Job ID: " + event.getJobId());
            }

            System.out.println("=================================");

        } catch (Exception e) {

            System.out.println("=================================");
            System.out.println("Failed to process Job Event");
            System.out.println("Job ID: " + event.getJobId());
            System.out.println("Error: " + e.getMessage());
            System.out.println("Deadline: " + event.getDeadline());
            System.out.println("=================================");

            throw new RuntimeException(e);
        }
    }
}