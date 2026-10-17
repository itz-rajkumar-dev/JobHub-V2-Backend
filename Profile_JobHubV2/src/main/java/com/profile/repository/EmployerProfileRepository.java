package com.profile.repository;

import com.profile.entity.EmployerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployerProfileRepository extends JpaRepository<EmployerProfile ,Long> {

    Optional<EmployerProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
