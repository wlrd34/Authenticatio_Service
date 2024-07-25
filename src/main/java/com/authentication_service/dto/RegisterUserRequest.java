package com.authentication_service.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;


   public RegisterUserRequest(String username, String password) {
    }

}