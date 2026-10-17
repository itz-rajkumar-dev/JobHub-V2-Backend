package com.engagement.controller;

import com.engagement.client.CompanyPostClient;
import com.engagement.dto.LikeRequestDto;
import com.engagement.dto.LikeResponseDto;
import com.engagement.entity.TargetType;
import com.engagement.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private CompanyPostClient companyPostClient;

    @PostMapping
    public LikeResponseDto createLike(@Valid @RequestBody LikeRequestDto likeRequestDto) {

        return likeService.createLike(likeRequestDto);
    }

    @GetMapping("/{id}")
    public LikeResponseDto getByLikeId(@PathVariable("id") Long likeId) {

        return likeService.getByLikeId(likeId);
    }

    @GetMapping
    public List<LikeResponseDto> getAllLike(@RequestParam Long targetId, @RequestParam TargetType targetType) {

        return likeService.getAllLike(targetId, targetType);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLike(
            @PathVariable("id") Long likeId) {

        String message = likeService.deleteLike(likeId);

        return ResponseEntity.ok(message);
    }

    @GetMapping("/internal/company/{companyId}/likes/count")
    public long countCompanyLikes(@PathVariable Long companyId) {

        return likeService.countCompanyLikes(companyId);
    }

    @GetMapping("/internal/company/{companyId}/posts/likes/count")
    public long countCompanyPostLikes(@PathVariable Long companyId) {
        return likeService.countCompanyPostLikes(companyId);
    }


}