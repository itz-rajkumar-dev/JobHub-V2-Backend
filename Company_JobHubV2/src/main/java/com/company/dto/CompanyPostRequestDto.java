package com.company.dto;

import com.company.entity.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyPostRequestDto {

    @NotNull
    private Long companyId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private PostType postType;
    private Long jobId;
    private MultipartFile image;
}