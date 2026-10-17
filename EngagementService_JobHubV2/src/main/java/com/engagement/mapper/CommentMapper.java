package com.engagement.mapper;

import com.engagement.dto.CommentRequestDto;
import com.engagement.dto.CommentResponseDto;
import com.engagement.entity.Comment;
public class CommentMapper {

    public static Comment toEntity(CommentRequestDto dto) {

        Comment comment = new Comment();

        comment.setTargetId(dto.getTargetId());
        comment.setTargetType(dto.getTargetType());
        comment.setContent(dto.getContent());

        return comment;
    }

    public static CommentResponseDto toResponseDto(Comment comment) {

        CommentResponseDto dto = new CommentResponseDto();

        dto.setCommentId(comment.getCommentId());
        dto.setContent(comment.getContent());
        dto.setTargetId(comment.getTargetId());
        dto.setTargetType(comment.getTargetType());
        dto.setUserId(comment.getUserId());

        return dto;
    }
}