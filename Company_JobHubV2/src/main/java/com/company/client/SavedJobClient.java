package com.company.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SavedJobService")
public interface SavedJobClient {

    @GetMapping("/saved-job/internal/company/{companyId}/count")
    long countSavedJobsByCompany(
            @PathVariable Long companyId
    );
}