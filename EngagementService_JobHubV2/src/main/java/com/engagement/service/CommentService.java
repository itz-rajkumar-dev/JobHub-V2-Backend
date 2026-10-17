package com.engagement.service;

import com.engagement.dto.CommentRequestDto;
import com.engagement.dto.CommentResponseDto;
import com.engagement.entity.TargetType;

import java.util.List;
public interface CommentService {

    CommentResponseDto createComment(CommentRequestDto dto);

    List<CommentResponseDto> getAllComment(Long targetId, TargetType targetType);
    CommentResponseDto getByCommentId(Long commentId);
    CommentResponseDto updateComment(CommentRequestDto dto,Long commentId);
    void deleteComment(Long commentId);

    long countCompanyComments(Long companyId);


    long countCompanyPostComments(Long companyId);
}
