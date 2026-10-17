package com.job.service;

import com.job.dto.JobRequestDto;
import com.job.dto.JobResponseDto;
import com.job.dto.JobUpdateRequestDto;
import com.job.dto.PageResponseDto;
import com.job.entity.JobStatus;

import java.util.List;

public interface JobService {

    JobResponseDto createJob(JobRequestDto requestDto);

    List<JobResponseDto> getAllJobs();

    PageResponseDto<JobResponseDto> getMyJobs(int page, int size,String search);

    JobResponseDto getJobById(Long jobId);

    JobResponseDto updateJob(JobUpdateRequestDto requestDto, Long jobId);

    void deleteJob(Long jobId);

    void updateJobStatus(Long jobId, JobStatus status);



    long countJobsByCompany(Long companyId);

    List<Long> getJobIdsByCompany(Long companyId);

    long getCompanyJobByStatus(Long companyId, JobStatus status);
}