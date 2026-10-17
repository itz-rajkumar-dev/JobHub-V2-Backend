package com.engagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CompanyService")
public interface CompanyPostClient {

    @GetMapping("/company-posts/internal/company/{companyId}/ids")
    List<Long> getPostIdsByCompany(@PathVariable Long companyId);
}