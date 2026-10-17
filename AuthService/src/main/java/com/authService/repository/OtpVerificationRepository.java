package com.authService.repository;

import com.authService.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpVerificationRepository  extends JpaRepository<OtpVerification,Long> {

    Optional<OtpVerification> findTopByEmailOrderByOtpIdDesc(String email);

    Optional<OtpVerification> findByRegistrationTokenAndEmailAndVerifiedTrueAndUsedFalse(String registrationToken, String email);
}