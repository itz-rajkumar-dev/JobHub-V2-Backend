package com.company.mapper;

import com.company.dto.*;
import com.company.entity.Company;
import com.company.entity.CompanyPost;

public class CompanyMapper {

    public static Company toEntity(CompanyRequestDto dto) {

        Company company = new Company();

        company.setCompanyName(dto.getCompanyName());
        company.setDescription(dto.getDescription());
        company.setWebsite(dto.getWebsite());
        company.setIndustry(dto.getIndustry());
        company.setLocation(dto.getLocation());
        company.setCompanySize(dto.getCompanySize());
        company.setFoundedYear(dto.getFoundedYear());
        company.setRegistrationNumber(dto.getRegistrationNumber());
        company.setContactEmail(dto.getContactEmail());
        company.setContactPhone(dto.getContactPhone());
        company.setLinkedinUrl(dto.getLinkedinUrl());
        company.setAbout(dto.getAbout());

        return company;
    }

    public static CompanyResponseDto toResponseDto(Company company) {

        CompanyResponseDto dto = new CompanyResponseDto();

        dto.setCompanyId(company.getCompanyId());
        dto.setCompanyName(company.getCompanyName());
        dto.setDescription(company.getDescription());
        dto.setWebsite(company.getWebsite());
        dto.setLogoUrl(company.getLogoUrl());
        dto.setCoverUrl(company.getCoverUrl());
        dto.setProfileViews(company.getProfileViews());
        dto.setIndustry(company.getIndustry());
        dto.setLocation(company.getLocation());
        dto.setCompanySize(company.getCompanySize());
        dto.setFoundedYear(company.getFoundedYear());
        dto.setRegistrationNumber(company.getRegistrationNumber());
        dto.setIsVerified(company.getIsVerified());
        dto.setContactEmail(company.getContactEmail());
        dto.setContactPhone(company.getContactPhone());
        dto.setLinkedinUrl(company.getLinkedinUrl());
        dto.setOwnerId(company.getOwnerId());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        dto.setAbout(company.getAbout());

        return dto;
    }

    public static void updateEntity(CompanyUpdateRequestDto dto, Company company) {

        if (dto.getCompanyName() != null) {
            company.setCompanyName(dto.getCompanyName());
        }

        if (dto.getDescription() != null) {
            company.setDescription(dto.getDescription());
        }

        if (dto.getAbout() != null) {
            company.setAbout(dto.getAbout());
        }

        if (dto.getWebsite() != null) {
            company.setWebsite(dto.getWebsite());
        }


        if (dto.getIndustry() != null) {
            company.setIndustry(dto.getIndustry());
        }

        if (dto.getLocation() != null) {
            company.setLocation(dto.getLocation());
        }

        if (dto.getCompanySize() != null) {
            company.setCompanySize(dto.getCompanySize());
        }

        if (dto.getFoundedYear() != null) {
            company.setFoundedYear(dto.getFoundedYear());
        }

        if (dto.getRegistrationNumber() != null) {
            company.setRegistrationNumber(dto.getRegistrationNumber());
        }

        if (dto.getContactEmail() != null) {
            company.setContactEmail(dto.getContactEmail());
        }

        if (dto.getContactPhone() != null) {
            company.setContactPhone(dto.getContactPhone());
        }

        if (dto.getLinkedinUrl() != null) {
            company.setLinkedinUrl(dto.getLinkedinUrl());
        }
    }

   public  static CompanyPost toEntity(CompanyPostRequestDto  request){

        CompanyPost post=new CompanyPost();

       post.setCompanyId(request.getCompanyId());
       post.setTitle(request.getTitle());
       post.setContent(request.getContent());
       post.setPostType(request.getPostType());
       post.setJobId(request.getJobId());

       return post;
   }

    public static CompanyPostResponseDto toResponseDto(CompanyPost post) {

        CompanyPostResponseDto dto = new CompanyPostResponseDto();

        dto.setPostId(post.getPostId());
        dto.setCompanyId(post.getCompanyId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setPostType(post.getPostType());
        dto.setJobId(post.getJobId());
        dto.setImageUrl(post.getImageUrl());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());

        return dto;
    }




    public static void updatePostEntity(
            CompanyUpdatePostRequestDto dto,
            CompanyPost companyPost) {

        if (dto.getTitle() != null) {
            companyPost.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null) {
            companyPost.setContent(dto.getContent());
        }

        if (dto.getPostType() != null) {
            companyPost.setPostType(dto.getPostType());
        }

        if (dto.getJobId() != null) {
            companyPost.setJobId(dto.getJobId());
        }
    }
}