package com.job.client;

import com.job.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CompanyService")
public interface CompanyClient {

    @GetMapping("/company/{companyId}")
    CompanyResponseDto getCompanyById(@PathVariable("companyId") Long companyId);
}