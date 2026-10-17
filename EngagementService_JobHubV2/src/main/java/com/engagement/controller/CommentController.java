package com.engagement.controller;

import com.engagement.dto.CommentRequestDto;
import com.engagement.dto.CommentResponseDto;
import com.engagement.entity.TargetType;
import com.engagement.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(
            @Valid @RequestBody CommentRequestDto dto) {

        return commentService.createComment(dto);
    }

    @GetMapping
    public List<CommentResponseDto> getAllComment(@RequestParam Long targetId, @RequestParam TargetType targetType) {

        return commentService.getAllComment(targetId, targetType);
    }

    @GetMapping("/{id}")
    public CommentResponseDto getByCommentId(
            @PathVariable("id") Long commentId) {

        return commentService.getByCommentId(commentId);
    }

    @PutMapping("/{id}")
    public CommentResponseDto updateComment(
            @PathVariable("id") Long commentId,
            @Valid @RequestBody CommentRequestDto dto) {

        return commentService.updateComment(dto, commentId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable("id") Long commentId) {

        commentService.deleteComment(commentId);

        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/internal/company/{companyId}/comments/count")
    public long countCompanyComments(
            @PathVariable Long companyId) {

        return commentService.countCompanyComments(companyId);
    }

    @GetMapping("/internal/company/{companyId}/posts/comments/count")
    public long countCompanyPostComments(@PathVariable Long companyId) {
        return commentService.countCompanyPostComments(companyId);
    }
}