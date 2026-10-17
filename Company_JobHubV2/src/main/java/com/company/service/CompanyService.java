package com.company.service;

import com.company.dto.*;

import java.util.List;

public interface CompanyService {


    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

    List<CompanyResponseDto> getAllCompany();

    CompanyResponseDto getByCompanyId(Long companyId);

    List<CompanyResponseDto> getCompaniesByOwnerId(Long ownerId);

   List<CompanyResponseDto> getMyCompany();

    CompanyResponseDto updateCompany(CompanyUpdateRequestDto dto, Long companyId);

    void deleteCompany(Long companyId);


    CompanyDeleteCheckResponseDto checkCompanyDelete(Long companyId);


    long countCompaniesByOwner(Long ownerId);

    void incrementProfileViews(Long companyId);

    long getProfileViews(Long companyId);

    long countProfileViewsByOwner(Long ownerId);

}
