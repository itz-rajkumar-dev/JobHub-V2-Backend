package com.company.service;

import com.company.dto.CompanyLifeRequestDto;
import com.company.dto.CompanyLifeResponseDto;

import java.util.List;

public interface CompanyLifeService {


    CompanyLifeResponseDto createCompanyLife(Long companyId, CompanyLifeRequestDto requestDto);

    CompanyLifeResponseDto updateCompanyLife(Long companyId, Long lifeId, CompanyLifeRequestDto requestDto);

    List<CompanyLifeResponseDto> getCompanyLife(Long companyId);

    void deleteCompanyLife(Long companyId, Long lifeId);
}
