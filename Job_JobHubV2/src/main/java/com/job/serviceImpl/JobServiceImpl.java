package com.job.serviceImpl;

import com.job.client.CategoryClient;
import com.job.client.CompanyClient;
import com.job.dto.*;
import com.job.entity.Job;
import com.job.entity.JobStatus;
import com.job.event.JobSearchEvent;
import com.job.exception.JobAlreadyExistException;
import com.job.exception.JobNotFoundException;
import com.job.exception.UnauthorizedException;
import com.job.kafka.JobKafkaProducer;
import com.job.mapper.JobMapper;
import com.job.repository.JobRepository;
import com.job.security.AuthenticatedUser;
import com.job.service.JobService;
import com.job.util.SlugUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final CategoryClient categoryClient;
    private final JobKafkaProducer jobKafkaProducer;


    public JobServiceImpl(
            JobRepository jobRepository,
            CompanyClient companyClient,
            CategoryClient categoryClient,
            JobKafkaProducer jobKafkaProducer
    ) {

        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.categoryClient = categoryClient;
        this.jobKafkaProducer=jobKafkaProducer;
    }

    private AuthenticatedUser getCurrentUser(){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        if(authentication==null||!(authentication.getPrincipal() instanceof AuthenticatedUser)){

            throw new UnauthorizedException("User Not Found");
        }

        return (AuthenticatedUser) authentication.getPrincipal();
    }

    @Override
    public JobResponseDto createJob(JobRequestDto requestDto) {

        AuthenticatedUser currentUser=getCurrentUser();

        CompanyResponseDto company=companyClient.getCompanyById(requestDto.getCompanyId());

        System.out.println("========== JOB OWNER CHECK ==========");
        System.out.println("Current User ID: " + currentUser.getUserId());
        System.out.println("Company Owner ID: " + company.getOwnerId());
        System.out.println("====================================");

        if (!company.getOwnerId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException("You are not the owner of this company");
        }

        CategoryResponseDto responseDto=categoryClient.getByCategoryId(requestDto.getCategoryId());

        boolean exists = jobRepository
                .existsByCompanyIdAndTitleAndLocationAndJobTypeAndStatusIn(
                        requestDto.getCompanyId(),
                        requestDto.getTitle(),
                        requestDto.getLocation(),
                        requestDto.getJobType(),
                        List.of(JobStatus.DRAFT, JobStatus.OPEN)
                );

        if (exists) {
            throw new JobAlreadyExistException("Similar job already exists");
        }

        Job job = JobMapper.toEntity(requestDto);

        job.setSlug(SlugUtil.generateSlug(requestDto.getTitle()));
        job.setPostedBy(currentUser.getUserId());
        job.setStatus(JobStatus.OPEN);
        job.setViewsCount(0L);
        job.setApplicationsCount(0L);
        job.setFeatured(false);
        job.setPostedAt(LocalDateTime.now());
        Job savedJob = jobRepository.save(job);

        JobSearchEvent event = JobSearchEvent.builder()

                .eventType("JOB_CREATED")
                .jobId(savedJob.getJobId())
                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .responsibilities(savedJob.getResponsibilities())
                .requirements(savedJob.getRequirements())
                .qualifications(savedJob.getQualifications())
                .benefits(savedJob.getBenefits())

                .companyId(savedJob.getCompanyId())
                .companyName(company.getCompanyName())
                .companyLogo(company.getLogoUrl())
                .categoryId(savedJob.getCategoryId())
                .categoryName(responseDto.getName())

                .salaryMin(savedJob.getSalaryMin())
                .salaryMax(savedJob.getSalaryMax())
                .experienceMin(savedJob.getExperienceMin())
                .experienceMax(savedJob.getExperienceMax())
                .jobType(savedJob.getJobType())
                .workMode(savedJob.getWorkMode())
                .location(savedJob.getLocation())
                .skills(savedJob.getSkills())
                .vacancies(savedJob.getVacancies())
                .viewsCount(savedJob.getViewsCount())
                .applicationsCount(savedJob.getApplicationsCount())
                .featured(savedJob.getFeatured())
                .deadline(savedJob.getDeadline())
                .status(savedJob.getStatus())
                .createdAt(savedJob.getCreatedAt())
                .updatedAt(savedJob.getUpdatedAt())
                .build();

        jobKafkaProducer.sendJobEvent(event);

        return JobMapper.toResponseDto(savedJob,company.getCompanyName(),
                company.getLogoUrl(),
                responseDto.getName());
    }

    @Override
    public List<JobResponseDto> getAllJobs() {

        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .filter(job -> job.getStatus() == JobStatus.OPEN)
                .map(job -> {

                    CompanyResponseDto company =
                            companyClient.getCompanyById(job.getCompanyId());

                    CategoryResponseDto category =
                            categoryClient.getByCategoryId(job.getCategoryId());

                    return JobMapper.toResponseDto(
                            job,
                            company.getCompanyName(),
                            company.getLogoUrl(),
                            category.getName()
                    );
                })
                .toList();
    }

    @Override
    public PageResponseDto<JobResponseDto> getMyJobs(int page, int size,String search) {

        AuthenticatedUser currentUser = getCurrentUser();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<Job> jobs;

        if (search == null || search.trim().isEmpty()) {

            jobs = jobRepository.findByPostedBy(
                    currentUser.getUserId(),
                    pageable
            );

        } else {

            jobs = jobRepository.searchMyJobs(
                    currentUser.getUserId(),
                    search.trim(),
                    pageable
            );
        }


        List<JobResponseDto> content = jobs.getContent()
                .stream()
                .map(job -> {

                    CompanyResponseDto company =
                            companyClient.getCompanyById(job.getCompanyId());

                    CategoryResponseDto category =
                            categoryClient.getByCategoryId(job.getCategoryId());

                    return JobMapper.toResponseDto(
                            job,
                            company.getCompanyName(),
                            company.getLogoUrl(),
                            category.getName()
                    );
                })
                .toList();
   long openCount=this.jobRepository.countByPostedByAndStatus(currentUser.getUserId(),JobStatus.OPEN);
   long draftCount=this.jobRepository.countByPostedByAndStatus(currentUser.getUserId(),JobStatus.DRAFT);
   long closedCount = jobRepository.countByPostedByAndStatus(currentUser.getUserId(), JobStatus.CLOSED);
   long expiredCount = jobRepository.countByPostedByAndStatus(currentUser.getUserId(), JobStatus.EXPIRED);

        return PageResponseDto.<JobResponseDto>builder()
                .content(content)
                .page(jobs.getNumber())
                .size(jobs.getSize())
                .totalElements(jobs.getTotalElements())
                .totalPages(jobs.getTotalPages())
                .hasNext(jobs.hasNext())
                .hasPrevious(jobs.hasPrevious())
                .openCount(openCount)
                .draftCount(draftCount)
                .closedCount(closedCount)
                .expiredCount(expiredCount)
                .build();
    }

    @Override
    public JobResponseDto getJobById(Long jobId) {

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException("Job Not Found: " + jobId));

        CompanyResponseDto company =
                companyClient.getCompanyById(job.getCompanyId());

        CategoryResponseDto category =
                categoryClient.getByCategoryId(job.getCategoryId());

        return JobMapper.toResponseDto(
                job,
                company.getCompanyName(),
                company.getLogoUrl(),
                category.getName()
        );
    }

    @Override
    public JobResponseDto updateJob(JobUpdateRequestDto requestDto, Long jobId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException("Job Not Found: " + jobId));

        // 1. Job owner check
        if (!job.getPostedBy().equals(currentUser.getUserId())) {
            throw new UnauthorizedException(
                    "You are not allowed to update this job");
        }

        // 2. Company owner check
        CompanyResponseDto company =
                companyClient.getCompanyById(job.getCompanyId());

        if (!company.getOwnerId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException(
                    "You are not the owner of this company");
        }

        // 3. Category validation only if category is being changed
        CategoryResponseDto category =
                categoryClient.getByCategoryId(
                        requestDto.getCategoryId() != null
                                ? requestDto.getCategoryId()
                                : job.getCategoryId()
                );

        // 4. Effective values
        String title = requestDto.getTitle() != null
                ? requestDto.getTitle()
                : job.getTitle();

        String location = requestDto.getLocation() != null
                ? requestDto.getLocation()
                : job.getLocation();

        var jobType = requestDto.getJobType() != null
                ? requestDto.getJobType()
                : job.getJobType();

        // 5. Duplicate job check
        boolean exists =
                jobRepository
                        .existsByCompanyIdAndTitleAndLocationAndJobTypeAndStatusInAndJobIdNot(
                                job.getCompanyId(),
                                title,
                                location,
                                jobType,
                                List.of(JobStatus.DRAFT, JobStatus.OPEN),
                                jobId
                        );

        if (exists) {
            throw new JobAlreadyExistException(
                    "Similar job already exists for this company");
        }

        // 6. Update only provided fields
        JobMapper.updateEntity(requestDto, job);

        // 7. Slug only changes when title changes
        if (requestDto.getTitle() != null) {
            job.setSlug(
                    SlugUtil.generateSlug(requestDto.getTitle())
            );
        }

        Job savedJob = jobRepository.save(job);

        // 8. Kafka event
        JobSearchEvent event = JobSearchEvent.builder()
                .eventType("JOB_UPDATED")
                .jobId(savedJob.getJobId())
                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .responsibilities(savedJob.getResponsibilities())
                .requirements(savedJob.getRequirements())
                .qualifications(savedJob.getQualifications())
                .benefits(savedJob.getBenefits())
                .companyId(savedJob.getCompanyId())
                .companyName(company.getCompanyName())
                .companyLogo(company.getLogoUrl())
                .categoryId(savedJob.getCategoryId())
                .categoryName(category.getName())
                .salaryMin(savedJob.getSalaryMin())
                .salaryMax(savedJob.getSalaryMax())
                .experienceMin(savedJob.getExperienceMin())
                .experienceMax(savedJob.getExperienceMax())
                .jobType(savedJob.getJobType())
                .workMode(savedJob.getWorkMode())
                .location(savedJob.getLocation())
                .skills(savedJob.getSkills())
                .vacancies(savedJob.getVacancies())
                .viewsCount(savedJob.getViewsCount())
                .applicationsCount(savedJob.getApplicationsCount())
                .featured(savedJob.getFeatured())
                .deadline(savedJob.getDeadline())
                .status(savedJob.getStatus())
                .createdAt(savedJob.getCreatedAt())
                .updatedAt(savedJob.getUpdatedAt())
                .build();

        jobKafkaProducer.sendJobEvent(event);

        return JobMapper.toResponseDto(
                savedJob,
                company.getCompanyName(),
                company.getLogoUrl(),
                category.getName()
        );
    }

    @Override
    public void updateJobStatus(Long jobId, JobStatus status) {

        AuthenticatedUser currentUser = getCurrentUser();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new JobNotFoundException("Job Not Found: " + jobId));

        // 1. Job owner check
        if (!job.getPostedBy().equals(currentUser.getUserId())) {
            throw new UnauthorizedException(
                    "You are not allowed to update this job");
        }

        // 2. Company owner check
        CompanyResponseDto company =
                companyClient.getCompanyById(job.getCompanyId());

        if (!company.getOwnerId().equals(currentUser.getUserId())) {
            throw new UnauthorizedException(
                    "You are not the owner of this company");
        }

        // 3. Category information
        CategoryResponseDto category =
                categoryClient.getByCategoryId(job.getCategoryId());

        // 4. Update status
        job.setStatus(status);

        Job savedJob = jobRepository.save(job);

        // 5. Send updated job to Search Service
        JobSearchEvent event = JobSearchEvent.builder()
                .eventType("JOB_UPDATED")

                .jobId(savedJob.getJobId())

                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .responsibilities(savedJob.getResponsibilities())
                .requirements(savedJob.getRequirements())
                .qualifications(savedJob.getQualifications())
                .benefits(savedJob.getBenefits())

                .companyId(savedJob.getCompanyId())
                .companyName(company.getCompanyName())
                .companyLogo(company.getLogoUrl())

                .categoryId(savedJob.getCategoryId())
                .categoryName(category.getName())

                .salaryMin(savedJob.getSalaryMin())
                .salaryMax(savedJob.getSalaryMax())

                .experienceMin(savedJob.getExperienceMin())
                .experienceMax(savedJob.getExperienceMax())

                .jobType(savedJob.getJobType())
                .workMode(savedJob.getWorkMode())
                .location(savedJob.getLocation())

                .skills(savedJob.getSkills())
                .vacancies(savedJob.getVacancies())

                .viewsCount(savedJob.getViewsCount())
                .applicationsCount(savedJob.getApplicationsCount())

                .featured(savedJob.getFeatured())

                .deadline(savedJob.getDeadline())
                .status(savedJob.getStatus())

                .createdAt(savedJob.getCreatedAt())
                .updatedAt(savedJob.getUpdatedAt())

                .build();

        jobKafkaProducer.sendJobEvent(event);
    }

    @Override
    public long countJobsByCompany(Long companyId) {

        return  this.jobRepository.countByCompanyId(companyId);
    }

    @Override
    public void deleteJob(Long jobId) {

        AuthenticatedUser currentUser = getCurrentUser();

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException("Job Not Found: " + jobId));

        if (!job.getPostedBy().equals(currentUser.getUserId())) {

            throw new UnauthorizedException("You are not allowed to Delete this job");
        }

        CompanyResponseDto company = companyClient.getCompanyById(job.getCompanyId());

        if (!company.getOwnerId().equals(currentUser.getUserId())) {

            throw new UnauthorizedException("You are not the owner of this company");
        }

        JobSearchEvent event = JobSearchEvent.builder()
                .eventType("JOB_DELETED")
                .jobId(job.getJobId())
                .build();

        jobRepository.delete(job);

        jobKafkaProducer.sendJobEvent(event);
    }

    @Override
    public List<Long> getJobIdsByCompany(Long companyId) {

        return jobRepository.findByCompanyId(companyId)
                .stream()
                .map(Job::getJobId)
                .toList();
    }


    @Override
    public long getCompanyJobByStatus(Long companyId, JobStatus status) {
        return jobRepository.countByCompanyIdAndStatus(companyId, status);
    }

}