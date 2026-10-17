package com.dashboardservice.client;

import com.dashboardservice.enums.JobStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "JobService")
public interface JobClient {

    @GetMapping("/jobs/internal/company/{companyId}/status-count")
    long countJobsByCompanyAndStatus(
            @PathVariable("companyId") Long companyId,
            @RequestParam("status") JobStatus status
    );
}
