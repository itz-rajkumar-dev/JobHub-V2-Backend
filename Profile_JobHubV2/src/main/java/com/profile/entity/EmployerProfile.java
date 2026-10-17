package com.profile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employer_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerProfile {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employerProfileId;

    @Column(nullable = false, unique = true)
    private Long userId;

    private String fullName;

    private String profileImage;

    private  String coverImage;

    private String headline;

    @Column(columnDefinition = "TEXT")
    private String about;

    private String phone;

    private String location;

    private String linkedinUrl;


}
