package com.lucianoreul.marketplace.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private String message;
    private UserDTO user;
}
