
package com.authService.controller;

import com.authService.service.OtpVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/otp")
public class OtpVerificationController {

    private final OtpVerificationService otpVerificationService;

    public OtpVerificationController(
            OtpVerificationService otpVerificationService) {
        this.otpVerificationService = otpVerificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendOtp(
            @RequestParam String email) {

        otpVerificationService.generateAndSendOtp(email);

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        String registrationToken =
                otpVerificationService.verifyOtp(email, otp);

        return ResponseEntity.ok(Map.of(
                "message", "OTP verified successfully",
                "registrationToken", registrationToken
        ));
    }
}
