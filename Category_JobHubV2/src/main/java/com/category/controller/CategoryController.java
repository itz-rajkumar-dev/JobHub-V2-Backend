package com.category.controller;

import com.category.dto.CategoryRequestDto;
import com.category.dto.CategoryResponseDto;
import com.category.dto.CategoryUpdateRequestDto;
import com.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CategoryResponseDto createCategory(@Valid @ModelAttribute CategoryRequestDto categoryRequestDto) {

        return categoryService.createCategory(categoryRequestDto);
    }

    @GetMapping
    public List<CategoryResponseDto> getAllCategory() {

        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public CategoryResponseDto getByCategoryId(@PathVariable("id") Long categoryId) {

        return categoryService.getByCategoryId(categoryId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateCategory(@Valid @ModelAttribute CategoryUpdateRequestDto categoryRequestDto, @PathVariable("id") Long categoryId) {

        categoryService.updateCategory(categoryRequestDto, categoryId);

        return ResponseEntity.ok("Category Update Successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category Delete Successfully");
    }
}