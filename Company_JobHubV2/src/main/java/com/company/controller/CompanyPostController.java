package com.company.controller;

import com.company.dto.CompanyPostRequestDto;
import com.company.dto.CompanyPostResponseDto;
import com.company.dto.CompanyUpdatePostRequestDto;
import com.company.service.CompanyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company-posts")
public class CompanyPostController {

    @Autowired
    private CompanyPostService companyPostService;

    // CREATE
    @PreAuthorize("hasRole('EMPLOYER')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CompanyPostResponseDto> createPost(
            @ModelAttribute CompanyPostRequestDto request) {

        return ResponseEntity.ok(
                companyPostService.createPost(request)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<CompanyPostResponseDto>> getAllPost() {

        return ResponseEntity.ok(companyPostService.getAllPost());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<CompanyPostResponseDto> getByPostId(@PathVariable("id") Long postId) {

        return ResponseEntity.ok(companyPostService.getByPostId(postId));
    }

    // UPDATE
    @PreAuthorize("hasRole('EMPLOYER')")
    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<CompanyPostResponseDto> updatePost(
            @PathVariable("id") Long postId,
            @ModelAttribute CompanyUpdatePostRequestDto request) {

        return ResponseEntity.ok(
                companyPostService.updatePost(request, postId)
        );
    }

    // DELETE
    @PreAuthorize("hasRole('EMPLOYER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable("id") Long postId) {

        companyPostService.deletePost(postId);

        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/internal/company/{companyId}/ids")
    public List<Long> getPostIdsByCompany(@PathVariable Long companyId) {

        return companyPostService.getPostIdsByCompany(companyId);
    }

    @GetMapping("/company/{id}")
    public List<CompanyPostResponseDto> getPostsByCompany(
            @PathVariable ("id") Long companyId) {

        return companyPostService.getPostsByCompany(companyId);
    }
}