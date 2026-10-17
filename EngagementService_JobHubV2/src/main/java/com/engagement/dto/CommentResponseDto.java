package com.engagement.dto;

import com.engagement.entity.TargetType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {


    private Long commentId;
    private Long userId;
    private Long targetId;
    private TargetType targetType;
    private String content;
}
