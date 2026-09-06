package com.sentinel.core.controller;

import com.sentinel.core.dto.AuthRequest;
import com.sentinel.core.dto.AuthResponse;
import com.sentinel.core.entity.Role;
import com.sentinel.core.entity.User;
import com.sentinel.core.repository.UserRepository;
import com.sentinel.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User account is disabled");
        }

        String token = jwtUtil.generateToken(user.getUsername());
        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), roles));
    }
}