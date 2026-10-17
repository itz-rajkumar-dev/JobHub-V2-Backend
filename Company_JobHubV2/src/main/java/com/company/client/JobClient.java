package com.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "JobService")
public interface JobClient {

    @GetMapping("/jobs/internal/company/{companyId}/count")
    long countJobsByCompany(@PathVariable Long companyId);

    @GetMapping("/jobs/internal/company/{companyId}/ids")
    List<Long> getJobIdsByCompany(@PathVariable Long companyId);
}