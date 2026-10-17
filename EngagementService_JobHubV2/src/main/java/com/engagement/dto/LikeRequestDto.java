package com.engagement.dto;

import com.engagement.entity.TargetType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDto {

    @NotNull
    private Long targetId;

    @NotNull
    private TargetType targetType;
}