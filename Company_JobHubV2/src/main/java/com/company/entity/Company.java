package com.company.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @Column(nullable = false, unique = true)
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String about;
    private String website;

    private String logoUrl;

    private String coverUrl;
    private String industry;

    @Builder.Default
    @Column(nullable = false)
    private Long profileViews = 0L;

    private String location;

    @Enumerated(EnumType.STRING)
    private CompanySize companySize;

    private Integer foundedYear;

    @Column(unique = true)
    private String registrationNumber;

    @Builder.Default
    private Boolean isVerified = false;

    private String contactEmail;

    private String contactPhone;

    private String linkedinUrl;

    // Auth/User Service ke userId ko store karega
    @Column(nullable = false)
    private Long ownerId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}