package com.engagement.dto;

import com.engagement.entity.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {


    private Long likeId;

    private Long userId;
    private boolean liked;
    private String message;
    private Long targetId;
    private TargetType targetType;
}
