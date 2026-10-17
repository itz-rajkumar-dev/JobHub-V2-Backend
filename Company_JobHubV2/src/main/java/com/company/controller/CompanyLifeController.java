package com.company.controller;

import com.company.dto.CompanyLifeRequestDto;
import com.company.dto.CompanyLifeResponseDto;
import com.company.service.CompanyLifeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyLifeController {

    @Autowired
    private CompanyLifeService companyLifeService;

    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping(value = "/{companyId}/life", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyLifeResponseDto createLife(@PathVariable Long companyId, @ModelAttribute CompanyLifeRequestDto requestDto) {

        return companyLifeService.createCompanyLife(companyId, requestDto);
    }

    @GetMapping("/{companyId}/life")
    public List<CompanyLifeResponseDto> getLife(@PathVariable Long companyId) {

        return companyLifeService.getCompanyLife(companyId);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping(value = "/{companyId}/life/{lifeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyLifeResponseDto updateLife(
            @PathVariable Long companyId,
            @PathVariable Long lifeId,
            @ModelAttribute CompanyLifeRequestDto requestDto) {

        return companyLifeService.updateCompanyLife(
                companyId,
                lifeId,
                requestDto
        );
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{companyId}/life/{lifeId}")
    public ResponseEntity<String> deleteLife(@PathVariable Long companyId, @PathVariable Long lifeId) {

        companyLifeService.deleteCompanyLife(companyId, lifeId);

        return ResponseEntity.ok("Company life deleted successfully");
    }
}