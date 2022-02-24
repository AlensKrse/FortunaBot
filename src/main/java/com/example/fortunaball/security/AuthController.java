package com.example.fortunaball.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @PostMapping
    public ResponseEntity<String> basicAuth(final HttpServletRequest request) {
        final String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        final String[] token = new String(Base64.getDecoder().decode(authToken)).split(":");
        final String requestUsername = token[0];
        final String requestPassword = token[1];
        if (requestUsername.equals(this.username) && requestPassword.equals(this.password)) {
            return ResponseEntity.ok(requestUsername);
        } else {
            return ResponseEntity.ok(null);
        }
    }

}
