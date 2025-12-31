package com.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ForgotPasswordRequest;
import com.example.dto.ResetPasswordRequest;
import com.example.services.ForgotPasswordService;

@RestController
@RequestMapping("/auth")
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        forgotPasswordService.forgotPassword(request);

        return ResponseEntity.ok(
                "If the email exists, a reset link has been sent."
        );
    }

    @GetMapping("/reset-password/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token) {

        forgotPasswordService.validateToken(token); // ✅ FIX

        return ResponseEntity.ok("Token is valid");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {

        forgotPasswordService.resetPassword(request);

        return ResponseEntity.ok("Password reset successful");
    }
}
