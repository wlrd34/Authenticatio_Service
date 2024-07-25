package com.authentication_service.service;
import com.authentication_service.dto.*;
import com.authentication_service.exc.InvalidCredentialsException;
import com.authentication_service.exc.UserNotFoundException;
import com.authentication_service.model.Empoloyees;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private UserServiceClient userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public AuthenticateUserResponse authenticateUser(AuthenticateUserRequest request) {
    // Cerca l'utente per email
    Empoloyees user = userService.getUserByEmailAndPassword(request.getEmail(), request.getPassword());

    // Se l'utente non viene trovato, genera un'eccezione UserNotFoundException
    if (user == null) {
        throw new UserNotFoundException("User not found");

    }

    // Verifica se la password fornita corrisponde alla password dell'utente

        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }


    // Se le credenziali sono valide, genera il token di autenticazione
    String generatedToken = generateToken(user.getEmail());
    AuthenticateUserResponse response = new AuthenticateUserResponse();
    response.setToken(generatedToken);

    // Invia una notifica di login (commentato nel tuo esempio)
    //sendLoginNotification(request.getEmail());

    return response;
}

    public String generateToken(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        Map<String, Object> claims = new HashMap<>();
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * 10 ; // 10 ore
        Date now = new Date(nowMillis);
        Date expiryDate = new Date(expMillis);

        // Replace SECRET_KEY with a securely stored secret key
        byte[] secretKeyBytes = "your_secure_secret_key".getBytes();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        try {
            // Replace SECRET_KEY with a securely stored secret key
            byte[] secretKeyBytes = "your_secure_secret_key".getBytes();

            Jwts.parser().setSigningKey(secretKeyBytes).parseClaimsJws(request.getToken());
            ValidateTokenResponse response = new ValidateTokenResponse();
            response.setValid(true);
            return response;
        } catch (Exception e) {
            ValidateTokenResponse response = new ValidateTokenResponse();
            response.setValid(false);
            return response;
        }
    }

    public RegisterUserResponse registerUser(RegisterUserRequest request) {
        // Verifica se la password è nulla o vuota
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Empoloyees user = new Empoloyees();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        Empoloyees createdUser = userService.createUser(user);

        RegisterUserResponse response = new RegisterUserResponse();
        response.setId(createdUser.getId());
        response.setFirtsName(createdUser.getFirstName());
        response.setLastName(createdUser.getLastName());
        response.setUsername(createdUser.getUsername());
        response.setPassword(createdUser.getPassword());
        response.setEmail(createdUser.getEmail());

        return response;
    }

    public ResponseEntity<UUID> getUserIdFromToken(ValidateTokenRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        ValidateTokenResponse response = new ValidateTokenResponse();
        byte[] secretKeyBytes = "your_secure_secret_key".getBytes();
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKeyBytes)
                    .parseClaimsJws(request.getToken());
            Claims claims = claimsJws.getBody();
            String email = claims.getSubject(); // Extract email from the token


            return ResponseEntity.ok(userService.getUserByEmail(email).getId());
        } catch (Exception e) {
            return null;
        }
    }
 }
