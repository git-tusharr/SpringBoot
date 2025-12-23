package com.example.controllers;

import org.springframework.web.bind.annotation.*;

import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.dto.GoogleLoginRequest;
import com.example.services.AuthService;
import com.example.services.GoogleAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthController {

	
	
    private final GoogleAuthService googleAuthService;
    private final AuthService authService;
    
//      @PostMapping("/register")
//     public String register(@RequestBody RegisterRequest req){
//    	return authService.register(req); }

    // 🔹 Login with email / username / phone + password
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    // 🔹 Login with Google
    @PostMapping("/google")
    public String googleLogin(@RequestBody GoogleLoginRequest req) {
        return googleAuthService.loginWithGoogle(req.getIdToken());
    }
}
