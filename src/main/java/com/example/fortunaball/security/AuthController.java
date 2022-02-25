package com.example.fortunaball.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @RequestMapping("/auth")
    public ResponseEntity<Boolean> basicAuth(final HttpServletRequest request) {
        final String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        final String[] token = new String(Base64.getDecoder().decode(authToken)).split(":");
        final String requestUsername = token[0];
        final String requestPassword = token[1];
        final String remoteHost = request.getRemoteHost();
        final String remoteAddr = request.getRemoteAddr();
        if (requestUsername.equals(this.username) && requestPassword.equals(this.password)) {
            LOGGER.info("Success request to the bot application! Request username: {}, request password {}. With host {}, address {}. Time: {}.", username, password, remoteHost, remoteAddr, new Date());
            return ResponseEntity.ok(Boolean.TRUE);
        } else {
            LOGGER.info("Failed request to the bot application! Request username: {}, request password {}. With  host {}, address {}. Time: {}", username, password, remoteHost, remoteAddr, new Date());
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }

}
