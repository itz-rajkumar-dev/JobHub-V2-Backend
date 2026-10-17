package com.category.service;

import com.category.dto.CategoryRequestDto;
import com.category.dto.CategoryResponseDto;
import com.category.dto.CategoryUpdateRequestDto;
import com.category.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    List<CategoryResponseDto> getAllCategory();

    CategoryResponseDto getByCategoryId(Long categoryId);

    CategoryResponseDto updateCategory(CategoryUpdateRequestDto categoryRequestDto, Long categoryId);

    void deleteCategory(Long categoryId);
}
