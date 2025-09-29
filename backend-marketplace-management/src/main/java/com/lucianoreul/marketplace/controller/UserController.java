package com.lucianoreul.marketplace.controller;

import com.lucianoreul.marketplace.dto.RegisterRequest;
import com.lucianoreul.marketplace.dto.RegisterResponse;
import com.lucianoreul.marketplace.exceptions.EmailAlreadyExistsException;
import com.lucianoreul.marketplace.exceptions.InvalidEmailFormatException;
import com.lucianoreul.marketplace.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            RegisterResponse response = userService.registerUser(request.getEmail(), request.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                    "error", "Conflict",
                    "message", "This email is already registered"
            ));
        } catch (InvalidEmailFormatException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid email",
                    "message", "Please provide a valid email address"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Internal server error",
                    "message", "An error occurred while processing registration"
            ));
        }
    }
}
