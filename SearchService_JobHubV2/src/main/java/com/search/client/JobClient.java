package com.search.client;

import com.search.dto.JobSearchResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "JobService")
public interface JobClient {

    @GetMapping("/jobs")
    List<JobSearchResponseDto> getAllJobs();
}