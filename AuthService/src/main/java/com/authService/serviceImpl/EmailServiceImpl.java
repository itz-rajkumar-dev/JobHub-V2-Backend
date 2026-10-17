package com.authService.serviceImpl;

import com.authService.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${jobhub.mail.from}")
    private String fromAddress;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendOptEmail(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromAddress);
        message.setTo(email);
        message.setSubject("JobHub - Email Verification OTP");

        message.setText(
                "Hello,\n\n" +
                        "Your JobHub OTP is: " + otp + "\n\n" +
                        "This OTP is valid for 3 minutes.\n" +
                        "Do not share this OTP with anyone.\n\n" +
                        "Regards,\nJobHub Team"
        );

        mailSender.send(message);
    }
}
