package com.savedjob.client;

import com.savedjob.dto.JobResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "JobService")
public interface JobClient {

    @GetMapping("/jobs/{jobId}")
    JobResponseDto getByJobId(@PathVariable("jobId") Long jobId);

    @GetMapping("/jobs/internal/company/{companyId}/ids")
    List<Long> getJobIdsByCompany(@PathVariable Long companyId);
}