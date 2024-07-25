package com.authentication_service.service;
import com.authentication_service.model.Empoloyees;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);
    @Autowired
    private RestTemplate restTemplate;

    private final String USER_SERVICE_URL = "http://localhost:8082/api/employees";

    public Empoloyees getUserByEmailAndPassword(String email, String password) {
        try {
            String url = UriComponentsBuilder.fromUriString(USER_SERVICE_URL)
                    .path("/search/findByEmailAndPassword")
                    .queryParam("email", email)
                    .queryParam("password", password)
                    .toUriString();
            System.out.println(url);
            return restTemplate.getForObject(url, Empoloyees.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("User not found with email: {}", email);
            throw new RuntimeException("User not found with email: " + email);
        } catch (Exception e) {
            logger.error("Error retrieving user by email: {}", email, e);
            throw new RuntimeException("Error retrieving user by email: " + email);
        }
    }

    public Empoloyees getUserByEmail(String email) {
        try {
            String url = UriComponentsBuilder.fromUriString(USER_SERVICE_URL)
                    .path("/search/findByEmail")
                    .queryParam("email", email)
                    .toUriString();
            System.out.println(url);
            return restTemplate.getForObject(url, Empoloyees.class);
        } catch (HttpClientErrorException.NotFound e) {
            logger.error("User not found with email: {}", email);
            throw new RuntimeException("User not found with email: " + email);
        } catch (Exception e) {
            logger.error("Error retrieving user by email: {}", email, e);
            throw new RuntimeException("Error retrieving user by email: " + email);
        }
    }

    public Empoloyees createUser(Empoloyees user) {
        try {
            return restTemplate.postForObject(USER_SERVICE_URL, user, Empoloyees.class);
        } catch (HttpServerErrorException.InternalServerError e) {
            logger.error("Internal server error while creating user: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Internal server error while creating user");
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }
}