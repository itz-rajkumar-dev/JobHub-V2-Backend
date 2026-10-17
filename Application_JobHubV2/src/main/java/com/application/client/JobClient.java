package com.application.client;

import com.application.dto.JobResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "JobService")
public interface JobClient {

    @GetMapping("/jobs/{jobId}")
    JobResponseDto getJobById(@PathVariable("jobId") Long jobId);
}