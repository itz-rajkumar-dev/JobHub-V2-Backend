package com.profile.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerResponseDto {


    private Long employerProfileId;
    private Long userId;
    private String fullName;
    private String profileImage;
    private String headline;
    private String about;
    private String phone;
    private String location;
    private String linkedinUrl;
    private String coverImage;
}
