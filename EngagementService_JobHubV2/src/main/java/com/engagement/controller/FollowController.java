package com.engagement.controller;

import com.engagement.dto.FollowRequestDto;
import com.engagement.dto.FollowResponseDto;
import com.engagement.service.FollowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follows")
public class FollowController {

    @Autowired
    private FollowService followService;

    @PostMapping
    public FollowResponseDto createFollow(
            @Valid @RequestBody FollowRequestDto requestDto) {

        return followService.createFollow(requestDto);
    }
    @GetMapping("/company")
    public List<FollowResponseDto> getFollowersByCompany(
            @RequestParam Long companyId) {

        return followService.getFollowersByCompany(companyId);
    }

    @GetMapping("/user/followers")
    public List<FollowResponseDto> getFollowersByUser(
            @RequestParam Long userId) {

        return followService.getFollowersByUser(userId);
    }

    @GetMapping("/user/following")
    public List<FollowResponseDto> getFollowingByUser(
            @RequestParam Long userId) {

        return followService.getFollowingByUser(userId);
    }
    @GetMapping("/{id}")
    public FollowResponseDto getByFollowId(
            @PathVariable("id") Long followId) {

        return followService.getByFollowId(followId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFollow(
            @PathVariable("id") Long followId) {

        followService.deleteFollow(followId);

        return ResponseEntity.ok("Unfollow successfully");
    }

    @GetMapping("/check/{followingUserId}")
    public FollowResponseDto checkFollow(
            @PathVariable Long followingUserId) {

        return followService.checkFollow(followingUserId);
    }

    @GetMapping("/internal/company/{companyId}/followers/count")
    public long countCompanyFollowers(@PathVariable Long companyId) {

        return followService.countCompanyFollowers(companyId);
    }
}