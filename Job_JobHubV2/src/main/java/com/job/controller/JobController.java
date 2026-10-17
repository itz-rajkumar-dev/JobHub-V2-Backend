package com.job.controller;

import com.job.dto.*;
import com.job.entity.JobStatus;
import com.job.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping
    public JobResponseDto createJob(@Valid @RequestBody JobRequestDto requestDto){

        return this.jobService.createJob(requestDto);
    }

    @GetMapping
    public List<JobResponseDto> getAllJobs(){

        return this.jobService.getAllJobs();
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/my")
    public PageResponseDto<JobResponseDto> getMyJobs(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,   @RequestParam(required = false) String search) {

        return jobService.getMyJobs(page, size,search);
    }

    @GetMapping("/{id}")
    public JobResponseDto getByJobId(@PathVariable("id")Long jobId){

        return this.jobService.getJobById(jobId);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateJob(@Valid @RequestBody JobUpdateRequestDto requestDto, @PathVariable("id")Long jobId){

        this.jobService.updateJob(requestDto,jobId);

        return ResponseEntity.ok("Job Update Successfully");
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable("id")Long jobId){

         this.jobService.deleteJob(jobId);

         return ResponseEntity.ok("Job Delete Successfully");
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping("/status/{id}")
    public ResponseEntity<String> updateJobStatus(
            @PathVariable Long id,
            @Valid @RequestBody JobStatusUpdateDto request) {

        jobService.updateJobStatus(id, request.getStatus());

        return ResponseEntity.ok("Job status updated successfully");
    }

    @GetMapping("/internal/company/{companyId}/count")
    public long countJobsByCompany(@PathVariable Long companyId) {
        return jobService.countJobsByCompany(companyId);
    }

    @GetMapping("/internal/company/{companyId}/ids")
    public List<Long> getJobIdsByCompany(
            @PathVariable Long companyId) {

        return jobService.getJobIdsByCompany(companyId);
    }
    @GetMapping("/internal/company/{companyId}/status-count")
    public long getCompanyByJobStatus(@PathVariable Long companyId, @RequestParam JobStatus status) {

        return this.jobService.getCompanyJobByStatus(companyId, status);
    }
}
