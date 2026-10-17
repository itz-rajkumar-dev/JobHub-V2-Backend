package com.profile.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerUpdateRequestDto {

    @Size(max = 255, message = "Full name cannot exceed 255 characters")
    private String fullName;

    @Size(max = 255, message = "Headline cannot exceed 255 characters")
    private String headline;

    @Size(max = 2000, message = "About cannot exceed 2000 characters")
    private String about;

    @Size(max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Phone number must be exactly 10 digits and start with 6-9")
    private String phone;
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @Size(max = 500, message = "LinkedIn URL cannot exceed 500 characters")
    private String linkedinUrl;
}