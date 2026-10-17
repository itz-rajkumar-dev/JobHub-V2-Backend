
        package com.category.mapper;

import com.category.dto.CategoryRequestDto;
import com.category.dto.CategoryResponseDto;
import com.category.dto.CategoryUpdateRequestDto;
import com.category.entity.Category;

public class CategoryMapper {

    public static Category toEntity(
            CategoryRequestDto dto,
            String uploadedIconUrl) {

        Category category = new Category();

        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setIconUrl(uploadedIconUrl);
        category.setIsActive(dto.getIsActive());
        category.setDisplayOrder(dto.getDisplayOrder());

        return category;
    }

    public static CategoryResponseDto toResponseDto(
            Category category) {

        CategoryResponseDto dto = new CategoryResponseDto();

        dto.setCategoryId(category.getCategoryId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setIconUrl(category.getIconUrl());
        dto.setIsActive(category.getIsActive());
        dto.setDisplayOrder(category.getDisplayOrder());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());

        return dto;
    }

    public static void updateCategory(
            Category category,
            CategoryUpdateRequestDto dto,
            String uploadedIconUrl) {

        if (dto.getName() != null) {
            category.setName(dto.getName());
        }

        if (dto.getSlug() != null) {
            category.setSlug(dto.getSlug());
        }

        if (dto.getDescription() != null) {
            category.setDescription(dto.getDescription());
        }

        if (dto.getIsActive() != null) {
            category.setIsActive(dto.getIsActive());
        }

        if (dto.getDisplayOrder() != null) {
            category.setDisplayOrder(dto.getDisplayOrder());
        }

        if (uploadedIconUrl != null) {
            category.setIconUrl(uploadedIconUrl);
        }
    }
}
