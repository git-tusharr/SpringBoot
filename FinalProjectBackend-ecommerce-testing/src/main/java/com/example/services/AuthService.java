package com.example.services;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.models.User;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // REGISTER
    public String register(RegisterRequest req) {

        if (userRepository.existsByEmail(req.getEmail())) {
            return "Email already registered";
        }

        User user = User.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .username(req.getUsername())
                .phone(req.getPhone())
                .build();

        userRepository.save(user);

        return "Registered Successfully";
    }

    // LOGIN
    public String login(LoginRequest req) {

        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
