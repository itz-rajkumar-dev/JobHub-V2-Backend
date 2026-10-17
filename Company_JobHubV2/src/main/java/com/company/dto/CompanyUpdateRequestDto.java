package com.company.dto;

import com.company.entity.CompanySize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyUpdateRequestDto {

    private String companyName;

    private String description;

    private String about;

    private String website;

    private MultipartFile logo;
    private MultipartFile cover;
    private String industry;

    private String location;

    private CompanySize companySize;

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