
package com.authService.serviceImpl;

import com.authService.entity.OtpVerification;
import com.authService.exception.InvalidOtpException;
import com.authService.exception.OtpAlreadyVerified;
import com.authService.exception.OtpExpiredException;
import com.authService.exception.OtpNotFoundException;
import com.authService.repository.OtpVerificationRepository;
import com.authService.service.EmailService;
import com.authService.service.OtpVerificationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OtpVerificationServiceImpl implements OtpVerificationService {

    private final OtpVerificationRepository otpVerificationRepository;
    private final EmailService emailService;
    private final SecureRandom secureRandom = new SecureRandom();

    public OtpVerificationServiceImpl(
            OtpVerificationRepository otpVerificationRepository,
            EmailService emailService) {
        this.otpVerificationRepository = otpVerificationRepository;
        this.emailService = emailService;
    }

    @Override
    public void generateAndSendOtp(String email) {

        String normalizedEmail = email.trim().toLowerCase();

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        OtpVerification verification = new OtpVerification();
        verification.setEmail(normalizedEmail);
        verification.setOtp(otp);
        verification.setExpriesAt(LocalDateTime.now().plusMinutes(3));
        verification.setVerified(false);
        verification.setUsed(false);
        verification.setRegistrationToken(null);

        // Save OTP, then send email.
        otpVerificationRepository.save(verification);
        emailService.sendOptEmail(normalizedEmail, otp);
    }

    @Override
    @Transactional
    public String verifyOtp(String email, String otp) {

        String normalizedEmail = email.trim().toLowerCase();

        OtpVerification verification =
                otpVerificationRepository
                        .findTopByEmailOrderByOtpIdDesc(normalizedEmail)
                        .orElseThrow(() ->
                                new OtpNotFoundException("OTP not found"));

        if (verification.isVerified() || verification.isUsed()) {
            throw new OtpAlreadyVerified("OTP already verified or used");
        }

        if (LocalDateTime.now().isAfter(verification.getExpriesAt())) {
            throw new OtpExpiredException("OTP expired");
        }

        if (!verification.getOtp().equals(otp)) {
            throw new InvalidOtpException("Invalid OTP");
        }

        String registrationToken = UUID.randomUUID().toString();

        verification.setVerified(true);
        verification.setRegistrationToken(registrationToken);

        otpVerificationRepository.save(verification);

        return registrationToken;
    }


    @Transactional(readOnly = true)
    public void validateRegistrationToken(
            String email,
            String registrationToken) {

        OtpVerification verification =
                otpVerificationRepository
                        .findByRegistrationTokenAndEmailAndVerifiedTrueAndUsedFalse(
                                registrationToken,
                                email.trim().toLowerCase()
                        )
                        .orElseThrow(() ->
                                new InvalidOtpException(
                                        "Email verification required"
                                ));

        if (LocalDateTime.now().isAfter(verification.getExpriesAt())) {
            throw new OtpExpiredException(
                    "Registration verification expired. Verify email again."
            );
        }
    }

    @Transactional
    public void consumeRegistrationToken(
            String email,
            String registrationToken) {

        OtpVerification verification =
                otpVerificationRepository
                        .findByRegistrationTokenAndEmailAndVerifiedTrueAndUsedFalse(
                                registrationToken,
                                email.trim().toLowerCase()
                        )
                        .orElseThrow(() -> new InvalidOtpException(
                                        "Invalid or already used registration token"));

        if (LocalDateTime.now().isAfter(verification.getExpriesAt())) {
            throw new OtpExpiredException("Registration verification expired");
        }

        verification.setUsed(true);
        otpVerificationRepository.save(verification);
    }
}
