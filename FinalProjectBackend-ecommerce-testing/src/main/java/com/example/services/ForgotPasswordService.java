package com.example.services;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.ForgotPasswordRequest;
import com.example.dto.ResetPasswordRequest;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.example.util.EmailServicereg;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EmailServicereg emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        String redisKey = "RESET_PASSWORD_TOKEN:" + token;
        redisTemplate.opsForValue()
                .set(redisKey, user.getEmail(), 15, TimeUnit.MINUTES);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;


        emailService.sendEmail(
                user.getEmail(),
                "Reset Your Password",
                "Click the link below to reset your password:\n" + resetLink +
                "\n\nThis link is valid for 15 minutes.",
                null
        );

    }

    public void validateToken(String token) {

        String redisKey = "RESET_PASSWORD_TOKEN:" + token;

        if (!Boolean.TRUE.equals(redisTemplate.hasKey(redisKey))) {
            throw new RuntimeException("Invalid or expired reset link");
        }
    }

    public void resetPassword(ResetPasswordRequest request) {

        String redisKey = "RESET_PASSWORD_TOKEN:" + request.getToken();
        String email = redisTemplate.opsForValue().get(redisKey);

        if (email == null) {
            throw new RuntimeException("Invalid or expired reset link");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        redisTemplate.delete(redisKey);
    }
}
