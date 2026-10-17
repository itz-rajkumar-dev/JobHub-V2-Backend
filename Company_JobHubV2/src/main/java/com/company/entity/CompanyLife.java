package com.company.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "company_life")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLife {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lifeId;

    @Column(nullable = false)
    private Long companyId;

    @Column(length = 150)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500, nullable = false)
    private String imageUrl;

    private Integer displayOrder;
}
