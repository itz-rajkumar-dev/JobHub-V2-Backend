package com.application.client;

import com.application.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CompanyService")
public interface CompanyClient {
    @GetMapping("/company/{companyId}")
    CompanyResponseDto getCompanyById(@PathVariable("companyId")Long companyId);

    @GetMapping("/company/owner/{ownerId}")
    List<CompanyResponseDto> getCompaniesByOwnerId(@PathVariable("ownerId") Long ownerId);

}
