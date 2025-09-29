package com.lucianoreul.marketplace.service;

import com.lucianoreul.marketplace.dto.LoginResponse;
import com.lucianoreul.marketplace.dto.UserDTO;
import com.lucianoreul.marketplace.entity.User;
import com.lucianoreul.marketplace.exceptions.InvalidCredentialsException;
import com.lucianoreul.marketplace.repository.UserRepository;
import com.lucianoreul.marketplace.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUser(userDTO);

        return response;
    }

}
