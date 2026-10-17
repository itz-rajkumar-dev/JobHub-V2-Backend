package com.authService.service;

public interface OtpVerificationService {

    void generateAndSendOtp(String email);

    String verifyOtp(String email,String otp);

    void validateRegistrationToken(String email, String registrationToken);
    void consumeRegistrationToken(String email, String registrationToken);
}
