package com.authentication_service.dto;

import lombok.Data;

@Data
public class AuthenticateUserRequest {
    private String email;
    private String password;
    // Constructor
    public AuthenticateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthenticateUserRequest() {

    }
    // Empty constructor

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
