package com.dashboardservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ApplicationService")
public interface ApplicationClient {

    @GetMapping("/applications/internal/company/{companyId}/count")
    long countApplicationsByCompany(
            @PathVariable("companyId") Long companyId
    );
}
