package com.engagement.dto;

import com.engagement.entity.TargetType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    @NonNull
    private Long targetId;

    @NonNull
    private TargetType targetType;

    @NotBlank
    private String content;
}
