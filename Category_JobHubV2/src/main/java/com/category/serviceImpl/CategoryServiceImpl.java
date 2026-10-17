package com.category.serviceImpl;

import com.category.config.CloudinaryService;
import com.category.dto.CategoryRequestDto;
import com.category.dto.CategoryResponseDto;
import com.category.dto.CategoryUpdateRequestDto;
import com.category.entity.Category;
import com.category.exception.CategoryAlreadyExistException;
import com.category.exception.CategoryNotFoundException;
import com.category.mapper.CategoryMapper;
import com.category.repository.CategoryRepository;
import com.category.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private  CloudinaryService cloudinaryService;

    // CREATE CATEGORY
    @Override
    @Transactional
    public CategoryResponseDto createCategory(
            CategoryRequestDto dto) {

        // Check duplicate name
        if (categoryRepository.findByName(dto.getName()).isPresent()) {
            throw new CategoryAlreadyExistException(
                    "Category Already Exist: " + dto.getName());
        }

        // Check duplicate slug
        if (categoryRepository.findBySlug(dto.getSlug()).isPresent()) {
            throw new CategoryAlreadyExistException(
                    "Category Slug Already Exist: " + dto.getSlug());
        }

        // Upload icon to Cloudinary
        if (dto.getIcon() == null || dto.getIcon().isEmpty()) {
            throw new IllegalArgumentException(
                    "Category icon is required");
        }

        String iconUrl =
                cloudinaryService.uploadIcon(dto.getIcon());

        // DTO -> Entity
        Category category =
                CategoryMapper.toEntity(dto, iconUrl);

        // Save category
        Category savedCategory =
                categoryRepository.save(category);

        // Entity -> Response DTO
        return CategoryMapper.toResponseDto(savedCategory);
    }

    // GET ALL CATEGORIES
    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponseDto> getAllCategory() {

        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toResponseDto)
                .toList();
    }

    // GET CATEGORY BY ID
    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getByCategoryId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category Not Found: " + categoryId));

        return CategoryMapper.toResponseDto(category);
    }

    // UPDATE CATEGORY - PARTIAL UPDATE
    @Override
    @Transactional
    public CategoryResponseDto updateCategory(
            CategoryUpdateRequestDto dto,
            Long categoryId) {

        Category existingCategory =
                categoryRepository.findById(categoryId)
                        .orElseThrow(() ->
                                new CategoryNotFoundException(
                                        "Category Not Found: " + categoryId));

        // Check duplicate name only if supplied
        if (dto.getName() != null
                && !dto.getName().equals(existingCategory.getName())) {

            categoryRepository.findByName(dto.getName())
                    .ifPresent(category -> {
                        if (!category.getCategoryId().equals(categoryId)) {
                            throw new CategoryAlreadyExistException(
                                    "Category Already Exist: " + dto.getName());
                        }
                    });
        }

        // Check duplicate slug only if supplied
        if (dto.getSlug() != null
                && !dto.getSlug().equals(existingCategory.getSlug())) {

            categoryRepository.findBySlug(dto.getSlug())
                    .ifPresent(category -> {
                        if (!category.getCategoryId().equals(categoryId)) {
                            throw new CategoryAlreadyExistException(
                                    "Category Slug Already Exist: " + dto.getSlug());
                        }
                    });
        }

        // Upload new icon only when supplied
        String uploadedIconUrl = null;

        if (dto.getIcon() != null && !dto.getIcon().isEmpty()) {
            uploadedIconUrl =
                    cloudinaryService.uploadIcon(dto.getIcon());
        }

        // Update only supplied fields
        CategoryMapper.updateCategory(
                existingCategory,
                dto,
                uploadedIconUrl);

        Category updatedCategory =
                categoryRepository.save(existingCategory);

        return CategoryMapper.toResponseDto(updatedCategory);
    }

    // DELETE CATEGORY
    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category Not Found: " + categoryId));

        categoryRepository.delete(category);
    }
}
