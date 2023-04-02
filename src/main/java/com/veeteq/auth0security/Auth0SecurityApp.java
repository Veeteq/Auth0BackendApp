package com.veeteq.auth0security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.veeteq.auth0security.model")
public class Auth0SecurityApp {
    
    public static void main(String[] args) {
        SpringApplication.run(Auth0SecurityApp.class, args);
    }

}
