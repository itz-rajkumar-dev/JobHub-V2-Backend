package com.company.service;

import com.company.dto.CompanyPostRequestDto;
import com.company.dto.CompanyPostResponseDto;
import com.company.dto.CompanyUpdatePostRequestDto;
import com.company.repository.CompanyPostRepository;

import java.util.List;

public interface CompanyPostService {

    CompanyPostResponseDto createPost(CompanyPostRequestDto request);

    CompanyPostResponseDto getByPostId(Long postId);

    List<CompanyPostResponseDto> getAllPost();

    CompanyPostResponseDto updatePost(CompanyUpdatePostRequestDto request, Long postId);

    void deletePost(Long postId);

    List<Long> getPostIdsByCompany(Long companyId);

    List<CompanyPostResponseDto> getPostsByCompany(Long companyId);
}
