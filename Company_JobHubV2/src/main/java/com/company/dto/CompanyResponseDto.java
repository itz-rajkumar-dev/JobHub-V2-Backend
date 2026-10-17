package com.company.dto;

import com.company.entity.CompanySize;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponseDto {

    private Long companyId;

    private String companyName;

    private String description;
    private String about;
    private String website;

    private String logoUrl;
    private String coverUrl;
    private Long profileViews;
    private String industry;

    private String location;

    private CompanySize companySize;

    private Integer foundedYear;

    private String registrationNumber;

    private Boolean isVerified;

    private String contactEmail;

    private String contactPhone;

    private String linkedinUrl;

    private Long ownerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}