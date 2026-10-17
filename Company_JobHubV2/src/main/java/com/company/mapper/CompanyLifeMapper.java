package com.company.mapper;


import com.company.dto.CompanyLifeResponseDto;
import com.company.entity.CompanyLife;
import com.company.dto.CompanyLifeRequestDto;


public class CompanyLifeMapper {

    public static CompanyLife toEntity(
            CompanyLifeRequestDto dto,
            Long companyId,
            String imageUrl) {

        CompanyLife life = new CompanyLife();

        life.setCompanyId(companyId);
        life.setTitle(dto.getTitle());
        life.setDescription(dto.getDescription());
        life.setImageUrl(imageUrl);
        life.setDisplayOrder(dto.getDisplayOrder());

        return life;
    }

    public static CompanyLifeResponseDto toResponseDto(
            CompanyLife life) {

        CompanyLifeResponseDto dto =
                new CompanyLifeResponseDto();

        dto.setLifeId(life.getLifeId());
        dto.setCompanyId(life.getCompanyId());
        dto.setTitle(life.getTitle());
        dto.setDescription(life.getDescription());
        dto.setImageUrl(life.getImageUrl());
        dto.setDisplayOrder(life.getDisplayOrder());

        return dto;
    }

    public static void updateEntity(
            CompanyLifeRequestDto dto,
            CompanyLife life,
            String imageUrl) {

        if (dto.getTitle() != null) {
            life.setTitle(dto.getTitle());
        }

        if (dto.getDescription() != null) {
            life.setDescription(dto.getDescription());
        }

        if (dto.getDisplayOrder() != null) {
            life.setDisplayOrder(dto.getDisplayOrder());
        }

        if (imageUrl != null) {
            life.setImageUrl(imageUrl);
        }
    }
}