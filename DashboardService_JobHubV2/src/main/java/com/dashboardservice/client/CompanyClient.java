package com.dashboardservice.client;

import com.dashboardservice.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CompanyService")
public interface CompanyClient {

    // Employer ki saari companies
    @GetMapping("/company/owner/{ownerId}")
    List<CompanyResponseDto> getCompaniesByOwnerId(
            @PathVariable("ownerId") Long ownerId
    );

    @GetMapping("/company/internal/owner/{ownerId}/count")
    long countCompaniesByOwner(
            @PathVariable("ownerId") Long ownerId
    );

    @GetMapping("/company/internal/{companyId}/views")
    long getProfileViews(
            @PathVariable("companyId") Long companyId
    );

    @GetMapping("/company/internal/owner/{ownerId}/views")
    long countProfileViewsByOwner(
            @PathVariable("ownerId") Long ownerId
    );
}
