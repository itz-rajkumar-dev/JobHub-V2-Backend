package com.company.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyLifeResponseDto {

    private Long lifeId;
    private Long companyId;
    private String title;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
}
