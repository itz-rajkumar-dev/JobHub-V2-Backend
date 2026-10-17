package com.engagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"follower_id", "company_id"}
                ),
                @UniqueConstraint(
                        columnNames = {"follower_id", "following_user_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // Jo user follow kar raha hai
    private Long followerId;

    // Jab user kisi dusre user ko follow kare
    private Long followingUserId;

    // Jab user kisi company ko follow kare
    private Long companyId;
}