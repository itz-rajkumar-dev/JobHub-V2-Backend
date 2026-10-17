package com.company.dto;

import com.company.entity.CompanySize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyRequestDto {

    @NotBlank(message = "Company name is required")
    private String companyName;

    private String description;

    private String about;
    @NotBlank(message = "Website is required")
    private String website;

    private MultipartFile logo;
    private MultipartFile cover;
    @NotBlank(message = "Industry is required")
    private String industry;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Company size is required")
    private CompanySize companySize;

    @NotNull(message = "Founded year is required")
    @Positive(message = "Founded year must be positive")
    private Integer foundedYear;

    private String registrationNumber;

    @Email(message = "Invalid contact email")
    private String contactEmail;

    @Pattern(
            regexp = "[6-9]\\d{9}",
            message = "Invalid contact phone number"
    )
    private String contactPhone;

    private String linkedinUrl;
}