package com.application.serviceImpl;

import com.application.client.CompanyClient;
import com.application.client.JobClient;
import com.application.client.UserClient;
import com.application.dto.*;
import com.application.entity.Application;
import com.application.entity.ApplicationStatus;
import com.application.exception.ApplicationAlreadyAppliedException;
import com.application.exception.ApplicationNotFoundException;
import com.application.exception.UnauthorizedException;
import com.application.kafka.ApplicationKafkaProducer;
import com.application.mapper.ApplicationMapper;
import com.application.repository.ApplicationRepository;
import com.application.security.AuthenticatedUser;
import com.application.service.ApplicationService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import com.application.storage.FileStorageService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {


    private final ApplicationRepository applicationRepository;
    private final UserClient userClient;
    private final JobClient jobClient;
    private final FileStorageService fileStorageService;
    private final CompanyClient companyClient;
    private  final ApplicationKafkaProducer applicationKafkaProducer;
    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository
            ,JobClient jobClient
            ,UserClient userClient,
            FileStorageService fileStorageService,
            CompanyClient companyClient,
            ApplicationKafkaProducer applicationKafkaProducer

            )
    {

        this.applicationRepository = applicationRepository;
        this.jobClient=jobClient;
        this.userClient=userClient;
        this.fileStorageService=fileStorageService;
        this.companyClient=companyClient;
        this.applicationKafkaProducer=applicationKafkaProducer;
    }


    private AuthenticatedUser getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser)) {

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }


    @Override
    public ApplicationResponseDto applyApplication(
            ApplicationRequestDto requestDto) {

        AuthenticatedUser currentUser = getCurrentUser();

        // 1. Get applicant details from User Service
        UserResponseDto user = userClient.getByUserId(currentUser.getUserId());


        // 2. Get job details from Job Service
        JobResponseDto job = jobClient.getJobById(requestDto.getJobId());


        if (!"OPEN".equals(job.getStatus())) {
            throw new UnauthorizedException("This job is not open for application");
        }

        // 4. Check duplicate application
        boolean exists = applicationRepository.existsByApplicantIdAndJobId(
                        currentUser.getUserId(),
                        requestDto.getJobId());

        if (exists) {
            throw new ApplicationAlreadyAppliedException("You have already applied for this job");
        }


        Application application = ApplicationMapper.toEntity(requestDto);


        application.setApplicantId(currentUser.getUserId());
        application.setApplicantName(user.getFullname());
        application.setApplicantEmail(user.getEmail());
        application.setCompanyId(job.getCompanyId());
        application.setStatus(ApplicationStatus.APPLIED);
        application.setApplicantPhone(user.getPhone());

        String resumePath=fileStorageService.storeFile(requestDto.getResume());

        application.setResumeUrl(resumePath);
        application.setResumeFileName(requestDto.getResume().getOriginalFilename());


// Additional Documents
        if (requestDto.getAdditionalDocuments() != null
                && !requestDto.getAdditionalDocuments().isEmpty()) {

            List<String> additionalDocumentUrls = requestDto
                    .getAdditionalDocuments()
                    .stream()
                    .filter(file -> file != null && !file.isEmpty())
                    .map(fileStorageService::storeFile)
                    .toList();

            List<String> additionalDocumentFileNames = requestDto
                    .getAdditionalDocuments()
                    .stream()
                    .filter(file -> file != null && !file.isEmpty())
                    .map(file -> file.getOriginalFilename())
                    .toList();

            application.setAdditionalDocumentUrls(additionalDocumentUrls);

            application.setAdditionalDocumentFileNames(
                    additionalDocumentFileNames
            );

        }

        Application savedApplication = applicationRepository.save(application);

        ApplicationSubmittedEventDto event =
                ApplicationSubmittedEventDto.builder()
                        .applicationId(savedApplication.getApplicationId())
                        .jobId(savedApplication.getJobId())
                        .applicantId(savedApplication.getApplicantId())
                        .companyId(savedApplication.getCompanyId())
                        .applicantName(savedApplication.getApplicantName())
                        .jobTitle(job.getTitle())
                        .build();

        applicationKafkaProducer.sendApplicationSubmittedEvent(event);
        return ApplicationMapper.toResponseDto(savedApplication);
    }


    @Override
    public List<ApplicationResponseDto> getAllApplication() {

        AuthenticatedUser currentUser = getCurrentUser();

        // JOB SEEKER sirf apni applications with company/job details
        if ("JOB_SEEKER".equals(currentUser.getRole())) {

            return applicationRepository
                    .findByApplicantId(currentUser.getUserId())
                    .stream()
                    .map(application -> {

                        String companyName = null;
                        String companyLogo = null;
                        String jobTitle = "Job Unavailable";
                        String profileImage = null;

                        try {
                            CompanyResponseDto company =
                                    companyClient.getCompanyById(application.getCompanyId());

                            if (company != null) {
                                companyName = company.getCompanyName();
                                companyLogo = company.getCompanyLogo();
                            }
                        } catch (FeignException.NotFound e) {
                            System.out.println(
                                    "Company not found: " + application.getCompanyId()
                            );
                        }

                        try {
                            JobResponseDto job =
                                    jobClient.getJobById(application.getJobId());

                            if (job != null) {
                                jobTitle = job.getTitle();
                            }

                        } catch (FeignException.NotFound e) {
                            System.out.println(
                                    "Job not found: " + application.getJobId()
                            );
                        }

                        try {
                            UserResponseDto user =
                                    userClient.getByUserId(
                                            application.getApplicantId()
                                    );

                            if (user != null) {
                                profileImage = user.getProfileImage();
                            }

                        } catch (FeignException.NotFound e) {
                            System.out.println(
                                    "User not found: "
                                            + application.getApplicantId()
                            );
                        }

                        return ApplicationMapper.toResponseDto(
                                application,
                                companyName,
                                companyLogo,
                                jobTitle,
                                profileImage
                        );
                    })
                    .toList();
        }

        // EMPLOYER sirf apni companies ki applications
        if ("EMPLOYER".equals(currentUser.getRole())) {

            List<CompanyResponseDto> companies = companyClient.getCompaniesByOwnerId(currentUser.getUserId());

            return companies.stream()
                    .flatMap(company ->
                            applicationRepository.findByCompanyId(company.getCompanyId()).stream()
                    )
                    .map(ApplicationMapper::toResponseDto)
                    .toList();
        }

        // ADMIN saari applications
        if ("ADMIN".equals(currentUser.getRole())) {

            return applicationRepository
                    .findAll()
                    .stream()
                    .map(ApplicationMapper::toResponseDto)
                    .toList();
        }

        throw new UnauthorizedException("Invalid user role");
    }

    @Override
    public ApplicationResponseDto getByApplicationId(Long applicationId) {

        AuthenticatedUser currentUser = getCurrentUser();

        // Pehle application find karo
        Application application = applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found: " + applicationId));

        // JOB_SEEKER sirf apni application
        if ("JOB_SEEKER".equals(currentUser.getRole())) {

            if (!application.getApplicantId()
                    .equals(currentUser.getUserId())) {

                throw new AccessDeniedException("You are not allowed to view this application");
            }

            return ApplicationMapper.toResponseDto(application);
        }

        // EMPLOYER sirf apni company ki application
        if ("EMPLOYER".equals(currentUser.getRole())) {

            List<CompanyResponseDto> companies =
                    companyClient.getCompaniesByOwnerId(currentUser.getUserId());

            boolean ownsCompany = companies.stream()
                    .anyMatch(company -> company.getCompanyId().equals(application.getCompanyId()));

            if (!ownsCompany) {

                throw new AccessDeniedException("You are not allowed to view this application");
            }

            return ApplicationMapper.toResponseDto(application);
        }

        // ADMIN koi bhi application
        if ("ADMIN".equals(currentUser.getRole())) {

            return ApplicationMapper.toResponseDto(application);
        }

        throw new UnauthorizedException("Invalid user role");
    }

    @Override
    public PageResponseDto<ApplicationResponseDto> getEmployerApplication(
            int page,
            int size,
            String search) {

        AuthenticatedUser currentUser = getCurrentUser();

        // 1. Get employer's companies
        List<CompanyResponseDto> companies =
                companyClient.getCompaniesByOwnerId(
                        currentUser.getUserId()
                );

        // 2. Extract company IDs
        List<Long> companyIds = companies.stream()
                .map(CompanyResponseDto::getCompanyId)
                .toList();

        // 3. Pagination
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "appliedAt")
        );

        // 4. Get applications
        Page<Application> applications;

        if (search == null || search.trim().isEmpty()) {

            applications = applicationRepository.findByCompanyIdIn(
                    companyIds,
                    pageable
            );

        } else {

            applications = applicationRepository.searchEmployerApplications(
                    companyIds,
                    search.trim(),
                    pageable
            );
        }

        // 5. Status counts
        long appliedCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.APPLIED
                );

        long underReviewCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.UNDER_REVIEW
                );

        long shortlistedCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.SHORTLISTED
                );

        long interviewScheduledCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.INTERVIEW_SCHEDULED
                );

        long interviewCompletedCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.INTERVIEW_COMPLETED
                );

        long offeredCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.OFFERED
                );

        long hiredCount =
                applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.HIRED
                );

        long rejectedCount = applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.REJECTED);

        long withdrawCount = applicationRepository.countByCompanyIdInAndStatus(
                        companyIds,
                        ApplicationStatus.WITHDRAWN
                );

        // 6. Convert applications to response DTO
        List<ApplicationResponseDto> content = applications.getContent()
                        .stream()
                        .map(application -> {

                            CompanyResponseDto company = companyClient.getCompanyById(application.getCompanyId());

                            String jobTitle = "Job Unavailable";
                            String profileImage = null;

                            try {
                                JobResponseDto job =
                                        jobClient.getJobById(application.getJobId());

                                jobTitle = job.getTitle();

                            } catch (FeignException.NotFound e) {
                                System.out.println(
                                        "Job not found: " + application.getJobId()
                                );
                            }
                            try {
                                UserResponseDto user =
                                        userClient.getByUserId(
                                                application.getApplicantId()
                                        );

                                profileImage = user.getProfileImage();

                            } catch (FeignException.NotFound e) {
                                System.out.println(
                                        "User not found: " + application.getApplicantId()
                                );
                            }


                            return ApplicationMapper.toResponseDto(
                                    application,
                                    company.getCompanyName(),
                                    company.getCompanyLogo(),
                                    jobTitle,
                                    profileImage
                            );
                        })
                        .toList();

        // 7. Return paginated response + counts
        return PageResponseDto.<ApplicationResponseDto>builder()
                .content(content)
                .page(applications.getNumber())
                .size(applications.getSize())
                .totalElements(applications.getTotalElements())
                .totalPages(applications.getTotalPages())
                .hasNext(applications.hasNext())
                .hasPrevious(applications.hasPrevious())

                .appliedCount(appliedCount)
                .underReviewCount(underReviewCount)
                .shortlistedCount(shortlistedCount)
                .interviewScheduledCount(interviewScheduledCount)
                .interviewCompletedCount(interviewCompletedCount)
                .offeredCount(offeredCount)
                .hiredCount(hiredCount)
                .rejectedCount(rejectedCount)
                .withdrawCount(withdrawCount)

                .build();
    }

    @Override
    public ApplicationResponseDto updateApplication(ApplicationUpdateRequestDto requestDto, Long applicationId) {

        AuthenticatedUser currentUser = getCurrentUser();


        Application application = applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found: " + applicationId));

        // Applicant ownership check
        if (!application.getApplicantId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You are not allowed to update this application");
        }

        ApplicationMapper.updateEntity(requestDto, application);

        // Resume is optional during update
        if (requestDto.getResume() != null && !requestDto.getResume().isEmpty()) {

            String resumePath = fileStorageService.storeFile(requestDto.getResume());

            application.setResumeUrl(resumePath);

            application.setResumeFileName(requestDto.getResume().getOriginalFilename());
        }

        Application savedApplication = applicationRepository.save(application);

        return ApplicationMapper.toResponseDto(savedApplication);
    }


    @Override
    public void deleteApplication(Long applicationId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Application application = applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new ApplicationNotFoundException("Application Not Found: " + applicationId));

        if (!application.getApplicantId().equals(currentUser.getUserId())) {

            throw new AccessDeniedException("You are not allowed to delete this application");
        }

        applicationRepository.delete(application);
    }

    @Override
    public ApplicationResponseDto updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status) {

        AuthenticatedUser currentUser = getCurrentUser();

        // Only EMPLOYER can update application status
        if (!"EMPLOYER".equals(currentUser.getRole())) {
            throw new AccessDeniedException(
                    "Only employer can update application status"
            );
        }

        // Find application
        Application application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() ->
                        new ApplicationNotFoundException(
                                "Application Not Found: " + applicationId
                        )
                );

        // Get employer's companies
        List<CompanyResponseDto> companies =
                companyClient.getCompaniesByOwnerId(
                        currentUser.getUserId()
                );

        // Check whether this application belongs to employer's company
        boolean ownsCompany = companies.stream()
                .anyMatch(company ->
                        company.getCompanyId()
                                .equals(application.getCompanyId())
                );

        if (!ownsCompany) {
            throw new AccessDeniedException(
                    "You are not authorized to update this application"
            );
        }

        // Update status
        application.setStatus(status);
        JobResponseDto job = jobClient.getJobById(application.getJobId());
        Application savedApplication =
                applicationRepository.save(application);

        ApplicationStatusUpdatedEventDto event =
                ApplicationStatusUpdatedEventDto.builder()
                        .applicationId(savedApplication.getApplicationId())
                        .applicantId(savedApplication.getApplicantId())
                        .companyId(savedApplication.getCompanyId())
                        .jobId(savedApplication.getJobId())
                        .applicantName(savedApplication.getApplicantName())
                        .jobTitle(job.getTitle())
                        .status(savedApplication.getStatus().name())
                        .build();

        applicationKafkaProducer.sendApplicationStatusUpdatedEvent(event);

        return ApplicationMapper.toResponseDto(savedApplication);
    }

    @Override
    public long countApplicationsByCompany(Long companyId) {

        return  this.applicationRepository.countByCompanyId(companyId);
    }
}