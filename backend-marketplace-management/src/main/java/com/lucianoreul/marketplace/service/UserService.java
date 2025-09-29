package com.lucianoreul.marketplace.service;

import com.lucianoreul.marketplace.dto.RegisterResponse;
import com.lucianoreul.marketplace.dto.UserDTO;
import com.lucianoreul.marketplace.entity.User;
import com.lucianoreul.marketplace.exceptions.EmailAlreadyExistsException;
import com.lucianoreul.marketplace.exceptions.InvalidEmailFormatException;
import com.lucianoreul.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(String email, String password) {
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new InvalidEmailFormatException();
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }

        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        RegisterResponse response = new RegisterResponse();
        response.setMessage("User successfully registered");
        response.setUser(userDTO);

        return response;
    }

}
