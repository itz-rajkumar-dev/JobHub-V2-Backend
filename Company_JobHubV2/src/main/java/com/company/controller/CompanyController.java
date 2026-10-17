package com.company.controller;

import com.company.dto.*;
import com.company.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyResponseDto createCompany(@Valid @ModelAttribute CompanyRequestDto companyRequestDto){

        return  companyService.createCompany(companyRequestDto);
    }

    @GetMapping
    public List<CompanyResponseDto> getAllCompany(){

        return  companyService.getAllCompany();
    }

    @GetMapping("/{id}")
    public CompanyResponseDto getByCompanyId(@PathVariable("id") Long companyId){

        return this.companyService.getByCompanyId(companyId);
    }
    @GetMapping("/owner/{ownerId}")
    public List<CompanyResponseDto> getCompaniesByOwnerId(
            @PathVariable("ownerId") Long ownerId) {

        return companyService.getCompaniesByOwnerId(ownerId);
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CompanyResponseDto updateCompany(@Valid @ModelAttribute CompanyUpdateRequestDto companyUpdateRequestDto,@PathVariable("id") Long companyId){


          return this.companyService.updateCompany(companyUpdateRequestDto,companyId);
    }
    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/delete/{id}")
     public ResponseEntity<String> deleteCompany(@PathVariable("id") Long companyId){

        companyService.deleteCompany(companyId);

        return ResponseEntity.ok("Company Delete Successfully");
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ResponseEntity<List<CompanyResponseDto>> getMyCompany() {
        return ResponseEntity.ok(companyService.getMyCompany());
    }

    @PreAuthorize("hasRole('EMPLOYER')")
    @GetMapping("/{id}/delete-check")
    public CompanyDeleteCheckResponseDto checkCompanyDelete(
            @PathVariable("id") Long companyId) {

        return companyService.checkCompanyDelete(companyId);
    }

    @GetMapping("/internal/owner/{ownerId}/count")
    public long countCompaniesByOwner(
            @PathVariable Long ownerId) {

        return companyService.countCompaniesByOwner(ownerId);
    }

    @PostMapping("/{id}/view")
    public ResponseEntity<Void> incrementProfileViews(
            @PathVariable Long id) {

        companyService.incrementProfileViews(id);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/internal/{id}/views")
    public long getProfileViews(@PathVariable Long id) {
        return companyService.getProfileViews(id);
    }

    @GetMapping("/internal/owner/{ownerId}/views")
    public long countProfileViewsByOwner(
            @PathVariable Long ownerId) {

        return companyService.countProfileViewsByOwner(ownerId);
    }
}
