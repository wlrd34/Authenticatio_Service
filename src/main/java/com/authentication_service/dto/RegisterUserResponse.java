package com.authentication_service.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserResponse {
    @Id
    @GeneratedValue
    private UUID id;
    private String firtsName;
    private String lastName;
    private String password;
    private String username;
    private String email;
}