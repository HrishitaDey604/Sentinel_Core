package com.sentinel.core.controller;

import com.sentinel.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        // Replace with real user lookup + password check against the database
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        }
        throw new RuntimeException("Invalid credentials");
    }
}