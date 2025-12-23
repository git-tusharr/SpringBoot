package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.models.User;
import com.example.services.RegistrationService;

@RestController
@RequestMapping("/auth")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        registrationService.registerUser(user);
        return "User registered. Verify email & phone OTPs.";
    }

    @PostMapping("/verify-email-otp")
    public String verifyEmail(@RequestParam String email, @RequestParam String otp) {
        boolean success = registrationService.verifyEmailOTP(email, otp);
        return success ? "Email verified" : "Invalid or expired OTP";
    }

    @PostMapping("/verify-phone-otp")
    public String verifyPhone(@RequestParam String phone, @RequestParam String otp) {
        boolean success = registrationService.verifyPhoneOTP(phone, otp);
        return success ? "Phone verified" : "Invalid or expired OTP";
    }
}
