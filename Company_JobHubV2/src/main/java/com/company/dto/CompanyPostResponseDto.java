package com.company.dto;

import com.company.entity.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPostResponseDto {

    private Long postId;
    private Long companyId;
    private String title;
    private String content;
    private PostType postType;
    private Long jobId;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
