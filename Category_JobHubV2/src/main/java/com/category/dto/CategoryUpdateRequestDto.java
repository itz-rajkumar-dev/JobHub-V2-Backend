package com.category.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateRequestDto {

    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String slug;

    private String description;

    private MultipartFile icon;

    private Boolean isActive;

    @Min(value = 0)
    private Integer displayOrder;
}