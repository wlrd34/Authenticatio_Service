package com.authentication_service.controller;
import com.authentication_service.dto.*;
import com.authentication_service.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public RegisterUserResponse registerUser(@RequestBody RegisterUserRequest request) {
        return authenticationService.registerUser(request);
    }


    @PostMapping("/authenticate")
    public AuthenticateUserResponse authenticateUser(@RequestBody AuthenticateUserRequest request) {
        System.out.println(request.toString());
        return authenticationService.authenticateUser(request);
    }


    @PostMapping("/validate")
    public ValidateTokenResponse validateToken(@RequestBody ValidateTokenRequest request) {
        return authenticationService.validateToken(request);
    }

    @PostMapping("/getUUID")
    public ResponseEntity<UUID> getUserIdFromToken(@RequestBody ValidateTokenRequest request) {
       return  authenticationService.getUserIdFromToken(request);
    }

}