package com.lucianoreul.marketplace.controller;

import com.lucianoreul.marketplace.dto.LoginRequest;
import com.lucianoreul.marketplace.dto.LoginResponse;
import com.lucianoreul.marketplace.exceptions.InvalidCredentialsException;
import com.lucianoreul.marketplace.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            LoginResponse response = authService.authenticate(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "data", response
            ));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Invalid credentials",
                    "message", "Incorrect email or password"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal server error",
                    "message", "An error occurred while processing login"
            ));
        }
    }

    @GetMapping("/protected")
    public ResponseEntity<?> validateToken() {
        return ResponseEntity.ok(Map.of(
                "message", "Token is valid",
                "status", "success"
        ));
    }
}
