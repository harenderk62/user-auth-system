package com.harender.user_auth_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication){
        String email = authentication.getName();
        // Fetch and return user details based on email
        // For simplicity, returning email
        return ResponseEntity.ok("User email: " + email);

    }
}
